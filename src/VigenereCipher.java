import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VigenereCipher {
    private final Alphabet alphabet;
    private final List<Character> key;

    private List<List<Character>> table;
    private final int m;
    private final int n;


    public VigenereCipher(Alphabet alphabet, String key) {
        this.alphabet = alphabet;
        this.key = new ArrayList<>();

        // For each character in the String
        // add it to the List
        for (char ch : key.toCharArray()) {
            this.key.add(ch);
        }
        m = key.length();
        n = alphabet.getSize();
        setTable();
    }

    public void setTable() {
        table = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            table.add(new ArrayList<>());
            char letter = key.get(i);
            int index = alphabet.getLetters().indexOf(letter);
            for(int j = 0; j < n; j++) {
                table.get(i).add(alphabet.getLetters().get(index));
                if(index != n - 1) {
                    index++;
                }
                else {
                    index = 0;
                }
            }

        }

    }

    public String encryption(String text) {
        int length = text.length();
        char[] originalText =  text.toCharArray();
        StringBuilder result = new StringBuilder();
        int currentRow = 0;
        for(int i = 0; i < length;i++) {
            int currentColumn = alphabet.getLetters().indexOf(originalText[i]);
            if(currentColumn == -1) {
                result.append( originalText[i]);
            }
            else {
                result.append(table.get(currentRow).get(currentColumn));
            }
            if(currentRow != m-1) {
                currentRow++;
            }
            else {
                currentRow = 0;
            }
        }
        return result.toString();
    }


}
