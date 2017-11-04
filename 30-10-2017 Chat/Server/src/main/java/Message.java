/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manue
 */
public class Message
{

    public String Text;
    public String Date;
    public String Username;
    public String ToUser;
}

class MessageGroup
{

    Message[] Messages;

    public MessageGroup(Message[] m)
    {
        this.Messages = m;
    }
}
