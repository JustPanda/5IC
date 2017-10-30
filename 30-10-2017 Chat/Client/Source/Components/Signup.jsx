'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import {ipcRenderer} from "electron"

export default class Signup extends React.Component
{
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
            
        }
        else
        {
            
        }
        ipcRenderer.send("main")
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
            </div>
        );
    }
}
