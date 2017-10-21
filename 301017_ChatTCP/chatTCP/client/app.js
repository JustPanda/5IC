const {app, BrowserWindow, ipcMain} = require('electron'),
    path=require('path'),
    url=require('url');

let loginWindow, registrationWindow, chatWindow;

app.on('ready', createWindow);

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
    loginWindow=new BrowserWindow({
        width: 600,
        height: 400
    });
    loginWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'login/index.html'),
        protocols: 'files',
        slashes: true
    }));
    loginWindow.on('close',
        function()
        {
            loginWindow=null;
        }
    );
    registrationWindow=new BrowserWindow({
        width: 600,
        height: 400,
        show: false
    });
    registrationWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'registration/index.html'),
        protocols: 'files',
        slashes: true
    }));
    registrationWindow.on('close',
        function()
        {
            registrationWindow=null;
        }
    );
    chatWindow=new BrowserWindow({
        width: 800,
        height: 600,
        show: false
    });
    chatWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'chat/index.html'),
        protocols: 'files',
        slashes: true
    }));
    chatWindow.on('close',
        function()
        {
            chatWindow=null;
        }
    )
}


ipcMain.on('registration',
    function()
    {
        registrationWindow.show();
        loginWindow.hide();
    }
);

ipcMain.on('login',
    function()
    {
        loginWindow.show();
        registrationWindow.hide();
    }
);
