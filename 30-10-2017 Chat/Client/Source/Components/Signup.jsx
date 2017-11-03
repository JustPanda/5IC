'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import {ipcRenderer} from "electron"
import ContentDialog from "./ContentDialog"

export default class Signup extends React.Component
{
    constructor()
    {
        super();
        this.ButtonSendOnClick = this.ButtonSendOnClick.bind(this);
        ipcRenderer.on("signup", function(event, data)
        {
            if(JSON.stringify(data)=="SignupFail")
            {
                console.log("Errore nel login")
            }
            else if(JSON.stringify(data)=="SignupSuccess")
            {
                event.sender.send("main");
            }
        })
        this.state={dialogText:""};
    }
    ButtonSwitchOnClick()
    {
        ipcRenderer.send("login")
    }

    ButtonSendOnClick()
    {
        var tbUser = document.getElementById("TextBoxUser");
        var tbPsd = document.getElementById("TextBoxPsd");
        var tbConfPsd = document.getElementById("TextBoxConfPsd");
        if(tbPsd.value == tbConfPsd.value)
        {
            var user = {Username:tbUser.value, Password:tbPsd.value, Action:"Registration"};
            ipcRenderer.send("main", user);
        }
        else
        {
            
        }
        
    }

    render()
    {
        return (
            <div className="" style={ { display: 'flex', alignItems:'center', height:"100%", width:"100%", justifyContent:'center', flexDirection:"column"  } }>
                <div className="win-textblock">Username</div>
                <input id="TextBoxUser" className="win-textbox" type="text" />
                <div className="win-textblock">Password</div>
                <input id="TextBoxPsd" className="win-textbox" type="text" />
                <div className="win-textblock">Conferma password</div>
                <input id="TextBoxConfPsd" className="win-textbox" type="text" />
                <button className="win-button" onClick={this.ButtonSendOnClick}>Registrati</button>
                <a onClick={this.ButtonSwitchOnClick} style={{margin:"20px"}}>Hai già un account? Fai il Login!</a>
                <ContentDialog text={"La password deve essere uguale"}></ContentDialog>
            </div>
        );
    }
}
