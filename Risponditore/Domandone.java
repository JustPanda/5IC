package risponditoreautomatico;

import java.io.IOException;

/**
 *
 * @author marco
 */
public class Domandone {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.serverAccept();
    }

}
