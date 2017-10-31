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
    public User(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }
    
    public User()
    {
        this.Username = "server";
        this.Password = "";
    }
    String Username;
    String Password;
}
