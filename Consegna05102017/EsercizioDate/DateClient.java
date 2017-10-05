/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eserciziodate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.*;

/**
 *
 * @author samue
 */
public class DateClient {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost"; // server string
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String str = "DATA RECEIVED:";
        do {
            System.out.println(str);
            str = input.readLine();
        } while(str != null);
    }
}
