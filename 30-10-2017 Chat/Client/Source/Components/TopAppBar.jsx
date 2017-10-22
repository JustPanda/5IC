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
        return (
            <div className="row" style={{height:'10%'}}>
                <img className="col-md-4" src="http://www.starcoppe.it/images/grafica-immagine-b.jpg" style={{height:'50px'}}/>
                <div className="col-md-8" style={{height:'50px'}}>UTENTE CAVALLO</div>
            </div>
        );
    }
}