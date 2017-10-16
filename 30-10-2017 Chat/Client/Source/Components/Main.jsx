'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import TopAppBar from "./TopAppBar"
import BottomAppBar from "./BottomAppBar"

export default class Main extends React.Component
{
  render()
  {
    return (
      <div>
        <TopAppBar></TopAppBar>
        <BottomAppBar></BottomAppBar>
      </div>        
    ) ;
  }
}
