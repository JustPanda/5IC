import { app, BrowserWindow } from 'electron';
import Client from './Client';

let mainWindow = null;

app.on('window-all-closed', () => {
  if (process.platform != 'darwin') {
    app.quit();
  }
});

app.on('ready', () => {
  mainWindow = new BrowserWindow({width: 1600, height: 1000});
  mainWindow.loadURL('file://' + __dirname + '/Index.html');
  mainWindow.on('closed', () => {
    mainWindow = null;
  });
});

var client = new Client();
client.StartClient();
