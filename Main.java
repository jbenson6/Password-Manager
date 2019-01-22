package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import application.LoginManager;

/** Main application class for the login demo application */
public class Main extends Application {

	public static void main(String[] args) throws Exception { 
		launch(args); 
		}
  @Override public void start(Stage primarystage) throws IOException {
    
	Scene scene = new Scene(new StackPane());
    
    LoginManager loginManager = new LoginManager(scene);
    loginManager.showLoginScreen();

    primarystage.setTitle("Password Manager");
    primarystage.setScene(scene);
    primarystage.show();
  }
}
