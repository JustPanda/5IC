'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import Chat from "./Chat"
import SendBar from "./SendBar"

export default class SplitViewContent extends React.Component
{
    render ()
    {
        return (
            <div>
                <TopAppBar style={ { alignSelf: 'top' } }></TopAppBar>
                <Chat  className="scrollBar" ></Chat>
                <SendBar ></SendBar>
            </div>
        );
    }
}
