import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import MenuIcon from 'material-ui-icons/Menu';
import ExitToAppIcon from 'material-ui-icons/ExitToApp';
import {ipcRenderer} from 'electron';
import {Router, Route, IndexRoute, browserHistory} from 'react-router';
import createBrowserHistory from 'history/createBrowserHistory';
import ResponsiveDrawer from './drawer/ResponsiveDrawer.jsx';
import Chat from './chat/Chat.jsx';
import Message from './chat/Message.jsx';
import '../css/app.css';

const drawerWidth=240, history=createBrowserHistory();

class App extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            titleBar: 'Global',
            listChat: [],
            chats: [],
            open: false,
        };
        this.username=null;
        this.createChats=this.createChats.bind(this);
        this.handleDrawerToggle=this.handleDrawerToggle.bind(this);
        this.changeChat=this.changeChat.bind(this);
        this.handleInit=this.handleInit.bind(this);
        this.handleMessage=this.handleMessage.bind(this);
        this.updateMessages=this.updateMessages.bind(this);
        this.exit=this.exit.bind(this);
        ipcRenderer.on('init', this.handleInit);
        ipcRenderer.on('message', this.handleMessage);
        this.state.chats['Global']={chat: Chat, messages: [], index: 0};
    }

    createChats(listChat, chats, updateMessages)
    {
        var ActualChat, tmp;
        tmp=listChat.map(
            function(item, index)
            {
                if(!chats[item.username])
                {
                    chats[item.username]={chat: Chat, messages: [], index: 0};
                }
                ActualChat=chats[item.username].chat;
                return (
                    <Route key={index}
                        path={'/'+item.username}
                        render={() => <ActualChat messages={chats[item.username].messages} updateMessages={updateMessages} section={item.username} /> } />
                );
            }
        );
        ActualChat=chats['Global'].chat;
        tmp.push(<Route key={-1} path='/' render={() => <ActualChat messages={chats['Global'].messages} updateMessages={updateMessages} section={'Global'} />} />);
        return tmp;
    }

    handleDrawerToggle()
    {
        this.setState({ open: !this.state.open });
    }

    changeChat(username)
    {
        this.setState({titleBar: username});
    }

    handleInit(event, data)
    {
        this.username=data;
        for(let key in this.state.chats)
        {
            let actualChat=this.state.chats[key];
            actualChat.messages=[];
            actualChat.index=0;
        }
    }

    handleMessage(event, data)
    {
        switch (data.key)
        {
            case "lu":
                this.setState(
                    function(prevState)
                    {
                        return {
                            listChat: data.users
                        };
                    }
                );
                break;
            case "msg":
                this.updateMessages(data.info, data.destination);
                break;
        }
    }

    updateMessages(info, destination, toSend)
    {
        if(toSend)
        {
            ipcRenderer.send('sendMessage', { destination: this.state.titleBar, text: info.text});
        }
        this.setState(
            function(prevState)
            {
                var destinationObj=prevState.chats[destination];
                console.log(destination);
                var d=new Date(), min=d.getMinutes();
                if (min<10) {
                    min='0'+min;
                }
                info.orientation=toSend?'right':'left';
                info.date=d.getHours()+':'+min;
                destinationObj.messages.push(<Message key={++destinationObj.index} info={info} />);
                return {
                    chats: prevState.chats
                };
            }
        );
    }

    exit()
    {
        ipcRenderer.send('login');
    }

    render()
    {
        const { classes, theme } = this.props;
        return (
            <Router history={history}>
                <div className={classes.appFrame}>
                    <AppBar className={classes.appBar} color='default'>
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
                            <IconButton
                                className={classes.exitButton}
                                onClick={this.exit}>
                                <ExitToAppIcon />
                            </IconButton>
                        </Toolbar>
                    </AppBar>
                    <ResponsiveDrawer open={this.state.open}
                         handleDrawerToggle={this.handleDrawerToggle}
                         listChat={this.state.listChat}
                         changeChat={this.changeChat} />
                    <main className={classes.content}>
                        {this.createChats(this.state.listChat, this.state.chats, this.updateMessages)}
                    </main>
                </div>
            </Router>
        );
    }
}

App.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

const styles=(theme) => ({
    appFrame: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: '100%',
        zIndex: 1,
        overflow: 'hidden',
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
    exitButton: {
        position: 'absolute',
        right: 15,
    },
    drawerHeader: theme.mixins.toolbar,
    content: {
        width: '100%',
        height: 'calc(100% - 56px)',
        backgroundImage: 'url(../images/app/Background.jpg)',
        backgroundSize: 'cover',
        backgroundAttachment: 'fixed',
        backgroundPosition: 'center',
        marginTop: 56,
        [theme.breakpoints.up('sm')]: {
            height: 'calc(100% - 64px)',
            marginTop: 64,
        },
    }
});

export default withStyles(styles, { withTheme: true })(App);
