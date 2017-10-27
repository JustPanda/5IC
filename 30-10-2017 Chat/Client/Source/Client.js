const net = require( 'net' );

export default class Client {
    StartClient() {
        this.client = net.connect( {
            port: 8080
        }, function () {
            console.log( "connected to server" );
        } );

        this.client.on( 'data', function ( data ) {

        } );

        this.client.on( "end", function () {
            console.log( "disconnected from server" );
        } );

        this.client.on( "error", function () {

        } )
    }

    WriteMessage( message ) {
        this.client.write( message );
        console.log("Ho inviato " + message);
    }
}