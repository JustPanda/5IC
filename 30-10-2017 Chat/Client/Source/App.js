const {app, BrowserWindow, ipcMain} = require("electron");

/*import {
  app,
  BrowserWindow,
  ipcMain
} from 'electron';
import Client from './Client.jsx'; */

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
    show: true,
    titleBarStyle: 'hidden',
    frame:true
  } );
  mainWindow.loadURL( 'file://' + __dirname + '/Index.html' );
  mainWindow.on( 'closed', () => {
    mainWindow = null;
  } );

  loginWindow = new BrowserWindow( {
    width: 800,
    height: 500,
    show:true,
    titleBarStyle: 'hidden',
    frame:false
  } );
  loginWindow.loadURL( "file://" + __dirname + "/Login.html" );
  loginWindow.on( 'closed', () => {
    loginWindow = null;
  } );

  signupWindow = new BrowserWindow( {
    width: 800,
    height: 500,
    show: false,
    titleBarStyle: 'hidden',
    frame:false
  } );
  signupWindow.loadURL( "file://" + __dirname + "/Signup.html" );
  signupWindow.on( 'closed', () => {
    signupWindow = null;
  } );

  
 // mainWindow.setMenu(null);
 /* loginWindow.setMenu(null);
  signupWindow.setMenu(null); */
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
    console.log("mi è arrivato un parametro nel main")
    if(arg==="ErroreConnessione")
    {
      console.log("Invio al login errore connessione")
      loginWindow.webContents.send("logincomponent", "ErroreConnessione");
    }
    else if(Array.isArray(arg))
    {
      mainWindow.webContents.send("pane", arg);
      console.log("Ho inviato a pane: " + arg);
    }
    else if(arg.UsernamePerPane!=null)
    {
      mainWindow.webContents.send("pane", arg)
    }
    else 
    {
      mainWindow.webContents.send("content", arg);
      console.log("Ho inviato a content: " + arg);
    }
    
  }
 
} );

ipcMain.on( "login", function (event, data) 
{
  console.log("Al login mi è arrivato un messaggio: " + data)
  if(data===null|data===undefined)
  {
     mainWindow.show();
    loginWindow.show();
    signupWindow.hide();
  }
  else
  {
    loginWindow.webContents.send("logincomponent", data);
    console.log("Ho inviato a login component ")
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