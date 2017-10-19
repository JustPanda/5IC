'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import WJS from 'winjs'
import SplitViewPane from "./SplitViewPane";
import SplitViewContent from "./SplitViewContent";

export default class SplitView extends React.Component
{
  constructor()
  {
    super();
    this.setState( { paneOpened: true } );
  }
  getInitialState ()
  {
    return 
    ({
      content: "Home",
      paneOpened: false
    });
  }
  handleAfterClose ()
  {
    
  }
  render ()
  {
    return (
      <div style={ { height: "100%" } }>
        <WinJS.SplitView id={ "splitView" }
          style={ { height: "100%" } }
          paneComponent={ <SplitViewPane /> }
          contentComponent={ <SplitViewContent /> }
          openedDisplayMode ={WJS.UI.SplitView.OpenedDisplayMode.inline}
          onAfterClose={ this.handleAfterClose } 
          paneOpened={true}
          >
        </WinJS.SplitView>
      </div>
    );
  }
}
