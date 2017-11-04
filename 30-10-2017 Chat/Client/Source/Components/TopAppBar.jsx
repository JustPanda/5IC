'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class TopAppBar extends React.Component
{
    render()
    {
        return (
            <div className="row" style={{height:'50px',  display:'flex', flexDirection:'row', justifyContent:'center', alignItems:"center", background:"gray"}}>
                <img className="col-md-4" src="http://www.starcoppe.it/images/grafica-immagine-b.jpg" style={{height:'50px', width:'50px'}}/>
                <div className="col-md-8" style={{height:'50px',}}>{this.props.name}</div>
            </div>
        );
    }
}