package com.emu.tqqserver.game.user.handler;

import com.emu.tqqserver.game.user.UserEntity;
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

@Route("/user/profile")
public class UserProfileHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("user/profile");
        String session = getSession(req);
        Long myUserId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (myUserId == null) myUserId = 1L;

        int targetUid = 0;
        com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
        if (body.has("target_id")) targetUid = body.get("target_id").asInt();
        else if (body.has("uid")) targetUid = body.get("uid").asInt();
        
        long uid = targetUid > 0 ? targetUid : myUserId;

        UserEntity user = userService.findById(uid);
        if (user == null) {
            user = userService.findOrCreate(uid, "Player " + uid);
        }

        com.emu.tqqserver.proto.pkg_puser.Unit unit = com.emu.tqqserver.proto.pkg_puser.Unit.newBuilder()
            .setUid((int) user.getUserId())
            .setIdx(1)
            .setUnitName("Default Team")
            .setMemberId1(1)
            .setMemberId2(2)
            .setMemberId3(3)
            .setMemberId4(4)
            .setMemberId5(5)
            .build();

        Profile profile = Profile.newBuilder()
            .setUid((int) user.getUserId())
            .setLevel(user.getRank())
            .setNickname(user.getNickname())
                        .setLastname("??")
                        .setFirstname("???")
            .setPlayerTitleId(user.getPlayerTitleId() > 0 ? user.getPlayerTitleId() : 50301003)
            .setPlayerTitleTargetId(user.getPlayerTitleTargetId())
            .setUnit(unit)
            .setComment("よろしくお願いします！")
            .setProfileBackgroundId(user.getProfileBackgroundId() > 0 ? user.getProfileBackgroundId() : 10001)
            .build();

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, profile.toByteArray());
    
    }
}
