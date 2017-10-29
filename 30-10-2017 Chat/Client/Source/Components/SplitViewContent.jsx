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
        this.state = { user: "cesare",messages: [], tags: [], client: ws };
        this.RefreshMessages = this.RefreshMessages.bind( this );
        this.state.client.StartClient(this.RefreshMessages);
        
    }
    RefreshMessages ( message )
    {
        this.setState( function ( prevstate )
        {
            prevstate.messages.push( message );
            
            var tag;

            var msg =prevstate.messages[ prevstate.messages.length - 1 ];
            if(msg.User==prevstate.user)
            {
                 tag = (
                <div className="row" style={ { width: '100%' } }>
                    <div className="col-md-8"></div>
                    <MessageUser className="col-md-4" text={ msg.Text } date={ msg.Date} user={msg.User}></MessageUser>
                </div>
            )

            this.state.client.WriteMessage("{" + "User:" + "\"" + message.Type + "\"" + ",Text:"+ "\"" + message.Text  + "\"" + ",Date:"+ "\"" + message.Date + "\"" +"}");
            }
            else if(msg.User!=prevstate.user)
            {
                tag = (
                    <div className="row" style={ { width: '100%' } }>
                        <MessageOther className="col-md-4" text={ msg.Text } date={ msg.Date } user={msg.User}></MessageOther>
                        <div className="col-md-8"></div>
                    </div>
                )
            }
        
            prevstate.tags.push( tag );

            return { messages: prevstate.messages, tags:prevstate.tags }
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
