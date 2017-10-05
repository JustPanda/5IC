package datesc;

/**
 *
 * @author gioele
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class DateS {

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("running server");
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true); // with autoflush
                    System.out.println("richiesta ricevuta : "+ new Date().toString());
                    out.println(new Date().toString());
                } finally {
                    // autocloseable ...
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}

