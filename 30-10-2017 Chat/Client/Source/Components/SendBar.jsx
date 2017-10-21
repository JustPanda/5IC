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
                <div className="row sendBar" style={ { width: '100%' } }>
              {/*     <button className="win-button ms-Grid-col ms-sm3 ms-md2 ms-lg2">Attach</button> */} 
                    <input type="text" className="win-textbox col-md-8" />
                    <button className="win-button col-md-4 " >Send</button>
                </div>
        );
    }
}
