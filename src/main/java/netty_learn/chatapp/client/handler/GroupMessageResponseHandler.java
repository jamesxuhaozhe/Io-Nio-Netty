package netty_learn.chatapp.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty_learn.chatapp.protocol.response.GroupMessageResponsePacket;
import netty_learn.chatapp.session.Session;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket responsePacket) {
        String fromGroupId = responsePacket.getFromGroupId();
        Session fromUser = responsePacket.getFromUser();
        System.out.println("收到群[" + fromGroupId + "]中[" + fromUser + "]发来的消息：" + responsePacket.getMessage());
    }
}