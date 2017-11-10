'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import UserProfile from "./UserProfile";
import { ipcRenderer } from "electron"

export default class SplitViewPane extends React.Component
{
    constructor ()
    {
        super();
        this.state = { users: [], user:null };
        ipcRenderer.on( "pane", function ( event, arg )
        {
            if(arg.UsernamePerPane!=null && arg.UsernamePerPane!=undefined)
            {
                this.setState({user:arg.UsernamePerPane})
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
                            label={arg[i]}
                            icon="home"
                            onInvoked={ this.handleChangeContent.bind( this, arg[i] ) } />
                        );
                }
                this.setState( { users: usrs } );
            }
           
        }.bind( this ) );
    }

    handleChangeContent ( a, name )
    {
        var User={ToUser:a, Action:"ChangeToUser"};
        ipcRenderer.send("main", User);
    }
    render ()
    {
        return (
            <div>
              {/*  <div style={{background:"azure", height:"75px", alignContent:"center", justifyContent:"center", textAlign:"center"}}>
                    {this.state.user}
        </div> */}
     {   <UserProfile username={this.state.user}></UserProfile> }
                <WinJS.SplitView.Command
                    label="Group"
                    icon="home"
                    onInvoked={ this.handleChangeContent.bind( null, "Group" ) } />
                    { this.state.users }
            </div>
        );
    }
}
