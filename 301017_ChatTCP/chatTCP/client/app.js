const {app, BrowserWindow, ipcMain} = require('electron'),
    path=require('path'),
    url=require('url'),
    net=require('net');

const PORT=6844, IP='127.0.0.1';
const OUT_SIGNAL="out", LOGIN_SIGNAL='l', REGISTER_SIGNAL='r', CHAT_SIGNAL='c';
let loginWindow, registrationWindow, chatWindow;
let iconPath=path.join(__dirname, 'images/Icon.png'),
    client=new net.Socket();

app.on('ready',
    function()
    {
        loginWindow=new BrowserWindow({
            width: 525,
            height: 425,
            resizable: false,
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
            height: 550,
            show: false,
            resizable: false,
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
);

app.on('window-all-closed', closeAll);

client.connect(PORT, IP);

client.on('connect', () => {client.write(LOGIN_SIGNAL+'\n');});

client.on('data',
    function(data)
    {
        data=JSON.parse(data);
        switch(data.section)
        {
            case LOGIN_SIGNAL:
                loginWindow.webContents.send('message', data.message);
                break;
            case REGISTER_SIGNAL:
                registrationWindow.webContents.send('message', data.message);
                break;
        }
    }
);

client.on('close', () => {console.log('end');});

ipcMain.on('sendLogin',
    function(event, data)
    {
        client.write(data+'\n');
    }
);

ipcMain.on('sendRegister',
    function(event, data)
    {
        client.write(data+'\n');
    }
);

ipcMain.on('goToRegistration',
    function(event, arg)
    {
        loginWindow.hide();
        registrationWindow.show();
        client.write(OUT_SIGNAL+'\n');
        client.write(REGISTER_SIGNAL+'\n');
    }
);

ipcMain.on('goToLogin',
    function(event, arg)
    {
        loginWindow.show();
        registrationWindow.hide();
        client.write(OUT_SIGNAL+'\n');
        client.write(LOGIN_SIGNAL+'\n');
    }
);

ipcMain.on('goToChat',
    function(event, arg)
    {
        chat.show();
        loginWindow.hide();
        registrationWindow.hide();
        client.write(OUT_SIGNAL+'\n');
        client.write(CHAT_SIGNAL+'\n');
    }
);

function closeAll()
{
    if(process.platform!='darwin')
        app.quit();
}
