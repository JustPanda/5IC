import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import TextField from 'material-ui/TextField';
import Tooltip from 'material-ui/Tooltip';
import Button from 'material-ui/Button';
import SendIcon from 'material-ui-icons/Send';

class BottomSection extends React.Component
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
        return (
            <div className={classes.bottomSectionCnt}>
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

BottomSection.propTypes={
    classes: PropTypes.object.isRequired
};

const styles={
    bottomSectionCnt: {
        position: 'absolute',
        width: '100%',
        display: 'flex',
        bottom: '0px',
        flexDirection: 'row',
        // left: 0
    },
    textField: {
        position: 'relative'
    },
    send: {
        position: 'relative'
    }
};

export default withStyles(styles)(BottomSection);
