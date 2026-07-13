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

@Route("/user/profile/picture")
public class UserProfilePictureHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("user/profile/picture");
        UserEntity me = requireUser(req);
        long myUserId = me.getUserId();

        io.netty.handler.codec.http.QueryStringDecoder decoder = new io.netty.handler.codec.http.QueryStringDecoder(req.uri());
        List<String> uidsParam = decoder.parameters().get("uids");

        List<Long> uids = new ArrayList<>();
        if (uidsParam != null && !uidsParam.isEmpty()) {
            String[] split = uidsParam.get(0).split(",");
            for (String s : split) {
                try {
                    uids.add(Long.parseLong(s.trim()));
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        }

        if (uids.isEmpty()) {
            uids.add(myUserId);
        }

        ProfilePictureForTimelineList.Builder responseBuilder = ProfilePictureForTimelineList.newBuilder()
            .setStoredData(storedDataService.build(me));

        for (long uid : uids) {
            UserEntity member = userService.findById(uid);
            if (member == null) continue;
            ProfilePictureForTimeline pic = ProfilePictureForTimeline.newBuilder()
                .setUid((int) member.getUserId())
                .setLevel(member.getRank())
                .setName(member.getNickname())
                .setData(ByteString.EMPTY)
                .setLikeCount(0)
                .setNewFlg(false)
                .setProfilePhotoUpdatedTs(System.currentTimeMillis() / 1000)
                .build();
            responseBuilder.addProfilePictureForTimeline(pic);
        }

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, responseBuilder.build().toByteArray());
    
    }
}
