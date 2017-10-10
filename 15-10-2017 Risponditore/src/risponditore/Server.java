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
        SSLServerSocket s = (SSLServerSocket) ssf.createServerSocket(8080);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        int clientIndex = 0;
        while (clientIndex <= 1000)
        {
            clientIndex++;
            SSLSocket socket = (SSLSocket) s.accept();
            ClientConnection client = new ClientConnection(socket, clientIndex);
            System.out.println("Connesso al client n°" + clientIndex);
            executor.execute(client);
        }
        executor.shutdown();
    }

    public void SQLSetup()
    {
        Connection c = null;
        Statement stmt = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY "
                    + "(ID INT PRIMARY KEY     NOT NULL,"
                    + " NAME           TEXT    NOT NULL, "
                    + " AGE            INT     NOT NULL, "
                    + " ADDRESS        CHAR(50), "
                    + " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");

    }

}

class ClientConnection implements Runnable
{

    SSLSocket socket;
    int index;

    public ClientConnection(SSLSocket socket, int index) throws IOException
    {
        this.socket = socket;
        this.index = index;
    }

    @Override
    public void run()
    {

        try
        {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.write("Inserisci l'username");
            String username = reader.readLine();
            writer.write("Inserisci la password");
            String password = reader.readLine();
            User user = new User(username, password);

            while (true)
            {

                //System.out.println(a+"pene");
                writer.write("Ciaoooo");
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
