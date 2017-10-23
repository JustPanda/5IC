'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import SplitView from "./SplitView";
import Login from "./Login"

export default class Main extends React.Component
{
  render()
  {
    return (
      <div style={{height:'100%'}}>
      { /* <SplitView></SplitView> */}
      <Login></Login>
      </div>        
    ) ;
  }
}
