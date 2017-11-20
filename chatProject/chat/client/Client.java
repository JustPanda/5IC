package chat.client;

import java.io.*;
import java.net.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.application.*;
import javafx.scene.image.*;
import java.util.*;

import com.jfoenix.controls.*;
import javafx.collections.*;

public class Client extends Application
{
    private final static String SERVER_ADDRESS="127.0.0.1";
    private final static int PORT=1723;

    private static String username;
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;
    private static ChatController gui;

    private final static String START_PATH="chat/client/views/Start.fxml";
    private final static String APP_ICON_PATH="chat/client/views/images/appIcon.png";
    private final static String APP_TITLE="Chat";

    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root=FXMLLoader.load(getClass().getClassLoader().getResource(START_PATH));

        Scene scene=new Scene(root);

        stage.getIcons().add(new Image(APP_ICON_PATH));
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) throws Exception
    {
        launch(args);
    }

    static void setName(String name)
    {
        username=name;
    }

    static String registration(String username, String password)
    {
        try
        {
            socket=new Socket(SERVER_ADDRESS, PORT);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(), true);
            out.println("registration");
            out.println(username);
            out.println(password);
            return in.readLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    static String login(String username, String password)
    {
        try
        {
            socket=new Socket(SERVER_ADDRESS, PORT);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(), true);
            out.println("login");
            out.println(username);
            out.println(password);
            return in.readLine();
        }
        catch(ConnectException e)
        {
            return "server";
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    static void getContacts()
    {
        try
        {
            int number=Integer.parseInt(in.readLine());
            LinkedList<String> l=new LinkedList<>();
            while(number--!=0)
            {
                String s=in.readLine();
                if(s!=username)
                    l.add(s);
            }
            l.remove(username);

            gui.setContacts(l);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    static void initChat(ChatController g)
    {
        gui=g;
        new Thread(new Runnable(){
            @Override
            public void run()
            {
                startChat();
            }
        }).start();
    }

    static void getMessage() throws IOException
    {
        String from=in.readLine();
        String message=in.readLine();
        gui.newMessage(from, message);
    }

    static void sendMessage(String message, String to)
    {
        out.println("message");
        out.println(to);
        out.println(message);
    }

    static void startChat()
    {
        boolean exit=false;
        while(!exit)
        {
            try
            {
                switch(in.readLine())
                {
                    case "exit": exit=true; break;
                    case "contacts": getContacts(); break;
                    case "add": gui.addContact(in.readLine()); break;
                    case "remove": gui.removeContact(in.readLine()); break;
                    case "message": getMessage();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    static void disconnect()
    {
        out.println("exit");
    }

    static void deleteAccount()
    {
        out.println("deleteAccount");
    }
}
