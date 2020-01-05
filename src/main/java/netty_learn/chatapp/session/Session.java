package netty_learn.chatapp.session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {

    private String userId;

    private String username;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.username = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + username;
    }
}
