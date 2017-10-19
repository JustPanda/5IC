'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import MessageUser from "./MessageUser"

export default class SendBar extends React.Component
{
    render ()
    {
        return (
            <div className="sendBar">
                <button className="sendButton win-button win-button ">Attach</button>
                <input type="text" className="sendInput win-textbox"/>
                <button className="sendButton win-button win-button ">Send</button>
            </div>
        );
    }
}
