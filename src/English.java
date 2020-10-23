import java.util.ArrayList;
import java.util.List;

public class English extends Alphabet {



    public English() {
        letters =new ArrayList<>();
        Character letter = 'A';
        for(int i = 0; i < 26; i++) {
            letters.add(letter);
            letter++;
        }
    }

}
