/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rispoinditore2;

/**
 *
 * @author davide
 */
public class Node
{
    private String information;
    private String action;

    public Node(String information, String action)
    {
	this.information = information;
	this.action = action;
    }

    public String getInformation()
    {
    return this.information;
    }
    
    public String getAction()
    {
    return action;
    }
 
    //need to override otherwise we can't access to
    //the information easly
    @Override
    public String toString()
    {
	return getInformation() + " "+getAction();
    }

}
