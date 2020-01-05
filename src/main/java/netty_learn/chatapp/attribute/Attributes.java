package netty_learn.chatapp.attribute;

import io.netty.util.AttributeKey;
import netty_learn.chatapp.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
