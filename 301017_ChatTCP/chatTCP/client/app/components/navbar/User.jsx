import React, {Component} from 'react';

class User extends Component
{
    render()
    {
        return (
            <div className='user'>
                <div className='avatar' />
                <div className='infoCnt'>
                    <div className='name'>{this.props.name}</div>
                </div>
            </div>
        );
    }
}

export default User;
