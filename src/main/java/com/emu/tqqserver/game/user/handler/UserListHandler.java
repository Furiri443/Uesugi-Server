package com.emu.tqqserver.game.user.handler;

import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.proto.pkg_proto.ListUsers;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.Profile;
import com.emu.tqqserver.proto.pkg_proto.ProfilePictureForTimeline;
import com.emu.tqqserver.proto.pkg_proto.ProfilePictureForTimelineList;
import com.emu.tqqserver.game.user.StoredDataService;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

@Route("/user/list")
public class UserListHandler extends BaseRoute {
    private final UserService userService = new UserService();


    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("user/list");
        UserEntity me = requireUser(req);
        long myUserId = me.getUserId();

        List<Long> uids = new ArrayList<>();
        com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
        if (body.has("uids") && body.get("uids").isArray()) {
            for (com.fasterxml.jackson.databind.JsonNode n : body.get("uids")) uids.add(n.asLong());
        }

        ListUsers.Builder builder = ListUsers.newBuilder()
            .setStoredData(storedDataService.build(me));

        for (long uid : uids) {
            UserEntity member = userService.findById(uid);
            if (member == null) continue;
            builder.addList(buildListUser(member));
        }

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    
    }

private ListUser buildListUser(UserEntity member) {
        com.emu.tqqserver.proto.pkg_puser.Card leader = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
            .setId(1823880390)
            .setUid((int) member.getUserId())
            .setCardId(10651)
            .setCardPropertyId(106511)
            .setCardPropertyId2(106511).setLevel(50).setActiveSkillLevel(5).setPassiveSkillLevel1(5).setPassiveSkillLevel2(5).setPassiveSkillLevel3(5).build();

        return ListUser.newBuilder()
            .setUid((int) member.getUserId())
            .setLevel(member.getRank())
            .setName(member.getNickname())
            .setComment("ごとぱずサーバー")
            .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
            .setPlayerTitleId(member.getPlayerTitleId() > 0 ? member.getPlayerTitleId() : 50301003)
            .setPlayerTitleTargetId(member.getPlayerTitleTargetId())
            .setLeader(leader)
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build();
    }
}
