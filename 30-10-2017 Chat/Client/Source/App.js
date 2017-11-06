import {
  app,
  BrowserWindow,
  ipcMain
} from 'electron';
import Client from './Client';

let mainWindow = null;
let loginWindow = null;
let signupWindow = null;

app.on( 'window-all-closed', () => {
  if ( process.platform != 'darwin' ) {
    app.quit();
  }
} );

app.on( 'ready', () => {
  mainWindow = new BrowserWindow( {
    width: 1600,
    height: 1000,
    show: false
  } );
  mainWindow.loadURL( 'file://' + __dirname + '/Index.html' );
  mainWindow.on( 'closed', () => {
    mainWindow = null;
  } );

  loginWindow = new BrowserWindow( {
    width: 800,
    height: 500,
    show:true
  } );
  loginWindow.loadURL( "file://" + __dirname + "/Login.html" );
  loginWindow.on( 'closed', () => {
    loginWindow = null;
  } );

  signupWindow = new BrowserWindow( {
    width: 800,
    height: 500,
    show: false
  } );
  signupWindow.loadURL( "file://" + __dirname + "/Signup.html" );
  signupWindow.on( 'closed', () => {
    signupWindow = null;
  } );
} );

ipcMain.on( "main", function (event, arg) 
{
  if(arg===null|arg===undefined)
  {
    mainWindow.show();
    loginWindow.hide();
    signupWindow.hide();
  }
  else
  {
    mainWindow.webContents.send("content", arg);
    console.log("Ho inviato: " + arg);
  }
 
} );

ipcMain.on( "login", function (event, data) {
  if(data===null|data===undefined)
  {
     mainWindow.hide();
    loginWindow.show();
    signupWindow.hide();
  }
  else
  {
    loginWindow.webContents.send("logincomponent", data);
  }
 
} );

ipcMain.on( "signup", function (event, data) {
  console.log("Il nodo del signup ha ricevuto"  +data);
  if(data===null|data===undefined)
  {
     mainWindow.hide();
    loginWindow.hide();
    signupWindow.show();
  }
  else
  {
    signupWindow.webContents.send("signupcomponent", data);
  }
} );