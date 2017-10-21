import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import TextField from 'material-ui/TextField';
import Tooltip from 'material-ui/Tooltip';
import Button from 'material-ui/Button';
import SendIcon from 'material-ui-icons/Send';

class WriteMessage extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            actualText: ''
        };
        this.handleChange=this.handleChange.bind(this);
    }

    handleChange(event)
    {
        this.setState({actualText: event.target.value});
    }

    render()
    {
        const {classes, toggle}=this.props;
        console.log(toggle);
        return (
            <div className={classes.writeMessageCnt}>
                <TextField
                    id="multiline-static"
                    className={classes.textField}
                    label="Type a message"
                    multiline
                    fullWidth={true}
                    value={this.state.actualText}
                    onChange={this.handleChange}
                    margin="normal" />
                <Tooltip id="tooltip-icon" className={classes.send} title="Send" placement="top-end">
                    <Button fab color="primary" aria-label="Send">
                        <SendIcon />
                    </Button>
                </Tooltip>
            </div>
        );
    }
}

WriteMessage.propTypes={
    classes: PropTypes.object.isRequired
};

const styles={
    writeMessageCnt: {
        position: 'absolute',
        width: '60vw',
        display: 'flex',
        bottom: '0px',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundImage: 'black'
    },
    textField: {
        position: 'relative'
    },
    send: {
        position: 'relative'
    }
};

export default withStyles(styles)(WriteMessage);
