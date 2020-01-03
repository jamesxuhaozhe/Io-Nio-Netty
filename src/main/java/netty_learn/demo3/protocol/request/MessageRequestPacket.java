package netty_learn.demo3.protocol.request;

import lombok.Data;
import netty_learn.demo3.protocol.Packet;

import static netty_learn.demo3.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
