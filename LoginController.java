package application;

import javafx.event.*;
import application.MySQLAccess;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import application.LoginManager;
//import application.MySQLAccess;
import java.sql.*;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField user;
  @FXML private TextField password;
  @FXML private Button loginButton;
  @FXML private Button signUpButton;
  @FXML private Text signinerror;
  
  public void initialize() {}
  
  public void initManager(final LoginManager loginManager) {
	try {
		MySQLAccess.createDB();
	} catch (ClassNotFoundException | SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}  
	  
    loginButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
		try {
			//System.out.println("I made it here!");
			ResultSet userID = null;
			userID = MySQLAccess.signIn(user.getText(), password.getText());
			if (userID != null) {
				loginManager.authenticated(userID);
			} else {
				signinerror.setText("Username or Password is Incorrect");
			}
		} catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

      }
    });
    signUpButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
//          User userID = authorize();
//          if (userID != null) {
//            loginManager.authenticated(userID);
//          }
        	//signUp();
        	loginManager.showSignUpView();
        }

		
      });
  }


  /**
   * Check authorization credentials.
   * 
   * If accepted, return a sessionID for the authorized session
   * otherwise, return null.
 * @throws SQLException 
 * @throws ClassNotFoundException 
   */   
//  private User authorize() {
//	//System.out.println("Authorizing!");
////	Main.temp.setUsername(user.getText());
////	System.out.println(Main.temp.getUsername());ctri1075PC
////    return 
//////      "jbenson6".equals(user.getText()) && "CCsabathia52".equals(password.getText()) 
////         Main.users.containsKey(user.getText()) && Main.users.get(user.getText()).equals(password.getText())
////    		? generateUserID(user.getText()) 
////            : null;
//	  return generateUserID(user.getText());
//  }
  
 // private User generateUserID(String username) throws ClassNotFoundException, SQLException {
//    sessionID++;
	  //PasswordEncryptionService pes = new PasswordEncryptionService();
	  
//		  for(int i = 0; i < list.size(); i++) {
//			  if(list.get(i).getUsername().equals(username)) {
//				  try {
//					boolean decrypt = false;
//					decrypt = pes.authenticate(password.getText(), list.get(i).getEncryptedPassword(), list.get(i).getSalt());
//					if(decrypt == true) {
//						  return list.get(i);
//					  }
//				  } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				  
//				  
//			  }
//		  }		  
//	  }
	//return null;
  //}
}