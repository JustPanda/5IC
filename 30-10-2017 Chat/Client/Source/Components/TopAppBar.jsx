'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class TopAppBar extends React.Component
{
    handleToggleToolBarSize()
    {
        this.setState( { toolBarIsSmall: !this.state.toolBarIsSmall } );
    }
    handleUpdateResult( res )
    {
        this.setState( { result: res } );
    }
    handleToggleMe( eventObject )
    {
        var toggleCommand = eventObject.currentTarget.winControl;
        this.setState( { toggleSelected: toggleCommand.selected } );
    }
    getInitialState()
    {
        return {
            toolBarIsSmall: false,
            result: null,
            toggleSelected: true
        };
    }
    componentDidUpdate( prevProps, prevState )
    {
        if ( this.state.toolBarIsSmall !== prevState.toolBarIsSmall )
        {
            // Notify the ToolBar that is has been resized.        
            this.refs.toolBar.winControl.forceLayout();
        }
    }
    render()
    {
        var subMenu = (
            <WinJS.Menu>
                <WinJS.Menu.Button
                    key="chooseMeA"
                    label="Or Choose Me"
                    onClick={ this.handleUpdateResult.bind( null, "Or Choose Me" ) } />
                <WinJS.Menu.Button
                    key="chooseMeB"
                    label="No, Choose Me!"
                    onClick={ this.handleUpdateResult.bind( null, "No, Choose Me!" ) } />
            </WinJS.Menu>
        );

        return (
            <div>
                <WinJS.ToolBar ref="toolBar">
                    <WinJS.ToolBar.ContentCommand
                        key="content"
                        icon="settings"
                        label="Impostazioni">
                        <div className="win win-textblock win-interactive userName">Manuele Lucchi</div>
                    </WinJS.ToolBar.ContentCommand>
                    <WinJS.ToolBar.FlyoutCommand
                        key="flyout"
                        icon="settings"
                        label="Settings"
                        flyoutComponent={ subMenu } />
                </WinJS.ToolBar>
            </div>
        );
    }
}