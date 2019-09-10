package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Typical tcp server
 */
public class SocketDemoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2000);

        System.out.println("Server is ready~~");
        System.out.println("Server info: " + server.getInetAddress() + " Port: " + server.getLocalPort());

        // wait for the client to connect
        while (true){
            // get the client connection
            Socket client = server.accept();

            //create handler thread
            HandlerThread handlerThread = new HandlerThread(client);

            // start the thread
            handlerThread.start();
        }
    }

    private static class HandlerThread extends Thread {
        private Socket socket;
        private boolean flag = true;

        HandlerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("New client connection: " + socket.getInetAddress() + " Port: " + socket.getPort());

            try {
                // get the output stream, so the server can send out msg
                PrintStream socketOutput = new PrintStream(socket.getOutputStream());
                // get the input stream, so the server can read msg
                BufferedReader socketInput = new BufferedReader((new InputStreamReader(socket.getInputStream())));

                do {
                    // get string from client
                    String msg = socketInput.readLine();
                    if ("bye".equalsIgnoreCase(msg)) {
                        flag = false;
                        // send bye to client as well
                        socketOutput.println("bye");
                    } else {
                        //print the msg in the server console, and send the data length back to client
                        System.out.println(msg);
                        socketOutput.println("msg length: " + msg.length());
                    }
                } while (flag);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //close socket
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("client exits: " + socket.getInetAddress() + " Port: ");
        }
    }
}
