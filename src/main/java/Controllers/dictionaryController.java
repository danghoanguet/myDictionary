package Controllers;

import Models.Dictionary;
import Models.DictionaryManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dictionaryController implements Initializable {
    Dictionary dictionary = new Dictionary();
    DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private int indexOfSelectedWord;
    private final String path = "src/main/resources/Utils/data.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dictionaryManagement.insertFromFile(dictionary , path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = (int) (Math.random() * (dictionary.size()));
        for (int i = index; i < index + 15; i++) {
            listView.getItems().add(dictionary.get(i).getWord_target());
        }
        englishWord.setText(dictionary.get(index).getWord_target());
        explanationArea.setText(dictionary.get(index).getWord_explain());
         //initial state
        explanationArea.setEditable(false);
        saveBtn.setVisible(false);
        searchButton.setDisable(true);
         //when user types in search field
        searchField.setOnKeyTyped(keyEvent -> {
            if(searchField.getText().trim().equals("")) {
                searchButton.setDisable(true);
            }else {
                searchButton.setDisable(false);
            }
        });
    }

    @FXML
    private void handleOnClickSearchButton() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String searhWord = searchField.getText().trim();
        int limit = 0;
        for (int i = 0; i < dictionary.size() && limit < 10; i++) {
            String englishWord = dictionary.get(i).getWord_target();
            if (englishWord.toLowerCase().startsWith(searhWord.toLowerCase())) {
                list.add(englishWord);
                ++limit;
            }
        }
        if (list.isEmpty()) {
            resultsLabel.setText("Cant find any results");
            englishWord.setText(null);
            explanationArea.clear();
            listView.setItems(null);
            //showAlertNotFound();
        } else {
            resultsLabel.setText(list.size() + " results found");
            listView.setItems(list);

            explanationArea.clear();
        }

    }

    @FXML
    private void handleMouseClickAWord( MouseEvent arg0 ) {
        String selectedWord = listView.getSelectionModel().getSelectedItem();
        try {
            indexOfSelectedWord = dictionaryManagement.searchForWord(dictionary , selectedWord);
            englishWord.setText(dictionary.get(indexOfSelectedWord).getWord_target());
            explanationArea.setText(dictionary.get(indexOfSelectedWord).getWord_explain());

            explanationArea.setEditable(false);
            saveBtn.setVisible(false);
            deleteBtn.setVisible(true);
        }
        catch (IndexOutOfBoundsException e){

        }
    }

    @FXML
    private void handleOnClickEditButton(ActionEvent event) {
        explanationArea.setEditable(true);
        saveBtn.setVisible(true);
    }

    @FXML
    private void handleOnClickSaveButton(ActionEvent event) {
        dictionaryManagement.updateWord(dictionary , indexOfSelectedWord , explanationArea.getText() , path);
        englishWord.setText("");
        explanationArea.setText("");
    }
    @FXML
    void handleOnClickDeleteButton(ActionEvent event) {
        dictionaryManagement.deleteWord(dictionary, indexOfSelectedWord , path);
        listView.setItems(null);
        englishWord.setText("");
        explanationArea.setText("");
    }

    @FXML
    void handleOnClickAddButton(ActionEvent event) throws IOException {
        main.getChildren().clear();
       try {
           AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/Views/add.fxml"));
           main.getChildren().add(anchorPane);
       }
       catch (IOException e) {
           e.printStackTrace();
       }

    }

    @FXML
    private AnchorPane main;
    @FXML
    private Button addButton;
    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TextArea ExplainArea;
    @FXML
    private Label englishWord;
    @FXML
    private TextArea explanationArea;

    @FXML
    private Button saveBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label resultsLabel;


}
