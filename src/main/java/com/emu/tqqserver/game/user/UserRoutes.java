package com.emu.tqqserver.game.user;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.ListUsers;

import com.emu.tqqserver.proto.pkg_proto.Profile;
import com.emu.tqqserver.proto.pkg_proto.ProfilePictureForTimeline;
import com.emu.tqqserver.proto.pkg_proto.ProfilePictureForTimelineList;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import com.google.protobuf.ByteString;

import java.util.ArrayList;
import java.util.List;

public class UserRoutes extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();

    /** POST /user/load */
    @Route("/user/load")
    public void load(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/load");
        UserEntity me = requireUser(req);
        long myUserId = me.getUserId();

        // The old bin approach is removed


        com.emu.tqqserver.proto.pkg_proto.Nocontent.Builder builder = com.emu.tqqserver.proto.pkg_proto.Nocontent.newBuilder()
            .setStoredData(storedDataService.build(me));

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    /** POST /user/list */
    @Route("/user/list")
    public void list(ChannelHandlerContext ctx, FullHttpRequest req) {
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

    /** POST /user/profile */
    @Route("/user/profile")
    public void profile(ChannelHandlerContext ctx, FullHttpRequest req) {
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

    /** POST /user/search */
    @Route("/user/search")
    public void search(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/search");
        UserEntity me = requireUser(req);
        long myUserId = me.getUserId();

        int targetUid = 0;
        com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
        if (body.has("target_id")) targetUid = body.get("target_id").asInt();
        else if (body.has("uid")) targetUid = body.get("uid").asInt();
        ListUsers.Builder builder = ListUsers.newBuilder()
            .setStoredData(storedDataService.build(me));

        if (targetUid > 0) {
            UserEntity target = userService.findById(targetUid);
            if (target != null) {
                builder.addList(buildListUser(target));
            }
        }

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    /** POST /user/changename */
    @Route("/user/changename")
    public void changeName(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/changename");
        sendNocontent(ctx, req);
    }

    /** POST /user/changenickname */
    @Route("/user/changenickname")
    public void changeNickname(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("user/changenickname");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            String nickname = "";
            if (body.has("nickname")) nickname = body.get("nickname").asText();
            else if (body.has("name")) nickname = body.get("name").asText();
            else if (body.size() > 0) nickname = body.elements().next().asText();
            
            if (!nickname.isEmpty()) {
                userService.updateNickname(userId, nickname);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /user/changecomment */
    @Route("/user/changecomment")
    public void changeComment(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/changecomment");
        sendNocontent(ctx, req);
    }

    /** POST /user/changefavorite */
    @Route("/user/changefavorite")
    public void changeFavorite(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/changefavorite");
        sendNocontent(ctx, req);
    }

    /** POST /user/changebackground */
    @Route("/user/changebackground")
    public void changeBackground(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("user/changebackground");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            int bgId = 0;
            if (body.has("bg_id")) bgId = body.get("bg_id").asInt();
            else if (body.has("background_id")) bgId = body.get("background_id").asInt();
            else if (body.has("id")) bgId = body.get("id").asInt();
            else if (body.size() > 0) bgId = body.elements().next().asInt();
            
            if (bgId > 0) {
                userService.updateBackground(userId, bgId);
                com.emu.tqqserver.game.home.HomeService homeService = new com.emu.tqqserver.game.home.HomeService();
                homeService.updateHomeBackground(userId, bgId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /user/oshimen */
    @Route("/user/oshimen")
    public void oshimen(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/oshimen");
        sendNocontent(ctx, req);
    }

    /** POST /user/setbirthday */
    @Route("/user/setbirthday")
    public void setBirthday(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/setbirthday");
        sendNocontent(ctx, req);
    }

    /** POST /user/setplayertitle */
    @Route("/user/setplayertitle")
    public void setPlayerTitle(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("user/setplayertitle");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            int titleId = body.has("title_id") ? body.get("title_id").asInt() : 0;
            int targetId = body.has("target_id") ? body.get("target_id").asInt() : 0;
            userService.updatePlayerTitle(userId, titleId, targetId);
        }
        sendNocontent(ctx, req);
    }

    /** POST /user/setprofile */
    @Route("/user/setprofile")
    public void setProfile(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("user/setprofile");
        UserEntity me = requireUser(req);
        if (me != null) {
            try {
                com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
                if (body.has("nickname")) {
                    userService.updateNickname(me.getUserId(), body.get("nickname").asText());
                }
                if (body.has("comment")) {
                    // Ignore comment for now or add updateComment
                }
            } catch (Exception e) {
                log.error("Failed to parse user profile update", e);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /user/setprofilebackground */
    @Route("/user/setprofilebackground")
    public void setProfileBackground(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("user/setprofilebackground");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            com.fasterxml.jackson.databind.JsonNode body = getJsonBody(req);
            int bgId = 0;
            if (body.has("bg_id")) bgId = body.get("bg_id").asInt();
            else if (body.has("background_id")) bgId = body.get("background_id").asInt();
            else if (body.has("id")) bgId = body.get("id").asInt();
            else if (body.size() > 0) bgId = body.elements().next().asInt();
            
            if (bgId > 0) {
                userService.updateBackground(userId, bgId);
            }
        }
        sendNocontent(ctx, req);
    }

    /** POST /user/setprofilecard */
    @Route("/user/setprofilecard")
    public void setProfileCard(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/setprofilecard");
        sendNocontent(ctx, req);
    }

    /** POST /user/recommand */
    @Route("/user/recommand")
    public void recommend(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/recommend");
        UserEntity me = requireUser(req);
        long myUserId = me.getUserId();

        ListUsers.Builder builder = ListUsers.newBuilder()
            .setStoredData(storedDataService.build(me));

        List<UserEntity> allUsers = userService.getAllUsers();
        allUsers.removeIf(u -> u.getUserId() == myUserId);
        
        for (UserEntity member : allUsers) {
            builder.addList(buildListUser(member));
        }

        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, builder.build().toByteArray());
    }

    /** POST /user/reset */
    @Route("/user/reset")
    public void reset(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.warn("user/reset — stub");
        sendNocontent(ctx, req);
    }

    /** POST /user/tutorialfinish */
    @Route("/user/tutorialfinish")
    public void tutorialFinish(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("user/tutorialfinish");
        String session = getSession(req);
        Long userId = com.emu.tqqserver.game.user.SessionService.getUserId(session);
        if (userId != null) {
            userService.updateTutorialStep(userId, 140);
        }
        sendNocontent(ctx, req);
    }

    /** POST /user/bgmdownload */
    @Route("/user/bgmdownload")
    public void bgmDownload(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/bgmdownload");
        sendNocontent(ctx, req);
    }

    /** POST /user/titleinfo */
    @Route("/user/titleinfo")
    public void titleInfo(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/titleinfo");
        sendNocontent(ctx, req);
    }

    /** POST /user/title_progress */
    @Route("/user/title_progress")
    public void titleProgress(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/title_progress");
        sendNocontent(ctx, req);
    }

    /** POST /user/profile/like */
    @Route("/user/profile/like")
    public void profileLike(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/profile/like");
        sendNocontent(ctx, req);
    }

    /** POST /user/profile/picture */
    @Route("/user/profile/picture")
    public void profilePicture(ChannelHandlerContext ctx, FullHttpRequest req) {
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

    /** POST /user/changeeventplayingpolicy */
    @Route("/user/changeeventplayingpolicy")
    public void changeEventPlayingPolicy(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.debug("user/changeeventplayingpolicy");
        sendNocontent(ctx, req);
    }

    private ListUser buildListUser(UserEntity member) {
        com.emu.tqqserver.proto.pkg_puser.Card leader = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
            .setId(1823880390)
            .setUid((int) member.getUserId())
            .setCardId(10651)
            .build();

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
