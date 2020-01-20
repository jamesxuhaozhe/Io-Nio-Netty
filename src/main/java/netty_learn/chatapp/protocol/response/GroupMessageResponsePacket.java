package netty_learn.chatapp.protocol.response;

import lombok.Data;
import netty_learn.chatapp.protocol.Packet;
import netty_learn.chatapp.session.Session;

import static netty_learn.chatapp.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommand() {

        return GROUP_MESSAGE_RESPONSE;
    }
}
