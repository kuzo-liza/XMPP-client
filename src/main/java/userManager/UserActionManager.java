package userManager;

import state.State;
import connection.Connection;
import request.RequestManager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserActionManager {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static String getUserInput(String message) throws IOException {
        System.out.println(message);
        return reader.readLine();
    }

    public static void handleUserInput(String userInput) throws IOException {
        String contactJID;
        String contactName;

        switch (userInput) {
            case "connect":
                try {
                    if (!Connection.connect(getUserInput("Enter properties path: "))) {
                        System.out.println("Connection error.");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Connection error.");
                }
                break;
            case "disconnect" :
                if (Connection.isConnected()) {
                    Connection.disconnect();
                }
                System.out.println("Disconnected.");
                break;
            case "load roster":
                if (Connection.isConnected()) {
                    RequestManager.getRoster();
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "quit":
                if (Connection.isConnected()) {
                    Connection.disconnect();
                }
                State.ApplicationState.set(State.ApplicationState.OFF);
                break;
            case "set status":
                if (Connection.isConnected()) {
                    String statusType = getUserInput("Enter status (chat, dnd, away, xa): ");
                    if (statusType == null ||
                            (!statusType.equals("chat") && !statusType.equals("dnd") &&
                                    !statusType.equals("away")) && !statusType.equals("xa")) {
                        System.out.println("Wrong input.");
                        break;
                    }
                    String statusMessage = getUserInput("Enter status message: ");
                    RequestManager.updateStatus(statusType, statusMessage);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "add contact":
                if (Connection.isConnected()) {
                    contactJID = getUserInput("Enter contact JID: ");
                    contactName = getUserInput("Enter contact Name: ");
                    String group = getUserInput("Enter group: ");
                    RequestManager.addContact(contactJID, contactName, group);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "remove contact":
                if (Connection.isConnected()) {
                    contactJID = getUserInput("Enter contact JID: ");
                    contactName = getUserInput("Enter contact Name: ");
                    RequestManager.removeContact(contactJID, contactName);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "accept sub":
                if (Connection.isConnected()) {
                    contactJID = getUserInput("Enter contact JID: ");
                    RequestManager.acceptSubscribe(contactJID);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "deny sub":
                if (Connection.isConnected()) {
                    contactJID = getUserInput("Enter contact JID: ");
                    RequestManager.rejectSubscribe(contactJID);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "subscribe":
                if (Connection.isConnected()) {
                    contactJID = getUserInput("Enter contact JID: ");
                    RequestManager.subscribe(contactJID);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "unsubscribe":
                if (Connection.isConnected()) {
                    contactJID = getUserInput("Enter contact JID: ");
                    RequestManager.unsubscribe(contactJID);
                } else {
                    System.out.println("No connection.");
                }
                break;
            case "send message":
                if (Connection.isConnected()) {
                    String anotherUserJID = getUserInput("Enter user JID: ");
                    String text = getUserInput("Enter text: ");
                    RequestManager.sendMessage(anotherUserJID, text);
                } else {
                    System.out.println("No connection.");
                }
                break;
            default:
                System.out.println("Wrong input.");
                break;
        }
    }
}
