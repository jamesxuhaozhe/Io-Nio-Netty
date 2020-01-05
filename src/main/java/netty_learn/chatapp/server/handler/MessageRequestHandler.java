package netty_learn.chatapp.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty_learn.chatapp.protocol.request.MessageRequestPacket;
import netty_learn.chatapp.protocol.response.MessageResponsePacket;
import netty_learn.chatapp.session.Session;
import netty_learn.chatapp.util.SessionUtil;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // get the session information
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUsername());
        messageResponsePacket.setMessage(msg.getMessage());

        // get the receiver's channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        // if the receiver's channel is ok and the is user has already login in
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            ctx.channel().writeAndFlush(messageResponsePacket);
        } else {
            System.out.println(new Date() + " user id: " + msg.getToUserId() + " is offline");
        }
    }
}
