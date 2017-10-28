'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageOther extends React.Component
{
  render()
  {
    return (
      <div className="row" style={{marginTop:"10px", marginBottom:"10px"}}>
        <div className="messageOther col-md-4">
          <div className="messageText" style={{fontSize:"20"}}>{this.props.text}</div>
          <div className="messageDate">{this.props.date}</div>
        </div>
        <div className="col-md-8"></div>
      </div>
    )
  }
}