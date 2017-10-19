'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import MessageOther from "./MessageOther"
import MessageUser from "./MessageUser"

export default class ChatContainer extends React.Component
{
    render ()
    {
        return (
            <div className="scrollBar">
                <MessageOther></MessageOther>
                <MessageUser></MessageUser>
                <MessageOther></MessageOther>
                <MessageUser></MessageUser>
                <MessageOther></MessageOther>
                <MessageUser></MessageUser>
                <MessageOther></MessageOther>
                <MessageUser></MessageUser>
            </div>
        );
    }
}
