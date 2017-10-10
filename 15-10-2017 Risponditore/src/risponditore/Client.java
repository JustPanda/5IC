/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author manue
 */
public class Client
{

    /**
     * @param args the command line arguments
     */
    public static String host = "localhost";
    public static int authPort = 8080;
    
    public static void main(String[] args) throws Exception
    {
        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket s = (SSLSocket) ssf.createSocket("localhost", 8080);
        s.startHandshake();
        
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, authPort);

        sslsocket.startHandshake();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
    }

}
