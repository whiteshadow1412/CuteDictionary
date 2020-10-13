package sample;
//import com.gtranslate.Audio;
//import com.gtranslate.Language;
//import javazoom.jl.decoder.JavaLayerException;
import Dict.Dictionary;
import Dict.DictionaryManagement;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.EventHandler;

public class Controller extends Dictionary implements Initializable {
    @FXML
    public TextField searchField;
    public ListView listView = new ListView<>();
    public TextArea textArea;
    public Button searchButton;
    public TextField targetAdd;
    public TextField explainAdd;
    public Button edditButton;
    public MenuBar menuBar;

    public void initialize(URL location, ResourceBundle resources) {

        final ToolBar toolBar = new ToolBar();
        final Tooltip tooltip0 = new Tooltip();
        tooltip0.setText("Click one time to start edit, and click again to end");
        edditButton.setTooltip(tooltip0);

        try {
            DictionaryManagement.InsertFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(listWordTarget);
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        listView.setItems(data);
    }

    public void inputsearch(KeyEvent event) {
        String look = searchField.getText().toString();
        List<String> s = DictionaryManagement.dictionarySearcher(look);
        ObservableList<String> d = FXCollections.observableArrayList(s);
        listView.setItems(d);
        listView.scrollTo(look);
        DictionaryManagement.searchD.clear();
    }

    public void wordlookup(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            String wordLook = searchField.getText();
            textArea.setText(DictionaryManagement.dictionaryLookup(wordLook));
        }
    }

    //click để hiện nghĩa
    public void clicked(MouseEvent e) {
        try {
            searchField.setText(listView.getSelectionModel().getSelectedItem().toString());
            textArea.setText(DictionaryManagement.dictionaryLookup(listView.getSelectionModel().getSelectedItem().toString()));
        } catch (NullPointerException e1) {
            System.out.println("There is nothing");
        }
    }

    public void setSearchButton(MouseEvent e) {
        try {
            String wordLook = searchField.getText();
            textArea.setText(DictionaryManagement.dictionaryLookup(wordLook));
        } catch (NullPointerException e1) {
            System.out.println("There is nothing");
        }
    }

    //sửa từ
    public void edit(MouseEvent e) {
        if (textArea.isEditable()) {  // khóa edit để bắt đầu update từ
            String modding = textArea.getText();
            String point = searchField.getText();
            String s = DictionaryManagement.modified(point, modding);
            textArea.setText(s);
            textArea.setEditable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("You are changing this word");
            alert.showAndWait();
            alert.close();
            textArea.setEditable(true); // mở để bắt đầu edit
        }
    }

    //xoá từ
    public void delete(MouseEvent e) {
        String w_target_ = searchField.getText();
        String w_explain_ = textArea.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText("This word " + '"' + w_target_ + '"' + " will be deleted" + "\n" +
                "You can't search it again");
        alert.showAndWait();
        textArea.clear();
        searchField.clear();
        DictionaryManagement.deletew(w_target_, w_explain_);
        ObservableList<String> data = FXCollections.observableArrayList(listWordTarget);
        listView.setItems(data);
    }

    //thêm từ
    public void add(MouseEvent e) {
        String w_target_ = targetAdd.getText();
        String w_explain_ = explainAdd.getText();
        if(w_explain_.isEmpty() && w_target_.isEmpty()) {
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
        alert.setContentText("This word " + '"' + w_target_ + '"' + " will be added" + "\n" +
                "If this word is existed, the word will be overwritted");
        alert.showAndWait();
        DictionaryManagement.addw(w_target_, w_explain_);
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
    //TTS API Google
    /*
    public void handle(ActionEvent event) {
        InputStream sound = null;
        try {
            Audio audio = Audio.getInstance();
            sound = audio.getAudio("Hello World", Language.ENGLISH);
            audio.play(sound);
        } catch (IOException | JavaLayerException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sound.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     */


}