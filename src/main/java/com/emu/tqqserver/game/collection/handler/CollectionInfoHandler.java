package com.emu.tqqserver.game.collection.handler;

import com.emu.tqqserver.game.collection.CollectionDao;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;

@Route("/collection/info")
public class CollectionInfoHandler extends BaseRoute {

    private final com.emu.tqqserver.game.user.StoredDataService storedDataService = new com.emu.tqqserver.game.user.StoredDataService();
    private final CollectionDao collectionDao = new CollectionDao();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.info("collection/info");
        UserEntity me = requireUser(req);
        
        com.emu.tqqserver.proto.pkg_proto.Collection.Builder colBuilder = com.emu.tqqserver.proto.pkg_proto.Collection.newBuilder()
            .setStoredData(storedDataService.build(me))
            .setCollection(collectionDao.getCollection(me.getUserId()));
            
        java.util.List<com.emu.tqqserver.proto.pkg_proto.CollectionPage> pages = collectionDao.getCollectionPages(me.getUserId());
        
        if (pages.isEmpty()) {
            // Load default layouts from master data
            java.util.Collection<com.emu.tqqserver.data.resources.CollectionLayoutDef> layouts = com.emu.tqqserver.data.GameData.getCollectionLayoutDataTable().values();
            int idx = 1;
            for (com.emu.tqqserver.data.resources.CollectionLayoutDef layout : layouts) {
                if (layout.getIsDefault() == 1) {
                    pages.add(com.emu.tqqserver.proto.pkg_proto.CollectionPage.newBuilder()
                        .setUid((int)me.getUserId())
                        .setIdx(idx++)
                        .setTitle(layout.getName())
                        .setLayoutId(layout.getId())
                        .setThemeId(1)
                        .setPageSort(idx - 1)
                        .setTransitionId(1)
                        .build());
                }
            }
            collectionDao.saveCollectionPages(me.getUserId(), pages);
        }
        
        com.emu.tqqserver.game.card.CardService cardService = new com.emu.tqqserver.game.card.CardService();
        java.util.Map<Long, com.emu.tqqserver.game.user.CardEntity> cardsMap = cardService.getUserCardsMap(me.getUserId());

        com.emu.tqqserver.game.user.UnitDao unitDao = new com.emu.tqqserver.game.user.UnitDao();
        com.emu.tqqserver.proto.pkg_puser.Unit activeUnit = null;
        for (com.emu.tqqserver.proto.pkg_puser.Unit u : unitDao.getUnits(me.getUserId())) {
            if (u.getIdx() == 1) {
                activeUnit = u;
                break;
            }
        }

        java.util.List<com.emu.tqqserver.proto.pkg_proto.CollectionPage> populatedPages = new java.util.ArrayList<>();
        for (com.emu.tqqserver.proto.pkg_proto.CollectionPage page : pages) {
            com.emu.tqqserver.proto.pkg_proto.CollectionPage.Builder pb = page.toBuilder();
            pb.clearCollectionElements();
            
            if (page.getIdx() == 1 && activeUnit != null) {
                long[] unitCards = { activeUnit.getCardId1(), activeUnit.getCardId2(), activeUnit.getCardId3(), activeUnit.getCardId4(), activeUnit.getCardId5() };
                int[] unitMembers = { activeUnit.getMemberId1(), activeUnit.getMemberId2(), activeUnit.getMemberId3(), activeUnit.getMemberId4(), activeUnit.getMemberId5() };
                for (int i = 0; i < unitCards.length; i++) {
                    if (unitCards[i] > 0) {
                        com.emu.tqqserver.game.user.CardEntity ce = cardsMap.get(unitCards[i]);
                        if (ce != null) {
                            pb.addCollectionElements(com.emu.tqqserver.proto.pkg_proto.CollectionElement.newBuilder()
                                .setIdx(unitMembers[i])
                                .setType(1) // 1 is Card
                                .setId(unitCards[i])
                                .setCard(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                                    .setId(ce.getId())
                                    .setCardId(ce.getCardId())
                                    .setLevel(ce.getLevel())
                                    .setExp(ce.getExp())
                                    .setLimitbreakRank(ce.getLimitbreakRank())
                                    .build())
                                .build());
                        }
                    }
                }
            } else {
                for (com.emu.tqqserver.proto.pkg_proto.CollectionElement el : page.getCollectionElementsList()) {
                    com.emu.tqqserver.proto.pkg_proto.CollectionElement.Builder eb = el.toBuilder();
                    if (el.getType() == 1) { // 1 is Card
                        com.emu.tqqserver.game.user.CardEntity ce = cardsMap.get(el.getId());
                        if (ce != null) {
                            eb.setCard(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                                    .setId(ce.getId())
                                    .setCardId(ce.getCardId())
                                    .setLevel(ce.getLevel())
                                    .setExp(ce.getExp())
                                    .setLimitbreakRank(ce.getLimitbreakRank())
                                    .build());
                        }
                    }
                    pb.addCollectionElements(eb.build());
                }
            }
            populatedPages.add(pb.build());
        }

        colBuilder.addAllPageList(populatedPages);
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, colBuilder.build().toByteArray());
    
    }
}
