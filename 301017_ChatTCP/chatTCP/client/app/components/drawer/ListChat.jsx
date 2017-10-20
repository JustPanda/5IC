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

class ListChat extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state={
            open: [true, true]
        };
    }

    createLists(listChat, classes)
    {
        var lists=[],
            icons=[<AccountCircleIcon />, <GroupWork />],
            i=0;
        for(let c in listChat)
        {
            lists.push(
                <div key={i}>
                    <ListItem button onClick={this.handleNestedListToggle.bind(this, i)}>
                        <ListItemIcon>
                            {icons[i]}
                        </ListItemIcon>
                        <ListItemText inset primary={c} />
                        {this.state.open[i] ? <ExpandLess /> : <ExpandMore />}
                    </ListItem>
                    <Collapse in={this.state.open[i]} transitionDuration="auto" unmountOnExit>
                        {listChat[c].map(
                            function(item, index)
                            {
                                return (
                                    <ListItem key={index} button className={classes.nested}>
                                        <Avatar className={classes.avatar}>{item.name[0]}</Avatar>
                                        <ListItemText inset primary={item.name} />
                                    </ListItem>
                                )
                            }
                        )}
                    </Collapse>
                </div>
            );
            i++;
        }
        return lists;
    }

    handleNestedListToggle(i)
    {
        this.setState(
            function(prevState)
            {
                prevState.open[i]=!prevState.open[i];
                return {
                    open: prevState.open
                }
            }
        );
    }

    render()
    {
        const {classes}=this.props;
        return (
            <List className={classes.root}>
                {this.createLists(this.props.listChat, classes)}
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
        background: theme.palette.background.paper,
    },
    nested: {
        paddingLeft: theme.spacing.unit * 4,
    },
});

export default withStyles(styles)(ListChat);
