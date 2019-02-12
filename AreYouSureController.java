package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import application.LoginManager;
import application.MySQLAccess;

public class AreYouSureController {

	@FXML private Button areyousureyes;
	@FXML private Button areyousureno;
//	@FXML private Button logoutButton;
//	@FXML private Button passwordsButton;
//	@FXML private Button deleteButton;
//	@FXML private Button editButton;
	@FXML private Text  sessionLabel;
	@FXML private Text  savedPasswords;
	@FXML private Text  unsafePasswords;

	public void initialize() {}
	public void initSessionID(final LoginManager loginManager, ResultSet userID) throws Exception {
		// TODO Auto-generated method stub
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
		areyousureyes.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent event) {
				try {
					MySQLAccess.deleteUser(userID);
					loginManager.logout();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		areyousureno.setOnAction(new EventHandler<ActionEvent>() {
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

}
