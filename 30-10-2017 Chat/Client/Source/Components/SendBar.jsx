'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import MessageUser from "./MessageUser"

export default class SendBar extends React.Component
{
    render()
    {
        return (
            <div className="sendBar ms-Grid">
                <div className="ms-Grid-row" style={ { width: '100%' } }>
                    <button className="win-button ms-Grid-col ms-sm3 ms-md2 ms-lg2">Attach</button>
                    <input type="text" className="win-textbox ms-Grid-col ms-sm6 ms-md8 ms-lg8" />
                    <button className="win-button ms-Grid-col ms-sm3 ms-md2 ms-lg2">Send</button>
                </div>
            </div>
        );
    }
}
