package sample;
//import com.gtranslate.Audio;
//import com.gtranslate.Language;
//import javazoom.jl.decoder.JavaLayerException;
import Dict.Dictionary;
import Dict.DictionaryHTMLManagement;
import Dict.Word;
import Google.GoogleAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller extends Dictionary implements Initializable {
    @FXML
    public TextField searchField;
    public ListView listView = new ListView<>();
    public Button searchButton;
    public TextField targetAdd;
    public TextField explainAdd;
    public Button edditButton;
    public MenuBar menuBar;
    public WebView webView;
    public WebEngine webEngine;
    public TextField targetEdit;
    public TextField explainEdit;

    public void initialize(URL location, ResourceBundle resources) {

        final ToolBar toolBar = new ToolBar();
        final Tooltip tooltip0 = new Tooltip();
        try {
            //DictionaryManagement.InsertFromFile();
            DictionaryHTMLManagement.insertFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        webEngine = webView.getEngine();
//        webEngine.setUserStyleSheetLocation(getClass().getResource("src/Graphic/webView.css").toString());
        Collections.sort(listWordTarget);
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        listView.setItems(data);
    }

    public void inputsearch(KeyEvent event) {
        String look = searchField.getText().toString();
        ArrayList<String> s = DictionaryHTMLManagement.searcher(look);
        ObservableList<String> d = FXCollections.observableArrayList(s);
        listView.setItems(d);
        listView.scrollTo(look);
    }

    public void wordlookup(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            String wordLook = searchField.getText();
            String wordFind = DictionaryHTMLManagement.lookup(wordLook);
            //textArea.setText(wordFind);
            webEngine.loadContent(wordFind, "text/html");

        }
    }

    //click để hiện nghĩa
    public void clicked(MouseEvent e) {
        try {
            searchField.setText(listView.getSelectionModel().getSelectedItem().toString());
            //textArea.setText();
            String wordFind = DictionaryHTMLManagement.lookup(listView.getSelectionModel().getSelectedItem().toString());
            webEngine.loadContent(wordFind, "text/html");
        } catch (NullPointerException e1) {
            System.out.println("There is nothing");
        }
    }

    public void setSearchButton(MouseEvent e) {
        try {
            String wordLook = searchField.getText();
            String wordFind=DictionaryHTMLManagement.lookup(wordLook);
            //textArea.setText();
            webEngine.loadContent(wordFind, "text/html");
        } catch (NullPointerException e1) {
            System.out.println("There is nothing");
        }
    }

    //xoá từ
    public void delete(MouseEvent e) {
        String w_target_ = searchField.getText();
        //String w_explain_ = textArea.getText();
        //Word w = new Word(w_target_,w_explain_);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText("This word " + '"' + w_target_ + '"' + " will be deleted" + "\n" +
                "You can't search it again");
        alert.showAndWait();
        webEngine.loadContent("<html></html>","text/html");
        searchField.clear();
        //DictionaryManagement.deletew(w_target_, w_explain_);
        DictionaryHTMLManagement.removeWord(w_target_);
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        listView.setItems(data);
    }

    //thêm từ
    public void add(MouseEvent e) {
        String w_target_ = targetAdd.getText();
        String w_explain_ = explainAdd.getText();
        Word w = new Word(w_target_,w_explain_);
        if(w_explain_.isEmpty() || w_target_.isEmpty()) {
            Alert.AlertType alertAlertType = AlertType.INFORMATION;
            Alert alert = new Alert(alertAlertType);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill out all fields!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText("This word " + '"' + w_target_ + '"' + " was be added" + "\n" +
                "If this word is existed, the word will be overwritted");
        alert.showAndWait();
        DictionaryHTMLManagement.addWord(w);
        targetAdd.clear();
        explainAdd.clear();
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        listView.setItems(data);
    }
    public void eddit(MouseEvent e) {
        String w_target_ = targetEdit.getText();
        String w_explain_ = explainEdit.getText();
        Word w = new Word(w_target_,w_explain_);
        if(w_explain_.isEmpty() || w_target_.isEmpty()) {
            Alert.AlertType alertAlertType = AlertType.INFORMATION;
            Alert alert = new Alert(alertAlertType);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("You need to fill out all fields!");
            alert.showAndWait();
            return;
        }
        int length = array.size();
        int i = Dictionary.binarySearcher(0,length-1,w.getWord_target());
        if(i<0||i>=length){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("This word is'nt exist , you can add it in button Add");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText("This word " + '"' + w_target_ + '"' + " was edited");
        alert.showAndWait();
        DictionaryHTMLManagement.editWord(i,w);
        targetEdit.clear();
        explainEdit.clear();
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        listView.setItems(data);
    }
    public void About(ActionEvent event)//hàm thông tin
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("MIT License\n" +
                "\n" +
                "Copyright (c) 2020 whiteshadow1412\n" +
                "\n" +
                "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                "of this software and associated documentation files (the \"Software\"), to deal\n" +
                "in the Software without restriction, including without limitation the rights\n" +
                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                "copies of the Software, and to permit persons to whom the Software is\n" +
                "furnished to do so, subject to the following conditions:\n" +
                "\n" +
                "The above copyright notice and this permission notice shall be included in all\n" +
                "copies or substantial portions of the Software.\n" +
                "\n" +
                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                "SOFTWARE.");
        alert.show();
    }

    public void Speak(MouseEvent event) {
        String spelling = searchField.getText();
        try {
            GoogleAPI.speak(spelling);
        } catch (IOException e) {
            System.out.println("[ERROR]: Speak word.");
        }
    }
}