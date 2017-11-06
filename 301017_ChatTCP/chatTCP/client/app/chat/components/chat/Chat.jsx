import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import Message from './Message.jsx';
import BottomSection from './BottomSection.jsx';

class Chat extends React.Component
{
    constructor(props)
    {
        super(props);
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.chatCnt}>
                <div className={classes.messagesCnt}>
                    {this.props.messages}
                </div>
                <BottomSection updateMessages={this.props.updateMessages} section={this.props.section} />
            </div>
        );
    }
}

Chat.propTypes={
    classes: PropTypes.object.isRequired,
};

const styles={
    chatCnt: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: '100%',
        flexDirection: 'column',
        alignItems: 'center',
    },
    messagesCnt: {
        position: 'relative',
        display: 'flex',
        width: '95%',
        height: '100%',
        flexDirection: 'column',
        overflow: 'auto'
    }
};

export default withStyles(styles)(Chat);
