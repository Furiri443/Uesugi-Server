package com.emu.tqqserver.game.shop;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.IAPProductList;
import com.emu.tqqserver.proto.pkg_proto.PaymentAccount;
import com.emu.tqqserver.proto.pkg_proto.ShopResult;
import com.emu.tqqserver.game.shop.IAPProductService;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.game.shop.ShopService;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.user.StoredDataService;
import java.util.Map;
import java.util.List;

public class ShopRoutes extends BaseRoute {
    @Route("/shop/products")
    public void products(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("shop/products");
        IAPProductList list = IAPProductList.newBuilder().build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, list.toByteArray());
    }

    /** POST /shop/account */
    @Route("/shop/account")
    public void account(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("shop/account");
        PaymentAccount account = PaymentAccount.newBuilder()
            .setBirthday(true)
            .setMonthlyPaymentLimit(50000)
            .setMonthlyPaymentAmount(0)
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, account.toByteArray());
    }

    /** POST /shop/buyrecoverap */
    @Route("/shop/buyrecoverap")
    public void buyRecoverAp(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("shop/buyrecoverap");
        sendNocontent(ctx, req);
    }

    private void processBuy(ChannelHandlerContext ctx, FullHttpRequest req, boolean isMultiple) {
        UserEntity me = requireUser(req);
        if (me == null) {
            sendNocontent(ctx, req);
            return;
        }

        byte[] body = getBody(req);
        String bodyStr = new String(body, java.nio.charset.StandardCharsets.UTF_8);
        log.info("SHOP BUY REQUEST: {}", bodyStr);
        
        java.util.Map<Integer, Integer> buyMap = new java.util.HashMap<>();
        
        io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder("?" + bodyStr);
        List<String> sids = decoder.parameters().get("sids");
        if (sids != null) {
            for (String sidStr : sids) {
                try {
                    int sid = Integer.parseInt(sidStr);
                    buyMap.put(sid, buyMap.getOrDefault(sid, 0) + 1);
                } catch (Exception e) {}
            }
        }
        
        // Also check if there's an 'id' and 'quantity' parameter, just in case
        List<String> idParam = decoder.parameters().get("id");
        List<String> qtyParam = decoder.parameters().get("quantity");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int sid = Integer.parseInt(idParam.get(0));
                int q = 1;
                if (qtyParam != null && !qtyParam.isEmpty()) {
                    q = Integer.parseInt(qtyParam.get(0));
                }
                buyMap.put(sid, buyMap.getOrDefault(sid, 0) + q);
            } catch (Exception e) {}
        }
        
        if (buyMap.isEmpty() && body.length > 0) {
            // Fallback to old protobuf logic if it wasn't a form string
            try {
                int[] fields = readTwoIntFields(req);
                if (fields[0] > 0) {
                    int qty = fields[1] > 0 ? fields[1] : 1;
                    if (!isMultiple) qty = 1;
                    buyMap.put(fields[0], qty);
                }
            } catch (Exception e) {}
        }
        
        if (buyMap.isEmpty()) {
            sendNocontent(ctx, req);
            return;
        }

        ShopService shopService = new ShopService();
        UserService userService = new UserService();
        
        int firstShopId = 0;
        int firstQuantity = 0;
        
        for (Map.Entry<Integer, Integer> entry : buyMap.entrySet()) {
            int shopId = entry.getKey();
            int quantity = entry.getValue();
            if (firstShopId == 0) {
                firstShopId = shopId;
                firstQuantity = quantity;
            }
            
            Map<String, Object> shopItem = shopService.getShopItem(shopId);
            if (shopItem != null) {
                int payType = (Integer) shopItem.getOrDefault("pay", 0);
                int price = (Integer) shopItem.getOrDefault("price", 0);
                int totalCost = price * quantity;
                
                boolean paid = false;
                
                if (payType == 5) {
                    int payItemId = (Integer) shopItem.getOrDefault("pay_item_id", 0);
                    if (payItemId > 0) {
                        paid = userService.deductItem(me.getUserId(), payItemId, totalCost);
                        if (paid) {
                            log.info("User {} bought shopId {} x{} using item {} x{}", me.getUserId(), shopId, quantity, payItemId, totalCost);
                        } else {
                            log.warn("User {} failed to buy shopId {} due to insufficient item {} (cost: {})", me.getUserId(), shopId, payItemId, totalCost);
                        }
                    }
                } else {
                    int coinCost = 0;
                    int jewelCost = 0;
                    if (payType == 2) {
                        coinCost = totalCost; // Assume 2 is Silver Coin
                        paid = userService.deductCurrency(me.getUserId(), coinCost, 0);
                        if (paid) {
                            log.info("User {} bought shopId {} x{} cost: coin={}", me.getUserId(), shopId, quantity, coinCost);
                        } else {
                            log.warn("User {} failed to buy shopId {} due to insufficient funds (cost: {},0)", me.getUserId(), shopId, coinCost);
                        }
                    } else {
                        jewelCost = totalCost; // Assume 1, 3, 4, 8 are Jewel
                        int deductFreeJewel = 0;
                        int deductPaidJewel = 0;
                        
                        if (me.getJewel() >= jewelCost) {
                            deductFreeJewel = jewelCost;
                        } else {
                            deductFreeJewel = me.getJewel();
                            deductPaidJewel = jewelCost - me.getJewel();
                        }
                        
                        paid = userService.deductJewels(me.getUserId(), deductFreeJewel, deductPaidJewel);
                        if (paid) {
                            log.info("User {} bought shopId {} x{} cost: free_jewel={}, paid_jewel={}", me.getUserId(), shopId, quantity, deductFreeJewel, deductPaidJewel);
                        } else {
                            log.warn("User {} failed to buy shopId {} due to insufficient funds (cost: free={}, paid={})", me.getUserId(), shopId, deductFreeJewel, deductPaidJewel);
                        }
                    }
                }

                if (paid) {
                    if (shopItem.containsKey("reward_id")) {
                        int rewardId = (Integer) shopItem.get("reward_id");
                        List<Map<String, Object>> rewards = shopService.getRewards(rewardId);
                        for (Map<String, Object> r : rewards) {
                            int rQty = (Integer) r.getOrDefault("quantity", 1) * quantity;
                            int rId = (Integer) r.getOrDefault("reward_id", 0);
                            userService.addItem(me.getUserId(), rId, rQty);
                            log.info("Granted user {} item {} x{}", me.getUserId(), rId, rQty);
                        }
                    }
                    
                    me = userService.findById(me.getUserId());
                }
            }
        }

        StoredDataService storedDataService = new StoredDataService();
        ShopResult.Builder builder = ShopResult.newBuilder()
                .setStoredData(storedDataService.build(me));
                
        // Add purchased IDs to the result
        for (Map.Entry<Integer, Integer> entry : buyMap.entrySet()) {
            builder.addId(entry.getKey());
        }
        if (firstQuantity > 0) {
            builder.setQuantity(firstQuantity);
        }
        
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    /** POST /shop/multiplebuy */
    @Route("/shop/multiplebuy")
    public void multipleBuy(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("shop/multiplebuy");
        processBuy(ctx, req, true);
    }

    /** POST /shop/updatelineupauto */
    @Route("/shop/updatelineupauto")
    public void updateLineupAuto(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("shop/updatelineupauto");
        UserEntity me = requireUser(req);
        if (me == null) {
            sendNocontent(ctx, req);
            return;
        }
        UserService userService = new UserService();
        me = userService.findById(me.getUserId());
        com.emu.tqqserver.proto.pkg_proto.ShopResult.Builder builder = com.emu.tqqserver.proto.pkg_proto.ShopResult.newBuilder();
        builder.setStoredData(new StoredDataService().build(me));
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }
    @Route("/shop/buy")
    public void buy(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("shop/buy"); processBuy(ctx, req, false); }
    @Route("/shop/buybilling")
    public void buyBilling(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("shop/buybilling"); sendNocontent(ctx, req); }
    @Route("/shop/buyupdatelineup")
    public void buyUpdateLineup(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("shop/buyupdatelineup"); sendNocontent(ctx, req); }
    @Route("/shop/setbirthday")
    public void setBirthday(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("shop/setbirthday"); sendNocontent(ctx, req); }
    @Route("/shop/verify")
    public void verify(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("shop/verify"); sendNocontent(ctx, req); }
    @Route("/shop_dearpoint/buy")
    public void dearpointBuy(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("shop_dearpoint/buy"); sendNocontent(ctx, req); }
    @Route("/shop_dearpoint/unlock")
    public void dearpointUnlock(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("shop_dearpoint/unlock"); sendNocontent(ctx, req); }
}
