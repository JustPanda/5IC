 import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import IconButton from 'material-ui/IconButton';
import CheckIcon from 'material-ui-icons/Check';
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
            verify: true,
            verifyPassoword: '',
            pass: false,
            color: '',
            message: {
                text: '',
                open: false
            }
        };
        this.handleUsername=this.handleUsername.bind(this);
        this.handlePassword=this.handlePassword.bind(this);
        this.handleVerifyPassword=this.handleVerifyPassword.bind(this);
        this.handleBack=this.handleBack.bind(this);
        this.handleRegister=this.handleRegister.bind(this);
        this.handleMessage=this.handleMessage.bind(this);
        ipcRenderer.on('message', this.handleMessage)
    }

    handleUsername(event)
    {
        this.setState({username: event.target.value})
    }

    handlePassword(event)
    {
        var password=event.target.value, state={};
        if(password)
        {
            var regexToPass=[/.{7,}/g, /[0-9]+/g, /[A-Z]+/g],
                colors=['#D50000', '#FFD600', '#00C853'],
                pass=true;
            for(var i=0;i<regexToPass.length&&pass;i++)
            {
                pass=pass&&(new RegExp(regexToPass[i])).test(password);
            }
            i--;
            state.pass=pass;
            state.color=colors[i];
            if(pass)
            {
                state.password=password;
            }
        }else{
            state.color='';
            state.verify=false;
        }
        this.setState(state);
        this.handleVerifyPassword({target: { value: this.state.verifyPassword}});
    }

    handleVerifyPassword(event)
    {
        var verifyPassword=event.target.value, verify;
        if(this.state.password)
        {
            verify=verifyPassword==this.state.password;
            console.log(verifyPassword, verify);
            this.setState({verify: verify, verifyPassword: verifyPassword});
        }
    }

    handleBack()
    {
        ipcRenderer.send('goToLogin', this.client);
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
            this.setState({message: error});
        }
    }

    handleMessage(event, data)
    {
        var text;
        switch(data)
        {
            case 'ok': break;
            case 'ae': text='Already exist'; break;
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
        console.log();
        return (
            <div className={classes.registrationCnt} style={{
                backgroundColor: this.state.bgColor
            }}>
                <div className={classes.textFieldCnt}>
                    <TextField
                        label="Username"
                        SelectProps={{
                            value: 'green'
                        }}
                        InputLabelProps={{
                            value: 'rgba(255, 0, 0, 0.87)'
                        }}
                        fullWidth={true}
                        margin="normal"
                        onChange={this.handleUsername} />
                    <div style={{
                        position: 'relative',
                        display: 'block',
                        width: '100%'}}>
                        <CheckIcon style={{position: 'absolute', right: 0, bottom: 15, fill: this.state.color}} />
                        <TextField
                            style={{
                                color: 'green'
                            }}
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
