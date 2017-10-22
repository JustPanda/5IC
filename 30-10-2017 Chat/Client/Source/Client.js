const net = require( 'net' );

export default class Client 
{
    StartClient()
    {
        var client = net.connect( { port: 8080 }, function ()
        {
            console.log( "connected to server" );
        } );
        client.on( 'data', function ( data )
        {
            console.log( data.toString() );
            client.write( "bb\n" );
        } );

        client.on( "end", function ()
        {
            console.log( "disconnected from server" );
        } )
    }
}
