/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author manue
 */


class User 
{
    public User(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
        Messages = new ArrayList<Message>();
    }   
    
    public String Username;
    public String Password;
    public boolean IsBlocked;
    public List<Message> Messages;
}

class Message
{
    public Message(String Text, String Date, MessageOwner Owner)
    {
        this.Text = Text;
        this.Date = new Date().toString();
    }
    public String Text;
    public String Date;
    public MessageOwner Owner;
}

enum MessageOwner
{
    Client, 
    Server
}

class Position
{
    
}
