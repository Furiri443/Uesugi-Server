package com.emu.tqqserver.network.http;

import com.emu.tqqserver.annotation.Route;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.model.entity.UserEntity;

// ─────────────────────────────────────────────────────────────────────────────
// STUB ROUTE CLASSES — all return empty Nocontent until properly implemented
// Generated from: endpoints.txt (Il2CppDumper analysis)
// ─────────────────────────────────────────────────────────────────────────────



class GachaRoutes extends BaseRoute {
    @Route("/gacha/purchase")
    public void purchase(ChannelHandlerContext ctx, FullHttpRequest req) { 
        log.info("gacha/purchase"); 
        
        String bodyString = "";
        try {
            byte[] body = getBody(req);
            bodyString = new String(body, java.nio.charset.StandardCharsets.UTF_8);
            log.info("RAW: " + bodyString);
        } catch (Exception e) {
            log.error("Failed to decode req", e);
        }

        com.emu.tqqserver.model.entity.UserEntity user = requireUser(req);
        
        java.util.Map<String, String> params = new java.util.HashMap<>();
        if (bodyString != null && !bodyString.isEmpty()) {
            for (String param : bodyString.split("&")) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    params.put(pair[0], pair[1]);
                }
            }
        }

        int gachaId = Integer.parseInt(params.getOrDefault("gacha_id", "0"));
        int mode = Integer.parseInt(params.getOrDefault("mode", "1"));
        int pay = Integer.parseInt(params.getOrDefault("pay", "1"));
        int ticketId = Integer.parseInt(params.getOrDefault("ticket_id", "0"));

        com.emu.tqqserver.service.UserService userService = new com.emu.tqqserver.service.UserService();
        
        // Deduct ticket/currency
        if (ticketId > 0 && pay > 0) {
            boolean success = userService.deductItem(user.getUserId(), ticketId, pay);
            if (!success) {
                log.warn("User does not have enough ticket_id=" + ticketId + " amount=" + pay);
                // Optionally return an error code, but we continue for now
            }
        }

        int drawCount = (mode == 2) ? 10 : 1;
        com.emu.tqqserver.proto.pkg_proto.GachaResult.Builder resultBuilder = com.emu.tqqserver.proto.pkg_proto.GachaResult.newBuilder();
        
        int[] drawnCards = new int[drawCount];
        java.util.Map<Integer, Integer> cardCounts = new java.util.HashMap<>();
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < drawCount; i++) {
            // Pick a random card from valid range (e.g., 10001 - 10020)
            int cardId = 10001 + rand.nextInt(20);
            drawnCards[i] = cardId;
            cardCounts.put(cardId, cardCounts.getOrDefault(cardId, 0) + 1);
        }

        for (java.util.Map.Entry<Integer, Integer> entry : cardCounts.entrySet()) {
            com.emu.tqqserver.proto.pkg_proto.Goods card = com.emu.tqqserver.proto.pkg_proto.Goods.newBuilder()
                .setCategory(2)
                .setTargetId(entry.getKey())
                .setQuantity(entry.getValue())
                .build();
            resultBuilder.addCards(card);
        }

        try {
            new com.emu.tqqserver.db.dao.UserDao().ensureDefaultCards(user.getUserId(), drawnCards);
        } catch (Exception e) {
            log.error("Failed to insert cards", e);
        }
            
        resultBuilder.setStoredData(new com.emu.tqqserver.service.StoredDataService().build(user));
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, resultBuilder.build().toByteArray());
    }
    @Route("/gacha/resetbonus")
    public void resetBonus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/resetbonus"); sendNocontent(ctx, req); }
    @Route("/gacha/reset_box")
    public void resetBox(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/reset_box"); sendNocontent(ctx, req); }
    @Route("/gacha/switch_box")
    public void switchBox(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/switch_box"); sendNocontent(ctx, req); }
    @Route("/gacha/choicebonus")
    public void choiceBonus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/choicebonus"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board")
    public void panelBoard(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board/lot")
    public void panelBoardLot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board/lot"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board/reset")
    public void panelBoardReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board/reset"); sendNocontent(ctx, req); }
    @Route("/gacha/panel_board/select_panel_reward")
    public void panelBoardSelectReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/panel_board/select_panel_reward"); sendNocontent(ctx, req); }
    @Route("/gacha/serialnumber/")
    public void serialNumber(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("gacha/serialnumber/"); sendNocontent(ctx, req); }
}

class CardRoutes extends BaseRoute {
    @Route("/card/enhance")
    public void enhance(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/enhance"); sendNocontent(ctx, req); }
    @Route("/card/enhanceawake")
    public void enhanceAwake(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/enhanceawake"); sendNocontent(ctx, req); }
    @Route("/card/enhancebyitem")
    public void enhanceByItem(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/enhancebyitem"); sendNocontent(ctx, req); }
    @Route("/card/awake")
    public void awake(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/awake"); sendNocontent(ctx, req); }
    @Route("/card/reawake")
    public void reAwake(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/reawake"); sendNocontent(ctx, req); }
    @Route("/card/sell")
    public void sell(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("card/sell"); sendNocontent(ctx, req); }
    @Route("/card/protect")
    public void protect(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/protect"); sendNocontent(ctx, req); }
    @Route("/card/unprotect")
    public void unprotect(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/unprotect"); sendNocontent(ctx, req); }
    @Route("/card/changepicture")
    public void changePicture(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/changepicture"); sendNocontent(ctx, req); }
    @Route("/card/bgm")
    public void bgm(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/bgm"); sendNocontent(ctx, req); }
    @Route("/card/receivegrowthreward")
    public void receiveGrowthReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("card/receivegrowthreward"); sendNocontent(ctx, req); }
}

class CardSpecialRoutes extends BaseRoute {
    @Route("/cardspecial/realize")
    public void realize(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("cardspecial/realize"); sendNocontent(ctx, req); }
    @Route("/cardspecial/addpicture")
    public void addPicture(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/addpicture"); sendNocontent(ctx, req); }
    @Route("/cardspecial/addvoice")
    public void addVoice(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/addvoice"); sendNocontent(ctx, req); }
    @Route("/cardspecial/addslot")
    public void addSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/addslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/restoreslot")
    public void restoreSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/restoreslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/exchangeslot")
    public void exchangeSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/exchangeslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/setslot")
    public void setSlot(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/setslot"); sendNocontent(ctx, req); }
    @Route("/cardspecial/setpassiveskill")
    public void setPassiveSkill(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/setpassiveskill"); sendNocontent(ctx, req); }
    @Route("/cardspecial/relotpassiveskill")
    public void relotPassiveSkill(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("cardspecial/relotpassiveskill"); sendNocontent(ctx, req); }
}

class StoryRoutes extends BaseRoute {
    @Route("/story/read")
    public void read(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("story/read"); sendNocontent(ctx, req); }
    @Route("/story/group/open")
    public void groupOpen(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("story/group/open"); sendNocontent(ctx, req); }
}

class StageRoutes extends BaseRoute {
    @Route("/stage/resetskipcnt")
    public void resetSkipCount(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("stage/resetskipcnt"); sendNocontent(ctx, req); }
}

class ChapterRoutes extends BaseRoute {
    @Route("/chapter/release")
    public void release(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("chapter/release"); sendNocontent(ctx, req); }
}

class ItemRoutes extends BaseRoute {
    @Route("/item/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/list"); sendNocontent(ctx, req); }
    @Route("/item/use")
    public void use(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("item/use"); sendNocontent(ctx, req); }
    @Route("/item/sell")
    public void sell(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("item/sell"); sendNocontent(ctx, req); }
    @Route("/item/exchangepool")
    public void exchangePool(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/exchangepool"); sendNocontent(ctx, req); }
    @Route("/item/password")
    public void password(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/password"); sendNocontent(ctx, req); }
    @Route("/item/likability/use")
    public void likabilityUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("item/likability/use"); sendNocontent(ctx, req); }
}



class UnitRoutes extends BaseRoute {
    @Route("/unit/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/set"); sendNocontent(ctx, req); }
    @Route("/unit/del")
    public void delete(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/del"); sendNocontent(ctx, req); }
    @Route("/unit/editname")
    public void editName(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/editname"); sendNocontent(ctx, req); }
    @Route("/unit/auto")
    public void auto(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unit/auto"); sendNocontent(ctx, req); }
}

class UnitSkillRoutes extends BaseRoute {
    @Route("/unitskill/enhance")
    public void enhance(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("unitskill/enhance"); sendNocontent(ctx, req); }
    @Route("/unitskill/reduce")
    public void reduce(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unitskill/reduce"); sendNocontent(ctx, req); }
    @Route("/unitskill/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("unitskill/set"); sendNocontent(ctx, req); }
}

class EncoreRoutes extends BaseRoute {
    @Route("/encore/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("encore/start"); sendNocontent(ctx, req); }
    @Route("/encore/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("encore/clear"); sendNocontent(ctx, req); }
    @Route("/encore/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/fail"); sendNocontent(ctx, req); }
    @Route("/encore/retry")
    public void retry(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/retry"); sendNocontent(ctx, req); }
    @Route("/encore/continue")
    public void resume(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/continue"); sendNocontent(ctx, req); }
    @Route("/encore/timeover")
    public void timeover(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/timeover"); sendNocontent(ctx, req); }
    @Route("/encore/close")
    public void close(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/close"); sendNocontent(ctx, req); }
    @Route("/encore/helper/list")
    public void helperList(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/helper/list"); sendNocontent(ctx, req); }
    @Route("/encore/helper/use")
    public void helperUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/helper/use"); sendNocontent(ctx, req); }
    @Route("/encore/unitskill/use")
    public void unitSkillUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("encore/unitskill/use"); sendNocontent(ctx, req); }
}

class RetryableRoutes extends BaseRoute {
    @Route("/retryable/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("retryable/start"); sendNocontent(ctx, req); }
    @Route("/retryable/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("retryable/clear"); sendNocontent(ctx, req); }
    @Route("/retryable/continue")
    public void resume(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/continue"); sendNocontent(ctx, req); }
    @Route("/retryable/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/fail"); sendNocontent(ctx, req); }
    @Route("/retryable/helper/use")
    public void helperUse(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/helper/use"); sendNocontent(ctx, req); }
    @Route("/retryable/chapter/reset")
    public void chapterReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("retryable/chapter/reset"); sendNocontent(ctx, req); }
}


class AppointmentRoutes extends BaseRoute {
    @Route("/appointment/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("appointment/set"); sendNocontent(ctx, req); }
    @Route("/appointment/cancel")
    public void cancel(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/cancel"); sendNocontent(ctx, req); }
    @Route("/appointment/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("appointment/clear"); sendNocontent(ctx, req); }
    @Route("/appointment/updatestatus")
    public void updateStatus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/updatestatus"); sendNocontent(ctx, req); }
    @Route("/appointment/surprisereceive")
    public void surpriseReceive(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/surprisereceive"); sendNocontent(ctx, req); }
    @Route("/appointment/surprisereset")
    public void surpriseReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("appointment/surprisereset"); sendNocontent(ctx, req); }
}

class NewsRoutes extends BaseRoute {
    @Route("/news/read")
    public void read(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("news/read"); sendNocontent(ctx, req); }
}

class LogRoutes extends BaseRoute {
    @Route("/log/bootfinish")
    public void bootFinish(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("log/bootfinish"); sendNocontent(ctx, req); }
    @Route("/log/send")
    public void send(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("log/send"); sendNocontent(ctx, req); }
    @Route("/log/storyreadskip")
    public void storyReadSkip(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("log/storyreadskip"); sendNocontent(ctx, req); }
}

class FcmRoutes extends BaseRoute {
    @Route("/fcm/token")
    public void token(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("fcm/token"); sendNocontent(ctx, req); }
}

class OptionsRoutes extends BaseRoute {
    @Route("/options/change")
    public void change(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("options/change"); sendNocontent(ctx, req); }
}

class TutorialRoutes extends BaseRoute {
    @Route("/tutorial/done")
    public void done(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("tutorial/done");
        UserEntity me = requireUser(req);
        if (me != null) {
            int stepId = readIntField(req, 1);
            if (stepId > 0) {
                userService.updateTutorialStep(me.getUserId(), stepId);
                me.setTutorialStep(stepId);
            }
            if (stepId >= 999) {
                com.emu.tqqserver.service.StoredDataService storedDataService = new com.emu.tqqserver.service.StoredDataService();
                com.emu.tqqserver.proto.pkg_proto.TutorialFinishResult response = com.emu.tqqserver.proto.pkg_proto.TutorialFinishResult.newBuilder()
                    .setStoredData(storedDataService.build(me))
                    .build();
                HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
                return;
            }
        }
        sendNocontent(ctx, req);
    }
    @Route("/functutorial/done")
    public void funcDone(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("functutorial/done"); sendNocontent(ctx, req); }
    @Route("/functutorial/enhance")
    public void funcEnhance(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("functutorial/enhance"); sendNocontent(ctx, req); }
    @Route("/functutorial/appointment")
    public void funcAppointment(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("functutorial/appointment"); sendNocontent(ctx, req); }
}

class FeatureRoutes extends BaseRoute {
    @Route("/feature/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/top"); sendNocontent(ctx, req); }
    @Route("/feature/result")
    public void result(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/result"); sendNocontent(ctx, req); }
    @Route("/feature/result/read")
    public void resultRead(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/result/read"); sendNocontent(ctx, req); }
    @Route("/feature/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/ranking"); sendNocontent(ctx, req); }
    @Route("/feature/apply")
    public void apply(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("feature/apply"); sendNocontent(ctx, req); }
    @Route("/feature/agreeterms")
    public void agreeTerms(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/agreeterms"); sendNocontent(ctx, req); }
    @Route("/feature/tutorialdone")
    public void tutorialDone(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/tutorialdone"); sendNocontent(ctx, req); }
    @Route("/feature/appointment")
    public void appointment(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/appointment"); sendNocontent(ctx, req); }
    @Route("/feature/nakayoshi/sendpt")
    public void nakayoshiSendPt(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/nakayoshi/sendpt"); sendNocontent(ctx, req); }
    @Route("/feature/selectionranking")
    public void selectionRanking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/selectionranking"); sendNocontent(ctx, req); }
    @Route("/feature/setselectiontarget")
    public void setSelectionTarget(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/setselectiontarget"); sendNocontent(ctx, req); }
    @Route("/feature/refreshrevival")
    public void refreshRevival(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/refreshrevival"); sendNocontent(ctx, req); }
    @Route("/feature/revivalreset")
    public void revivalReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/revivalreset"); sendNocontent(ctx, req); }
    @Route("/feature/team/create")
    public void teamCreate(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("feature/team/create"); sendNocontent(ctx, req); }
    @Route("/feature/team/join")
    public void teamJoin(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("feature/team/join"); sendNocontent(ctx, req); }
    @Route("/feature/team/request")
    public void teamRequest(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/request"); sendNocontent(ctx, req); }
    @Route("/feature/team/approve")
    public void teamApprove(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/approve"); sendNocontent(ctx, req); }
    @Route("/feature/team/reject")
    public void teamReject(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/reject"); sendNocontent(ctx, req); }
    @Route("/feature/team/kick")
    public void teamKick(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/kick"); sendNocontent(ctx, req); }
    @Route("/feature/team/modify")
    public void teamModify(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/modify"); sendNocontent(ctx, req); }
    @Route("/feature/team/search")
    public void teamSearch(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/search"); sendNocontent(ctx, req); }
    @Route("/feature/team/retire")
    public void teamRetire(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/retire"); sendNocontent(ctx, req); }
    @Route("/feature/team/ranking")
    public void teamRanking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/ranking"); sendNocontent(ctx, req); }
    @Route("/feature/team/auto")
    public void teamAuto(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/auto"); sendNocontent(ctx, req); }
    @Route("/feature/team/charge")
    public void teamCharge(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/charge"); sendNocontent(ctx, req); }
    @Route("/feature/team/boost")
    public void teamBoost(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/boost"); sendNocontent(ctx, req); }
    @Route("/feature/team/boost_auth")
    public void teamBoostAuth(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/team/boost_auth"); sendNocontent(ctx, req); }
    @Route("/feature/treasurehunting/stageinit")
    public void treasureHuntingStageInit(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/treasurehunting/stageinit"); sendNocontent(ctx, req); }
    @Route("/feature/treasurehunting/stagereset")
    public void treasureHuntingStageReset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/treasurehunting/stagereset"); sendNocontent(ctx, req); }
    @Route("/feature/treasurehunting/exchange")
    public void treasureHuntingExchange(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/treasurehunting/exchange"); sendNocontent(ctx, req); }
    @Route("/feature/treasurehunting/exchangeitemlist")
    public void treasureHuntingExchangeItemList(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("feature/treasurehunting/exchangeitemlist"); sendNocontent(ctx, req); }
}

class BondsRoutes extends BaseRoute {
    @Route("/bonds/confessionevent")
    public void confessionEvent(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("bonds/confessionevent"); sendNocontent(ctx, req); }
    @Route("/bonds/step/reward")
    public void stepReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("bonds/step/reward"); sendNocontent(ctx, req); }
    @Route("/bonds/lastseenstep")
    public void lastSeenStep(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("bonds/lastseenstep"); sendNocontent(ctx, req); }
    @Route("/bonds/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("bonds/ranking"); sendNocontent(ctx, req); }
    @Route("/bonds/unlock")
    public void unlock(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("bonds/unlock"); sendNocontent(ctx, req); }
    @Route("/bonds/buy/premiumstep")
    public void buyPremiumStep(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("bonds/buy/premiumstep"); sendNocontent(ctx, req); }
}

class CollectionRoutes extends BaseRoute {
    private final com.emu.tqqserver.service.StoredDataService storedDataService = new com.emu.tqqserver.service.StoredDataService();

    @Route("/collection/info")
    public void info(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("collection/info");
        UserEntity me = requireUser(req);
        com.emu.tqqserver.proto.pkg_proto.Collection response = com.emu.tqqserver.proto.pkg_proto.Collection.newBuilder()
            .setStoredData(storedDataService.build(me))
            .addPageList(com.emu.tqqserver.proto.pkg_proto.CollectionPage.newBuilder()
                .setUid((int)me.getUserId())
                .setIdx(1)
                .setTitle("Default")
                .setLayoutId(1)
                .setThemeId(1)
                .setPageSort(1)
                .build())
            .build();
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
    @Route("/collection/otherinfo")
    public void otherInfo(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/otherinfo"); sendNocontent(ctx, req); }
    @Route("/collection/setting")
    public void setting(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/setting"); sendNocontent(ctx, req); }
    @Route("/collection/pagesetting")
    public void pageSetting(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/pagesetting"); sendNocontent(ctx, req); }
    @Route("/collection/pagetransition")
    public void pageTransition(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("collection/pagetransition"); sendNocontent(ctx, req); }
    @Route("/collection/releasepage")
    public void releasePage(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("collection/releasepage"); sendNocontent(ctx, req); }
}



class LikabilityRoutes extends BaseRoute {
    @Route("/likability/set")
    public void set(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("likability/set"); sendNocontent(ctx, req); }
}

class WorkRoutes extends BaseRoute {
    @Route("/work/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("work/start"); sendNocontent(ctx, req); }
    @Route("/work/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("work/clear"); sendNocontent(ctx, req); }
    @Route("/work/reset")
    public void reset(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("work/reset"); sendNocontent(ctx, req); }
    @Route("/work/update")
    public void update(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("work/update"); sendNocontent(ctx, req); }
    @Route("/work/shorten")
    public void shorten(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("work/shorten"); sendNocontent(ctx, req); }
}


class PhotoboothRoutes extends BaseRoute {
    @Route("/photobooth/hicheese")
    public void hicheese(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("photobooth/hicheese"); sendNocontent(ctx, req); }
    @Route("/photobooth/sns")
    public void sns(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("photobooth/sns"); sendNocontent(ctx, req); }
}

class VrRoutes extends BaseRoute {
    @Route("/vr/open")
    public void open(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("vr/open"); sendNocontent(ctx, req); }
    @Route("/vr/watch")
    public void watch(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("vr/watch"); sendNocontent(ctx, req); }
}

class ChallengeRoutes extends BaseRoute {
    @Route("/challenge/reward")
    public void reward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/reward"); sendNocontent(ctx, req); }
    @Route("/challenge/newrank")
    public void newRank(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/newrank"); sendNocontent(ctx, req); }
    @Route("/challenge/clearbeginner")
    public void clearBeginner(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("challenge/clearbeginner"); sendNocontent(ctx, req); }
    @Route("/challenge/forcecleargroup")
    public void forceClearGroup(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("challenge/forcecleargroup"); sendNocontent(ctx, req); }
}

class AchievementRoutes extends BaseRoute {
    @Route("/achievement/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("achievement/ranking"); sendNocontent(ctx, req); }
}

class AdvertisingRoutes extends BaseRoute {
    @Route("/advertising/view")
    public void view(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("advertising/view"); sendNocontent(ctx, req); }
}

class MemberRoutes extends BaseRoute {
    @Route("/member/resetdearlevelflag")
    public void resetDearLevelFlag(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("member/resetdearlevelflag"); sendNocontent(ctx, req); }
}



class MiniGameRoutes extends BaseRoute {
    @Route("/mini_game/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("mini_game/top"); sendNocontent(ctx, req); }
    @Route("/mini_game/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("mini_game/start"); sendNocontent(ctx, req); }
    @Route("/mini_game/done")
    public void done(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("mini_game/done"); sendNocontent(ctx, req); }
    @Route("/mini_game/exchange")
    public void exchange(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("mini_game/exchange"); sendNocontent(ctx, req); }
    @Route("/mini_game/use")
    public void use(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("mini_game/use"); sendNocontent(ctx, req); }
}

class PracticeExamRoutes extends BaseRoute {
    @Route("/practice_exam/chapter/myranks")
    public void chapterMyRanks(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/chapter/myranks"); sendNocontent(ctx, req); }
    @Route("/practice_exam/myrank")
    public void myRank(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/myrank"); sendNocontent(ctx, req); }
    @Route("/practice_exam/ranking/score/daily")
    public void rankingScoreDaily(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/ranking/score/daily"); sendNocontent(ctx, req); }
    @Route("/practice_exam/ranking/score/total")
    public void rankingScoreTotal(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/ranking/score/total"); sendNocontent(ctx, req); }
    @Route("/practice_exam/report")
    public void report(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("practice_exam/report"); sendNocontent(ctx, req); }
    @Route("/practice_exam/stats")
    public void stats(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("practice_exam/stats"); sendNocontent(ctx, req); }
}

class QuintupletGameRoutes extends BaseRoute {
    @Route("/quintuplet_game/send_result")
    public void sendResult(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("quintuplet_game/send_result"); sendNocontent(ctx, req); }
}

class ReviewRoutes extends BaseRoute {
    @Route("/review/accept")
    public void accept(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("review/accept"); sendNocontent(ctx, req); }
    @Route("/review/decline")
    public void decline(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("review/decline"); sendNocontent(ctx, req); }
    @Route("/review/suspend")
    public void suspend(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("review/suspend"); sendNocontent(ctx, req); }
}

class LotteryRoutes extends BaseRoute {
    @Route("/lottery/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("lottery/list"); sendNocontent(ctx, req); }
    @Route("/lottery/apply")
    public void apply(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("lottery/apply"); sendNocontent(ctx, req); }
    @Route("/lottery/agreeterms")
    public void agreeTerms(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("lottery/agreeterms"); sendNocontent(ctx, req); }
    @Route("/lottery/seenresult")
    public void seenResult(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("lottery/seenresult"); sendNocontent(ctx, req); }
}

class TeamBattleRoutes extends BaseRoute {
    @Route("/teambattle/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/top"); sendNocontent(ctx, req); }
    @Route("/teambattle/start")
    public void start(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/start"); sendNocontent(ctx, req); }
    @Route("/teambattle/clear")
    public void clear(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/clear"); sendNocontent(ctx, req); }
    @Route("/teambattle/fail")
    public void fail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/fail"); sendNocontent(ctx, req); }
    @Route("/teambattle/retire")
    public void retire(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/retire"); sendNocontent(ctx, req); }
    @Route("/teambattle/continue")
    public void resume(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/continue"); sendNocontent(ctx, req); }
    @Route("/teambattle/skip")
    public void skip(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/skip"); sendNocontent(ctx, req); }
    @Route("/teambattle/timeover")
    public void timeover(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/timeover"); sendNocontent(ctx, req); }
    @Route("/teambattle/result")
    public void result(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/result"); sendNocontent(ctx, req); }
    @Route("/teambattle/result/read")
    public void resultRead(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/result/read"); sendNocontent(ctx, req); }
    @Route("/teambattle/ranking")
    public void ranking(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/ranking"); sendNocontent(ctx, req); }
    @Route("/teambattle/apply")
    public void apply(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/apply"); sendNocontent(ctx, req); }
    @Route("/teambattle/join")
    public void join(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("teambattle/join"); sendNocontent(ctx, req); }
    @Route("/teambattle/step/clear")
    public void stepClear(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/step/clear"); sendNocontent(ctx, req); }
    @Route("/teambattle/teamstatus")
    public void teamStatus(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/teamstatus"); sendNocontent(ctx, req); }
    @Route("/teambattle/agreeterms")
    public void agreeTerms(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/agreeterms"); sendNocontent(ctx, req); }
    @Route("/teambattle/tutorialdone")
    public void tutorialDone(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/tutorialdone"); sendNocontent(ctx, req); }
    @Route("/teambattle/setselectiontarget")
    public void setSelectionTarget(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("teambattle/setselectiontarget"); sendNocontent(ctx, req); }
}

class SynthesisRoutes extends BaseRoute {
    @Route("/synthesis/exec")
    public void exec(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("synthesis/exec"); sendNocontent(ctx, req); }
}

class InvitationRoutes extends BaseRoute {
    @Route("/invitation/top")
    public void top(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("invitation/top"); sendNocontent(ctx, req); }
    @Route("/invitation/verify")
    public void verify(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("invitation/verify"); sendNocontent(ctx, req); }
    @Route("/invitation/share")
    public void share(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("invitation/share"); sendNocontent(ctx, req); }
}

class SpecialContentRoutes extends BaseRoute {
    @Route("/special_content/read")
    public void read(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("special_content/read"); sendNocontent(ctx, req); }
}



class HomeRoutes extends BaseRoute {
    @Route("/home/actor/set")
    public void actorSet(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("home/actor/set");
        UserEntity me = requireUser(req);
        if (me != null) {
            try {
                byte[] body = getBody(req);
                com.emu.tqqserver.proto.pkg_puser.HomeActorList list = com.emu.tqqserver.proto.pkg_puser.HomeActorList.parseFrom(body);
                if (list.getListCount() > 0) {
                    com.emu.tqqserver.service.HomeService homeService = new com.emu.tqqserver.service.HomeService();
                    homeService.saveHomeActors(me.getUserId(), list.getListList());
                }
            } catch (Exception e) {
                log.error("Failed to parse HomeActorList", e);
            }
        }
        sendNocontent(ctx, req);
    }
}

class ImageRoutes extends BaseRoute {
    @Route("/image/jpeg")
    public void jpeg(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("image/jpeg"); sendNocontent(ctx, req); }
    @Route("/image/png")
    public void png(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("image/png"); sendNocontent(ctx, req); }
}
