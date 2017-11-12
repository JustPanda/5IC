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
        this.state.chats['Global']={messages: [], index: 0};
    }

    createChats(listChat, chats)
    {
        listChat.map(

        );
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
        for(let chat in this.state.chats)
        {
            this.state.chats[chat].messages=[];
            this.state.chats[chat].index=0;
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
                        data.users.map(
                            function(item, index)
                            {
                                if(!prevState.chats[item.username])
                                {
                                    prevState.chats[item.username]={messages: [], index: 0};
                                }
                            }
                        );
                        return {
                            listChat: data.users,
                            chats: prevState.chats
                        };
                    }
                );
                break;
            case "msg":
                for(let message of data.messages)
                {
                    this.updateMessages(message, message.destination);
                }
                break;
        }
    }

    updateMessages(info, destination, toSend)
    {
        var username=this.username;
        if(toSend)
        {
            ipcRenderer.send('sendMessage', `{ "destination": "${this.state.titleBar}", "text": "${info.text}"}`);
        }
        this.setState(
            function(prevState)
            {
                var destinationObj=prevState.chats[destination];
                var d=new Date(), min=d.getMinutes();
                if(min<10)
                {
                    min='0'+min;
                }
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
                    <Chat ref='messages' messages={this.state.chats[this.state.titleBar].messages} updateMessages={this.updateMessages} section={this.state.titleBar} />
                </main>
            </div>
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
