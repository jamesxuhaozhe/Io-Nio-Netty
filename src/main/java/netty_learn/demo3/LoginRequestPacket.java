package netty_learn.demo3;

import lombok.Data;

import static netty_learn.demo3.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
