'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import MessageUser from './MessageUser';
import MessageOther from './MessageOther';
import SendBar from "./SendBar";

export default class Chat extends React.Component
{
    render()
    {
        return (
            <section style={{width:'100%'}}>
                <div className="row" style={{width:'100%'}}>
                    <MessageOther className="col-md-4"></MessageOther>
                    <div className="col-md-8"></div>
                </div>

                <div className="row" style={{width:'100%'}}>
                    <div className="col-md-8"></div>
                    <MessageUser className="col-md-4"></MessageUser>
                </div>

            </section>
        );
    }
}
