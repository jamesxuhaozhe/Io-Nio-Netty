package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * UDP searcher, used to search the provider
 */
public class UDPSearcher {

    private static final int LISTEN_PORT = 30000;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("UDPSearcher started");

        Listener listener = listen();
        sendBroadcast();

        // any keyboard event will exit the program
        System.in.read();

        List<Device> devices = listener.getDevicesAndClose();

        for(Device device : devices) {
            System.out.println("Device: " + device);
        }

        // finish
        System.out.println("UDPSearcher finished");
    }

    private static Listener listen() throws InterruptedException {
        System.out.println("UDPSearcher start listening");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Listener listener = new Listener(LISTEN_PORT, countDownLatch);
        listener.start();

        countDownLatch.await();
        return listener;
    }

    private static void sendBroadcast() throws IOException {
        System.out.println("UDPSearcher sendBroadcast started.");

        // allocate the port by default
        DatagramSocket datagramSocket = new DatagramSocket();

        // build a data packet to search
        String requestData = MessageCreator.buildWithPort(LISTEN_PORT);
        byte[] requestDataBytes = requestData.getBytes();

        // build packet
        DatagramPacket requestPacket = new DatagramPacket(requestDataBytes,
                requestDataBytes.length);

        // 20000, broadcast
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacket.setPort(20000);

        // send
        datagramSocket.send(requestPacket);
        datagramSocket.close();

        // finish
        System.out.println("UDPSearcher send broadcast finished");
    }

    private static class Device {
        final int port;
        final String ip;
        final String sn;

        private Device(int port, String ip, String sn) {
            this.port = port;
            this.ip = ip;
            this.sn = sn;
        }

        @Override
        public String toString() {
            return "Device{" +
                    "port=" + port +
                    ", ip='" + ip + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }

    private static class Listener extends Thread {
        private final int listenPort;
        private final CountDownLatch countDownLatch;
        private final List<Device> devices = new ArrayList<>();
        private boolean done = false;
        private DatagramSocket datagramSocket = null;

        public Listener(int listenPort, CountDownLatch countDownLatch) {
            super();
            this.listenPort = listenPort;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();

            // notify it is running already
            countDownLatch.countDown();

            try {
                //listening port
                datagramSocket = new DatagramSocket(listenPort);

                while (!done) {
                    // build receive pack
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

                    // receive data
                    datagramSocket.receive(receivePack);

                    // print sender's info data, ip , port and so on
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    int dataLen = receivePack.getLength();
                    String data = new String(receivePack.getData(), 0, dataLen);
                    System.out.println("UDPSearcher receive from ip: " + ip +
                            "\tport: " + port + "\tdata: " + data);

                    String sn = MessageCreator.parseSn(data);
                    if (sn != null) {
                        Device device = new Device(port, ip, sn);
                        devices.add(device);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close();
            }
            System.out.println("UDPSearcher listener is dead.");
        }

        private void close() {
            if (datagramSocket!= null) {
                datagramSocket.close();
                datagramSocket = null;
            }
        }

        List<Device> getDevicesAndClose() {
            done = true;
            close();
            return devices;
        }
    }
}
