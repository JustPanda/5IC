import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import Avatar from 'material-ui/Avatar';
import ListSubheader from 'material-ui/List/ListSubheader';
import List, { ListItem, ListItemIcon, ListItemText } from 'material-ui/List';
import Collapse from 'material-ui/transitions/Collapse';
import AccountCircleIcon from 'material-ui-icons/AccountCircle';
import GroupWork from 'material-ui-icons/GroupWork';
import ExpandLess from 'material-ui-icons/ExpandLess';
import ExpandMore from 'material-ui-icons/ExpandMore';
import {Link} from 'react-router-dom';

class ListChat extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            open: true
        };
        this.createUserChat=this.createUserChat.bind(this);
        this.handleNestedListToggle=this.handleNestedListToggle.bind(this);
    }

    createUserChat(item, index)
    {
        return (
            <Link key={index} to={'/'+item.username}>
                <ListItem button className={this.props.classes.nested} onClick={this.changeChat.bind(this, item.username)}>
                    <Avatar className={this.props.classes.avatar} style={{
                        backgroundColor: item.online?'#00C853':'red'}}>
                        {item.username[0]}
                    </Avatar>
                    <ListItemText inset primary={item.username} />
                </ListItem>
            </Link>
        );
    }

    handleNestedListToggle()
    {
        this.setState({open: !this.state.open});
    }

    changeChat(objChat)
    {
        this.props.changeChat(objChat);
    }

    render()
    {
        const {classes}=this.props;
        return (
            <List className={classes.root}>
                <Link to='/'>
                    <ListItem button onClick={this.changeChat.bind(this, {name: "Global"})}>
                        <ListItemIcon>
                            <GroupWork />
                        </ListItemIcon>
                        <ListItemText inset primary="Global" />
                    </ListItem>
                </Link>
                <ListItem button onClick={this.handleNestedListToggle}>
                    <ListItemIcon>
                        <AccountCircleIcon />
                    </ListItemIcon>
                    <ListItemText inset primary="Users" />
                    {(this.state.open?<ExpandLess />:<ExpandMore />)}
                </ListItem>
                <Collapse in={this.state.open} transitionDuration="auto" unmountOnExit>
                    {this.props.listChat.map(this.createUserChat)}
                </Collapse>
            </List>
        );
    }
}

ListChat.propTypes = {
    classes: PropTypes.object.isRequired,
};

const styles = (theme) => ({
    root: {
        width: '100%',
        maxWidth: 360,
        background: theme.palette.background.paper
    },
    nested: {
        paddingLeft: theme.spacing.unit * 4
    },
});

export default withStyles(styles)(ListChat);
