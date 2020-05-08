package handler;

import connection.Connection;
import request.RequestManager;
import sessionInfo.SessionInfo;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class ResponseHandler {

    private static List<Handler> handlers = new ArrayList<>();

    public static void handle(String response) throws IOException {

        if (response.contains("/stream:stream")) {
            System.out.println(response);
            Connection.disconnect();
            System.out.println("Disconnected");
            return;
        }

        if (!handlers.isEmpty()) {
            handlers.get(0).handle(response);
            handlers.remove(0);
            return;
        }

        System.out.println(response);
        if (response.contains("type='result'") && response.contains("bind")) {
            String userResource = response.split("<jid>")[1].split("</jid>")[0].split(SessionInfo.getInstance().getServerInfo().getDomain() + "/")[1];
            SessionInfo.getInstance().getUserInfo().setResource(userResource);
        }

        if (response.contains("jabber:iq:roster")) {
            String id = response.split("id='")[1].split("'")[0];
            try {
                RequestManager.sendResult(id);;
            } catch (Exception e) {
                System.out.println("Error sending result.");
            }
        }
    }


    public static void addHandler(Handler handler) {
        handlers.add(handler);
    }

}
