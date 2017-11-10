'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageOther extends React.Component
{
  render()
  {
    return (
        <div className="messageOther"  style={ { marginTop: "10px", marginBottom: "10px", marginLeft:"20px" } }>
          <div style={{color:"white", fontSize:"20"}}>{this.props.user}</div>
          <div className="messageText" style={{fontSize:"20"}}>{this.props.text}</div>
          <div className="messageDate">{this.props.date}</div>
        </div>
    )
  }
}