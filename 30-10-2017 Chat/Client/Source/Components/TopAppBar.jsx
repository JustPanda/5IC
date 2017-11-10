'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class TopAppBar extends React.Component
{
    render()
    {
        return (
            <div style={{height:'50px',  display:'flex', flexDirection:'row', justifyContent:'center', alignItems:"center", background:"rgb(25, 25, 25)"}}>
                <img data-grid="col-3" src="http://www.starcoppe.it/images/grafica-immagine-b.jpg" style={{height:'50px', width:'50px'}}/>
                <div data-grid="col-9" style={{height:'50px',marginLeft:"20px", color:"white"}}>{this.props.name}</div>
            </div>
        );
    }
}