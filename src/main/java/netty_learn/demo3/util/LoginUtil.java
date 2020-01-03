package netty_learn.demo3.util;


import io.netty.channel.Channel;
import io.netty.util.Attribute;
import netty_learn.demo3.attribute.Attributes;

public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> attribute = channel.attr(Attributes.LOGIN);
        return attribute.get() != null;
    }
}