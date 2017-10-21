import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import Login from './Login.jsx';
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
                <AppBar position="static" color="default">
                    <Toolbar>
                        <Typography type="title" color="inherit">
                            Login
                        </Typography>
                    </Toolbar>
                </AppBar>
                <main className={classes.cnt}>
                    <Login />
                </main>
            </div>
        );
    }
}

App.propTypes={
    classes: PropTypes.object.isRequired,
};

const styles = (theme) => ({
    root: {
        width: '100%'
    },
    cnt: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: 'calc(100% - 64px)',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
    }
});

export default withStyles(styles)(App);
