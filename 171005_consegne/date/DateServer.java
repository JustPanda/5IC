package date;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class DateServer
{
    private final static int PORT=1723;
    private static int clientNumber=0;

    public static void main(String[] args) throws InterruptedException
    {
        ServerSocket server=null;
        try
        {
            server=new ServerSocket(PORT);
            System.out.println("\n\n\nRunning server...\n\nListenig at port: "+PORT+"\n\npress CTRL+C in any moment to shutdown the server");

            while (true)
            {
                Socket socket=server.accept();
                System.out.println("\nConnected with client number :"+(++clientNumber));
                Thread.sleep(1000);

                PrintWriter out=new PrintWriter(socket.getOutputStream(), true);
                out.println(new Date().toString());
                System.out.println("\nSent date to client: "+clientNumber);
                Thread.sleep(1000);

                socket.close();
                System.out.println("\nDisconnected client: "+clientNumber);
                Thread.sleep(1000);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                server.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
