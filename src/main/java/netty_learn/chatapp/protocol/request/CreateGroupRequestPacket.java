package netty_learn.chatapp.protocol.request;

import lombok.Data;
import netty_learn.chatapp.protocol.Packet;

import java.util.List;

import static netty_learn.chatapp.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
