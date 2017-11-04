'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class SplitViewPane extends React.Component
{
    constructor()
    {
        super();
        this.handleTogglePane=this.handleTogglePane.bind(this);
        this.state= {users:[]};
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
                <div>

                </div>
                <div>
                    
                </div>
            </div>
        );
    }
}
