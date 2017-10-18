'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import SplitView from "./SplitView";

export default class Main extends React.Component
{
  render()
  {
    return (
      <div>
        <SplitView></SplitView>
      </div>        
    ) ;
  }
}
