package netty_learn.demo3.attribute;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
