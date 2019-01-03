package application;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.ArrayList;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import application.PasswordEncryptionService;

public class MySQLAccess {
public static void createDB() throws SQLException, ClassNotFoundException {
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");
		String sql_stmt = "CREATE DATABASE IF NOT EXISTS  `password_manager`;";
		Statement statement = connect.createStatement();
		statement.executeUpdate(sql_stmt);


		String sqlCreate = "CREATE TABLE IF NOT EXISTS " + "password_manager.users("
				+ "`id` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "`user_name` varchar(32) NOT NULL,\n"
				+ "`first_name` varchar(32) NOT NULL,\n"
				+ "`last_name` varchar(32) NOT NULL,\n"
				+ "`salt` blob,\n"
				+ "`encrypted_password` blob,\n"
				+ "PRIMARY KEY (`id`,`user_name`)\n"
				+ ");";


		Statement stmt = connect.createStatement();
		stmt.execute(sqlCreate);

		String sqlCreate_2 = "CREATE TABLE IF NOT EXISTS " + "password_manager.sites(\n"
				+"`id` int(11) NOT NULL AUTO_INCREMENT,\n"
				+"`user_id` int(11) NOT NULL,\n"
				+"`user_name` varchar(32) NOT NULL,\n"
				+"`username` varchar(32) NOT NULL,\n"
				+"`domain_name` varchar(32) NOT NULL,\n"
				+"`url` varchar(32) NOT NULL,\n"
				+"`cypher_text` blob,\n"
				+"`secret_key` blob,\n"
				+"PRIMARY KEY (`id`),\n"
				+"KEY `user_id` (`user_id`,`user_name`),\n"
				+"FOREIGN KEY (`user_id`, `user_name`) REFERENCES `users` (`id`, `user_name`)\n"
				+");";


				Statement stmt_2 = connect.createStatement();
				stmt_2.execute(sqlCreate_2);
	}
	
    public static boolean addUser(String username, String firstname, String lastname, byte[] salt, byte[] encryptedPassword) throws SQLException, ClassNotFoundException {
    	// TODO Auto-generated method stub
    	boolean exists = false;
    	Connection connect = null;
    	//Statement statement = null;
    	exists = checkuser(username);
    	// This will load the MySQL driver, each DB has its own driver
    	if (exists == false) {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		// Setup the connection with the DB
    		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");

    		// Statements allow to issue SQL queries to the database
    		// Result set get the result of the SQL query
    		//resultSet = statement.executeQuery("select * from password_manager.users");
    		//writeResultSet(resultSet);
    		String query = ("INSERT INTO users (id, user_name, first_name, last_name, salt, encrypted_password) "
    				+ "VALUES (default, ? , ? , ? , ? , ?)"); 
    		PreparedStatement preparedStatement = connect.prepareStatement(query);
    		preparedStatement.setString(1, username);
    		preparedStatement.setString(2, firstname);
    		preparedStatement.setString(3, lastname);
    		preparedStatement.setBytes(4, salt);
    		preparedStatement.setBytes(5, encryptedPassword);
    		preparedStatement.execute();

    		//preparedStatement = connect.prepareStatement("SELECT user_name, first_name, last_name, salt, encrypted_password from password_manager.users");
    		// resultSet = preparedStatement.executeQuery();
    		//writeResultSet(resultSet);

    		connect.close();
    		return true;
    	} else {
    		return false;
    	}
	
	}

	static boolean checkuser(String username) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt1 = null;
		//PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
        // Setup the connection with the DB
        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");
        String query = "SELECT user_name FROM users where user_name=?";
        pstmt1 = connect.prepareStatement(query);
        pstmt1.setString(1, username);
        
        rs = pstmt1.executeQuery();

        if(rs.next()) {
//        	pstmt2 = connect.prepareStatement("INSERT INTO users(user_name) VALUES(?)");
//        	pstmt2.setString(1, username);
//        	pstmt2.executeUpdate();
        	pstmt1.close();
        	//pstmt2.close();
        	rs.close();
        	//connect.close();
        	return true;
        	//System.out.println("Username " + username + " has been registered.");
        }  else {
        	pstmt1.close();
        	//pstmt2.close();
        	rs.close();
        	//connect.close();
        	return false;
        	//System.out.println("Username " + username + " already exists.");
        }
	}

	public static ResultSet signIn(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		// TODO Auto-generated method stub
		boolean exists = false;
		
		exists = checkuser(username);
		if(exists) {
			Connection connect = null;
			PasswordEncryptionService pes = new PasswordEncryptionService();
			Class.forName("com.mysql.cj.jdbc.Driver");
    		// Setup the connection with the DB
    		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");
    		String query = "SELECT user_name, salt, encrypted_password FROM users WHERE user_name=?";
    		PreparedStatement pstmt1 = null;
    		pstmt1 = connect.prepareStatement(query);
            pstmt1.setString(1, username);
    		
    		//Statement statement = connect.createStatement();
    		ResultSet rs = pstmt1.executeQuery();
    		if (rs.next()) {
    			boolean decrypt = false;
    			decrypt = pes.authenticate(password, rs.getBytes("encrypted_password"), rs.getBytes("salt"));
    			if (decrypt == true) {
    				PreparedStatement pstmt = null;
    				String query_2 = "Select * from users WHERE user_name=?";
    				pstmt = connect.prepareStatement(query_2);
    				pstmt.setString(1, username);
    				ResultSet rs_final = pstmt.executeQuery();
    				//System.out.println(rs_final);
    				//pstmt1.close();
    				//rs.close();
    				//pstmt.close();
    				return rs_final;
    			}
    			else {
    				//System.out.println("Decryption Failed!");
    				pstmt1.close();
    				rs.close();
    				//connect.close();
    				return null;
    			}
    		} 
		} 
		return null;
	}

	public static int getPasswordsInt(ResultSet userID) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");
		String query = "SELECT * FROM sites WHERE user_name=?";
		PreparedStatement pstmt1 = null;
		pstmt1 = connect.prepareStatement(query);
        pstmt1.setString(1, userID.getString(2));
		
		//Statement statement = connect.createStatement();
		ResultSet rs = pstmt1.executeQuery();
		int rows = 0;
		while (rs.next()) {
			rows++;
		}
		pstmt1.close();
		rs.close();
		//connect.close();
		return rows;
	}

	public static ResultSet getSites(ResultSet userID) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");
		String query = "SELECT * FROM sites WHERE user_name=? && user_id=?";
		PreparedStatement pstmt1 = null;
		pstmt1 = connect.prepareStatement(query);
        pstmt1.setString(1, userID.getString(2));
        pstmt1.setInt(2, userID.getInt("id"));
		
		//Statement statement = connect.createStatement();
		ResultSet rs = pstmt1.executeQuery();
		return rs;
	}

	public static void addPassword(ResultSet userID, String domainName, String url, String username, SecretKey sk, byte[] cypherText) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");

		// Statements allow to issue SQL queries to the database
		// Result set get the result of the SQL query
		//resultSet = statement.executeQuery("select * from password_manager.users");
		//writeResultSet(resultSet);
		String query = "INSERT INTO sites (id, user_id, user_name, username, domain_name, url, cypher_text, secret_key) "
				+ "VALUES (default, ? , ? , ? , ? , ?, ?, ?)"; 
		PreparedStatement preparedStatement = connect.prepareStatement(query);
		preparedStatement.setInt(1, userID.getInt(1));
		preparedStatement.setString(2, userID.getString(2));
		preparedStatement.setString(3, username);
		preparedStatement.setString(4, domainName);
		preparedStatement.setString(5, url);
		preparedStatement.setBytes(6, cypherText);
		preparedStatement.setBytes(7, sk.getEncoded());

		preparedStatement.execute();

		//preparedStatement = connect.prepareStatement("SELECT user_name, first_name, last_name, salt, encrypted_password from password_manager.users");
		// resultSet = preparedStatement.executeQuery();
		//writeResultSet(resultSet);

		connect.close();
	}

	public static void removePassword(Site itemToRemove, ResultSet userID) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");

		// Statements allow to issue SQL queries to the database
		// Result set get the result of the SQL query
		//resultSet = statement.executeQuery("select * from password_manager.users");
		//writeResultSet(resultSet);
		String query = "DELETE from sites WHERE user_id=? && user_name=? && username=? && "
				+ "domain_name=? && url=? && cypher_text=? && secret_key=?"; 
		PreparedStatement preparedStatement = connect.prepareStatement(query);
		preparedStatement.setInt(1, userID.getInt(1));
		preparedStatement.setString(2, userID.getString(2));
		preparedStatement.setString(3, itemToRemove.getUsername());
		preparedStatement.setString(4, itemToRemove.getDomainName());
		preparedStatement.setString(5, itemToRemove.getDomain());
		preparedStatement.setBytes(6, itemToRemove.getCypherText());
		preparedStatement.setBytes(7, itemToRemove.getSecretKey().getEncoded());

		preparedStatement.execute();
		connect.close();
	}

	public static int getUnsafeInt(ResultSet userID) throws Exception {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");
		String query = "SELECT * FROM sites WHERE user_name=?";
		PreparedStatement pstmt1 = null;
		pstmt1 = connect.prepareStatement(query);
        pstmt1.setString(1, userID.getString(2));
		
		//Statement statement = connect.createStatement();
		ResultSet rs = pstmt1.executeQuery();
		int rows = 0;
		ArrayList<String> passwords = new ArrayList<String>();
		SiteEncryption se = new SiteEncryption();
		while(rs.next()) {
			SecretKey key = new SecretKeySpec(rs.getBytes("secret_key"), 0, rs.getBytes(8).length, "AES");
			if (passwords.contains(se.decryptText(rs.getBytes("cypher_text"), key))) {
				rows++;
			} else {
				passwords.add(se.decryptText(rs.getBytes("cypher_text"), key));
			}
		}
		passwords.clear();
//		while (rs.next()) {
//			rows++;
//		}
//		pstmt1.close();
//		rs.close();
		//connect.close();
		return rows;
	}

	public static void deleteUser(ResultSet userID) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");

		// Statements allow to issue SQL queries to the database
		// Result set get the result of the SQL query
		//resultSet = statement.executeQuery("select * from password_manager.users");
		//writeResultSet(resultSet);
		String query = "DELETE from sites WHERE user_name=? && user_id=?"; 
		PreparedStatement preparedStatement = connect.prepareStatement(query);
		preparedStatement.setString(1, userID.getString("user_name"));
		preparedStatement.setInt(2, userID.getInt("id"));

		preparedStatement.execute();
		
		String query_2 = "DELETE from users WHERE user_name=? && first_name=? && last_name=?"; 
		PreparedStatement preparedStatement_2 = connect.prepareStatement(query_2);
		preparedStatement_2.setString(1, userID.getString("user_name"));
		preparedStatement_2.setString(2, userID.getString("first_name"));
		preparedStatement_2.setString(3, userID.getString("last_name"));

		preparedStatement_2.execute();
		connect.close();
		
	}
	public static ResultSet editUser(String firstname, String lastname, byte[] salt, byte[] encryptedPassword, ResultSet userID) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");

		// Statements allow to issue SQL queries to the database
		// Result set get the result of the SQL query
		//resultSet = statement.executeQuery("select * from password_manager.users");
		//writeResultSet(resultSet);
		String query = "Update users SET first_name=?, last_name=?, salt=?, encrypted_password=? WHERE user_name=?"; 
		PreparedStatement preparedStatement = connect.prepareStatement(query);
		preparedStatement.setString(1, firstname);
		preparedStatement.setString(2, lastname);
		preparedStatement.setBytes(3, salt);
		preparedStatement.setBytes(4, encryptedPassword);
		preparedStatement.setString(5, userID.getString("user_name"));

		preparedStatement.execute();
		
		String query_2 = "Select * from users WHERE user_name=?";
		PreparedStatement pstmt = connect.prepareStatement(query_2);
		pstmt.setString(1, userID.getString("user_name"));
		ResultSet rs_final = pstmt.executeQuery();
		//System.out.println(rs_final);
		//pstmt1.close();
		//rs.close();
		//pstmt.close();
		return rs_final;
		
	}

	public static ResultSet editUser(String firstname, String lastname, ResultSet userID) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Connection connect = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/password_manager", "password_manager", "");

		// Statements allow to issue SQL queries to the database
		// Result set get the result of the SQL query
		//resultSet = statement.executeQuery("select * from password_manager.users");
		//writeResultSet(resultSet);
		String query = "Update users SET first_name=?, last_name=? WHERE user_name=?"; 
		PreparedStatement preparedStatement = connect.prepareStatement(query);
		preparedStatement.setString(1, firstname);
		preparedStatement.setString(2, lastname);
		preparedStatement.setString(3, userID.getString("user_name"));

		preparedStatement.execute();
		
		String query_2 = "Select * from users WHERE user_name=?";
		PreparedStatement pstmt = connect.prepareStatement(query_2);
		pstmt.setString(1, userID.getString("user_name"));
		ResultSet rs_final = pstmt.executeQuery();
		//System.out.println(rs_final);
		//pstmt1.close();
		//rs.close();
		//pstmt.close();
		return rs_final;
		
		
	}

}
