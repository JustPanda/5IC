const net = require( 'net' );
import {ipcRenderer} from "electron";

export default class Client
{
    StartClient(method)
    {
        this.Refresh = method;
        this.client =new  net.connect( 
        {
            port: 9090
        }, function ()
        {
            console.log( "connected to server" );
        } );

        this.client.on( 'data', function ( data )
        {
            var d = data.toString();
            console.log("Ho ricevuto: " + d);
            if(d=="LoginSuccess")
            {
                ipcRenderer.send("login", "LoginSuccess");
            }
            else if(d=="LoginFail")
            {
                ipcRenderer.send("login", "LoginFail");
            }
            else if(d=="RegistrationSuccess")
            {
                ipcRenderer.send("signup", "RegistrationSuccess");
            }
            else if(d=="RegistrationFail")
            {
                ipcRenderer.send("signup", "RegistrationFail");
            }
            else 
            {
                var final = JSON.parse(d);  
                this.Refresh(final);
            }
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