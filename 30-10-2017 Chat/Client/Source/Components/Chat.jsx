'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import MessageUser from './MessageUser';
import MessageOther from './MessageOther';
import SendBar from "./SendBar";

export default class Chat extends React.Component
{
    constructor()
    {
        super();
    }
    getInitialState()
    {
        return {
            messages: [            ]
        }
    }
    addMessage()
    {
        var mes = this.state.messages;
        mes.push
            (
            <div className="row" style={ { width: '100%' } } >
                <MessageOther className="col-md-4" text="TESTO PROVA2" date="20/10/2222"></MessageOther>
                <div className="col-md-8" style={ { background: 'green' } }></div>
            </div>
            );
        mes.push
            (
            <div className="row" style={ { width: '100%' } } >
                <div className="col-md-8" style={ { background: 'green' } }></div>
                <MessageUser className="col-md-4" text="TESTO PROVA" date="20/10/2222" ></MessageUser>
            </div>
            );

            this.setState({messages:mes});
    }
    render()
    {
        this.addMessage();
        return (
            <section id="ChatSection" style={ { width: '100%' } }>
            {this.state.messages}
                {/*    <div className="row" style={ { width: '100%' } } >
                    <MessageOther className="col-md-4" text="TESTO PROVA2" date="20/10/2222"></MessageOther>
                    <div className="col-md-8" style={ { background: 'green' } }></div>
                </div>

                <div className="row" style={ { width: '100%' } } >
                    <div className="col-md-8" style={ { background: 'green' } }></div>
                    <MessageUser className="col-md-4" text="TESTO PROVA" date="20/10/2222" ></MessageUser>
        </div> */}

            </section>
        );
    }
}
