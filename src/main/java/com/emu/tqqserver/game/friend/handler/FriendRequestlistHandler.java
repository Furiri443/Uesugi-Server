package com.emu.tqqserver.game.friend.handler;

import com.emu.tqqserver.game.friend.FriendService;
import com.emu.tqqserver.proto.pkg_proto.FriendList;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.RankListUser;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.ArrayList;
import java.util.List;

@Route("/friend/requestlist")
public class FriendRequestlistHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final FriendService friendService = new FriendService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("friend/requestlist");
        UserEntity me = requireUser(req);
        long userId = me.getUserId();

        List<UserEntity> requestsSent = friendService.getRequestsSent(userId);
        FriendList response = buildFriendList(me, requestsSent);

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    
    }

    private com.emu.tqqserver.proto.pkg_proto.FriendList buildFriendList(com.emu.tqqserver.game.user.UserEntity me, java.util.List<com.emu.tqqserver.game.user.UserEntity> members) {
        java.util.List<com.emu.tqqserver.proto.pkg_proto.RankListUser> rankList = new java.util.ArrayList<>();
        for (com.emu.tqqserver.game.user.UserEntity member : members) {
            com.emu.tqqserver.proto.pkg_proto.ListUser lu = buildListUser(member);
            com.emu.tqqserver.proto.pkg_proto.RankListUser rlu = com.emu.tqqserver.proto.pkg_proto.RankListUser.newBuilder()
                .setUser(lu)
                .setRank(1)
                .setScore(0)
                .setEpBoostRate(10)
                .build();
            rankList.add(rlu);
        }
        com.emu.tqqserver.proto.pkg_proto.FriendEpBoost boost = com.emu.tqqserver.proto.pkg_proto.FriendEpBoost.newBuilder()
            .setFriendCount(members.size())
            .setEpBoost(0)
            .build();

        return com.emu.tqqserver.proto.pkg_proto.FriendList.newBuilder()
            .setStoredData(storedDataService.build(me))
            .addAllListUsers(rankList)
            .setFriendCount(members.size())
            .setBlockCount(0)
            .setFriendBoost(boost)
            .build();
    }

    private com.emu.tqqserver.proto.pkg_proto.ListUser buildListUser(com.emu.tqqserver.game.user.UserEntity member) {
        com.emu.tqqserver.proto.pkg_puser.Card leader = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
            .setId(1823880390)
            .setUid((int) member.getUserId())
            .setCardId(10651).setCardPropertyId(106511).setCardPropertyId2(106511).setLevel(50).setActiveSkillLevel(5).setPassiveSkillLevel1(5).setPassiveSkillLevel2(5).setPassiveSkillLevel3(5).build();

        return com.emu.tqqserver.proto.pkg_proto.ListUser.newBuilder()
            .setUid((int) member.getUserId())
            .setLevel(member.getRank())
            .setName(member.getNickname())
            .setComment("ごとぱずサーバー")
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setPlayerTitleId(member.getPlayerTitleId())
            .setPlayerTitleTargetId(member.getPlayerTitleTargetId())
            .setLeader(leader)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();
    }

}
