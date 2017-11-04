
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
        SQLiteManager man = new SQLiteManager();    
        man.CreateTables();
        man.Disconnect();
        
    }
}
