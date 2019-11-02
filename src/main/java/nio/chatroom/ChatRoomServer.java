package nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChatRoomServer {

    private Selector selector;

    static final int port = 9999;

    private Charset charset = Charset.forName("UTF-8");

    private static Set<String> users = new HashSet<>();

    private static final String USER_EXIST = "system message: user exist, please change a name";

    private static String USER_CONTENT_SPLIT = "#@#";

    private static boolean flag = false;

    public void init() throws IOException {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        //set non-blocking
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server is listening now...");

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels <= 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                handleSelectionKey(server, key);
            }
        }
    }

    private void handleSelectionKey(ServerSocketChannel server, SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            SocketChannel socketChannel = server.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);

            key.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println("Server is listening from client: " + socketChannel.getRemoteAddress());
            socketChannel.write(charset.encode("Please input your name."));
        }

        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            try {
                while (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }
                System.out.println("Server is listening from client " + socketChannel.getRemoteAddress() + " data rev is: " + content);
            } catch (IOException io) {
                key.cancel();
                if (key.channel() != null) {
                    key.channel().close();
                }
            }

            if (content.length() > 0) {
                String[] arrayContent = content.toString().split(USER_CONTENT_SPLIT);
                if (arrayContent != null && arrayContent.length == 1) {
                    String name = arrayContent[0];
                    if (users.contains(name)) {
                        socketChannel.write(charset.encode(USER_EXIST));
                    } else {
                        users.add(name);
                        int num = getOnlineNum(selector);
                        String message = "welcome " + name + " to chat room! Online numbers: " + num;
                        BroadCast(selector, null, message);
                    }
                } else if (arrayContent != null && arrayContent.length > 1) {
                    String name = arrayContent[0];
                    String message = content.substring(name.length() + USER_CONTENT_SPLIT.length());
                    message = name + " say " + message;
                    if (users.contains(name)) {
                        BroadCast(selector, socketChannel, message);
                    }
                }
            }
        }
    }

    private void BroadCast(Selector selector, SocketChannel except, String content) throws IOException {
        //broadcast the message content to all socketchannels
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != except) {
                SocketChannel dest = (SocketChannel) targetChannel;
                dest.write(charset.encode(content));
            }
        }
    }

    private static int getOnlineNum(Selector selector) {
        int result = 0;
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel) {
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        new ChatRoomServer().init();
    }
}
