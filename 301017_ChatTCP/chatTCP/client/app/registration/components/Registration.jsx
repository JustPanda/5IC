 import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import IconButton from 'material-ui/IconButton';
import LockIcon from 'material-ui-icons/LockOutline';
import LockOpenIcon from 'material-ui-icons/LockOpen';
import CloseIcon from 'material-ui-icons/Close';
import Snackbar from 'material-ui/Snackbar';
import {ipcRenderer} from 'electron';

class Registration extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            username: '',
            password: '',
            pass: false,
            verify: false,
            verifypassoword: '',
            color: '',
            popup: {
                text: 'Password must be at least 7 characters long with a capital letter and a capital letter',
                open: true
            }
        };
        this.colors=['#D50000', '#FFD600', '#00C853'];
        this.handleUsername=this.handleUsername.bind(this);
        this.handlePassword=this.handlePassword.bind(this);
        this.handleVerifyPassword=this.handleVerifyPassword.bind(this);
        this.handleBack=this.handleBack.bind(this);
        this.handleRegister=this.handleRegister.bind(this);
        this.handleRequestClose=this.handleRequestClose.bind(this);
        this.handleMessage=this.handleMessage.bind(this);
        this.clean=this.clean.bind(this);
        ipcRenderer.on('message', this.handleMessage);
        ipcRenderer.on('clean', this.clean);
    }

    handleUsername(event)
    {
        this.setState({username: event.target.value})
    }

    handlePassword(event)
    {
        var password=event.target.value;
        if(password)
        {
            var regexToPass=[/.{7,}/g, /[0-9]+/g, /[A-Z]+/g],
                pass=true;
            for(var i=0;i<regexToPass.length&&pass;i++)
            {
                pass=pass&&(new RegExp(regexToPass[i])).test(password);
            }
            this.state.pass=pass;
            this.state.color=this.colors[--i];
            this.state.password=password;
        }else{
            this.state.color='';
            this.state.verify=false;
        }
        this.handleVerifyPassword({target: { value: this.state.verifyPassword}});
    }

    handleVerifyPassword(event)
    {
        var verifyPassword=event.target.value;
        this.setState({verify: this.state.pass&&verifyPassword==this.state.password, verifyPassword: verifyPassword});
    }

    handleBack()
    {
        ipcRenderer.send('login', this.client);
    }

    handleRegister()
    {
        var error;
        if(this.state.username)
        {
            if(this.state.pass)
            {
                if(this.state.verify)
                {
                    ipcRenderer.send('sendRegister', `{"username": "${this.state.username}", "password": "${this.state.password}"}`);
                }else{
                    error={
                        open: true,
                        text: 'Wrong verify password'
                    };
                }
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
            this.setState({popup: error});
        }
    }

    handleRequestClose(event, reason)
    {
        if(reason!=='clickaway')
        {
            this.setState({popup: { open: false }});
        }
    }

    handleMessage(event, data)
    {
        var text;
        switch(data)
        {
            case 'ok': ipcRenderer.send('chat', this.state.username); break;
            case 'ae': text='Already exist'; break;
            case 'al': text='Already logged'; break;
        }
        if(text)
        {
            this.setState({popup: {
                text: text,
                open: true
            }});
        }
    }

    clean()
    {
        this.setState({popup: {
            text: 'Password must be at least 7 characters long with a capital letter and a capital letter',
            open: true
        }});
    }

    render()
    {
        const {classes}=this.props;
        console.log();
        return (
            <div className={classes.registrationCnt}>
                <div className={classes.textFieldCnt}>
                    <TextField
                        label="Username"
                        fullWidth={true}
                        margin="normal"
                        onChange={this.handleUsername} />
                    <div className={classes.passwordCnt}>
                        {(this.state.color==this.colors[this.colors.length-1]?<LockOpenIcon className={classes.lockStyle} style={{fill: this.state.color}} />:<LockIcon className={classes.lockStyle} style={{fill: this.state.color}} />)}
                        <TextField
                            label="Password"
                            fullWidth={true}
                            type="password"
                            margin="normal"
                            onChange={this.handlePassword} />
                    </div>
                    <TextField
                        label="Verify password"
                        fullWidth={true}
                        type="password"
                        margin="normal"
                        error={!this.state.verify}
                        onChange={this.handleVerifyPassword} />
                </div>
                <div className={classes.buttonsCnt}>
                    <Button raised className={classes.button} onClick={this.handleBack}>
                        Back
                    </Button>
                    <Button raised className={classes.button} onClick={this.handleRegister}>
                        Register
                    </Button>
                </div>
                <Snackbar
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'left',
                    }}
                    open={this.state.popup.open}
                    autoHideDuration={2500}
                    onRequestClose={this.handleRequestClose}
                    SnackbarContentProps={{
                        'aria-describedby': 'message-id',
                    }}
                    message={<span id="message-id">{this.state.popup.text}</span>}
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

Registration.propTypes={
    classes: PropTypes.object.isRequired
};

const styles={
    registrationCnt: {
        display: 'flex',
        width: '60%',
        height: '75%',
        flexDirection: 'column',
        justifyContent: 'space-between'
    },
    passwordCnt: {
        position: 'relative',
        display: 'block',
        width: '100%'
    },
    lockStyle: {
        position: 'absolute',
        right: 0,
        bottom: 15
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

export default withStyles(styles)(Registration);
