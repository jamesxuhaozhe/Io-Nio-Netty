package netty_learn.chatapp.protocol.response;

import lombok.Data;
import netty_learn.chatapp.protocol.Packet;

import java.util.List;

import static netty_learn.chatapp.protocol.command.Command.CREATE_GROUP_RESPONSE;

@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_RESPONSE;
    }
}
