import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HostsGrabber implements Initializable {

    public AnchorPane frame;
    //Main tab
    public PasswordField passwordField;
    public TextArea processLog;
    //Whitelist tab
    public TextField whitelistURLField;
    public ListView whitelist;
    //Custom tab
    public ListView customList;
    public TextField customURLField;
    //Hosts tab
    public TextArea hostsFile;
    //Options tab
    public RadioButton radio0;
    public RadioButton radio127;
    public RadioButton radio00;
    //Misc
    private String os;
    private String version;

    public void closeProgram() {
        Stage stage = (Stage) frame.getScene().getWindow();
        stage.close();
    }

    public void showAbout() {
        Stage about = new Stage();
        about.setTitle("About");
    }

    public void getHosts() {
        System.out.println("Working");
    }

    public void cancelTask() {
        System.out.println("Working");
    }

    public void addToWhitelist() {
        System.out.println("Working");
    }

    public void removeFromWhitelist() {
        System.out.println("Working");
    }

    private void loadOptions() {
        int numberOfOptions = 1;
        String[] currentOption;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Settings"));
            String currentLine;
            for (int i = 0; i < numberOfOptions; i++) {
                currentLine = bufferedReader.readLine();
                if (currentLine == null) {
                    processLog.appendText("\nError reading previous settings. Default settings applied.");
                    defaultSettings();
                    return;
                }
                currentOption = currentLine.split("=");
                switch (i) {
                    case 0:
                        if (currentOption[0].equals("selectedPrefix")) {
                            switch (currentOption[1]) {
                                case "radio0":
                                    radio0.setSelected(true);
                                    break;
                                case "radio127":
                                    radio127.setSelected(true);
                                    break;
                                case "radio00":
                                    radio00.setSelected(true);
                                    break;
                                default:
                                    System.out.println("wtf?");
                                    break;
                            }
                        }
                        break;
                    default:
                        System.out.println("wtf?");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            defaultSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void defaultSettings() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Settings"));
            bufferedWriter.write("selectedPrefix=radio0");
            radio0.setSelected(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Main tab
        os = System.getProperty("os.name");
        version = System.getProperty("os.version");
        processLog.setText("You are using " + os + " " + version);
        //Options tab
        ToggleGroup radioGroup = new ToggleGroup();
        radio0.setToggleGroup(radioGroup);
        radio127.setToggleGroup(radioGroup);
        radio00.setToggleGroup(radioGroup);
        loadOptions();
    }
}