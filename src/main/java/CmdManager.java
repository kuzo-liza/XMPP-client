import connection.Connection;
import state.State;
import userManager.UserActionManager;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


public class CmdManager {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            State.ApplicationState.set(State.ApplicationState.ON);

            while (State.ApplicationState.get() == State.ApplicationState.ON) {
                userInput = reader.readLine();
                UserActionManager.handleUserInput(userInput);
            }
        } catch (IOException e) {
            try {
                Connection.disconnect();
            } catch (IOException ex) {
                System.out.println("Error closing connection.");
            }
            System.out.println("Error.");
        }

    }

}
