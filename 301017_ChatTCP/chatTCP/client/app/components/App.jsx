import React, {Component} from 'react';
import Navbar from './navbar/Navbar.jsx';
import '../css/app.css';

class App extends Component
{
    constructor()
    {
        super();
        this.chat={
            "users": [
                {
                    "name": "Filippo Fantinato"
                },
                {
                    "name": "aaaa"
                },
                {
                    "name": "sanata"
                }
            ]
        };
    }

    render()
    {
        return (
            <div>
                <Navbar chat={this.chat} />
            </div>
        );
    }
}

export default App;
