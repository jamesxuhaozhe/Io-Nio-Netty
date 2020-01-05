package netty_learn.chatapp.protocol.request;

import lombok.Data;
import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
