import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from 'material-ui/styles';
import Paper from 'material-ui/Paper';
import Typography from 'material-ui/Typography';
import Divider from 'material-ui/Divider';

class Message extends React.Component
{
    constructor(props)
    {
        super(props);
    }

    render()
    {
        const {classes, info}=this.props;
        return(
            <div className={classes.messageCnt}>
                <Paper className={classes.message} elevation={4} style={{
                    float: info.orientation
                }}>
                    {
                        (function(name){
                            if(name)
                            {
                                return (
                                    <div>
                                        <Typography  type="title" gutterBottom>{name}</Typography>
                                        <Divider />
                                    </div>
                                );
                            }
                        })(info.name)
                    }
                    <Typography type="body2" gutterBottom>
                        {info.text}
                    </Typography>
                    <Typography type="caption" gutterBottom align="right">
                        {info.date}
                    </Typography>
                </Paper>
            </div>
        );
    }
}

Message.propTypes={
    classes: PropTypes.object.isRequired,
};

const style={
    messageCnt: {
        position: 'relative',
        width: '100%',
        marginBottom: '3%'
    },
    message: {
        width: '40%'
    }
};

export default withStyles(style)(Message);
