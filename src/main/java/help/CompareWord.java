package help;

import Dict.Word;

import java.util.Comparator;

public class CompareWord implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord_target().compareTo(o2.getWord_target());
    }
}