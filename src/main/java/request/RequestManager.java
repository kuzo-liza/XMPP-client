package request;

import stream.StreamWriter;
import sessionInfo.SessionInfo;
import request.RequestBuilder;

import java.io.IOException;

public class RequestManager {

    public static void sendResult(String id) throws IOException {
        StreamWriter.write(
                RequestBuilder.createResultRequest(id)
        );
    }

    public static void sendMessage(String anotherUserJID, String message) throws IOException {
        StreamWriter.write(
                RequestBuilder.createMessageRequest(
                        SessionInfo.getInstance().getUserInfo().getJid(),
                        anotherUserJID, message)
        );
    }

    public static void getRoster() throws IOException {
        StreamWriter.write(RequestBuilder.createGetRosterRequest(SessionInfo.getInstance().getUserInfo().getJid()));
    }

    public static void updateStatus(String statusType, String statusMessage) throws IOException {
        StreamWriter.write(
                RequestBuilder.createSetStatusRequest(
                        SessionInfo.getInstance().getUserInfo().getJid(),
                        statusType,
                        statusMessage)
        );
    }

    public static void removeContact(String contactJID, String contactName) throws IOException {
        StreamWriter.write(RequestBuilder.createRemoveContactRequest(contactJID, contactName));
    }

    public static void subscribe(String contactJID) throws IOException {
        StreamWriter.write(
                RequestBuilder.createSubscribeRequest(
                        SessionInfo.getInstance().getUserInfo().getJid(),
                        contactJID)
        );
    }

    public static void unsubscribe(String contactJID) throws IOException {
        StreamWriter.write(
                RequestBuilder.createUnsubscribeSubRequest(
                        SessionInfo.getInstance().getUserInfo().getJid(),
                        contactJID)
        );
    }

    public static void acceptSubscribe(String contactJID) throws IOException {
        StreamWriter.write(RequestBuilder.createAcceptSubRequest(contactJID));
    }

    public static void rejectSubscribe(String contactJID) throws IOException {
        StreamWriter.write(RequestBuilder.createRejectSubRequest(contactJID));
    }

    public static void addContact(String contactJID, String contactName, String group) throws IOException {
        StreamWriter.write(
                RequestBuilder.createAddContactRequest(
                        contactJID,
                        contactName,
                        "none",
                        group)
        );
    }



}
