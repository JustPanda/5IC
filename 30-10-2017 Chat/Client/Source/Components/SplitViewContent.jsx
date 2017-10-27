'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import Client from "../Client";

import TopAppBar from "./TopAppBar"
import SendBar from "./SendBar"
import MessageUser from "./MessageUser"
import MessageOther from "./MessageOther";

export default class SplitViewContent extends React.Component
{
    constructor()
    {
        super();
        var ws = new Client();
        this.state = { messages: [], tags: [], client: ws };
        this.state.client.StartClient();
        this.RefreshMessages = this.RefreshMessages.bind( this );
    }
    RefreshMessages ( message )
    {
        this.setState( function ( prevstate )
        {
            prevstate.messages.push( message );
            
            var tag;
        //    console.log(prevstate.messages);
            if ( prevstate.messages[ prevstate.messages.length - 1 ].Type == "client" )
            {
                var msg =prevstate.messages[ prevstate.messages.length - 1 ];
                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <div className="col-md-8"></div>
                        <MessageUser className="col-md-4" text={ msg.Text } date={ msg.Date }></MessageUser>
                    </div>
                )
            }
            else if ( prevstate.messages[ prevstate.messages.length - 1 ].Type == "server" )
            {
                var msg = prevstate.messages[ prevstate.messages.length - 1 ];
                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <MessageOther className="col-md-4" text={ msg.Text } date={ msg.Date }></MessageOther>
                        <div className="col-md-8"></div>
                    </div>
                )
            }
            prevstate.tags.push( tag );

            return { messages: prevstate.messages, tags:prevstate.tags }
        } );

        console.log("Sto per inviare :" + "Text:" + message.Text + "Date:" + message.date + "Type:" + message.Type);
        this.state.client.WriteMessage( "Text:" + message.Text + /*"Date:" + message.date +*/ "|Type:" + message.Type);

    }
    render ()
    {
        return (
            <div style={ { height: "100%" } }>
                <TopAppBar></TopAppBar>
                <section id="ChatSection" className="scrollBar" style={ { width: '100%', height: '85%' } }>
                    { this.state.tags }
                </section>
                <SendBar method={ this.RefreshMessages }></SendBar>
            </div>
        );
    }
}
