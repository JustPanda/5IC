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
        ipcRenderer.send("main")
    }

    render()
    {
        return (
            <div className="" style={ { display: 'flex', alignItems:'center', height:"100%", width:"100%", justifyContent:'center', flexDirection:"column"  } }>
                <div className="win-textblock">Username</div>
                <input className="win-textbox" type="text" />
                <div className="win-textblock">Password</div>
                <input className="win-textbox" type="text" />
                <div className="win-textblock">Conferma password</div>
                <input className="win-textbox" type="text" />
                <button className="win-button" onClick={this.ButtonSendOnClick}>Registrati</button>
                <a onClick={this.ButtonSwitchOnClick} style={{margin:"20px"}}>Hai già un account? Fai il Login!</a>
            </div>
        );
    }
}
