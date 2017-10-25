'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import MessageUser from "./MessageUser"
import MessageOther from "./MessageOther";

export default class SendBar extends React.Component
{
    constructor()
    {
        super();
        this.ButtonSendOnClick = this.ButtonSendOnClick.bind( this );
        this.state = { a: true };
    }
    ButtonSendOnClick ()
    {
        var Text = document.getElementById( "TextBoxSend" ).value;
        var Date;
        var Message;
        if ( this.state.a == true )
        {
            Message = (
                <div className="row" style={ { width: '100%' } }>
                    <div className="col-md-8"></div>
                    <MessageUser className="col-md-4" text={ Text } date={ "10/20/200" }></MessageUser>
                </div>
            );
        }
        else
        {
            Message = (
                <div className="row" style={ { width: '100%' } }>
                    <MessageOther className="col-md-4" text={ Text } date={ "10/20/200" }></MessageOther>
                    <div className="col-md-8"></div>
                </div>
            );
        }

        this.setState({a: !this.state.a})
        this.props.method( Message );

    }

    render ()
    {
        return (
            <div className="row sendBar" style={ { width: '100%', height: '5%' } }>
                {/*     <button className="win-button ms-Grid-col ms-sm3 ms-md2 ms-lg2">Attach</button> */ }
                <input id="TextBoxSend" type="text" className="win-textbox col-md-8" />
                <button id="ButtonSend" className="win-button col-md-4 " onClick={ this.ButtonSendOnClick }>Send</button>
            </div>
        );
    }
}
