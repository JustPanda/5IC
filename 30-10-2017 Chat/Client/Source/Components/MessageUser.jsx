'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class MessageUser extends React.Component
{
  handleAddToRating(a, b)
  {

  }
  handleChangeRating()
  {
    console.log( "pene" );
  }
  render()
  {
    return (
      <div className="MessageUser">
       <div className="Text">Messaggiooooooooooooooooooooooooooooo</div>
       <div className="Date">30/20/2017</div>
      </div>
    )
  }
}