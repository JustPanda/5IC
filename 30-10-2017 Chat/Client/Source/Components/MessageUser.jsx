'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageUser extends React.Component
{
  render ()
  {
    return (
      <div className="messageUser" style={ { marginTop: "10px", marginBottom: "10px" } }>
      <div>{this.props.user}</div>
        <div className="messageText" style={ { fontSize:"20" } }>{ this.props.text }</div>
        <div className="messageDate">{ this.props.date }</div>
      </div>
    )
  }
}