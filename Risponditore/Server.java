package risponditoreautomatico;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author marco
 */
public class Server {

    final int nPorta = 4455;
    ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(nPorta);
        } catch (IOException ex) {
        }
    }

    public void serverAccept() throws IOException {
        while (true) {
            Socket client = server.accept();
            Connessioni nuovaConnessione = new Connessioni(client);
            nuovaConnessione.start();
        }
    }

}
