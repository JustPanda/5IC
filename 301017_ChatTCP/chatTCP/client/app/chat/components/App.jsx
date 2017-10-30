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
            titleBar: 'Global chat'
        };
        this.listChat={
            'users': [
                {
                    'name': 'Filippo'
                },
                {
                    'name': 'wdaws'
                },
            ]
        };
        this.handleDrawerToggle=this.handleDrawerToggle.bind(this);
        this.changeChat=this.changeChat.bind(this);
    }

    handleDrawerToggle()
    {
        this.setState({ open: !this.state.open });
    }

    changeChat(objChat)
    {
        this.setState({titleBar: objChat.name});
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
                                className={classes.navIconHide}
                                aria-label="open drawer"
                                onClick={this.handleDrawerToggle}>
                                <MenuIcon />
                            </IconButton>
                            <Typography type="title" noWrap>
                                {this.state.titleBar}
                            </Typography>
                        </Toolbar>
                    </AppBar>
                    <ResponsiveDrawer open={this.state.open}
                         handleDrawerToggle={this.handleDrawerToggle}
                         listChat={this.listChat}
                         changeChat={this.changeChat} />
                    <main className={classes.content}>
                        <Chat />
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
        backgroundColor: "white",
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
        backgroundImage: 'url(../images/app/Background.jpg)',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        marginTop: 56,
        [theme.breakpoints.up('sm')]: {
            height: 'calc(100% - 64px)',
            marginTop: 64,
        },
    }
});

export default withStyles(styles, { withTheme: true })(App);
