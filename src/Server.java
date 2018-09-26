/**
 * Author : Chris Bell
 * Date   : 03/14/2018
 */

public class Server {
    private String hostName;
    private int port;

    public Server (String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getHostName() {
        return this.hostName;
    }

    public int getPort() {
        return this.port;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
