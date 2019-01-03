# Password-Manager
Java based password manager. Uses java, javafx, fxml, css, and uses local MySQL database

Need to have MySQL installed, along with the java connector. Connector must be included in the build path. Must manually create schema called password_manager.
Must create a user in Database Workbench that is limited to hosts matching localhost, and can only access the password_manager database with username: password_manager and no password. Then add entry under schema privledges so user can only access password_manager database and has right to create, references, select, insert, update, delete, and execute.

Executable jar is in folder. Download jar and batch file. To run, either execute batch command in command prompt under the same directory of jar file or double click batch file (assuming jar and batch file are in the same directory).



