import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HostsGrabber implements Initializable{

    public AnchorPane frame;
    public PasswordField passwordField;
    public TextArea processLog;
    public TextField whitelistURLField;
    public ListView whitelist;

    public void closeProgram() {
        Stage stage = (Stage) frame.getScene().getWindow();
        stage.close();
    }

    public void showAbout() {
        Stage about = new Stage();
        about.setTitle("About");
    }

    public void getHosts(ActionEvent actionEvent) {
        System.out.println("Working");
    }

    public void cancelTask(ActionEvent actionEvent) {
        System.out.println("Working");
    }

    public void addToWhitelist(ActionEvent actionEvent) {
        System.out.println("Working");
    }

    public void removeFromWhitelist(ActionEvent actionEvent) {
        System.out.println("Working");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        whitelistURLField.setText("Initialize working.");
    }
}