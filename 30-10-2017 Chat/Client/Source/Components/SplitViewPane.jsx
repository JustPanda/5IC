'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class SplitViewPane extends React.Component
{
    constructor()
    {
        super();
        this.handleTogglePane=this.handleTogglePane.bind(this);
        this.state= {paneOpened:true};
    }
    /*getInitialState()
    {
        return {
            paneOpened: true,
        }
    } */
    handleTogglePane()
    {
        this.setState( { paneOpened: !this.state.paneOpened } );
    }
    handleChangeContent( a, name )
    {

    }
    render()
    {
        return (
            <div> {/*
                <div>
                    <WinJS.SplitViewPaneToggle
                        aria-controls={ "splitView" }
                        paneOpened={ this.state.paneOpened }
                        onInvoked={ this.handleTogglePane } >
                    </WinJS.SplitViewPaneToggle>
            </div> */}
                <WinJS.SplitView.Command
                    label="Home"
                    icon="home"
                    onInvoked={ this.handleChangeContent.bind( null, "Home" ) } >
                </WinJS.SplitView.Command>
                <WinJS.SplitView.Command
                    label="Favorite"
                    icon="favorite"
                    onInvoked={ this.handleChangeContent.bind( null, "Favorite" ) } >
                </WinJS.SplitView.Command>
                <WinJS.SplitView.Command 
                    label="Settings"
                    icon="settings"
                    onInvoked={ this.handleChangeContent.bind( null, "Settings" ) } >
                </WinJS.SplitView.Command>
            </div>
        );
    }
}
