package stream;

import connection.Connection;
import handler.ResponseHandler;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLOutput;

public class StreamReader implements Runnable{

    private Socket socket;

    public StreamReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String r = readResponse();
                if (!r.equals("")) {
                    ResponseHandler.handle(r);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from server.");
            try {
                Connection.disconnect();
            } catch (IOException ex) {
                System.out.println("Error close connection.");
            }
        }
    }

    private String readResponse() throws IOException {
        byte[] buffer = new byte[1];
        StringBuilder result = new StringBuilder();

        while (!socket.isClosed() && socket.getInputStream().available() > 0) {
            socket.getInputStream().read(buffer);
            result.append((char)buffer[0]);
        }

        return result.toString();
    }
}
