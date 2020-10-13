package Dict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {

    protected static ArrayList<Word> array = new ArrayList<>();
    protected static List<String> listWordTarget = new ArrayList<>();

    public static void modifyword(int i, Word modify) {
        array.set(i, modify);
    }

    public static void deleteword(int i) {
        array.remove(i);
        listWordTarget.remove(i);
    }

    public static void addword(String w_target, String w_explain) {
        Word word = new Word(w_target, w_explain);
        array.add(word);
        listWordTarget.add(w_target);
        Collections.sort(listWordTarget);
    }
}
