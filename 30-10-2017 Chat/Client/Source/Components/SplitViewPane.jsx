'use babel';
import React from 'react';
import WinJS from 'react-winjs';
import {ipcRenderer} from "electron"

export default class SplitViewPane extends React.Component
{
    constructor()
    {
        super();
        this.handleTogglePane=this.handleTogglePane.bind(this);
        this.state= {users:[]};
        ipcRenderer.on("pane", function(event, arg)
        {
            console.log("mi è arrivato " + arg)
            var usrs = []
            for(var i=0; i<arg.length; i++)
            {
                usrs.push(<div style={{height:"75px"}}>{arg[i]}</div>);
            }
            this.setState({users:usrs});
        }.bind(this));
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
            <div> 
                <div style={{height:"75px"}}>
                    Group
                </div>
                <div>
                   {this.state.users} 
                </div>
            </div>
        );
    }
}
