package netty_learn.chatapp.util;

import io.netty.channel.Channel;
import netty_learn.chatapp.attribute.Attributes;
import netty_learn.chatapp.session.Session;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SessionUtil {

    private static final ConcurrentMap<String, Channel> userIdToMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdToMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdToMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + " 退出登录!");
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdToMap.get(userId);
    }
}
