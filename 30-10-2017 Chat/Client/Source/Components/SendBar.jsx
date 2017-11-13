'use babel';
import React from 'react';
import WinJS from 'react-winjs';

import TopAppBar from "./TopAppBar.jsx"
import MessageUser from "./MessageUser.jsx"
import MessageOther from "./MessageOther.jsx";

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
        var Message ={Username:"", Text:text, Date:date};

        this.props.method( Message );

    }

    render ()
    {
        return (
            <div className="row sendBar" style={ { width: '100%', height: '50px', marginLeft:"0px"} }>
                <input id="TextBoxSend" type="text" for="defaultFlex" className="c-text-field f-flex sendInput" data-grid="col-8" style={{marginLeft:"20px", marginRight:"20px", marginTop:"7px"}}/>
                <button id="ButtonSend" data-grid="col-4" className="c-button f-primary sendButton" placeholder="Inserisci il messaggio"  onClick={ this.ButtonSendOnClick } style={{marginRight:"20px", marginTop:"7px", marginBottom:"8px"}}>Send</button>
            </div>
        );
    }
}
