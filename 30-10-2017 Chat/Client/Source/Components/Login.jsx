'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import { ipcRenderer } from "electron"

export default class Login extends React.Component
{
    constructor()
    {
        super();
        this.state = { dialogResult: null }
        this.HandleShow.bind( this );
        this.ButtonSendOnClick = this.ButtonSendOnClick.bind( this );
        ipcRenderer.on( "logincomponent", function ( event, data )
        {
            if ( data.includes( "LoginFail" ) )
            {
                console.log( "Errore nel login" );
                this.HandleShow();
            }
            else if ( data.includes( "LoginSuccess" ) )
            {
                console.log( "Fine login, apro il main" )
                event.sender.send( "main", null );
            }
        }.bind(this) )
        this.user = "";
    }

    HandleShow()
    {
,       this.refs.dialog.winControl.show().then( function ( eventObject )
        {
            this.setState( { dialogResult: eventObject.result } );
        }.bind( this ) ); 

    }

    ButtonSwitchOnClick()
    {
        ipcRenderer.send( "signup" )
    }

    ButtonSendOnClick()
    {
        var username = document.getElementById( "TextBoxUser" ).value;
        var psd = document.getElementById( "TextBoxPsd" ).value;
        this.user = { Username: username, Password: psd, Action: "Login" };
        ipcRenderer.send( "main", this.user );
        console.log( "Ho inviato al main---->" + JSON.stringify( this.user ) );
    }

    render()
    {
        var ContentDialog =
            (
                    <WinJS.ContentDialog
                        ref="dialog"
                        title="Errore"
                        primaryCommandText="OK">
                        <div>
                           Errore nel login
                        </div>
                    </WinJS.ContentDialog>
            );

        return (
            <div className="" style={ { display: 'flex', alignItems: 'center', height: "100%", width: "100%", justifyContent: 'center', flexDirection: "column" } }>
               {this.ContentDialog}
                <div className="win-textblock">Username</div>
                <input className="win-textbox" type="text" id="TextBoxUser" />
                <div className="win-textblock">Password</div>
                <input className="win-textbox" type="password" id="TextBoxPsd" />
                <button className="win-button" onClick={ this.ButtonSendOnClick }>Conferma</button>
                <a onClick={ this.ButtonSwitchOnClick } style={ { margin: "20px" } }>Non hai un account? Registrati!</a>
            </div>
        );
    }
}

