package netty_learn.chatapp.protocol.response;

import lombok.Data;
import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
