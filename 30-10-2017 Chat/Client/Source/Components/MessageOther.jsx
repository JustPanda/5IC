'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageOther extends React.Component
{
  render()
  {
    return (
      <div className="row" >
        <div className="messageOther col-md-4">
          <div className="messageText">{this.props.text}</div>
          <div className="messageDate">{this.props.date}</div>
        </div>
        <div className="col-md-8" style={{background:'green'}}></div>
      </div>
    )
  }
}