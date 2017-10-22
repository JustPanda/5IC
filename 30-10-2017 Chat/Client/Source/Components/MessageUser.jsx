'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageUser extends React.Component
{
  render()
  {
    return (
        <div className="messageUser">
          <div className="messageText">{ this.props.text }</div>
          <div className="messageDate">{ this.props.date }</div>
        </div>
    )
  }
}