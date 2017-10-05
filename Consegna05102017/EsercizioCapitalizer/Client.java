/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esercizio;

/**
 *
 * @author samue
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader stdIn;

    public void connectToServer() throws IOException {

        String serverAddress = "127.0.0.1";
        String userInput;

        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 9898);
        // in = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        // Consume the initial welcoming messages from the server
        for (int i = 0; i < 3; i++) {
            System.out.println(in.readLine());
        }
        while ((userInput = stdIn.readLine()) != null) {
	        out.println(userInput);
	        System.out.println("server responds: " + in.readLine());
	    }
    }

    /**
     * Runs the client application.
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.connectToServer();
    }
}

