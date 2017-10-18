'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import BottomAppBar from "./BottomAppBar"
import MessageUser from "./MessageUser"

export default class SplitViewContent extends React.Component
{
    render ()
    {
        return (
            <div>
                <TopAppBar style={ { alignSelf: 'top' } }></TopAppBar>
                <MessageUser></MessageUser>
                <MessageUser></MessageUser>
                <MessageUser></MessageUser>
                <MessageUser></MessageUser>
                <MessageUser></MessageUser>
                <MessageUser></MessageUser>
                <MessageUser></MessageUser>
                <BottomAppBar style={ { alignSelf: 'bottom' } }></BottomAppBar>
            </div>
        );
    }
}
