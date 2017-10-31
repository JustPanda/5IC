var React = require( 'react' );
var WinJS = require( 'react-winjs' );

export default class ContentDialog extends React.Component
{
    handleShow()
    {

        this.refs.dialog.winControl.show().then( function ( eventObject )
        {

            this.setState( { dialogResult: eventObject.result } );

        }.bind( this ) );

    }

    getInitialState()
    {

        return {

            dialogResult: null

        };

    }

    render()
    {

        return (

            <div>

                <WinJS.ContentDialog

                    ref="dialog"

                    title="Errore"

                    primaryCommandText="OK">

                    <div>

                       {this.props.text}

                    </div>

                </WinJS.ContentDialog>

            </div>

        );

    }

} 