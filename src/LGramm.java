import java.util.ArrayList;
import java.util.List;

public class LGramm {
    List<Character> characters;
    List<Integer> positions;


    public LGramm(List<Character> characters) {
        this.characters = characters;
        this.positions = new ArrayList<>();
    }

    public void addPosition(int posititon) {
        positions.add(posititon);
    }

    public int getFrequency() {
        return positions.size();
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int template = a % b;
            a = b;
            b = template;
        }
        return a;
    }

    private int gcd(List<Integer> numbers) {
        int result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = gcd(result, numbers.get(i));
        }

        return result;
    }

    public int findGCD() {
        List<Integer> distances = new ArrayList<>();
        for (int i = 0; i < positions.size() - 1; i++) {
            distances.add(positions.get(i+1) - positions.get(i));
        }

        return gcd(distances);
    }
}
