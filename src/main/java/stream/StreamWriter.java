package stream;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class StreamWriter {

    private static Socket socket;

    public static void init(Socket newSocket) {
        socket = newSocket;
    }

    public static void write(ByteBuffer request) throws IOException {
        if (!socket.isClosed()) {
            socket.getOutputStream().write(request.array());
        }
    }

}
