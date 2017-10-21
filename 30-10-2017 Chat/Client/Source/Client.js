
class Client 
{
    StartClient()
    {
        var socket = new WebSocket( 'ws://localhost:8080/server/test' );

        echo_service.onmessage = function ( event )
        {
            alert( event.data );
        }
    }
}
