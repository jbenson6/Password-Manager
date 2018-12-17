package application;

import javafx.event.*;
import application.MySQLAccess;
import application.SiteEncryption;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import application.LoginManager;
import java.sql.*;

import javax.crypto.SecretKey;

/** Controls the main application screen */
public class AddViewController {
  @FXML private Button AddPasswordButton;
  @FXML private Button cancelButton;
  @FXML private TextField name;
  @FXML private TextField url;
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Text adderror;
  boolean fill;
  
  public void initialize() {}
  
  public void initSessionID(final LoginManager loginManager, ResultSet userID) {
    //sessionLabel.get.add("application/mainview.css");
    AddPasswordButton.setOnAction(new EventHandler<ActionEvent>() {
    	@Override public void handle(ActionEvent event) {
    		if(name.getLength() != 0 && url.getLength() != 0 && password.getLength() != 0) {
    			fill = true;
    			SiteEncryption se = new SiteEncryption();
    			SecretKey sk;
				try {
					sk = se.getSecretEncryptionKey();
					MySQLAccess.addPassword(userID, name.getText(), url.getText(),username.getText(), sk, se.encryptText(password.getText(), sk));
					//Site site = new Site(name.getText(), url.getText(),username.getText(), sk, se.encryptText(password.getText(), sk));
	    			//userID.addSite(site);
	    			loginManager.showPasswordsView(userID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		} else {
    			fill = false;
    			adderror.setText("Please Fill in All Fields");
    		}
    	}
    });
    cancelButton.setOnAction(new EventHandler<ActionEvent>() {
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