'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import Chat from "./Chat"
import SendBar from "./SendBar"

export default class SplitViewContent extends React.Component
{
    constructor()
    {
        super();
        this.state = { messages: [] };
        this.refreshMessages = this.RefreshMessages.bind( this );
    }
    RefreshMessages ( message )
    {
        this.setState( function(prevstate)
    {
        prevstate.messages.push(message);
        return {messages:prevstate.messages}
    });

    }
    render ()
    {
        return (
            <div style={ { height: "100%" } }>
                <TopAppBar></TopAppBar>
                { <section id="ChatSection" className="scrollBar" style={ { width: '100%', height: '85%' } }>
                    {this.state.messages}
                </section> }
                {/*  <Chat></Chat> */ }
                <SendBar method={ this.refreshMessages }></SendBar>
            </div>
        );
    }
}
