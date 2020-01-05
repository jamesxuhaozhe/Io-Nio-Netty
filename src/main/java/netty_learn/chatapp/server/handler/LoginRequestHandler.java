package netty_learn.chatapp.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty_learn.chatapp.protocol.request.LoginRequestPacket;
import netty_learn.chatapp.protocol.response.LoginResponsePacket;
import netty_learn.chatapp.util.LoginUtil;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + " server receives message from client");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());
        if (isValid(msg)) {
            loginResponsePacket.setSuccess(true);
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println(new Date() + " valid login information and login successfully");
        } else {
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + " validation failed");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private static boolean isValid(LoginRequestPacket msg) {
        return true;
    }
}
