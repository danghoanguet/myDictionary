package Controllers;

import Models.DictionaryManagement;
import Models.Word;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addController implements Initializable {

    DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final String path = "src\\Utils\\data.txt";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //saveButton.setVisible(false);
        boolean isEmptyInput = VietnameseWordArea.getText().isEmpty() || EnglishWordArea.getText().isEmpty();
        //initial state
        if (isEmptyInput) {
            saveButton.setDisable(true);
        }
        EnglishWordArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle( KeyEvent keyEvent ) {
                if (VietnameseWordArea.getText().isEmpty() || EnglishWordArea.getText().isEmpty() || VietnameseWordArea.getText().equals("") || EnglishWordArea.getText().equals("")) {
                    saveButton.setDisable(true);
                } else {
                    saveButton.setDisable(false);
                }
            }
        });
        VietnameseWordArea.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle( KeyEvent keyEvent ) {
                if (VietnameseWordArea.getText().isEmpty() || EnglishWordArea.getText().isEmpty() || VietnameseWordArea.getText().equals("") || EnglishWordArea.getText().equals("")) {
                    saveButton.setDisable(true);
                } else {
                    saveButton.setDisable(false);
                }
            }
        });
    }

    @FXML
    private TextArea EnglishWordArea;

    @FXML
    private TextArea VietnameseWordArea;

    @FXML
    private Button backButton;

    @FXML
    private Button saveButton;

    @FXML
    private AnchorPane addPage;

    @FXML
    void handleOnClickBackButton(ActionEvent event) {

        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/Views/dictionaryGui.fxml"));
            addPage.getChildren().clear();
            addPage.getChildren().add(anchorPane);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handleOnClickSaveButton(ActionEvent event) {
        Word word = new Word(EnglishWordArea.getText().trim(),VietnameseWordArea.getText().trim());
        dictionaryManagement.addWord(word,path);
        EnglishWordArea.setText("");
        VietnameseWordArea.setText("");
    }
}
