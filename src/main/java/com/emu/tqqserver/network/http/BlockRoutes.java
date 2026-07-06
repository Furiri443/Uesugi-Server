package com.emu.tqqserver.network.http;

import com.emu.tqqserver.annotation.Route;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.ListUsers;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.service.FriendService;
import com.emu.tqqserver.service.StoredDataService;
import com.emu.tqqserver.model.entity.UserEntity;

import java.util.List;

public class BlockRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final FriendService friendService = new FriendService();

    /** POST /blocks */
    @Route("/blocks")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("blocks/list");
        UserEntity me = requireUser(req);
        long userId = me.getUserId();

        List<UserEntity> blockedUsers = friendService.getBlockedUsers(userId);
        ListUsers.Builder builder = ListUsers.newBuilder()
            .setStoredData(storedDataService.build(me));

        for (UserEntity target : blockedUsers) {
            builder.addList(buildListUser(target));
        }

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    /** POST /block/add */
    @Route("/block/add")
    public void add(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("block/add");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.service.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = readIntField(req, 1);
            if (targetId > 0 && targetId != userId) {
                friendService.blockUser(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /block/del */
    @Route("/block/del")
    public void delete(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("block/del");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.service.SessionService.getUserId(session);
        if (userId != null) {
            int targetId = readIntField(req, 1);
            if (targetId > 0) {
                friendService.unblockUser(userId, targetId);
            }
        }
        sendNocontent(ctx, req);
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
            .setComment("ブロック中")
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setPlayerTitleId(member.getPlayerTitleId())
            .setPlayerTitleTargetId(member.getPlayerTitleTargetId())
            .setLeader(leader)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();
    }
}
