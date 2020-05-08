package sessionInfo;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SessionInfo {

    private final UserInfo userInfo;
    private final ServerInfo serverInfo;
    private static SessionInfo instance;

    public SessionInfo() {
        this.serverInfo = new ServerInfo();
        this.userInfo = new UserInfo();
    }

    public static SessionInfo getInstance() {
        if (instance == null) {
            instance = new SessionInfo();
        }

        return instance;
    }


    private boolean setProperties(Properties properties) {
        try {
            serverInfo.server = properties.getProperty("server");
            serverInfo.domain = properties.getProperty("domain");
            serverInfo.port = Integer.parseInt(properties.getProperty("port", "5222"));

            userInfo.username = properties.getProperty("username");
            userInfo.password = properties.getProperty("password");
            userInfo.resource = properties.getProperty("resource", "");
            userInfo.jid = userInfo.username + "@" + serverInfo.domain + "/" + userInfo.resource;

            return serverInfo.domain != null && userInfo.username != null && userInfo.password != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean load(String filePath) {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(filePath));

            return setProperties(properties);
        } catch (IOException e) {
            return false;
        }
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public static class UserInfo {
        private String username;
        private String password;
        private String resource;
        private String jid;

        public void setResource(String resource) {
            this.resource = resource;
            this.jid = username + "@" + getInstance().getServerInfo().getDomain() + "/" + resource;
        }

        public String getJid() {
            return jid;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getResource() {
            return resource;
        }
    }

    public static class ServerInfo {
        private int port;
        private String domain;
        private String server;

        public String getServer() {
            return server;
        }

        public int getPort() {
            return port;
        }

        public String getDomain() {
            return domain;
        }
    }
}
