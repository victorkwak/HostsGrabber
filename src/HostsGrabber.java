import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
    public ListView<String> whitelist;
    public Button whitelistAddButton;
    ObservableList<String> whitelistEntries;
    //Blacklist tab
    public ListView<String> blacklist;
    public TextField blacklistURLField;
    public Button blacklistAddButton;
    ObservableList<String> blacklistEntries;
    //Hosts tab
    public TextArea hostsFile;
    //Options tab
    public RadioButton radio0;
    public RadioButton radio127;
    public RadioButton radio00;
    public Button startButton;
    public Button cancelButton;
    public Tab whiteListTab;
    //OS Information
    private String os;
    private String version;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Main tab
        os = System.getProperty("os.name");
        version = System.getProperty("os.version");
        processLog.setEditable(false);
        processLog.appendText("You are using " + os + " " + version + "\n");
        //Whitelist tab
        initializeWhitelist();
        //Blacklist tab
        initializeBlacklist();
        //Options tab
        ToggleGroup radioGroup = new ToggleGroup();
        radio0.setToggleGroup(radioGroup);
        radio127.setToggleGroup(radioGroup);
        radio00.setToggleGroup(radioGroup);
        loadOptions();
        startButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
        cancelButton.setDisable(true);
    }

    public void closeProgram() {
        Stage stage = (Stage) frame.getScene().getWindow();
        stage.close();
    }

    public void showAbout() {
        Stage about = new Stage();
        about.setTitle("About");
    }

    public void getHosts() {
        if (verifyPassword(passwordField.getText())) {
            startButton.setDisable(true);
            passwordField.setEditable(false);
            passwordField.setDisable(true);
            cancelButton.setDisable(false);
//            GetHosts getHosts = new GetHosts();
//            getHosts.addPropertyChangeListener(this);
//            getHosts.execute();
        } else {
            processLog.appendText("Incorrect password.\n");
            System.out.println("Incorrect password");
        }
    }

    public void cancelTask() {
        System.out.println("Working");
    }

    public void initializeWhitelist() {
        whitelistEntries = FXCollections.observableArrayList();
        try (BufferedReader whitelistReader = new BufferedReader(new FileReader("Lists/Whitelist"))) {
            String currentLine;
            while ((currentLine = whitelistReader.readLine()) != null) {
                whitelistEntries.add(currentLine);
            }
            whitelist.setItems(whitelistEntries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addToWhitelist() {
        String toAdd = whitelistURLField.getText();
        if (toAdd != null) {
            whitelistEntries.add(whitelistURLField.getText());
            whitelistURLField.setText("");
            writeToList("Lists/Whitelist", whitelistEntries);
        }
    }

    public void removeFromWhitelist() {
        String toRemove = whitelist.getSelectionModel().getSelectedItem();
        if (toRemove != null) {
            whitelist.getSelectionModel().clearSelection();
            whitelistEntries.remove(toRemove);
            writeToList("Lists/Whitelist", whitelistEntries);
        }
    }

    public void initializeBlacklist() {
        blacklistEntries = FXCollections.observableArrayList();
        try (BufferedReader whitelistReader = new BufferedReader(new FileReader("Lists/Blacklist"))) {
            String currentLine;
            while ((currentLine = whitelistReader.readLine()) != null) {
                blacklistEntries.add(currentLine);
            }
            blacklist.setItems(blacklistEntries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToBlacklist() {
        String toAdd = blacklistURLField.getText();
        if (toAdd != null) {
            blacklistEntries.add(blacklistURLField.getText());
            blacklistURLField.setText("");
            writeToList("Lists/Blacklist", blacklistEntries);
        }
    }

    public void removeFromBlacklist() {
        String toRemove = blacklist.getSelectionModel().getSelectedItem();
        if (toRemove != null) {
            blacklist.getSelectionModel().clearSelection();
            blacklistEntries.remove(toRemove);
            writeToList("Lists/Blacklist", blacklistEntries);
        }
    }

    private void writeToList(String file, ObservableList<String> list) {
        try (BufferedWriter listWriter = new BufferedWriter(new FileWriter(file))) {
            for (String s : list) {
                listWriter.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadOptions() {
        int numberOfOptions = 1;
        String[] currentOption;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Lists/Settings"))) {
            String currentLine;
            for (int i = 0; i < numberOfOptions; i++) {
                currentLine = bufferedReader.readLine();
                if (currentLine == null) {
                    processLog.appendText("Error reading previous settings. Default settings applied.\n");
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Settings"))) {
            bufferedWriter.write("selectedPrefix=radio0");
            radio0.setSelected(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyPassword(String password) {
        boolean working = false;
        if (os.contains("Mac") || os.contains("Linux")) {
            String[] commands = {"/bin/bash", "-c",
                    "echo " + password + " | sudo -S echo working && " +
                            "sudo -K"}; //sudo -K makes it so that another sudo command cannot be made without the password
            Process vPass = null;
            try {
                vPass = Runtime.getRuntime().exec(commands);
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(vPass.getInputStream()))) {
                    String currentLine;
                    while ((currentLine = bufferedReader.readLine()) != null) {
                        if (currentLine.equals("working")) {
                            working = true;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (vPass != null) {
                    vPass.destroy();
                }
            }
        }
        return working;
    }

    public void tabListener(Event event) {
        if (event.getTarget().toString().contains("Main")) {
            System.out.println("Main");
            startButton.setDefaultButton(true);
        } else if (event.getTarget().toString().contains("Whitelist")) {
            System.out.println("Whitelist");
            whitelistAddButton.setDefaultButton(true);
        } else if (event.getTarget().toString().contains("Blacklist")) {
            System.out.println("Blacklist");
            blacklistAddButton.setDefaultButton(true);
        } else if (event.getTarget().toString().contains("Hosts")) {
            System.out.println("Hosts");
        } else if (event.getTarget().toString().contains("Options")) {
            System.out.println("Options");
        }
    }
}