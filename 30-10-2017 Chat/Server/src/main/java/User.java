/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manue
 */
public class User
{
    public User(String Username, String Password, String Action)
    {
        this.Username = Username;
        this.Password = Password;
        this.Action = Action;
    }
    
    public User()
    {
        this.Username = "";
        this.Password = "";
        this.Action = "";
    }
    String Username;
    String Password;
    String ToUser;
    String Action;
}
