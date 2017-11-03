'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import {ipcRenderer} from "electron"

export default class Login extends React.Component
{
constructor()
{
    super();
    this.ButtonSendOnClick = this.ButtonSendOnClick.bind(this);
    ipcRenderer.on("login", function(event, data)
    {
        if(toString(data)=="LoginFail")
        {
            console.log("Errore nel login")
        }
        else if(toString(data)=="LoginSuccess")
        {
            event.sender.send("main");
        }
    })
    this.user = "";
}

    ButtonSwitchOnClick()
    {
        ipcRenderer.send("signup")
    }

    ButtonSendOnClick()
    {
        var username = document.getElementById("TextBoxUser").value;
        var psd = document.getElementById("TextBoxPsd").value;
        this.user = {Username:username, Password:psd, Action:"Login"};
        ipcRenderer.send("main", this.user);
        console.log("Ho inviato al main---->" + JSON.stringify(this.user));
    }

    render()
    {
        return (
            <div className="" style={ { display: 'flex', alignItems:'center', height:"100%", width:"100%", justifyContent:'center', flexDirection:"column"  } }>
                <div className="win-textblock">Username</div>
                <input className="win-textbox" type="text" id="TextBoxUser"/>
                <div className="win-textblock">Password</div>
                <input className="win-textbox" type="text" id="TextBoxPsd" />
                <button className="win-button" onClick={this.ButtonSendOnClick}>Conferma</button>
                <a onClick={this.ButtonSwitchOnClick} style={{margin:"20px"}}>Non hai un account? Registrati!</a>
            </div>
        );
    }
}
