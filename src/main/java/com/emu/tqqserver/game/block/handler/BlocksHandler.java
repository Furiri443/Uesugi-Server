package com.emu.tqqserver.game.block.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.proto.pkg_proto.ListUsers;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import com.emu.tqqserver.game.friend.FriendService;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserEntity;
import java.util.List;

@Route("/blocks")
public class BlocksHandler extends BaseRoute {

    private final StoredDataService storedDataService = new StoredDataService();
    private final FriendService friendService = new FriendService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

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
