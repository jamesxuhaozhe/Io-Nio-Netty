package netty_learn.chatapp.client.console;

import io.netty.channel.Channel;
import netty_learn.chatapp.protocol.request.GroupMessageRequestPacket;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息给某个某个群组：");

        String toGroupId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new GroupMessageRequestPacket(toGroupId, message));
    }
}
