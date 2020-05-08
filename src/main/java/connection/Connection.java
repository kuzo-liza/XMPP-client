package connection;

import handler.Handler;
import stream.StreamReader;
import stream.StreamWriter;
import handler.ResponseHandler;
import sessionInfo.SessionInfo;
import request.RequestBuilder;

import java.io.IOException;
import java.net.Socket;

public class Connection {

    private static StreamReader reader;
    private static Socket socket;
    private static boolean isConnected;

    public static boolean connect(String propertiesPath) {
        try {
            SessionInfo sessionInfo = SessionInfo.getInstance();
            if (!sessionInfo.load(propertiesPath)) {
                return false;
            }

            initConnection();
            reader = new StreamReader(socket);
            StreamWriter.init(socket);

            new Thread(reader).start();

            if (!connect()) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Wrong input.");
            return false;
        }

        isConnected = true;
        return true;
    }

    private static void initConnection() throws IOException {
        socket = new Socket(
                SessionInfo.getInstance().getServerInfo().getServer(),
                SessionInfo.getInstance().getServerInfo().getPort()
        );
    }

    private static boolean connect() throws IOException {
        ResponseHandler.addHandler(new Handler() {
            @Override
            public void handle(String response) {
                super.handle(response);
                try {
                    StreamWriter.write(
                            RequestBuilder.createAuthRequest(
                                    SessionInfo.getInstance().getUserInfo().getJid(),
                                    SessionInfo.getInstance().getUserInfo().getPassword())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ResponseHandler.addHandler(new Handler() {
            @Override
            public void handle(String response) {
                super.handle(response);
                try {
                    StreamWriter.write(
                            RequestBuilder.createConnectionRequest(
                                    SessionInfo.getInstance().getServerInfo().getDomain(),
                                    SessionInfo.getInstance().getUserInfo().getJid())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ResponseHandler.addHandler(new Handler() {
            @Override
            public void handle(String response) {
                super.handle(response);
                try {
                    StreamWriter.write(
                            RequestBuilder.createBindRequest(
                                    SessionInfo.getInstance().getUserInfo().getResource())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        StreamWriter.write(
                RequestBuilder.createConnectionRequest(
                        SessionInfo.getInstance().getServerInfo().getDomain(),
                        SessionInfo.getInstance().getUserInfo().getJid())
        );

        return true;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static void disconnect() throws IOException {
        StreamWriter.write(RequestBuilder.createCloseStreamRequest());
        socket.close();
        isConnected = false;
    }
}
