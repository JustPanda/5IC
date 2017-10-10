/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author manue
 */
public class Util 
{
    static User Autenticazione() throws Exception 
    {
        Console console = System.console();
        System.out.println("Inserisci l'Username");
        String username = (console.readPassword()).toString();
        System.out.println("Inserisci la password");
        String password = (console.readPassword()).toString();
        
        String encrPassword ="";
        User user = new User(username, encrPassword);
        
        return user;   
    }
    
    
}
