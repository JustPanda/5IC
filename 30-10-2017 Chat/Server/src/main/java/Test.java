
import java.sql.SQLException;

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
        SQLiteManager man = new SQLiteManager();
        man.Register(new User("manuele", "pene", "Register"));
        man.Login(new User("manuele", "pene", "Register"));
    }
}
