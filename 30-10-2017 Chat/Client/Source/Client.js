const net = require( 'net' );

export default class Client
{
    StartClient(method)
    {
        this.Refresh = method;
        this.client =new  net.connect( 
        {
            port: 8080
        }, function ()
        {
            console.log( "connected to server" );
        } );

        this.client.on( 'data', function ( data )
        {
            var d = data.toString();
       //     console.log("Mi è arrivata roba")
            console.log("Ho ricevuto: " + d);
            var final = JSON.parse(d);  
            this.Refresh(final);
        }.bind(this) );

        this.client.on( "end", function ()
        {
            console.log( "disconnected from server" );
        } );

        this.client.on( "error", function ()
        {
            console.log("Errore nel client")
        } )
    }

    WriteMessage( message )
    {
        this.client.write( message +"\n" );
        console.log( "Ho inviato: " + message );
    }
}