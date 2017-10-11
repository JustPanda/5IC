/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author manue
 */
public class Server
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable, CertificateException
    {
        /*
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore.jks"), "keystorePassword".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, "keystorePassword".toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        sc.init(kmf.getKeyManagers(), trustManagers, null);

        SSLServerSocketFactory ssf = sc.getServerSocketFactory();
        SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(8080); */

        ServerSocket s = new ServerSocket(8080);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        SQLSetup();
        int clientIndex = 0;
        while (clientIndex <= 1000)
        {
            clientIndex++;
            Socket socket = (Socket) s.accept();
            ClientConnection client = new ClientConnection(socket, clientIndex);
            System.out.println("Connesso al client n°" + clientIndex);
            executor.execute(client);
        }
        executor.shutdown();
        s.close();
    }

    public static void SQLSetup()
    {
        Connection c = null;
        Statement stmt = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE USER "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " USERNAME       TEXT    NOT NULL, "
                    + " PSD            TEXT    NOT NULL, "
                    + " BLK            BOOL    FALSE"
                    + " MONEY          INT              )";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");

    }

}

class ClientConnection implements Runnable
{

    Socket socket;
    int index;

    public ClientConnection(Socket socket, int index) throws IOException
    {
        this.socket = socket;
        this.index = index;
    }

    @Override
    public void run()
    {

        try
        {
            boolean isExit = false;
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Risponditore risponditore = new Risponditore(writer, reader);

            while (!isExit)
            {
                String input = reader.readLine();
                if (risponditore.user != null)
                {
                    risponditore.user.Messages.add(new Message(input, MessageOwner.Client));
                }

                if (input.toLowerCase().equals("exit"))
                {
                    System.out.println("Ho ricevuto exit||||||||||||||||");
                    writer.println("Uscita...");
                    if (risponditore.user != null)
                    {
                        risponditore.user.Messages.add(new Message("Uscita...", MessageOwner.Server));
                    }
                    isExit = true;
                } else
                {
                    System.out.println("Ho ricevuto " + input);
                    risponditore.Compare(input);
                }

            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
