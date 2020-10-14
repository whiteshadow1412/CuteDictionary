package Dict;

import help.CompareWord;
import help.IOHTML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DictionaryHTMLManagement extends Dictionary {
    //static Dictionary dictionary =new Dictionary();

    public static void insertFromFile() throws IOException  {
        System.out.println("Start insert from file...");
        IOHTML rd = new IOHTML();
        ArrayList<Word> adds = rd.read();
        Collections.sort(adds, new CompareWord());
        array = adds;
        for (int i = 0; i < array.size(); i++) {
            listWordTarget.add(array.get(i).getWord_target());
        }
        System.out.println("End insert from file...");
    }
    public static void saveWordsToFile() {
        IOHTML iohtml = new IOHTML();
        iohtml.write(array);
    }
    public static void addWord(Word word) {
        String explain = word.getWord_explain();
        if (!explain.startsWith("<html>") || !explain.contains("</html>")) {
            word.setWord_explain("<html>" + explain + "</html>");
        }
        push(word);
        saveWordsToFile();
    }
    public static void removeWord(String w_target) {
            del(w_target);
            saveWordsToFile();
    }
    public static void editWord(int i,Word word){
        String explain = word.getWord_explain();
        if (!explain.startsWith("<html>") || !explain.contains("</html>")) {
            word.setWord_explain("<html>" + explain + "</html>");
        }
        modifyword(i,word);
        saveWordsToFile();
    }
}
