package application;

import java.io.IOException;
import java.sql.*;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import application.LoginController;
import application.MainViewController;
/** Manages control flow for logins */
public class LoginManager {
  private Scene scene;

  public LoginManager(Scene scene) {
    this.scene = scene;
  }

  /**
   * Callback method invoked to notify that a user has been authenticated.
   * Will show the main application screen.
 * @throws Exception 
   */ 
  public void authenticated(ResultSet userID) throws Exception {
	  //System.out.println("Authenticated!");
	  showMainView(userID);
  }

  /**
   * Callback method invoked to notify that a user has logged out of the main application.
   * Will show the login application screen.
   */ 
  public void logout() {
    showLoginScreen();
  }
  
  public void showLoginScreen() {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("login.fxml")
      );
      scene.setRoot((Parent) loader.load());
      LoginController controller = 
        loader.<LoginController>getController();
      controller.initManager(this);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  void showMainView(ResultSet userID) throws Exception {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("mainview.fxml")
      );
      scene.setRoot((Parent) loader.load());
      MainViewController controller = 
        loader.<MainViewController>getController();
      controller.initSessionID(this, userID);
    } catch (IOException ex) {
      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  void showPasswordsView(ResultSet userID) throws ClassNotFoundException, SQLException {
	    try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("passwordsview.fxml")
	      );
	      scene.setRoot((Parent) loader.load());
	      PasswordsViewController controller = 
	        loader.<PasswordsViewController>getController();
	      controller.initSessionID(this, userID);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
	  }

public void showAddView(ResultSet userID) {
	// TODO Auto-generated method stub
	try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("addview.fxml")
	      );
	      scene.setRoot((Parent) loader.load());
	      AddViewController controller = 
	        loader.<AddViewController>getController();
	      controller.initSessionID(this, userID);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
}
public void showSignUpView() {
	// TODO Auto-generated method stub
	try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("signupview.fxml")
	      );
	      scene.setRoot((Parent) loader.load());
	      SignUpViewController controller = 
	        loader.<SignUpViewController>getController();
	      controller.initSessionID(this);
	    } catch (IOException ex) {
	      Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
	    }
}
  
}