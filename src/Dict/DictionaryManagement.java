package Dict;

import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    public static List<String> searchD = new ArrayList<>();

    public static String dictionaryLookup(String wordToLookup) {
        for (int i = 0; i < array.size(); i++) {

            if (array.get(i).getWord_target().toLowerCase().equals(wordToLookup.toLowerCase())) {
                return array.get(i).getWord_explain();
            }
        }

        return "";
    }

    //Nhap vao tu file
    public static void InsertFromFile() throws IOException {
        Scanner sc = null;
        try {

            sc = new Scanner(new File("src/Database/Database.txt"));
            while (sc.hasNext()) {
                String word = sc.nextLine();
                String word_mean = sc.nextLine();
                array.add(new Word(word, word_mean));
            }
            for (int i = 0; i < array.size(); i++) {
                listWordTarget.add(array.get(i).getWord_target());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<String> dictionarySearcher(String wordSearch) {

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getWord_target().toLowerCase().startsWith(wordSearch.toLowerCase())) {
                searchD.add(array.get(i).getWord_target());
            } else
                continue;
        }
        Collections.sort(searchD);
        return searchD;

    }

    public static void dictionaryExportToFile()//hàm viết vào file Database//phục vụ chức năng thêm bớt tù
    {
        PrintWriter writer = null;
        try {
            FileWriter write = new FileWriter("src/Database/Database.txt");
            writer = new PrintWriter(write);
            for (int i = 0; i < array.size(); i++) {
                Word outfile = array.get(i);
                writer.println(outfile.getWord_target());
                writer.println(outfile.getWord_explain());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //sửa nghĩa từ
    public static String modified(String change_target, String change_explain) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getWord_target().toLowerCase().equals(change_target.toLowerCase())) {
                Dictionary.modifyword(i, new Word(change_target, change_explain));
            }
        }
        dictionaryExportToFile();
        return change_explain;
    }

    //xoá từ
    public static void deletew(String w_target, String w_explain)//hàm chỉnh sửa nghĩa từ//
    {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getWord_target().toLowerCase().equals(w_target.toLowerCase())) {
                Dictionary.deleteword(i);
            }
        }
        dictionaryExportToFile();
    }

    //thêm từ
    public static void addw(String w_target, String w_explain) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getWord_target().toLowerCase().equals(w_target.toLowerCase())) {
                Dictionary.modifyword(i,new Word(w_target,w_explain));
                return;
            }
        }
        Dictionary.addword(w_target, w_explain);
        dictionaryExportToFile();
    }
    //lọc từ
    public static void fillter()
    {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getWord_target().equals(array.get(i+1).getWord_target())) {
                Dictionary.deleteword(i+1);
            }
        }
        dictionaryExportToFile();
    }
}

