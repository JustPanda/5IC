'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class SplitViewPane extends React.Component
{
    constructor()
    {
        super();
        this.setState({paneOpened: false});
    }
    handleTogglePane()
    {
        this.state.paneOpened=true;
    }
    handleChangeContent(a, name)
    {

    }
    render ()
    {
        return (
            <div>
                <WinJS.SplitViewPaneToggle
                    aria-controls={ "splitView" }
                    paneOpened={true}
                    onInvoked={ this.handleTogglePane } >
                </WinJS.SplitViewPaneToggle>
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
