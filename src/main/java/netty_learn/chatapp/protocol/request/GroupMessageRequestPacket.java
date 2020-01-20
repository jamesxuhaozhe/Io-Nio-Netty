package netty_learn.chatapp.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.GROUP_MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String toGroupId;
    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
