import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import {ipcRenderer} from 'electron';

class Registration extends React.Component
{
    constructor(props)
    {
        super(props);
        this.init=this.init.bind(this);
        ipcRenderer.on('registrationGetClientObj',
            function(event, arg)
            {
                console.log(arg);
                this.init(arg);
            }.bind(this)
        );
    }

    init(client)
    {
        this.client=client;
        this.client.on('connect', () => {this.client.send("login")});
        this.client.on('data',
            function(data)
            {

            }
        );
        this.client.on('end',
            function()
            {
                console.log('end');
            }
        )
        this.client.on('error',
            function()
            {
                console.log("Error");
            }
        );
    }

    handleBack()
    {
        ipcRenderer.send('goToLogin', this.client);
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.registrationCnt}>
                <div className={classes.textFieldCnt}>
                    <TextField
                        label="Username"
                        placeholder="Insert username"
                        fullWidth={true}
                        margin="normal"
                    />
                    <TextField
                        label="Password"
                        placeholder="Insert password"
                        fullWidth={true}
                        type="password"
                        margin="normal"
                    />
                    <TextField
                        label="Verify password"
                        placeholder="Insert again password"
                        fullWidth={true}
                        type="password"
                        margin="normal"
                    />
                </div>
                <div className={classes.buttonsCnt}>
                    <Button raised className={classes.button} onClick={this.handleBack}>
                        Back
                    </Button>
                    <Button raised className={classes.button}>
                        Register
                    </Button>
                </div>
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

export default withStyles(styles)(Registration);
