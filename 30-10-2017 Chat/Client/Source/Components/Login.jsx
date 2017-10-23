'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class Login extends React.Component
{
    ButtonSendOnClick()
    {

    }

    render()
    {
        return (
            <div className="" style={ { display: 'flex', alignItems:'center', height:"100%", width:"100%", justifyContent:'center', flexDirection:"column"  } }>
                <div className="win-textblock">Username</div>
                <input className="win-textbox" type="text" />
                <div className="win-textblock">Password</div>
                <input className="win-textbox" type="text" />
                <button className="win-button">Conferma</button>
                <a href="#" style={{margin:"20px"}}>Non hai un account? Registrati!</a>
            </div>
        );
    }
}
