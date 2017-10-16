package pizzeria;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author gioele
 */
public class Server {
    final int port=3333;
    ServerSocket server;

    public Server(){
        try {
            server=new ServerSocket(port);
        } catch (IOException ex) {
        }
    }
    
    public void acceptance(){
        while(true){
            Socket client;
            try {
                client = server.accept();
                Connection cnn=new Connection(client);
                cnn.start();
            } catch (IOException ex) {
            }
        }
    }
    
}
