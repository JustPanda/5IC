'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import Client from "../Client";

import TopAppBar from "./TopAppBar"
import SendBar from "./SendBar"
import MessageUser from "./MessageUser"
import MessageOther from "./MessageOther";
import { ipcRenderer } from "electron"

export default class SplitViewContent extends React.Component
{
    constructor()
    {
        super();
        var ws = new Client();

        this.state = { user: "cesare", messages: [], tags: [], client: ws };
        this.RefreshMessages = this.RefreshMessages.bind( this );
        this.state.client.StartClient( this.RefreshMessages );

        ipcRenderer.on( "content", function (event, data )
        {
            var userObj =data;
            this.state.client.WriteMessage(JSON.stringify(userObj));
            console.log("mi è arrivato l'user: " + JSON.stringify(data));
            this.setState( { user: userObj.Username } );
            console.log("Ho cambiato l'user a " + this.state.user )
        }.bind( this ) );

    }
    RefreshMessages ( message )
    {
        this.setState( function ( prevstate )
        {
            prevstate.messages.push( message );

            var tag;

            var msg = prevstate.messages[ prevstate.messages.length - 1 ];
            console.log("User arrivato dal messaggio: " + msg.User);
            console.log("User stabile: " + this.state.user)
            if ( msg.User == this.state.user )
            {
                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <div className="col-md-8"></div>
                        <MessageUser className="col-md-4" text={ msg.Text } date={ msg.Date } user={ msg.User }></MessageUser>
                    </div>
                )
                console.log( "sto inviando" )
                this.state.client.WriteMessage( "{" + "\"" + "User" + "\"" + ":" + "\"" + msg.User + "\"" + "," + "\"" + "Text" + "\"" + ":" + "\"" + msg.Text + "\"" + "," + "\"" + "Date" + "\"" + ":" + "\"" + msg.Date + "\"" + "}" );
                this.state.client.WriteMessage( "{" + "\"" + "User" + "\"" + ":" + "\"" + msg.User + "\"" + "," + "\"" + "Text" + "\"" + ":" + "\"" + msg.Text + "\"" + "," + "\"" + "Date" + "\"" + ":" + "\"" + msg.Date + "\"" + "}" );
            }
            else if ( msg.User != this.state.user )
            {
                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <MessageOther className="col-md-4" text={ msg.Text } date={ msg.Date } user={ msg.User }></MessageOther>
                        <div className="col-md-8"></div>
                    </div>
                )
            }

            prevstate.tags.push( tag );

            return { messages: prevstate.messages, tags: prevstate.tags }
        } );


    }
    render ()
    {
        return (
            <div style={ { height: "100%" } }>
                <TopAppBar></TopAppBar>
                <section id="ChatSection" className="scrollBar" style={ { width: '100%', height: '90%' } }>
                    { this.state.tags }
                </section>
                <SendBar method={ this.RefreshMessages }></SendBar>
            </div>
        );
    }
}
