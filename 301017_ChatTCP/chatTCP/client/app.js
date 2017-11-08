const {app, BrowserWindow, ipcMain}=require('electron'),
    path=require('path'),
    url=require('url'),
    net=require('net');

const PORT=6844, IP='127.0.0.1';
const OUT_SIGNAL="out", LOGIN_SIGNAL='l', REGISTER_SIGNAL='r', CHAT_SIGNAL='c';
let loginWindow, registrationWindow, chatWindow, errorWindow;
let chatIconPath=path.join(__dirname, 'images/icon/Chat.png'),
    errorIconPath=path.join(__dirname, 'images/icon/Error.png');
    client=new net.Socket();

app.on('ready',
    function()
    {
        loginWindow=new BrowserWindow({
            width: 525,
            height: 425,
            resizable: false,
            icon: chatIconPath
        });
        loginWindow.loadURL(url.format({
            pathname: path.join(__dirname, 'pages/login.html'),
            protocols: 'files',
            slashes: true,
        }));
        loginWindow.on('close', closeAll);
        registrationWindow=new BrowserWindow({
            width: 600,
            height: 550,
            show: false,
            resizable: false,
            icon: chatIconPath
        });
        registrationWindow.loadURL(url.format({
            pathname: path.join(__dirname, 'pages/registration.html'),
            protocols: 'files',
            slashes: true
        }));
        registrationWindow.on('close', closeAll);
        chatWindow=new BrowserWindow({
            width: 800,
            height: 600,
            show: false,
            icon: chatIconPath
        });
        chatWindow.loadURL(url.format({
            pathname: path.join(__dirname, 'pages/chat.html'),
            protocols: 'files',
            slashes: true
        }));
        chatWindow.on('close', closeAll);
        errorWindow=new BrowserWindow({
            width: 400,
            height: 120,
            show: false,
            resizable: false,
            icon: errorIconPath
        });
        errorWindow.loadURL(url.format({
            pathname: path.join(__dirname, 'pages/error.html'),
            protocols: 'files',
            slashes: true
        }));
        errorWindow.on('close', closeAll);
    }
);

app.on('window-all-closed', closeAll);

client.connect(PORT, IP);

client.on('connect', () => {client.write(LOGIN_SIGNAL+'\n');});

client.on('data',
    function(data)
    {
        console.log(data.toString().trim());
        data=JSON.parse(data);
        switch(data.section)
        {
            case LOGIN_SIGNAL:
                loginWindow.webContents.send('message', data.message);
                break;
            case REGISTER_SIGNAL:
                registrationWindow.webContents.send('message', data.message);
                break;
            case CHAT_SIGNAL:
                chatWindow.webContents.send('message', data.message);
                break;
        }
    }
);

process.on('uncaughtException',
    function(err)
    {
        console.log(err);
        loginWindow.hide();
        registrationWindow.hide();
        chatWindow.hide();
        errorWindow.show();
    }
);

ipcMain.on('sendLogin',
    function(event, arg)
    {
        client.write(arg+'\n');
    }
);

ipcMain.on('sendRegister',
    function(event, arg)
    {
        client.write(arg+'\n');
    }
);

ipcMain.on('sendMessage',
    function(event, arg)
    {
        client.write(JSON.stringify(arg)+'\n');
    }
);

ipcMain.on('login',
    function(event, arg)
    {
        loginWindow.show();
        chatWindow.hide();
        registrationWindow.hide();
        changeSection(LOGIN_SIGNAL);
    }
);

ipcMain.on('registration',
    function(event, arg)
    {
        loginWindow.hide();
        registrationWindow.show();
        changeSection(REGISTER_SIGNAL);
    }
);

ipcMain.on('chat',
    function(event, arg)
    {
        loginWindow.hide();
        registrationWindow.hide();
        chatWindow.show();
        changeSection(CHAT_SIGNAL);
        chatWindow.webContents.send('init', arg);
    }
);

function changeSection(section)
{
    client.write(OUT_SIGNAL+'\n');
    client.write(section+'\n');
}

function closeAll()
{
    if(process.platform!='darwin')
        app.quit();
}
