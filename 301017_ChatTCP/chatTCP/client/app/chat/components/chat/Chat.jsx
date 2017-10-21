import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import classNames from 'classnames';
import BottomSection from './BottomSection.jsx';

class Chat extends React.Component
{
    constructor(props)
    {
        super(props);
    }

    render()
    {
        const {classes}=this.props;
        return (
            <div className={classes.mainChat}>
                <BottomSection />
            </div>
        );
    }
}

Chat.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

const styles={
    mainChat: {
        width: '100%',
        height: '100%',
    }
};

export default withStyles(styles, { withTheme: true })(Chat);
