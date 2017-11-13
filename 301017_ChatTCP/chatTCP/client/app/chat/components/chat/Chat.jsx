import React from 'react';
import ReactDOM from 'react-dom';
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

    componentDidMount()
    {
        this.scrollToBottom();
    }

    componentDidUpdate()
    {
        this.scrollToBottom();
    }

    scrollToBottom()
    {
        const node=ReactDOM.findDOMNode(this.messagesCnt);
        node.scrollTop = node.scrollHeight;
    }


    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.chatCnt}>
                <div className={classes.messagesCnt} ref={(el) => {this.messagesCnt=el}}>
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
