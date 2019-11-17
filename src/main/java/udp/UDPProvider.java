package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * UDP provider,
 */
public class UDPProvider {

    public static void main(String[] args) throws IOException {
        // generate the device id
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();

        //any keyboard input will terminate the program
        System.in.read();
        provider.exit();

    }

    private static class Provider extends Thread {
        private final String sn;
        private boolean done = false;
        private DatagramSocket datagramSocket = null;

        private Provider(String sn) {
            super();
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("UDPProvider with device id: " + sn + " just started.");

            try {
                // keep listening port 20000
                datagramSocket = new DatagramSocket(20000);

                while (!done) {
                    //build the receive packet
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

                    // receive, which is a blocking call
                    datagramSocket.receive(receivePack);

                    // get received message's ip address and port information
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);
                    System.out.println("UDPProvider receive from ip: " + ip + "\tport: " + port + "\tdata: " + data);

                    // parse the sender's port from its carried message
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        // build a response
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes();
                        // send a response back to
                        DatagramPacket responsePacket = new DatagramPacket(responseDataBytes, responseDataBytes.length, receivePack.getAddress(), responsePort);

                        datagramSocket.send(responsePacket);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // no-op
            }
        }

        //release resource
        private void close() {
            if (datagramSocket != null) {
                datagramSocket.close();
                datagramSocket = null;
            }
        }

        void exit() {
            done = true;
            close();
        }
    }

}
