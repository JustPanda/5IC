/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.util.*;

/**
 *
 * @author manue
 */
class Risponditore
{
    
    public String DeleteAccount(User user)
    {
        return "The account has been deleted";
    }
    
    public String BlockAccount(User user)
    {
        user.IsBlocked=true;
        return "The account has been blocked";
    }
    
    public String NearerStation(User user, Position position)
    {
        return "The nearer station is at 100m north from you";
    }
    
    public String GetMoney(User user)
    {
        int money =100;
        return money+"";
    }
    
    public String AddMoney(User user, int money)
    {
        return "We've added " + money;
    }
    
    public String[] GetLog(User user)
    {
        
        return new String[]{"", ""};
    }
}

class Comparatore
{
    public Comparatore(String Key)
    {
        this.Key = Key;
    }
    public String Key;
    public List<String> KeyWords = new ArrayList<String>();
    
    public String Compare(String input)
    {
        for(int i=0; i<KeyWords.size(); i++)
        {
            if(input.toLowerCase().contains(KeyWords.get(i)))
            {
                
            }
        }
        return null;
    }
}
