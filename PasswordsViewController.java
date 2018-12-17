package application;

import javafx.collections.ObservableList;
import application.MySQLAccess;
import application.SiteEncryption;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.sql.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PasswordsViewController {
	@FXML private Button logoutButton;
	@FXML private Button HomeButton;
	@FXML private Button AddPasswordButton;
	@FXML private Button removeButton;
	@FXML private ListView<Site> observableList;
	@FXML private Label item;
	@FXML public void handleMouseClick(MouseEvent arg0) {
	    System.out.println("clicked on " + observableList.getSelectionModel().getSelectedItem());
	}
	
	public void initialize() {}
	public void initSessionID(final LoginManager loginManager, ResultSet userID) throws ClassNotFoundException, SQLException {
		ObservableList<Site> items = observableList.getItems();
//		for(int i = 0; i < userID.getSites().size(); i++) {
//			items.add(userID.getSites().get(i));
		//		}
		userID.beforeFirst();
		userID.next();
		ResultSet sites = MySQLAccess.getSites(userID);
			while(sites.next()) {
				SecretKey key = new SecretKeySpec(sites.getBytes("secret_key"), 0, sites.getBytes(8).length, "AES");
				Site site = new Site(sites.getString("domain_name"), sites.getString("url"), sites.getString("username"), key, sites.getBytes("cypher_text"));
//				System.out.println(site.getDomainName() + " " + site.getDomain() + " " + site.getUsername() + " " + site.getCypherText() + " " + site.getSecretKey());
				items.add(site);
			}
		observableList.setEditable(true);
		observableList.setCellFactory(param -> new ListCell<Site>() {
            @Override
            protected void updateItem(Site item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getDomainName() == null) {
                    setText(null);
                } else {
                    setText(item.getDomainName());
                }
            }
        });
		
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
		HomeButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent event) {
	          try {
				loginManager.showMainView(userID);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	      });
		AddPasswordButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent event) {
	          loginManager.showAddView(userID);
	        	
	        }
	      });
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent event) {
	        	final int selectedIdx = observableList.getSelectionModel().getSelectedIndex();
	            if (selectedIdx != -1) {
	              Site itemToRemove = observableList.getSelectionModel().getSelectedItem();
	              //userID.removeSite(itemToRemove);
//	              final int newSelectedIdx =
//	                (selectedIdx == observableList.getItems().size() - 1)
//	                   ? selectedIdx - 1
//	                   : selectedIdx;
	     
	              observableList.getItems().remove(selectedIdx);
	              //status.setText("Removed " + itemToRemove);
	              //observableList.getSelectionModel().select(newSelectedIdx);
	              item.setText("");
	              try {
					MySQLAccess.removePassword(itemToRemove, userID);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            }
	          }
	      });
		observableList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
		        	try {
		        		SiteEncryption se = new SiteEncryption();
		        		String password = se.decryptText(observableList.getSelectionModel().getSelectedItem().getCypherText(),
		        				observableList.getSelectionModel().getSelectedItem().getSecretKey());
						item.setText("Site: " + observableList.getSelectionModel().getSelectedItem().getDomainName() 
								+ "\nURL: " + observableList.getSelectionModel().getSelectedItem().getDomain() 
								+ "\nUsername: " + observableList.getSelectionModel().getSelectedItem().getUsername()
								+ "\nPassword: " + password);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		});
	}
}
