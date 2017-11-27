/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Server extends Thread
{

	private ServerSocket server;
	private static ArrayList<ClientThraed> clients;
	private static int uniqueId;
	private boolean keepGoing = false;
	private static boolean authentication = false;
	private static SQLHelper helper = new SQLHelper();
	private ThreadPoolExecutor executor;//creo un pool che cresca dinamicamente riutilizzando i vecchi thread

	public static void main(String[] args)
	{
		Server s = new Server();
		s.start();
	}

	public Server()
	{
		clients = new ArrayList<>();
		executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
	}

	@Override
	public void run()
	{
		try
		{
			helper.createDatabase();
			helper.createNewTable();
			keepGoing = true;
			server = new ServerSocket(8080);
			while (keepGoing)
			{
				Socket client = server.accept();
				ClientThraed c = new ClientThraed(client);
				clients.add(c);
				executor.execute(c);
			}

			server.close();
			for (int i = 0; i < clients.size(); ++i)
			{
				ClientThraed tc = clients.get(i);
				tc = clients.get(i);

				tc.sInput.close();
				tc.sOutput.close();
				tc.socket.close();
			}
		} catch (IOException ex)
		{
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	//imposta se l'utente è registrato nel database
	public static void setAuthentication(boolean b)
	{
		authentication = b;
	}

	//ritorna se l'utente è registarto nel database
	public static boolean getAuthetication()
	{
		return authentication;
	}

	//rimuove il client
	synchronized void remove(int id)
	{
		for (int i = 0; i < clients.size(); ++i)
		{
			ClientThraed tc = clients.get(i);
			if (tc.id == id)
			{
				clients.remove(i);
			}
		}
	}

	/*
	@param username user username
    @param password user passord
    check if the the user is register he log in otherwise the program register the 
    new account
	 */
	public static boolean checkCredentials(String username, char[] password)
	{
		try
		{
			Connection conn = helper.connect();
			ResultSet rs = null;
			PreparedStatement st = null;
			String sql = ("SELECT username,password FROM dati WHERE username=? AND password=?");
			st = conn.prepareStatement(sql);
			st.setString(1, username);
			st.setString(2, Arrays.toString(password));
			rs = st.executeQuery();
			if (rs.next())
			{
				authentication = true;
				System.out.println(username + " has joined the chat");
			} else
			{
				if (validatePassword(password))
				{
					helper.addNewUser(username, Arrays.toString(password));
				}
			}
			st.close();
			conn.close();
			rs.close();
		} catch (SQLException ex)
		{
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}

		return authentication;
	}

	//check if the password follow the minum requirements
	public static boolean validatePassword(char[] password)
	{
		boolean alright = false;
		if (password == null || password.length > 18)
		{
			return alright;
		}
		boolean upper = false;
		boolean lower = false;
		boolean digit = false;
		boolean symbol = false;
		for (char c : password)
		{
			if (Character.isUpperCase(c))
			{
				upper = true;
			} else if (Character.isLowerCase(c))
			{
				lower = true;
			} else if (Character.isDigit(c))
			{
				digit = true;
			} else
			{
				symbol = true;
			}
			if (upper && lower && digit && symbol)
			{
				alright = true;
			}
		}
		return alright;
	}

	class ClientThraed implements Runnable
	{

		private String recipient = null;
		private Socket socket;
		private BufferedReader sInput;
		private PrintWriter sOutput;
		//id unico per distinguire il cliente da disconnettere
		private int id;
		private boolean online = true;
		private String username;
		private boolean isChatting = false;
		private boolean arrivedRequest = false;
		private boolean authenticated = false;
		private int searchedUser = -1;

		public ClientThraed(Socket client)
		{
			try
			{
				id = ++uniqueId;
				this.socket = client;
				sOutput = new PrintWriter(socket.getOutputStream(), true);
				sInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				username = this.sInput.readLine();
			} catch (IOException ex)
			{
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		@Override
		synchronized public void run()
		{
			try
			{
				while (online)
				{
					//registrazione/autenticazione
					while (!getAuthorized())
					{
						try
						{
							this.getOutput().println("Inserire la password: ");
							char[] password = new char[18];
							password = getInput().readLine().toCharArray();
							if (Server.checkCredentials(getUsername(), password))
							{
								setAutorized(true);
							}
						} catch (IOException ex)
						{
							Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
						}
					}

					Server.setAuthentication(false);
					String sender = getUsername();
					String realAnswer = "";

					//esaminazione della lista utente e scelta dell'utente con il quale chattare
					do
					{

						getOutput().println("Per chiudere il programma digitare /exit");

						showUserAbleToChat();

						getOutput().println("Inserire il nome dell'utente con cui si desidera chattare");

						String message = getInput().readLine();
						int pos = message.indexOf(": ");

						realAnswer = message.substring(pos + 1).trim();
						for (int k = 0; k < clients.size(); ++k)
						{
							if (clients.get(k).getUsername().equalsIgnoreCase(realAnswer))
							{
								setIndexUser(k);
							}
						}

						//rimozione del nome di chi invia l'utente destinatario
						if (getIndexSearched() != -1
								&& !clients.get(getIndexSearched()).isChatting()
								&& !isChatting()
								&& getRecipient() == null
								&& !realAnswer.trim().toUpperCase().equals(this.getUsername().toUpperCase())
								&& !realAnswer.trim().equalsIgnoreCase("/exit"))
						{
							setRecipient(message.substring(pos + 1).trim());

							clients.get(getIndexSearched()).setIndexUser(this.getMyId() - 1);
							clients.get(getIndexSearched()).setRecipient(this.getUsername());
							clients.get(getIndexSearched()).setIsChatting(true);
							setIsChatting(true);

						}

						if (realAnswer.trim().equals(getUsername()))
						{
							getOutput().println("Non fare l' asociale");
						}

						if (realAnswer.trim().equalsIgnoreCase("/exit"))
						{
							online = false;
							break;
						}

						if (clients.get(getIndexSearched()).isChatting())
						{
							getOutput().println("L'utente sta già chattando");
							getOutput().println();
						}

					} while (!clients.get(getIndexSearched()).getRecipient().equalsIgnoreCase(this.getUsername()));

					if (online)
					{
						//inizio chat
						setRequestArrived(false);
						getOutput().println();
						getOutput().println("Benevenuto nella chat con " + clients.get(getIndexSearched()).getUsername());
						String message = "";

						try
						{
							getOutput().println("Per uscire dalla chat digitare /stop");
							while (clients.get(getIndexSearched()) != null && clients.get(getIndexSearched()).isChatting() && !realAnswer.trim().equalsIgnoreCase("/stop"))
							{

								message = getInput().readLine();
								int pos = message.indexOf(": ");
								realAnswer = message.substring(pos + 1).trim();

								if (getRecipient() != null && !realAnswer.trim().equalsIgnoreCase("/stop") && !realAnswer.trim().equalsIgnoreCase("/stop"))
								{
									clients.get(getIndexSearched()).getOutput().println(message);
								}
							}

							clients.get(getIndexSearched()).getOutput().println("L'utente " + getUsername() + " ha lasciato la chat");
							setIsChatting(false);
							setRecipient(null);
							setIndexUser(-1);
						} catch (IOException ex)
						{
							Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
				clients.remove(getMyId() - 1);
				getInput().close();
				getOutput().close();
				getSocket().close();
				currentThread().interrupt();
			} catch (IOException ex)
			{
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

		private void showUserAbleToChat()
		{
			for (int i = 0; i < clients.size(); ++i)
			{
				if (!clients.get(i).isChatting())
				{
					clients.get(i).getOutput().println("Gli utenti online sono :");
					for (int k = 0; k < clients.size(); ++k)
					{
						if (!clients.get(i).isChatting())
						{
							clients.get(i).getOutput().println(clients.get(k).getUsername());
						}
					}
				}
			}
		}

		public boolean getIsOnline()
		{
			return online;
		}

		public void setOnline(boolean b)
		{
			this.online = b;
		}

		public int getIndexSearched()
		{
			return this.searchedUser;
		}

		public void setIndexUser(int user)
		{
			this.searchedUser = user;
		}

		public boolean getAuthorized()
		{
			return this.authenticated;
		}

		public void setAutorized(boolean b)
		{
			this.authenticated = b;
		}

		public boolean getArrivedRequest()
		{
			return this.arrivedRequest;
		}

		public void setRequestArrived(boolean isArrived)
		{
			this.arrivedRequest = isArrived;
		}

		public String getRecipient()
		{
			return this.recipient;
		}

		public void setRecipient(String recipient)
		{
			this.recipient = recipient;
		}

		public String getUsername()
		{
			return this.username;
		}

		public Socket getSocket()
		{
			return this.socket;
		}

		public BufferedReader getInput()
		{
			return this.sInput;
		}

		public PrintWriter getOutput()
		{
			return this.sOutput;
		}

		public int getMyId()
		{
			return this.id;
		}

		public boolean isChatting()
		{
			return isChatting;
		}

		private void setIsChatting(boolean b)
		{
			this.isChatting = b;
		}
	}
}
