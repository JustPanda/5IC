
import java.sql.SQLException;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manue
 */
public class Test
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        boolean success;
        SQLiteManager man = new SQLiteManager();
        
        success=man.Register(new User("manuele", "pene", "Register"));
        System.out.println("Registrazione: " + success);
        
        success = man.Login(new User("manuele", "pene", "Register"));
        System.out.println("Login: " + success);
        
        success=man.Register(new User("merda", "dio", "Register"));
        System.out.println("Registrazione: " + success);
        
        for(int i=0; i<10; i++)
        {
            Message message = new Message();
            message.Text = "PORCODDIO ";// + i;
            message.User = "manuele" ;
            message.Date = "Adesso dio can";
            man.AddMessage(message);
        } 
        
        List<Message> messages = man.GetMessages();
        for(int i=0; i<messages.size(); i++)
        {
            System.out.println("Message N°: " + i + " from " + "\"" + messages.get(i).User + "\"" + "\"" + messages.get(i).Text + "\"" + " at " + "\"" + messages.get(i).Date + "\"");
        }
        
        man.Disconnect();
        
    }
}
