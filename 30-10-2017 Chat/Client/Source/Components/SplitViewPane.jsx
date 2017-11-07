'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import { ipcRenderer } from "electron"

export default class SplitViewPane extends React.Component
{
    constructor ()
    {
        super();
        this.handleTogglePane = this.handleTogglePane.bind( this );
        this.state = { users: [] };
        ipcRenderer.on( "pane", function ( event, arg )
        {
            console.log( "mi è arrivato " + arg )
            var usrs = []
            for ( var i = 0; i < arg.length; i++ )
            {
                usrs.push
                    (
                    <WinJS.SplitnView.Command
                        label={arg[i]}
                        icon="home"
                        onInvoked={ this.handleChangeContent.bind( null, arg[i] ) } ></WinJS.SplitView.Command>
                    );
            }
            this.setState( { users: usrs } );
        }.bind( this ) );
    }

    handleChangeContent ( a, name )
    {

    }
    render ()
    {
        return (
            <div>
                <WinJS.SplitView.Command
                    label="Group"
                    icon="home"
                    onInvoked={ this.handleChangeContent.bind( null, "Group" ) } />
                <div>
                    { this.state.users }
                </div>
            </div>
        );
    }
}
