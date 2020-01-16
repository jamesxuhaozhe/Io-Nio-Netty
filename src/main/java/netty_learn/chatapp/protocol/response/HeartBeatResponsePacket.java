package netty_learn.chatapp.protocol.response;

import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}