import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import {ipcRenderer} from 'electron';
import net from 'net';

class Login extends React.Component
{
    constructor(props)
    {
        super(props);
        const PORT=8080, IP='127.0.0.1';
        this.state={
            username: '',
            password: '',
            pass: false
        };
        // this.client=new net.connect(PORT, IP);
        this.handleUsername=this.handleUsername.bind(this);
        this.handlePassword=this.handlePassword.bind(this);
        this.handleRegistration=this.handleRegistration.bind(this);
        this.handleLogin=this.handleLogin.bind(this);
        // this.client.on('connect', () => {this.client.send("login")});
        // this.client.on('data',
        //     function(data)
        //     {
        //
        //     }
        // );
        // this.client.on('end',
        //     function()
        //     {
        //         console.log('end');
        //     }
        // )
        // this.client.on('error',
        //     function()
        //     {
        //         console.log("Error");
        //     }
        // );
    }

    handleUsername(event)
    {
        this.setState({username: event.target.value});
    }

    handlePassword(event)
    {
        var password=event.target.value;
        console.log(password);
        if(password)
        {
            var regexToPass=[/.{7,}/g, /[0-9]+/g, /[A-Z]+/g],
                pass=true;
            for(let i=0;i<regexToPass.length&&pass;i++)
            {
                pass&=(new RegExp(regexToPass[i])).test(password);
            }
            this.setState({password: password, pass: pass});
        }
    }

    handleLogin()
    {

    }

    handleRegistration()
    {
        ipcRenderer.send('goToRegistration');
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.loginCnt}>
                <div className={classes.textFieldCnt}>
                    <TextField
                        label="Username"
                        fullWidth={true}
                        margin="normal"
                        onChange={this.handleUsername} />
                    <TextField
                        label="Password"
                        type="password"
                        fullWidth={true}
                        margin="normal"
                        onChange={this.handlePassword} />
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
