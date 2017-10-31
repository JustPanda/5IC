'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar"
import MessageUser from "./MessageUser"
import MessageOther from "./MessageOther";

export default class SendBar extends React.Component
{
    constructor()
    {
        super();
        this.ButtonSendOnClick = this.ButtonSendOnClick.bind( this );
        //this.state = { a: true };
    }
    ButtonSendOnClick ()
    {
        var text = document.getElementById( "TextBoxSend" ).value;
        if(text==""||text==null)
        {
            return;
        }
        document.getElementById( "TextBoxSend" ).value ="";
        var d = new Date();
        var date = d.getHours() + ":" +d.getMinutes() + "," + d.getDate() + "/" + d.getMonth() +"/" + d.getFullYear();
        var Message ={User:"", Text:text, Date:date};
     /*   if ( this.state.a == true )
        {
            Message = text;
        }
        else
        {
            Message = text;            
        } */

    //    this.setState({a: !this.state.a})
        this.props.method( Message );

    }

    render ()
    {
        return (
            <div className="row sendBar" style={ { width: '100%', height: '5%' } }>
                <input id="TextBoxSend" type="text" className="win-textbox col-md-8" style={{marginLeft:"40px", marginRight:"20px"}}/>
                <button id="ButtonSend" className="win-button col-md-4 " onClick={ this.ButtonSendOnClick } style={{marginRight:"20px", marginTop:"5px", marginBottom:"5px", width:"75px"}}>Send</button>
            </div>
        );
    }
}
