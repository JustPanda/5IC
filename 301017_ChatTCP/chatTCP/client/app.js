const {app, BrowserWindow, ipcMain} = require('electron'),
    notifier=require('node-notifier'),
    path=require('path'),
    url=require('url');

let loginWindow, registrationWindow, chatWindow;
let iconPath=path.join(__dirname, 'images/Icon.png');

app.on('ready', createWindows);

app.on('window-all-closed', closeAll);

function createWindows()
{
    loginWindow=new BrowserWindow({
        width: 525,
        height: 400,
        // resizable: false,
        icon: iconPath
    });
    loginWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'login/index.html'),
        protocols: 'files',
        slashes: true,
    }));
    loginWindow.on('close', closeAll);
    registrationWindow=new BrowserWindow({
        width: 600,
        height: 400,
        show: false,
        icon: iconPath
    });
    registrationWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'registration/index.html'),
        protocols: 'files',
        slashes: true
    }));
    registrationWindow.on('close', closeAll);
    // chatWindow=new BrowserWindow({
    //     width: 800,
    //     height: 600,
    //     show: true,
    //     icon: iconPath
    // });
    // chatWindow.loadURL(url.format({
    //     pathname: path.join(__dirname, 'chat/index.html'),
    //     protocols: 'files',
    //     slashes: true
    // }));
    // chatWindow.on('close', closeAll);
}

ipcMain.on('goToRegistration',
    function()
    {
        registrationWindow.show();
        loginWindow.hide();
    }
);

ipcMain.on('goToLogin',
    function()
    {
        loginWindow.show();
        registrationWindow.hide();
    }
);

ipcMain.on('goToChat',
    function()
    {
        chat.show();
    }
);

function closeAll()
{
    if(process.platform!='darwin')
        app.quit();
}
