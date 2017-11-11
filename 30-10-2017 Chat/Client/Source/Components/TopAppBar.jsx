'use babel';
import React from 'react';
import WinJS from 'react-winjs';

export default class TopAppBar extends React.Component
{
    render()
    {
        return (
            <div style={{height:'60px',  display:'flex', flexDirection:'row', justifyContent:'center', alignItems:"left", background:"linear-gradient(to right, black, blue, royalblue, purple, fuchsia, violet)"}}>
                <div style={{height:'100%',marginLeft:"20px", color:"white", display:'flex', flexDirection:'column', justifyContent:'center', alignItems:"center"}}>{this.props.name}</div>
            </div>
        );
    }
}