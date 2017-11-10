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
        this.state = { dialogResult: null, errorText: "Errore durante signup, l'utente esiste già o c'è stato un errore interno" }
        this.HandleShow.bind( this );
        this.ButtonSendOnClick = this.ButtonSendOnClick.bind(this);
        ipcRenderer.on("signupcomponent", function(event, data)
        {
            if(data.includes("SignupFail"))
            {
                console.log("Errore nel login")
                this.setState({errorText:"Errore durante signup, l'utente esiste già o c'è stato un errore interno"})
            }
            else if(data.inculdes("SignupSuccess"))
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
        if(tbPsd.value === null || tbPsd===undefined || tbConfPsd.value ===null || tbConfPsd.value === undefined || tbConfPsd.value =="" || tbConfPsd.value == "")
        {
            this.setState({errorText:"La password deve contenere almeno un carattere"})
            this.HandleShow();
        }
        else if(tbPsd.value == tbConfPsd.value)
        {
            var user = {Username:tbUser.value, Password:tbPsd.value, Action:"Registration"};
            ipcRenderer.send("main", user);
        }
        else if(tbPsd.value!=tbConfPsd.value)
        {
            this.setState({errorText:"Le password non sono uguali "})
            this.HandleShow();
        }
        
    }

    HandleShow()
    {
        this.refs.dialog.winControl.show().then( function ( eventObject )
        {
            this.setState( { dialogResult: eventObject.result } );
        }.bind( this ) );

    }

    render()
    {
        return (
            <div className="" style={ { display: 'flex', alignItems:'center', height:"100%", width:"100%", justifyContent:'center', flexDirection:"column", background:"rgb(25, 25, 25)" } }>
                <div className="win-textblock" style={{color:"white"}}>Username</div>
                <input id="TextBoxUser" className="win-textbox" type="text" />
                <div className="win-textblock" style={{color:"white"}}>Password</div>
                <input id="TextBoxPsd" className="win-textbox" type="password" />
                <div className="win-textblock" style={{color:"white"}}>Conferma password</div>
                <input id="TextBoxConfPsd" className="win-textbox" type="password" />
                <button className="win-button" onClick={this.ButtonSendOnClick}>Registrati</button>
                <a onClick={this.ButtonSwitchOnClick} style={{margin:"20px"}}>Hai già un account? Fai il Login!</a>
                <div >
                    <WinJS.ContentDialog
                        ref="dialog"
                        title="Errore"
                        primaryCommandText="OK">
                        <div>
                            { this.state.errorText }
                        </div>
                    </WinJS.ContentDialog>
                </div>
            </div>
        );
    }
}
