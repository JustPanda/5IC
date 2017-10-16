'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class Main extends React.Component
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
      <div>
        <div>
          <button className="win-button" onClick={ this.handleAddToRating.bind( null, -1 ) }>-1</button>
          <button className="win-button" onClick={ this.handleAddToRating.bind( null, 1 ) }>+1</button>
        </div>
        <p>Current Rating: { 4}</p>

        <WinJS.Rating
          userRating={ 4}
          maxRating={ 8 }
          onChange={ this.handleChangeRating } />
      </div>
    ) /*<div>Hello from React with ES6 :)</div>; */
  }
}