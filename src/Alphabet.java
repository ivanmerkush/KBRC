import java.util.List;

abstract public class Alphabet {
    protected List<Character> letters;

    public List<Character> getLetters() {
        return letters;
    }

    public int getSize() {
        return letters.size();
    }


}
