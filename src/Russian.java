import java.util.ArrayList;
import java.util.List;

public class Russian extends Alphabet {

    public Russian() {
        letters =new ArrayList<>();
        Character letter = 'А';
        for(int i = 0; i < 33; i++) {
            letters.add(letter);
            letter++;
        }
    }

}
