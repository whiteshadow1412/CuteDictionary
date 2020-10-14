package Dict;

import help.CompareWord;

import java.util.*;

public class Dictionary {

    protected static ArrayList<Word> array = new ArrayList<>();
    protected static List<String> listWordTarget = new ArrayList<>();

    public static void modifyword(int i, Word modify) {
        array.set(i, modify);
    }

    public static void del(String w_target) {
        int length = array.size();
        int index = binarySearcher(0, length - 1, w_target);
        array.remove(index);
        listWordTarget.remove(w_target);
        //System.out.println(w_target);
    }

    public static void push(Word word) {
        int length = array.size();
        int index = searchIndexInsert(0, length - 1, word.getWord_target());
        if (index <= length && index >= 0) {
            array.add(index, word);
            listWordTarget.add(word.getWord_target());
        }
        Collections.sort(array,new CompareWord());
    }

    public static int searchIndexInsert(int start, int end, String w_target) {
        if (end < start) return start;
        int mid = start + (end - start) / 2;
        if (mid == array.size()) return mid;
        Word word = array.get(mid);
        int compare = word.getWord_target().compareTo(w_target);
        if (compare == 0) return -1;
        if (compare > 0) return searchIndexInsert(start, mid - 1, w_target);
        return searchIndexInsert(mid + 1, end, w_target);
    }

    public static Word binaryLookup(int start, int end, String w_target) {
        if (end < start) return null;
        int mid = start + (end - start) / 2;
        Word word = array.get(mid);
        String currentw_target = word.getWord_target();
        int compare = currentw_target.compareTo(w_target);
        if (compare == 0) return word;
        if (compare > 0) return binaryLookup(start, mid - 1, w_target);
        return binaryLookup(mid + 1, end, w_target);
    }

    public static int binarySearcher(int start, int end, String w_target) {
        if (end < start) return -1;
        int mid = start + (end - start) / 2;
        Word word = array.get(mid);
        String currentw_target = word.getWord_target();
        if (currentw_target.startsWith(w_target)) {
            return mid;
        }
        int compare = currentw_target.compareTo(w_target);
        if (compare == 0) return mid;
        if (compare > 0) return binarySearcher(start, mid - 1, w_target);
        return binarySearcher(mid + 1, end, w_target);
    }

    public static String lookup(String w_target) {
        return binaryLookup(0, array.size() - 1, w_target).getWord_explain();
    }


    public static ArrayList<Word> getWords() {
        return array;
    }

    public void setWords(ArrayList<Word> words) {
        this.array = words;
    }
    public static ArrayList<String> searcher(String w_target) {
        ArrayList<String> result = new ArrayList<>();
        int index = binarySearcher(0, array.size() - 1, w_target);
        if (index >= 0) {
            result.add(array.get(index).getWord_target());
            int left = index - 1, right = index + 1;

            while (left >= 0) {
                Word leftWord = array.get(left--);
                if (leftWord.getWord_target().startsWith(w_target))
                    result.add(leftWord.getWord_target());
                else
                    break;
            }

            int length = array.size();
            while (right < length) {
                Word leftWord = array.get(right++);
                if (leftWord.getWord_target().startsWith(w_target))
                    result.add(leftWord.getWord_target());
                else
                    break;
            }
        }
        return result;
    }

}
