package com.emu.tqqserver.game.book;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.proto.pkg_pmaster.CollectionRewardList;
import com.emu.tqqserver.proto.pkg_pmisc.BookManage;
import com.emu.tqqserver.proto.pkg_pmisc.BookManageDetail;
import com.emu.tqqserver.proto.pkg_pmisc.BookManageDetailList;
import com.emu.tqqserver.proto.pkg_proto.BookManageInfo;
import com.emu.tqqserver.proto.pkg_proto.StoredData;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookRoutes extends BaseRoute {
    private static final Logger log = LoggerFactory.getLogger(BookRoutes.class);
    private final UserService userService = new UserService();
    private final StoredDataService storedDataService = new StoredDataService();
    @Route("/book/buyback")
    public void buyBack(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("book/buyback"); sendNocontent(ctx, req); }
    @Route("/book/exchange")
    public void exchange(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("book/exchange"); sendNocontent(ctx, req); }
    @Route("/book/manageinfo")
    public void manageInfo(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("book/manageinfo");
        String session = getSession(req);
        Long userIdBoxed = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userIdBoxed == null) {
            sendNocontent(ctx, req);
            return;
        }
        long userId = userIdBoxed;

        UserEntity user = userService.findById(userId);
        if (user == null) {
            sendNocontent(ctx, req);
            return;
        }

        StoredData storedData = storedDataService.build(user);

        BookManage bookManage = BookManage.newBuilder()
                .setUid((int) userId)
                .setMemberId1(1)
                .setMemberId2(2)
                .setMemberId3(3)
                .setCostumeId1(1000)
                .setCostumeId2(1010)
                .setGroupId1(1)
                .build();

        BookManageDetail detail1 = BookManageDetail.newBuilder()
                .setUid((int) userId)
                .setType(3)
                .setTargetId(1)
                .setCardId1(10001)
                .setCardId2(10002)
                .build();

        BookManageDetailList detailList = BookManageDetailList.newBuilder()
                .addList(detail1)
                .build();
                
        CollectionRewardList rewardList = CollectionRewardList.newBuilder().build();

        BookManageInfo response = BookManageInfo.newBuilder()
                .setStoredData(storedData)
                .setBookManage(bookManage)
                .setBookManageDetailList(detailList)
                .setCollectionRewardList(rewardList)
                .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
    @Route("/book/collectionreward")
    public void collectionReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("book/collectionreward"); sendNocontent(ctx, req); }
    @Route("/book/receivegrowthreward")
    public void receiveGrowthReward(ChannelHandlerContext ctx, FullHttpRequest req) { log.info("book/receivegrowthreward"); sendNocontent(ctx, req); }
    @Route("/book/updatebookdetail")
    public void updateBookDetail(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("book/updatebookdetail"); sendNocontent(ctx, req); }
    @Route("/book/updatefavorite")
    public void updateFavorite(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("book/updatefavorite"); sendNocontent(ctx, req); }
    @Route("/book/updatepictureidx")
    public void updatePictureIdx(ChannelHandlerContext ctx, FullHttpRequest req) { log.debug("book/updatepictureidx"); sendNocontent(ctx, req); }
}
