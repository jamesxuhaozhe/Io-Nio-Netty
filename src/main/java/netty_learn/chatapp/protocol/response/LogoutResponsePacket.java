package netty_learn.chatapp.protocol.response;

import lombok.Data;
import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
