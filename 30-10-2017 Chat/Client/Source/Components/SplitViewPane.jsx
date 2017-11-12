'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import UserProfile from "./UserProfile";
import { ipcRenderer } from "electron"

export default class SplitViewPane extends React.Component
{
    constructor()
    {
        super();
        this.state = { users: [], user: null };
        ipcRenderer.on( "pane", function ( event, arg )
        {
            if ( arg.UsernamePerPane != null && arg.UsernamePerPane != undefined )
            {
                this.setState( { user: arg.UsernamePerPane } )
            }
            else
            {
                console.log( "mi è arrivato " + arg )
                var usrs = []
                for ( var i = 0; i < arg.length; i++ )
                {
                    usrs.push
                        (
                        <WinJS.SplitView.Command
                            label={ arg[ i ] }
                            icon="contact"
                            onInvoked={ this.handleChangeContent.bind( this, arg[ i ] ) } />
                        );
                }
                this.setState( { users: usrs } );
            }

        }.bind( this ) );
    }

    handleChangeContent( a, name )
    {
        var User = { ToUser: a, Action: "ChangeToUser" };
        ipcRenderer.send( "main", User );
    }
    render()
    {
        return (
            <div>
                <UserProfile username={ this.state.user }></UserProfile>
                <WinJS.SplitView.Command
                    label="Group"
                    icon="contact"
                    onInvoked={ this.handleChangeContent.bind( null, "Group" ) } />
                    <div style={{height:"1px", background:"gray", width:"90%", marginLeft:"5%", marginRight:"5%", marginTop:"10px", marginBottom:"10px"}}></div>
                { this.state.users }
            </div>
        );
    }
}
