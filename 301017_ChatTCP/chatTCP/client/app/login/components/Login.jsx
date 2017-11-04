import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import IconButton from 'material-ui/IconButton';
import CloseIcon from 'material-ui-icons/Close';
import Snackbar from 'material-ui/Snackbar';
import {ipcRenderer} from 'electron';

class Login extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            username: '',
            password: '',
            pass: false,
            message: {
                text: '',
                open: false
            }
        };
        this.handleUsername=this.handleUsername.bind(this);
        this.handlePassword=this.handlePassword.bind(this);
        this.handleRegistration=this.handleRegistration.bind(this);
        this.handleLogin=this.handleLogin.bind(this);
        this.handleRequestClose=this.handleRequestClose.bind(this);
        this.handleMessage=this.handleMessage.bind(this);
        ipcRenderer.on('message', this.handleMessage);
    }

    handleUsername(event)
    {
        this.setState({username: event.target.value});
    }

    handlePassword(event)
    {
        var password=event.target.value;
        if(password)
        {
            var regexToPass=[/.{7,}/g, /[0-9]+/g, /[A-Z]+/g],
                state={},
                pass=true;
            for(let i=0;i<regexToPass.length&&pass;i++)
            {
                pass=pass&&(new RegExp(regexToPass[i])).test(password);
            }
            state.pass=pass;
            if(pass)
            {
                state.password=password;
            }
            this.setState(state);
        }
    }

    handleLogin()
    {
        var error;
        if(this.state.username)
        {
            if(this.state.pass)
            {
                ipcRenderer.send('sendLogin', `{"username": "${this.state.username}", "password": "${this.state.password}"}`);
            }else{
                error={
                    open: true,
                    text: 'Wrong password'
                };
            }
        }else{
            error={
                open: true,
                text: 'Missing username'
            };
        }
        if(error)
        {
            this.setState({message: error});
        }
    }

    handleRegistration()
    {
        ipcRenderer.send('registration');
    }

    handleRequestClose(event, reason)
    {
        if(reason!=='clickaway')
        {
            this.setState({message: { open: false }});
        }
    }

    handleMessage(event, data)
    {
        var text;
        switch(data)
        {
            case 'ok': ipcRenderer.send('chat', this.state.username); break;
            case 'ne': text='User not exist'; break;
            case 'wp': text='Wrong password'; break;
            case 'al': text='Already logged'; break;
        }
        if(text)
        {
            this.setState({message: {
                text: text,
                open: true
            }});
        }
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
                <Snackbar
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    open={this.state.message.open}
                    autoHideDuration={2500}
                    onRequestClose={this.handleRequestClose}
                    SnackbarContentProps={{
                        'aria-describedby': 'message-id',
                    }}
                    message={<span id="message-id">{this.state.message.text}</span>}
                    action={[
                        <Button key="undo" color="accent" dense onClick={this.handleRequestClose}>
                            UNDO
                        </Button>,
                        <IconButton
                            key="close"
                            aria-label="Close"
                            color="inherit"
                            className={classes.close}
                            onClick={this.handleRequestClose} >
                            <CloseIcon />
                        </IconButton>
                    ]} />
            </div>
        );
    }
}

Login.propTypes={
    classes: PropTypes.object.isRequired
};

const styles=(theme) => ({
    loginCnt: {
        display: 'flex',
        width: '60%',
        height: '75%',
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
    },
    close: {
        width: theme.spacing.unit * 4,
        height: theme.spacing.unit * 4
    }
});

export default withStyles(styles)(Login);
