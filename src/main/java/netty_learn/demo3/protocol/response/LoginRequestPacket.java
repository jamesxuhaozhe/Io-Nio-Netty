package netty_learn.demo3.protocol.response;

import lombok.Data;
import netty_learn.demo3.protocol.Packet;

import static netty_learn.demo3.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
