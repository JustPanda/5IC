import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import Paper from 'material-ui/Paper';
import Typography from 'material-ui/Typography';
import classNames from 'classnames';
import Message from './Message.jsx';
import BottomSection from './BottomSection.jsx';

class Chat extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            messages: [],
            index: 0
        };
        this.updateMessages=this.updateMessages.bind(this);
    }

    updateMessages(info)
    {
        this.setState(
            function(prevState)
            {
                prevState.messages.push(
                    <Message key={++prevState.index} info={info} />
                );
                return {
                    messages: prevState.messages,
                    index: prevState.index
                };
            }
        );
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.chatCnt}>
                <div style={{
                    position: 'relative',
                    display: 'flex',
                    width: '95%',
                    height: '100%',
                    flexDirection: 'column-reverse',
                    overflow: 'vertical'
                }}>
                    {this.state.messages}
                </div>
                <BottomSection updateMessages={this.updateMessages} />
            </div>
        );
    }
}

Chat.propTypes = {
    classes: PropTypes.object.isRequired,
};

const styles={
    chatCnt: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: '100%',
        flexDirection: 'column',
        alignItems: 'center'
    }
};

export default withStyles(styles)(Chat);
