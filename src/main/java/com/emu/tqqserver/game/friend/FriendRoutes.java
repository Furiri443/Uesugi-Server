package com.emu.tqqserver.game.friend;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.FriendList;
import com.emu.tqqserver.proto.pkg_proto.RankListUser;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.proto.pkg_proto.FriendEpBoost;
import com.emu.tqqserver.game.friend.FriendService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class FriendRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final FriendService friendService = new FriendService();

    /** POST /friend/list */
    @Route("/friend/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/list");
        UserEntity me = requireUser(req);
        long userId = me.getUserId();

        List<UserEntity> friends = friendService.getFriends(userId);
        FriendList.Builder responseBuilder = buildFriendList(me, friends).toBuilder();

        // Add GM Bot 999 ONLY to the friend list, not request/approval lists
        ListUser botUser = ListUser.newBuilder()
            .setUid(999)
            .setLevel(999)
            .setName("Server Console")
            .setComment("Type GM commands here!")
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setPlayerTitleId(0)
            .setPlayerTitleTargetId(0)
            .setLeader(com.emu.tqqserver.proto.pkg_pmaster.Card.newBuilder().setId(1823880390).setMemberId(1).setCostumeId(10651).build())
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();
        
        RankListUser botRlu = RankListUser.newBuilder()
            .setUser(botUser)
            .setRank(1)
            .setScore(0)
            .setEpBoostRate(10)
            .build();
        responseBuilder.addListUsers(botRlu);

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, responseBuilder.build().toByteArray());
    }

    /** POST /friend/requestlist */
    @Route("/friend/requestlist")
    public void requestList(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/requestlist");
        UserEntity me = requireUser(req);
        long userId = me.getUserId();

        List<UserEntity> requestsSent = friendService.getRequestsSent(userId);
        FriendList response = buildFriendList(me, requestsSent);

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /friend/approvallist */
    @Route("/friend/approvallist")
    public void approvalList(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/approvallist");
        UserEntity me = requireUser(req);
        long userId = me.getUserId();

        List<UserEntity> requestsReceived = friendService.getRequestsReceived(userId);
        FriendList response = buildFriendList(me, requestsReceived);

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }

    /** POST /friend/request */
    @Route("/friend/request")
    public void request(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("friend/request");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = 0;
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            if (body.has("target_id")) targetId = body.get("target_id").asInt();
            else if (body.has("uid")) targetId = body.get("uid").asInt();
            
            if (targetId > 0 && targetId != userId) {
                friendService.sendRequest(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /friend/cancelrequest */
    @Route("/friend/cancelrequest")
    public void cancelRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/cancelrequest");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = 0;
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            if (body.has("target_id")) targetId = body.get("target_id").asInt();
            else if (body.has("uid")) targetId = body.get("uid").asInt();
            
            if (targetId > 0) {
                friendService.cancelRequest(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /friend/approval */
    @Route("/friend/approval")
    public void approval(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("friend/approval");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = 0;
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            if (body.has("target_id")) targetId = body.get("target_id").asInt();
            else if (body.has("uid")) targetId = body.get("uid").asInt();
            
            if (targetId > 0) {
                friendService.approveRequest(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /friend/reject */
    @Route("/friend/reject")
    public void reject(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/reject");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = 0;
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            if (body.has("target_id")) targetId = body.get("target_id").asInt();
            else if (body.has("uid")) targetId = body.get("uid").asInt();
            
            if (targetId > 0) {
                friendService.rejectRequest(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /friend/insulate */
    @Route("/friend/insulate")
    public void insulate(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/insulate");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = 0;
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            if (body.has("target_id")) targetId = body.get("target_id").asInt();
            else if (body.has("uid")) targetId = body.get("uid").asInt();
            
            if (targetId > 0) {
                friendService.deleteFriend(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /friend/greetingall */
    @Route("/friend/greetingall")
    public void greetingAll(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/greetingall");
        sendNocontent(ctx, req);
    }

    /** POST /friend/profile/picture/timeline */
    @Route("/friend/profile/picture/timeline")
    public void profilePictureTimeline(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("friend/profile/picture/timeline");
        sendNocontent(ctx, req);
    }

    private FriendList buildFriendList(UserEntity me, List<UserEntity> members) {
        List<RankListUser> rankList = new ArrayList<>();
        for (UserEntity member : members) {
            ListUser lu = buildListUser(member);
            RankListUser rlu = RankListUser.newBuilder()
                .setUser(lu)
                .setRank(1)
                .setScore(0)
                .setEpBoostRate(10)
                .build();
            rankList.add(rlu);
        }
        FriendEpBoost boost = FriendEpBoost.newBuilder()
            .setFriendCount(members.size())
            .setEpBoost(0)
            .build();

        return FriendList.newBuilder()
            .setStoredData(storedDataService.build(me))
            .addAllListUsers(rankList)
            .setFriendCount(members.size())
            .setBlockCount(0)
            .setFriendBoost(boost)
            .build();
    }

    private ListUser buildListUser(UserEntity member) {
        com.emu.tqqserver.proto.pkg_pmaster.Card leader = com.emu.tqqserver.proto.pkg_pmaster.Card.newBuilder()
            .setId(1823880390)
            .setMemberId((int) member.getUserId())
            .setCostumeId(10651)
            .build();

        return ListUser.newBuilder()
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
