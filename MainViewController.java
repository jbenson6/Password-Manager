package application;

import javafx.event.*;
import application.MySQLAccess;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import application.LoginManager;

/** Controls the main application screen */
public class MainViewController {
  @FXML private Button logoutButton;
  @FXML private Button passwordsButton;
  @FXML private Text  sessionLabel;
  @FXML private Text  savedPasswords;
  @FXML private Text  unsafePasswords;
  
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager, ResultSet userID) throws Exception {
	  //ResultSetMetaData meta = userID.getMetaData();
	  int count = 0;
	  if (count == 0) {
		  userID.beforeFirst();
		  if (userID.next()) {
			  String columnFirst = userID.getString(3);
			  String columnLast = userID.getString(4);
			  sessionLabel.setText(columnFirst + " " + columnLast);
		  }
		  count++;
	  }
	  userID.beforeFirst();
	  userID.next();
	  int savedPasswordsInt = MySQLAccess.getPasswordsInt(userID);
	  savedPasswords.setText(Integer.toString(savedPasswordsInt));
	  
	  userID.beforeFirst();
	  userID.next();
	  int unsafePasswordsInt = MySQLAccess.getUnsafeInt(userID);
	  unsafePasswords.setText(Integer.toString(unsafePasswordsInt));
	  //sessionLabel.setText(userID.getString("first_name")+ " " + userID.getString("last_name"));
	  //sessionLabel.get.add("application/mainview.css");
	  //    savedPasswords.setText(userID.getSize());
	  //    unsafePasswords.setText(userID.getUnsafe());
	  logoutButton.setOnAction(new EventHandler<ActionEvent>() {
		  @Override public void handle(ActionEvent event) {
			  try {
				userID.close();
				loginManager.logout();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		  }
	  });
	  passwordsButton.setOnAction(new EventHandler<ActionEvent>() {
		  @Override public void handle(ActionEvent event) {
			  try {
				loginManager.showPasswordsView(userID);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	  });
  }

}