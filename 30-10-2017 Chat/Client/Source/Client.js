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
            var d = data//.toString();
            console.log("Ho ricevuto: " + d);
            if(d.includes("LoginSuccess"))
            {
                ipcRenderer.send("login", "LoginSuccess");
                console.log("Ho chiamato il login per dirgli che è ok")
            //   ipcRenderer.send("main")
            }
            else if(d.includes("LoginFail"))
            {
                ipcRenderer.send("login", "LoginFail");
            }
            else if(d.includes("RegistrationSuccess"))
            {
                ipcRenderer.send("signup", "RegistrationSuccess");
                ipcRenderer.send("main")
            }
            else if(d.includes("RegistrationFail"))
            {
                ipcRenderer.send("signup", "RegistrationFail");
            }
            else 
            {
                var final = JSON.parse(d);  
                if(final.Messages!=null || final.Messages!=undefined)
                {
                    for(var i=0; i<final.Messages.length; i++)
                    {
                        console.log("Eseguo refresh con " + final.Messages[i].Text)
                        this.Refresh(final.Messages[i]);
                    }
                }
                else
                {
                    this.Refresh(final);
                }
               
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