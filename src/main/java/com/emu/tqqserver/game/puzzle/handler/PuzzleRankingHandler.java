package com.emu.tqqserver.game.puzzle.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.game.user.StoredDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/puzzle/ranking/")
public class PuzzleRankingHandler extends BaseRoute {
    private static final Logger log = LoggerFactory.getLogger(PuzzleRankingHandler.class);
    private final StoredDataService storedDataService = new StoredDataService();

    
    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
        log.info("puzzle/ranking/");
        UserEntity me = requireUser(req);
        
        java.util.List<UserEntity> allUsers = new com.emu.tqqserver.game.user.UserService().getAllUsers();
        java.util.List<UserEntity> friends = new com.emu.tqqserver.game.friend.FriendService().getFriends(me.getUserId());
        java.util.Set<Long> friendIds = new java.util.HashSet<>();
        for (UserEntity f : friends) friendIds.add(f.getUserId());
        friendIds.add(me.getUserId()); // Always include self in friend ranking
        
        com.emu.tqqserver.proto.pkg_proto.Ranking.Builder leaderBuilder = com.emu.tqqserver.proto.pkg_proto.Ranking.newBuilder();
        com.emu.tqqserver.proto.pkg_proto.Ranking.Builder friendBuilder = com.emu.tqqserver.proto.pkg_proto.Ranking.newBuilder();
        
        int rank = 1;
        int friendRank = 1;
        for (UserEntity u : allUsers) {
            com.emu.tqqserver.proto.pkg_proto.RankUser ru = com.emu.tqqserver.proto.pkg_proto.RankUser.newBuilder()
                .setUid((int)u.getUserId())
                .setName(u.getNickname() != null ? u.getNickname() : "Unknown")
                .setRank(rank)
                .setScore(9999999 - rank) // Fake score for display
                .build();
                
            leaderBuilder.addRanker(ru);
            
            if (friendIds.contains(u.getUserId())) {
                com.emu.tqqserver.proto.pkg_proto.RankUser fru = ru.toBuilder().setRank(friendRank).build();
                friendBuilder.addRanker(fru);
                if (u.getUserId() == me.getUserId()) {
                    friendBuilder.setPlayer(fru);
                }
                friendRank++;
            }
            
            if (u.getUserId() == me.getUserId()) {
                leaderBuilder.setPlayer(ru);
            }
            rank++;
        }
        
        leaderBuilder.setTotal(allUsers.size());
        friendBuilder.setTotal(friendIds.size());
        
        com.emu.tqqserver.proto.pkg_proto.PuzzleRanking response = com.emu.tqqserver.proto.pkg_proto.PuzzleRanking.newBuilder()
            .setStoredData(storedDataService.build(me))
            .setLeader(leaderBuilder.build())
            .setFriend(friendBuilder.build())
            .build();
            
        HttpApiHandler.sendProto(ctx, req, HttpResponseStatus.OK, response.toByteArray());
    }
}
