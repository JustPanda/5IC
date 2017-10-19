import React, {Component} from 'react';
import User from './User.jsx';
import '../../css/navbar.css';

class Navbar extends Component
{
    constructor(props)
    {
        super(props);
        this.state={
            show: true
        };
        this.changeState=this.changeState.bind(this);
    }

    createList(key, chat)
    {
        return(chat[key].map(
                    function(item, index)
                    {
                        return (
                            <User key={index}
                                name={item.name} />
                        );
                    }
                )
            );
    }

    changeState()
    {
        this.setState(
            function(prev)
            {
                return {
                    show: !prev.show
                };
            }
        );
    }


    render()
    {
        return (
            <div id='chatGroup'>
                <div className='label' onClick={this.changeState}>Users</div>
                <div id='users' style={{
                    'maxHeight': this.state.show?'100%':'0%'
                }}>
                    <div className='cnt'>
                        {this.createList('users', this.props.chat)}
                    </div>
                </div>
                {/* <div className='label'>Groups</div> */}
                <div id='groups'>
                    {/* {this.createList('groups', this.props.chat)} */}
                </div>
            </div>
        );
    }
}

export default Navbar;
