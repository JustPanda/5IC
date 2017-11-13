import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import Typography from 'material-ui/Typography';
import Button from 'material-ui/Button';
import ErrorIcon from 'material-ui-icons/Error';
import '../css/app.css';

class App extends React.Component
{
    constructor(props)
    {
        super(props);
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.root}>
                <ErrorIcon className={classes.errorIcon}/>
                <Typography type='title' align='right'>
                    Error connecting to server
                </Typography>
            </div>
        );
    }
}

App.propTypes={
    classes: PropTypes.object.isRequired,
};

const styles={
    root: {
        display: 'flex',
        width: '100%',
        height: '100vh',
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
    },
    errorIcon: {
        fill: '#D50000',
        marginRight: '5%'
    }
};

export default withStyles(styles)(App);
