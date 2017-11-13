'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import SplitView from "./SplitView.jsx";
import Login from "./Login.jsx"

export default class Main extends React.Component
{
  render ()
  {
    return (
      <div style={ { height: '100%' } }>
        { <SplitView></SplitView> }
      </div>
    );
  }
}
