package netty_learn.chatapp.protocol.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import netty_learn.chatapp.protocol.Packet;

import static netty_learn.chatapp.protocol.command.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
