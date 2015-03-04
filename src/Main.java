import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HostsGrabber.fxml"));
        primaryStage.setTitle("Hosts");
        primaryStage.setScene(new Scene(root, 380, 400));
        primaryStage.resizableProperty().set(false);
        primaryStage.show();
    }
}
