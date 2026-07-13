package com.emu.tqqserver.game.chat.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserService;
import com.emu.tqqserver.game.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/chat/friends")
public class ChatFriendsHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(ChatFriendsHandler.class);

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {

        log.debug("chat/friends");
        UserEntity user = requireUser(req);

        com.emu.tqqserver.proto.pkg_proto.ListUser bot = 
            com.emu.tqqserver.proto.pkg_proto.ListUser.newBuilder()
                .setUid(999)
                .setLevel(999)
                .setName("Server Console")
                .setComment("Type GM commands here!")
                .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
                .setPlayerTitleId(0)
                .setPlayerTitleTargetId(0)
                .setLeader(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder().setId(1823880390).setUid(1).setCardId(10651).setCardPropertyId(106511).setCardPropertyId2(106511).setLevel(50).setActiveSkillLevel(5).setPassiveSkillLevel1(5).setPassiveSkillLevel2(5).setPassiveSkillLevel3(5).build())
                .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
                .build();

        com.emu.tqqserver.proto.pkg_proto.ChatFriendList response = 
            com.emu.tqqserver.proto.pkg_proto.ChatFriendList.newBuilder()
                .setStoredData(new com.emu.tqqserver.game.user.StoredDataService().build(user))
                .addListUsers(bot)
                .build();
                
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    
    }
}
