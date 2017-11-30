package chattcp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
link scuola =  jdbc:sqlite:C:\Users\alessandro.vianello.LAP\Desktop\Personal\TPSIT\chatTCP\NOMEFILE
link casa =  jdbc:sqlite:C:\Users\alexi\Desktop\Personal\TPSIT\chatTCP\NOMEFILE
 */
public class DatabaseSQL {

    String nameFile;

    public void newDatabase(String nameFile) {
        this.nameFile = nameFile;
        File file = new File("C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP");//cambiare per ogni computer
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Multiple directories are created!");
            } else {
                System.out.println("Failed to create multiple directories!");
            }
        }

        String url = "jdbc:sqlite:C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP\\" + this.nameFile;//cambiare per ogni computer

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void createDati(String nameFile) {
        this.nameFile = nameFile;
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP\\" + this.nameFile);//cambiare per ogni computer
            System.out.println("Opened database successfully");

            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS dati(\n "
                    + "id INTEGER  PRIMARY KEY,\n"
                    + "username      TEXT        NOT NULL		UNIQUE,\n"
                    + "password      TEXT        NOT NULL,\n"
                    + "socket		 TEXT,\n"
                    + "status		 TEXT		 NOT NULL\n"
                    + ");";
            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Table created successufully");
    }

    /*public void createTable(String nameFile, String nameTab)
	{
		this.nameFile = nameFile;
		Connection conn = null;
		Statement stmt = null;

		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alessandro.vianello.LAP\\Desktop\\Personal\\TPSIT\\chatTCP\\" + this.nameFile);//cambiare per ogni computer
			System.out.println("Opened database successfully");

			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "+nameTab+"\n"
					+ "id INTEGER  PRIMARY KEY,\n"
					+ "username      TEXT        NOT NULL		UNIQUE,\n"
					+ "password      TEXT        NOT NULL\n"
					+ ");";
			stmt.executeUpdate(sql);

		} catch (ClassNotFoundException ex)
		{
			Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex)
		{
			Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("Table created successufully");
	}
     */
    public void addUser(String username, String password, Socket user, String nameFile) {
        String credenziali = "INSERT INTO dati VALUES(?,?,?,?,?);";
        this.nameFile = nameFile;
        String IDsocket = user.getLocalAddress().toString();
        String online = "offline";
        try (Connection conn = DatabaseSQL.connect(this.nameFile);
                PreparedStatement pstmt = conn.prepareStatement(credenziali)) {
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, IDsocket);
            pstmt.setString(5, online);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
        }

    }

    private static Connection connect(String nameFile) {
        //SQLite connection string 
        String url = "jdbc:sqlite:C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP\\" + nameFile;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
        }
        return conn;
    }

    public boolean checkInfo(String username, String password, Socket socket, String nameFile) {
        this.nameFile = nameFile;
        boolean x = false;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP\\" + this.nameFile);//cambiare per ogni computer
            Statement statement = conn.createStatement();
            String check = "SELECT username,password FROM dati ;";
            ResultSet rs = statement.executeQuery(check);
            Integer id = 0;
            try {
                while (rs.next()) {
                    id++;
                    String checkUsername = rs.getString("username");
                    String checkPassword = rs.getString("password");
                    if (username.equals(checkUsername) && password.equals(checkPassword)) {
                        x = true;
                        rs.close();
                        statement.executeUpdate("UPDATE dati SET socket ='" + socket.getLocalAddress().toString() + "';");
                        statement.executeUpdate("UPDATE dati SET status = 'online' WHERE id=" + id + ";");
                        statement.close();
                        //PreparedStatement online = conn.prepareStatement("UPDATE dati SET online = 1;");
                        //online.executeUpdate();
                        //online.close();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
            }

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }

    public void printUser(String nameFile, Socket socket) {
        PrintWriter out = null;
        try {
            this.nameFile = nameFile;
            out = new PrintWriter(socket.getOutputStream(), true);
            Connection conn = null;
            Statement stmt = null;
            String user = "Nessuno online.";
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP\\" + this.nameFile);//cambiare per ogni computer
                stmt = conn.createStatement();
                String s = "SELECT username FROM dati WHERE status = 'online';";
                ResultSet rs = stmt.executeQuery(s);
                while (rs.next()) {
                    user = rs.getString("username");
                    if (user != null) {
                        out.println(user);
                    }
                }
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOffline(String username, String password, Socket socket, String nameFile) {
        this.nameFile = nameFile;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alexi\\Desktop\\Personal\\TPSIT\\chatTCP\\" + this.nameFile);//cambiare per ogni computer
            Statement statement = conn.createStatement();
            String check = "SELECT username,password FROM dati ;";
            ResultSet rs = statement.executeQuery(check);
            Integer id = 0;
            try {
                while (rs.next()) {
                    id++;
                    String checkUsername = rs.getString("username");
                    String checkPassword = rs.getString("password");
                    if (username.equals(checkUsername) && password.equals(checkPassword)) {
                        rs.close();
                        statement.executeUpdate("UPDATE dati SET status = 'offline' WHERE id=" + id + ";");
                        statement.close();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
