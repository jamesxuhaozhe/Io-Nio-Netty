package nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;

public class ChatRoomClient {

    private Selector selector;

    private static final int port = 9999;

    private static final Charset charset = Charset.forName("UTF-8");

    private SocketChannel socketChannel;

    private String name = "";

    private static final String USER_EXIST = "system message: user exist, please change a name";

    private static final String USER_CONTENT_SPLIT = "#@#";

    private void init() throws IOException {
        selector = Selector.open();
        //connect remote ip and port num
        socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new ClientThread()).start();
        //in the main thread
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("".equals(line)) {
                continue;
            }
            if ("".equals(name)) {
                name = line;
                line = name + USER_CONTENT_SPLIT;
            } else {
                line = name + USER_CONTENT_SPLIT + line;
            }
            socketChannel.write(charset.encode(line));
        }
    }

    private class ClientThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if (readyChannels <= 0) {
                        continue;
                    }

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        handleSelectionKey(key);
                    }
                }
            } catch (IOException io) {
                // no-op
            }
        }

        private void handleSelectionKey(SelectionKey key) throws IOException {
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                StringBuilder content = new StringBuilder();
                while (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    content.append(charset.decode(buffer));
                }

                if (USER_EXIST.equals(content.toString())) {
                    name = "";
                }
                System.out.println(content);
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatRoomClient().init();
    }
}
