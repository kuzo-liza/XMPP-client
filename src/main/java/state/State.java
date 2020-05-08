package state;

public class State {

    public static class ConnectionState {
        public static final int CONNECTED = 1;
        public static final int DISCONNECTED = 2;

        private static int curState = DISCONNECTED;

        public static void set(int newState) {
            curState = newState;
        }

        public static int get() {
            return curState;
        }
    }

    public static class ApplicationState {
        public static int ON = 1;
        public static int OFF = 2;

        private static int curState = ON;

        public static void set(int newState) {
            curState = newState;
        }

        public static int get() {
            return curState;
        }
    }

}
