package capitalize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CapitalizeServer
{
    private final static int PORT=1723;

    public static void main(String[] args) throws InterruptedException
    {
        ServerSocket listener=null;
        System.out.println("\nThe capitalization server is running.\nPress CTRL+C in any moment to shutdown the server");
        int clientNumber=1;
        try
        {
            listener=new ServerSocket(PORT);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            while (true)
            {
                new Capitalizer(listener.accept(), clientNumber++).start();
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
                listener.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static class Capitalizer extends Thread
    {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber)
        {
            this.socket = socket;
            this.clientNumber = clientNumber;
            myLog("\nNew connection with client #" + clientNumber + " at " + socket);
        }

        public void run()
        {
            try
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Hello, you are client #" + clientNumber + ".");
                out.println("Enter a line with only a period to quit\n");

                while (true)
                {
                    String input = in.readLine();
                    System.out.println("\nReceived "+input+" from client #"+clientNumber);
                    Thread.sleep(1000);
                    if (input == null || input.equals("."))
                    {
                        socket.close();
                        break;
                    }
                    System.out.println("\nSent response to client #"+clientNumber);
                    out.println(input.toUpperCase());
                }
            }
            catch (IOException e)
            {
                myLog("Error handling client# " + clientNumber + ": " + e);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                myLog("Connection with client# " + clientNumber + " closed");
            }
        }

        private void myLog(String message)
        {
            System.out.println(message);
        }
    }
}
