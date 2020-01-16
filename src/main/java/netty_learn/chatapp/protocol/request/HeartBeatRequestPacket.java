package netty_learn.chatapp.protocol.request;

import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
