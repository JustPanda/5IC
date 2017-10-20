import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import MenuIcon from 'material-ui-icons/Menu';
import ResponsiveDrawer from './drawer/ResponsiveDrawer.jsx';
import Chat from './chat/Chat.jsx';
import '../css/app.css';

const drawerWidth = 240;

class App extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            open: false,
        };
        this.listChat={
            'Users': [
                {
                    'name': 'Filippo'
                },
                {
                    'name': 'wdaws'
                },
            ],
            'Groups': [
                {
                    'name': 'Filippo'
                },
                {
                    'name': 'wdaws'
                },
            ]
        };
        this.handleDrawerToggle=this.handleDrawerToggle.bind(this);
    }

    handleDrawerToggle()
    {
        this.setState({ open: !this.state.open });
    }

    render()
    {
        const { classes, theme } = this.props;
        return (
            <div className={classes.root}>
                <div className={classes.appFrame}>
                    <AppBar className={classes.appBar}>
                    <Toolbar>
                        <IconButton
                        color="contrast"
                        aria-label="open drawer"
                        onClick={this.handleDrawerToggle}
                        className={classes.navIconHide}>
                            <MenuIcon />
                        </IconButton>
                            <Typography type="title" color="inherit" noWrap>
                                Chat TCP
                            </Typography>
                        </Toolbar>
                    </AppBar>
                    <ResponsiveDrawer open={this.state.open}
                         handleDrawerToggle={this.handleDrawerToggle}
                         listChat={this.listChat} />
                    <main className={classes.content}>
                        <Chat toggle={this.state.open} />
                    </main>
                </div>
            </div>
        );
    }
}

App.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

const styles=(theme) => ({
    root: {
        width: '100%',
        height: '100%',
        zIndex: 1,
        overflow: 'hidden',
    },
    appFrame: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: '100%',
    },
    appBar: {
        position: 'absolute',
        marginLeft: drawerWidth,
        [theme.breakpoints.up('md')]: {
            width: `calc(100% - ${drawerWidth}px)`,
        },
    },
    navIconHide: {
        [theme.breakpoints.up('md')]: {
            display: 'none',
        },
    },
    drawerHeader: theme.mixins.toolbar,
    content: {
        width: '100%',
        height: 'calc(100% - 56px)',
        backgroundImage: 'url(app/images/background.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        padding: theme.spacing.unit * 3,
        marginTop: 56,
        [theme.breakpoints.up('sm')]: {
            height: 'calc(100% - 64px)',
            marginTop: 64,
        },
    }
});

export default withStyles(styles, { withTheme: true })(App);
