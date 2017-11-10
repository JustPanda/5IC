'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import Client from "../Client";

import TopAppBar from "./TopAppBar"
import SendBar from "./SendBar"
import MessageUser from "./MessageUser"
import MessageOther from "./MessageOther"
import { ipcRenderer } from "electron"

export default class SplitViewContent extends React.Component
{
    constructor()
    {
        super();
        var ws = new Client();

        this.state = { user: "cesare", toUser: "Group", messages: [], tags: [], client: ws };
        this.RefreshMessages = this.RefreshMessages.bind( this );
        this.state.client.StartClient( this.RefreshMessages );

        ipcRenderer.on( "content", function ( event, data )
        {
            var userObj = data;
            if ( userObj.Action.includes( "ChangeToUser" ) )
            {
                this.setState( { toUser: userObj.ToUser } );
                console.log( "HO settato il toUser" + this.state.toUser );
                console.log( "Mi preparo a mandare al server change to user" )
                var User = { Username: this.state.user, ToUser: this.state.toUser, Action: "ChangeToUser" }
                console.log( JSON.stringify( User ) );
                this.state.client.WriteMessage( JSON.stringify( User ) );
                this.setState( { messages: [], tags: [] } );

            }
            else
            {

                this.state.client.WriteMessage( JSON.stringify( userObj ) );
                console.log( "mi è arrivato l'user: " + JSON.stringify( data ) );
                this.setState( { user: userObj.Username } );
                console.log( "Ho cambiato l'user a " + this.state.user )
                ipcRenderer.send( "main", { UsernamePerPane: this.state.user } );
            }

        }.bind( this ) );

    }
    RefreshMessages( message )
    {
        this.setState( function ( prevstate )
        {
            prevstate.messages.push( message );

            var tag;

            var msg = prevstate.messages[ prevstate.messages.length - 1 ];
            console.log( "User arrivato dal messaggio: " + msg.Username );
            console.log( "User stabile: " + this.state.user )
            if ( msg.Username == "" || msg.Username == this.state.user )
            {
                console.log( msg.Username )
                if ( msg.Username == "" )
                {
                    msg.Username = this.state.user
                    this.state.client.WriteMessage( "{" + "\"" + "Username" + "\"" + ":" + "\"" + msg.Username + "\"" + "," + "\"" + "Text" + "\"" + ":" + "\"" + msg.Text + "\"" + "," + "\"" + "Date" + "\"" + ":" + "\"" + msg.Date + "\"" + "," + "\"" + "ToUser" + "\"" + ":" + "\"" + this.state.toUser + "\"" + "}" );
                }

                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <div data-grid="col-8"></div>
                        <MessageUser data-grid="col-4" text={ msg.Text } date={ msg.Date } user={ msg.Username }></MessageUser>
                    </div>
                )

            }
            else if ( msg.Username != this.state.user )
            {
                console.log( msg.Username + " è l'username arrivato grffffffffff" )
                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <MessageOther data-grid="col-4" text={ msg.Text } date={ msg.Date } user={ msg.Username }></MessageOther>
                        <div data-grid="col-8"></div>
                    </div>
                )
            }

            prevstate.tags.push( tag );

            return { messages: prevstate.messages, tags: prevstate.tags }
        } );


    }
    render()
    {
        return (
            <div style={ { height: "100%" } }>
                <TopAppBar name={ this.state.toUser }></TopAppBar>
                <section id="ChatSection" className="scrollBar" style={ { width: '100%', height: '90%', backgroundImage: "url(https://www.walldevil.com/wallpapers/a26/background-pictures-cool-funny-large.jpg)", backgroundPosition: "center", backgroundAttachment:"fixed"  } }>
                    { this.state.tags }
                </section>
                <SendBar method={ this.RefreshMessages }></SendBar>
            </div>
        );
    }
}
