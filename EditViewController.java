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
public class EditViewController {
  @FXML private Button editButton;
  @FXML private Button cancelButton;
  @FXML private TextField firstname;
  @FXML private TextField lastname;
  @FXML private TextField username;
  @FXML private PasswordField password;
  @FXML private PasswordField confirmpassword;
  @FXML private Text editerror;
  boolean passwordMatch;
  boolean fill; 
  
  public void initialize() {}

  public void initSessionID(final LoginManager loginManager, ResultSet userID) throws SQLException {
	  //sessionLabel.get.add("application/mainview.css");
	  firstname.setText(userID.getString("first_name"));
	  lastname.setText(userID.getString("last_name"));

	  editButton.setOnAction(new EventHandler<ActionEvent>() {
		  @Override public void handle(ActionEvent event) {
			  try {
				  ResultSet newuserID = editUser(userID);
				  if(passwordMatch == true) {
					  if (fill == true) {
						  loginManager.showMainView(newuserID);
					  } else {
						  editerror.setText("First Name and Last Name Must be Filled In");
					  }
				  } else if (fill == false){
					  editerror.setText("First Name and Last Name Must be Filled In");
				  } else {
					  editerror.setText("Passwords Don't Match");
				  }
			  } catch (Exception e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }

	  });
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent event) {
        	try {
				loginManager.showMainView(userID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    });
  }
  private ResultSet editUser(ResultSet userID) throws ClassNotFoundException, SQLException {
	  // TODO Auto-generated method stub
	  PasswordEncryptionService pes = new PasswordEncryptionService();
	  if(firstname.getText().length() != 0 && lastname.getText().length() != 0) {
		  fill = true;
		  if(password.getText().length() != 0 || confirmpassword.getText().length() != 0 || (password.getText().length() != 0 && confirmpassword.getText().length() != 0)) {
			  if(password.getText().equals(confirmpassword.getText())) {
				  passwordMatch = true;
				  try {
					  byte[] salt = pes.generateSalt();
					  byte[] encryptedPassword = pes.getEncryptedPassword(password.getText(), salt);
					  //			  temp.setSalt(salt);
					  //			  temp.setEncryptedPassword(encryptedPassword);
					  return MySQLAccess.editUser(firstname.getText(), lastname.getText(), salt, encryptedPassword, userID);
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

		  } else {
			  passwordMatch = true;
			  return MySQLAccess.editUser(firstname.getText(), lastname.getText(), userID);
		  }
	  } else {
		  fill = false;
	  }
	  return userID;
  }

  //  private void signUp() throws SQLException, ClassNotFoundException {
  //	  // TODO Auto-generated method stub
  //	  PasswordEncryptionService pes = new PasswordEncryptionService();
//	  if (username.getLength() != 0 && firstname.getLength() != 0 && lastname.getLength() != 0 && password.getLength() != 0 && confirmpassword.getLength() != 0) {
//		  fill = true;	  
////		  User temp = new User(username.getText());
////		  temp.setFirstName(firstname.getText());
////		  temp.setLastName(lastname.getText());
////		  if(MySQLAccess.checkuser(username.getText()) == false) {
//			  if(password.getText().equals(confirmpassword.getText())) {
//				  passwordMatch = true;
//				  try {
//					  byte[] salt = pes.generateSalt();
//					  byte[] encryptedPassword = pes.getEncryptedPassword(password.getText(), salt);
////					  temp.setSalt(salt);
////					  temp.setEncryptedPassword(encryptedPassword);
//					  success = MySQLAccess.addUser(username.getText(), firstname.getText(), lastname.getText(), salt, encryptedPassword);
//				  } catch (NoSuchAlgorithmException e) {
//					  // TODO Auto-generated catch block
//					  e.printStackTrace();
//				  } catch (InvalidKeySpecException e) {
//					  // TODO Auto-generated catch block
//					  e.printStackTrace();
//				  }
//			  } else {
//				  //System.out.println("Passwords Don't Match");
//				  passwordMatch = false;
//			  }
//			  //System.out.println("User Added");
////		  } else {
//			  //System.out.println("User already Exists");
////			  passwordMatch = true;
////			  return success;
////		  }
//	  } else {
//		  fill = false;
//		  success = false;
//	  }
//  }
  
}
