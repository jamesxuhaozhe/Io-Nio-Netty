package udp;

class MessageCreator {

    private static final String SN_HEADER = "收到暗号，我是（SN）：";
    private static final String PORT_HEADER = "这是暗号，请回电脑（Port）：";

    static String buildWithPort(int port) {
        return PORT_HEADER + port;
    }

    static int parsePort(String data) {
        if (data.startsWith(PORT_HEADER)) {
            return Integer.parseInt(data.substring(PORT_HEADER.length()));
        }

        return -1;
    }

    static String buildWithSn(String sn) {
        return SN_HEADER + sn;
    }

    static String parseSn(String data) {
        if (data.startsWith(SN_HEADER)) {
            return data.substring(SN_HEADER.length());
        }
        return null;
    }
}
