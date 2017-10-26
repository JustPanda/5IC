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
        this.updateMessages=this.updateMessages.bind(this);
    }

    handleChange(event)
    {
        this.setState({actualText: event.target.value});
    }

    updateMessages()
    {
        if(this.state.actualText.trim())
        {
            let text=this.state.actualText.split("\n").map((item, index) => {return (<span key={index}>{item}<br /></span>)});
            let d=new Date();
            let min=d.getMinutes();
            if (min < 10) {
                min = "0" + min;
            }
            this.props.updateMessages({
                orientation: 'right',
                text: text,
                date: d.getHours()+":"+min
            });
            this.setState({actualText: ''});
        }
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.bottomSectionCnt}>
                <div className={classes.bottomSection}>
                    <TextField
                        id="multiline-static"
                        className={classes.textField}
                        label="Message"
                        multiline
                        fullWidth={true}
                        value={this.state.actualText}
                        onChange={this.handleChange}
                        margin="normal" />
                        <Tooltip id="tooltip-icon" className={classes.send} title="Send" placement="top-end">
                            <Button fab color="primary" aria-label="Send" onClick={this.updateMessages}>
                                <SendIcon />
                            </Button>
                        </Tooltip>
                </div>
            </div>
        );
    }
}

BottomSection.propTypes={
    classes: PropTypes.object.isRequired
};

const styles=(theme) => ({
    bottomSectionCnt: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        bottom: '0px',
        flexDirection: 'column',
        alignItems: 'center',
    },
    bottomSection: {
        position: 'relative',
        display: 'flex',
        width: '98%',
        flexDirection: 'row',
    },
    textField: {
        position: 'relative',
    },
    send: {
        position: 'relative'
    }
});

export default withStyles(styles)(BottomSection);
