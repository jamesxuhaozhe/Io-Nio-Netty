package netty_learn.demo3.protocol.request;

import netty_learn.demo3.protocol.Packet;

import static netty_learn.demo3.protocol.command.Command.MESSAGE_RESPONSE;

public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
