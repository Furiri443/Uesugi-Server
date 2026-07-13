package com.emu.tqqserver.game.puzzle.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.proto.pkg_proto.ListUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/puzzle/helper/list")
public class PuzzleHelperListHandler extends BaseRoute {
    private static final Logger log = LoggerFactory.getLogger(PuzzleHelperListHandler.class);
    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/helper/list");
        UserEntity me = requireUser(req);
        
        java.util.List<UserEntity> friends = new com.emu.tqqserver.game.friend.FriendService().getFriends(me.getUserId());
        
        com.emu.tqqserver.proto.pkg_proto.PuzzleHelper.Builder responseBuilder = com.emu.tqqserver.proto.pkg_proto.PuzzleHelper.newBuilder()
            .setStoredData(storedDataService.build(me))
            .setNextFriendIndex(0)
            .setNextNonFriendIndex(0);

        for (UserEntity u : friends) {

            com.emu.tqqserver.proto.pkg_puser.Card leaderCard = com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                .setId(1823880390) // Default card id for now
                .setUid((int)u.getUserId())
                .setCardId(10651)
                .setCardPropertyId(106511)
                .setCardPropertyId2(106511)
                .setLevel(50)
                .setLevelAwake(50)
                .setLimitbreakRank(4)
                .setAwakePriority(1)
                .setActiveSkillLevel(5)
                .setPassiveSkillLevel1(5)
                .setPassiveSkillLevel2(5)
                .setPassiveSkillLevel3(5)
                .setKirameki(0)
                .setTokimeki(0)
                .build();

            ListUser helper = ListUser.newBuilder()
                .setUid((int)u.getUserId())
                .setLevel(u.getRank() > 0 ? u.getRank() : 1)
                .setName(u.getNickname() != null ? u.getNickname() : "Player")
                .setComment(u.getComment() != null ? u.getComment() : "Hello!")
                .setPlayerTitleId(50701001)
                .setPlayerTitleTargetId(1)
                .setLastLoginTs((int) (System.currentTimeMillis() / 1000))
                .setFriend("none")
                .setLeader(leaderCard)
                .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
                .build();
            responseBuilder.addHelpers(helper);
        }

        // Add dummy helper 1
        responseBuilder.addHelpers(com.emu.tqqserver.proto.pkg_proto.ListUser.newBuilder()
            .setUid(1002)
            .setLevel(100)
            .setName("Nakano Miku")
            .setComment("Matcha soda")
            .setPlayerTitleId(50701001)
            .setPlayerTitleTargetId(1)
            .setLastLoginTs((int)(System.currentTimeMillis() / 1000) - 3600)
            .setFriend("none")
            .setLeader(com.emu.tqqserver.proto.pkg_puser.Card.newBuilder()
                .setId(1002)
                .setUid(1002)
                .setCardId(10472)
                .setCardPropertyId(104721)
                .setCardPropertyId2(104721)
                .setLevel(50)
                .setLevelAwake(50)
                .setLimitbreakRank(4)
                .setAwakePriority(1)
                .setActiveSkillLevel(5)
                .setPassiveSkillLevel1(5)
                .setPassiveSkillLevel2(5)
                .setPassiveSkillLevel3(5)
                .setKirameki(0)
                .setTokimeki(0)
                .build())
            .setGreeting(com.emu.tqqserver.proto.pkg_puser.Greeting.getDefaultInstance())
            .build());
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, responseBuilder.build().toByteArray());
    }
}
