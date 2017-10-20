import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import TextField from 'material-ui/TextField';
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
        const {classes}=this.props;
        console.log(this.state.actualText);
        return (
            <div className={classes.writeMessageCnt} style={{
                    width: this.props.toggle?'75%':'100%'
                }}>
                <TextField
                    id="multiline-static"
                    label="Type a message"
                    multiline
                    className={classes.textField}
                    value={this.state.actualText}
                    onChange={this.handleChange}
                    margin="normal" />
                <Button fab color="primary" aria-label="Send" className={classes.send}>
                    <SendIcon />
                </Button>
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
        display: 'flex',
        bottom: 0,
        height: 'auto',
        flexDirection: 'row',
    },
    textField: {
        width: '80%',
    },
};

export default withStyles(styles)(WriteMessage);
