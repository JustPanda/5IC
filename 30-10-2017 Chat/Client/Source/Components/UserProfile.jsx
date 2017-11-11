'use babel';
import React from 'react';

export default class UserProfile extends React.Component
{
    render()
    {
        return (
            <div style={ { height: "150px" , width:"100%", backgroundImage:"url('./Media/Image.png')", backgroundPosition:"center", backgroundRepeat:"no-repeat", backgroundSize:"contain"}}>
                <div style={ { height:"150px" ,color: "white", fontSize: "40", flexDirection: "column",  justifyContent: "center", display:"flex", alignItems:"center"} }>{ this.props.username }</div>
            </div>
        );
    }
}
