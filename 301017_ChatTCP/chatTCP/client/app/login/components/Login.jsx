import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import {ipcRenderer} from 'electron';

class Login extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            username: '',
            password: ''
        };
        this.handleUsername=this.handleUsername.bind(this);
        this.handleRegistration=this.handleRegistration.bind(this);
        this.handleLogin=this.handleLogin.bind(this);
    }

    handleRegistration()
    {
        ipcRenderer.send('registration');
    }

    handleUsername(event)
    {
        this.setState({username: event.target.value});
    }

    handlePassword(event)
    {
        this.setState({password: event.target.value});
    }

    handleLogin()
    {
        
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.loginCnt}>
                <div className={classes.textFieldCnt}>
                    <TextField
                        label="Username"
                        placeholder="Insert username"
                        fullWidth={true}
                        margin="normal"
                        onChange={this.handleUsername} />
                    <TextField
                        label="Password"
                        placeholder="Insert password"
                        type="password"
                        fullWidth={true}
                        margin="normal"
                        onChange={this.handleRegistration} />
                </div>
                <div className={classes.buttonsCnt}>
                    <Button raised className={classes.button} onClick={this.handleRegistration}>
                        Registration
                    </Button>
                    <Button raised className={classes.button} onClick={this.handleLogin}>
                        Login
                    </Button>
                </div>
            </div>
        );
    }
}

Login.propTypes={
    classes: PropTypes.object.isRequired
};

const styles={
    loginCnt: {
        display: 'flex',
        width: '60%',
        height: '80%',
        flexDirection: 'column',
        justifyContent: 'space-between'
    },
    textFieldCnt: {
        width: '100%',
        height: 'auto'
    },
    buttonsCnt: {
        display: 'flex',
        width: '100%',
        height: 'auto',
        flexDirection: 'row',
        justifyContent: 'space-between'
    }
};

export default withStyles(styles)(Login);
