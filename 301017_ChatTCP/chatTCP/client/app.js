const {app, BrowserWindow} = require('electron'),
    path=require('path'),
    url=require('url');

let window;

app.on('ready',createWindow);

app.on('activate',
    function()
    {
        if(window==null) createWindow();
    }
);

app.on('window-all-closed',
    function()
    {
        if(process.platform!='darwin')
        {
            app.quit();
        }
    }
);

function createWindow()
{
    window=new BrowserWindow({
        width: 800,
        height: 600
    });
    window.loadURL(url.format({
        pathname: path.join(__dirname, 'index.html'),
        protocols: 'files',
        slashes: true
    }));
    window.on('close',
        function()
        {
            window=null;
        }
    );
}
