package application;

import javafx.event.*;
import application.MySQLAccess;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.sql.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import application.LoginManager;
import application.PasswordEncryptionService;

/** Controls the main application screen */
public class SignUpViewController {
  @FXML private Button SignUpButton;
  @FXML private Button cancelButton;
  @FXML private TextField firstname;
  @FXML private TextField lastname;
  @FXML private TextField username;
  @FXML private PasswordField password;
  @FXML private PasswordField confirmpassword;
  @FXML private Text signuperror;
  boolean passwordMatch;
  boolean fill;
  boolean success;
  
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager) {
    //sessionLabel.get.add("application/mainview.css");
    SignUpButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
    	  try {
			signUp();
			if (fill == true && passwordMatch == true) {
				if(success == true) {
		    		  loginManager.showLoginScreen();
				} else {
					signuperror.setText("Username already Exists");	
				}	
			} else if (fill == true && passwordMatch == false) {
				signuperror.setText("Passwords don't Match. Try retyping Password");
				password.setText("");
				confirmpassword.setText("");
			} else if (fill == false){
				signuperror.setText("Please Fill in All Fields");
	    	  } 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  
//    	  User userID = authorize();
    	  
      }
    });
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
        	loginManager.showLoginScreen();
        }
    });
  }

  private void signUp() throws SQLException, ClassNotFoundException {
	  // TODO Auto-generated method stub
	  PasswordEncryptionService pes = new PasswordEncryptionService();
	  if (username.getLength() != 0 && firstname.getLength() != 0 && lastname.getLength() != 0 && password.getLength() != 0 && confirmpassword.getLength() != 0) {
		  fill = true;	  
//		  User temp = new User(username.getText());
//		  temp.setFirstName(firstname.getText());
//		  temp.setLastName(lastname.getText());
//		  if(MySQLAccess.checkuser(username.getText()) == false) {
			  if(password.getText().equals(confirmpassword.getText())) {
				  passwordMatch = true;
				  try {
					  byte[] salt = pes.generateSalt();
					  byte[] encryptedPassword = pes.getEncryptedPassword(password.getText(), salt);
//					  temp.setSalt(salt);
//					  temp.setEncryptedPassword(encryptedPassword);
					  success = MySQLAccess.addUser(username.getText(), firstname.getText(), lastname.getText(), salt, encryptedPassword);
				  } catch (NoSuchAlgorithmException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  } catch (InvalidKeySpecException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  }
			  } else {
				  //System.out.println("Passwords Don't Match");
				  passwordMatch = false;
			  }
			  //System.out.println("User Added");
//		  } else {
			  //System.out.println("User already Exists");
//			  passwordMatch = true;
//			  return success;
//		  }
	  } else {
		  fill = false;
		  success = false;
	  }
  }
  
}
