package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Typical tcp client demo
 */
public class SocketDemoClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();

        // set timeout
        socket.setSoTimeout(3000);

        //bind the local machine
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        System.out.println("Started connecting the server~");
        System.out.println("Client information: " + socket.getLocalAddress() + " Port: " + socket.getLocalPort());
        System.out.println("Server information: " + socket.getInetAddress() + " Port: " + socket.getPort());

        try {
            todo(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // close resource
        socket.close();
        System.out.println("Client has exited");
    }

    private static void todo(Socket client) throws IOException {
        // get the keyboard input stream
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        // get the output stream and convert to printstream
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        // get the socket input stream and convert
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do {
            // get the keyboard line
            String msg = input.readLine();

            // send the msg to server
            socketPrintStream.println(msg);

            // get the server response
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);

        // close resource
        socketPrintStream.close();
        socketBufferedReader.close();
    }
}
