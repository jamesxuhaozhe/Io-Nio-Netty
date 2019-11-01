package nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
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


        }
    }
}
