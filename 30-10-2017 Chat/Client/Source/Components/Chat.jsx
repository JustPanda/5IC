'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import ChatContainer from './ChatContainer'
import SendBar from "./SendBar";

export default class Chat extends React.Component
{
    render ()
    {
        return (
            <div>
                <ChatContainer></ChatContainer>
                <SendBar></SendBar>
            </div>
        );
    }
}
