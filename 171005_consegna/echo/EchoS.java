package echosc;

/**
 *
 * @author gioele
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.util.Date;

public class EchoS {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9090);
//            System.out.println("Use \"netcat 127.0.0.1 9999\"");
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                 line = is.readLine();
                 System.out.println("richiesta ricevuta : "+ new Date().toString()+" ,contenente la stringa "+ line);
                 Thread.sleep(1000);
                 os.println("ECHO " + line); 
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}

