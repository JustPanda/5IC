import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import Drawer from 'material-ui/Drawer';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import List from 'material-ui/List';
import Typography from 'material-ui/Typography';
import Divider from 'material-ui/Divider';
import IconButton from 'material-ui/IconButton';
import Hidden from 'material-ui/Hidden';
import MenuIcon from 'material-ui-icons/Menu';
import Input from 'material-ui/Input';
import ChevronLeftIcon from 'material-ui-icons/ChevronLeft';
import ChevronRightIcon from 'material-ui-icons/ChevronRight';
import ListChat from './ListChat.jsx';

const drawerWidth = 240;

class ResponsiveDrawer extends React.Component
{
    constructor(props)
    {
        super(props);
    }

    render()
    {
        const {classes, theme, changeChat}=this.props;
        const drawer = (
            <div>
                <div className={classes.drawerHeader} />
                <ListChat changeChat={changeChat} listChat={this.props.listChat} />
            </div>
        );
        return (
            <div>
                <Hidden mdUp>
                    <Drawer type="temporary"
                        anchor={theme.direction === 'rtl' ? 'right' : 'left'}
                        open={this.props.open}
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                        onRequestClose={this.props.handleDrawerToggle}
                        ModalProps={{
                            keepMounted: true
                        }}>
                        {drawer}
                    </Drawer>
                </Hidden>
                <Hidden mdDown implementation="css">
                    <Drawer type="permanent" open
                        classes={{
                            paper: classes.drawerPaper,
                        }}>
                        {drawer}
                    </Drawer>
                </Hidden>
            </div>
        );
    }
}

ResponsiveDrawer.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

const styles = (theme) => ({
    drawerPaper: {
        width: 250,
        [theme.breakpoints.up('md')]: {
            width: drawerWidth,
            position: 'relative',
            height: '100%',
        },
    }
});

export default withStyles(styles, { withTheme: true })(ResponsiveDrawer);
