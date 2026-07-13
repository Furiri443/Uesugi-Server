package com.emu.tqqserver.game.book.handler;

import com.emu.tqqserver.annotation.Route;
import com.emu.tqqserver.network.http.BaseRoute;
import com.emu.tqqserver.network.http.HttpApiHandler;
import com.emu.tqqserver.game.user.UserEntity;
import com.emu.tqqserver.proto.pkg_pmaster.CollectionRewardList;
import com.emu.tqqserver.proto.pkg_proto.StoredData;
import com.emu.tqqserver.game.user.StoredDataService;
import com.emu.tqqserver.game.user.UserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route("/book/updatebookdetail")
public class BookUpdatebookdetailHandler extends BaseRoute {

    private static final Logger log = LoggerFactory.getLogger(BookUpdatebookdetailHandler.class);
    private final UserService userService = new UserService();
    private final StoredDataService storedDataService = new StoredDataService();

    public void handle(ChannelHandlerContext ctx, FullHttpRequest req) {
 log.debug("book/updatebookdetail"); sendNocontent(ctx, req); 
    }
}
