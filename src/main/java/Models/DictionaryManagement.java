package Models;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
   // final String path = "C:\\Users\\Admin\\IdeaProjects\\LearnJava\\myDictionary\\data.txt";
    public void showAllWords(Dictionary myDictionary) {
        for (Word word : myDictionary) {
            System.out.format("  %20s  %30s ", "|English :" + word.getWord_target(), " \n|Vietnamese: " + word.getWord_explain());
            System.out.println("");
        }
    }

    public void insertFromFile(Dictionary myDictionary, String path) throws IOException {
        Scanner sc = new Scanner(Paths.get
                (path), StandardCharsets.UTF_8);
        String englishWord = sc.nextLine();
        String currLine = "";
        while (sc.hasNextLine()) {
            String meaning = "";
            Word word = new Word();
            englishWord = englishWord.replace("|", "");
            String[] part = englishWord.split("/", 2);
            word.setWord_target(part[0]);

            if (part.length > 1) meaning += "\n" + "/" + part[1];
            else meaning += sc.nextLine() + "\n";
            while (sc.hasNextLine()) {
                currLine = sc.nextLine();
                if (!currLine.startsWith("|")) {
                    meaning += "\n" + currLine;
                } else {
                    englishWord = currLine;
                    break;
                }
            }
            word.setWord_explain(meaning);
            myDictionary.add(word);
        }
        sc.close();
    }

    public void exportToFile(Dictionary myDictionary , String path) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter printWriter = new PrintWriter(path, "UTF-8");
        for (Word word : myDictionary) {
            printWriter.println("\n"+ "|" + word.getWord_target() + "\n" + word.getWord_explain());
        }
        printWriter.close();
    }

    public int searchForWord(Dictionary dictionary, String word_target) {
        try {
            for(int i = 0 ; i < dictionary.size(); i++) {
                if(dictionary.get(i).getWord_target().compareTo(word_target) == 0) {
                    return i;
                }
            }
        }
        catch (Exception e) {
            System.out.println("" );
        }
        return -1;
    }

    public void updateWord( Dictionary dictionary , int index , String meaning , String path ) {
        try {
            dictionary.get(index).setWord_explain(meaning);
            exportToFile(dictionary , path);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void deleteWord( Dictionary dictionary , int index , String path ) {
        try {
            dictionary.remove(index);
            exportToFile(dictionary , path);
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addWord( Word word, String path ) {
        try (FileWriter fileWriter = new FileWriter(path,true);
             BufferedWriter buf = new BufferedWriter(fileWriter)){
            buf.write("|" + word.getWord_target() + "\n" + word.getWord_explain());
        }catch (IOException e){
            System.out.println("IOException.");
        }
        catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
    }
}
