'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageOther extends React.Component
{
  handleAddToRating ( a, b )
  {

  }
  handleChangeRating ()
  {
    console.log( "pene" );
  }
  render ()
  {
    return (
      <div className="messageOther">
       <div className="messageText">Messaggiooooooooooooooooooooooooooooo</div>
       <div className="messageDate">30/20/2017</div>
      </div>
    ) 
  }
}