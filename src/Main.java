import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.chartpart.Chart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
//
//        String line = reader.readLine();
//        StringBuilder str = new StringBuilder();
//        while(line != null) {
//            str.append(line.toUpperCase()).append("\n");
//            line = reader.readLine();
//        }
//        List<Character> key = new ArrayList<>();
////        key.add('K');
////        key.add('E');
////        key.add('Y');
////        key.add('W');
////        key.add('O');
////        key.add('R');
////        key.add('D');
//        List<Character> anotherKey = new ArrayList<>();
//        key.add('A');
//        key.add('B');
//        key.add('E');
//        key.add('T');
//        key.add('A');
//        key.add('L');
//        key.add('I');
//        key.add('P');
//        key.add('O');
//        key.add('P');
//        key.add('R');
//        key.add('O');
//        key.add('T');
//        key.add('E');
//        key.add('I');
//        key.add('N');
//        key.add('E');
//        key.add('M');
//        key.add('I');
//        key.add('A');
//
//        VigenereCipher vigenereCipher = new VigenereCipher(new English(), key);
//
//        String result = vigenereCipher.encryption(str.toString());
//
//
//        int length = Kasiski.findLength(new English(), result);
//
//        System.out.println("Keyword length = " + length);
//
//        String keyword = Kasiski.getKey(result, length);
//        System.out.println(keyword);
//        System.exit(0);
        List<String> readText = Files.readAllLines(Paths.get("text.txt"));
        StringBuilder str = new StringBuilder();
        readText.forEach(x -> str.append(str).append("\n"));
        String text = str.toString();
        final int MAX_LENGTH = 20000;
        final int maxStartPoint = text.length() - MAX_LENGTH - 1;
        Random randomGen = new Random();
        String key = "KEYWORD";
        VigenereCipher vigenereCipher = new VigenereCipher(new English(),key);

        List<Integer> lengths = new ArrayList<>();
        List<Double> successData = new ArrayList<>();

        for (int i = 500; i <= MAX_LENGTH; i += 500) {
            int successes = 0;
            final int ATTEMPTS = 50;
            for (int j = 0; j < ATTEMPTS; j++) {
                int randomStartPoint = randomGen.nextInt(maxStartPoint);
                String strToEncode = text.substring(randomStartPoint, i + randomStartPoint);
                String encodedString = vigenereCipher.encryption(strToEncode);
                int keywordLen = Kasiski.findLength(encodedString);
                String decodedKeyword = Kasiski.getKey(encodedString, keywordLen);
                if (key.equals(decodedKeyword)) {
                    successes++;
                }
                System.out.println(decodedKeyword);
            }
            lengths.add(i);
            successData.add(successes / (double) ATTEMPTS);
            System.out.println("i=" + i);
        };
        Chart chart = QuickChart.getChart("Text length increases", "Text length", "Success rate", "y(x)", lengths, successData);
        new SwingWrapper(chart).displayChart();

        int FIXED_TEXT_LENGTH = 10000;
        List<Integer> keyWordsLength = new ArrayList<>();
        List<Double> successData1 = new ArrayList<>();
        for (int i = 1; i <= key.length(); i++) {
            int successes = 0;
            final int ATTEMPTS = 50;
            for (int j = 0; j < ATTEMPTS; j++) {
                int randomStartPoint = randomGen.nextInt(maxStartPoint);
                String strToEncode = text.substring(randomStartPoint, randomStartPoint + FIXED_TEXT_LENGTH);
                VigenereCipher tempViginereCipher = new VigenereCipher( new English(), key.substring(0, i));
                String encodedString = tempViginereCipher.encryption(strToEncode);
                int keywordLen = Kasiski.findLength(encodedString);
                String decodedKeyword = Kasiski.getKey(encodedString, keywordLen);
                if (key.substring(0, i).equals(decodedKeyword)) {
                    successes++;
                }
                System.out.println(decodedKeyword);
            }
            keyWordsLength.add(i);
            successData1.add(successes / (double) ATTEMPTS);
            System.out.println("i=" + i);
        };

        Chart chart1 = QuickChart.getChart("KEYWORD length increases", "KeyWord length", "Success rate", "y(x)", keyWordsLength, successData1);
        new SwingWrapper(chart1).displayChart();
    }
}
