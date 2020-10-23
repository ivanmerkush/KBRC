import javafx.util.Pair;
import javax.crypto.Cipher;
import java.util.*;
import java.util.stream.Collectors;

public class Kasiski {
    static Alphabet alphabet = new English();

    static String getKey(String encodedText, int length) {
            char[] chars = encodedText.toCharArray();
            char[] keywordChars = new char[length];
            for (int i = 0; i < length; i++) {
                Map<Character, Double> currentFrequency = new HashMap<>();
                int quant = 0;
                for (int j = i; j < chars.length;) {
                    if (alphabet.letters.contains(chars[j])) {
                        currentFrequency.compute(chars[j], (k, v) -> v == null ? 1 : v + 1);
                        quant++;
                    }
                    j += length;
                }
                char c = '*';
                if (currentFrequency.size() > 0) {
                    c = currentFrequency.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
                }
                int index = alphabet.letters.indexOf(c) - alphabet.letters.indexOf('E');
                if (index < 0) {
                    index += alphabet.letters.size();
                }
                keywordChars[i] = alphabet.letters.get(index);
            }
            if (length == 2 && keywordChars[0] == keywordChars[1]) {
                return String.valueOf(keywordChars[0]);
            }

            return String.valueOf(keywordChars);
    }

    static int findLength(String encodedText) {
        int l = 3;
        List<Character> letters = new ArrayList<>();
        for(Character i: encodedText.toCharArray()) {
            letters.add(i);
        }
        Map<List<Character>, LGramm> mapOfLGramms = new HashMap<>();
        for (int i = 0; i < letters.size() - l - 1; i++) {
            List<Character> lGrammCharacters = letters.subList(i, i + l);
            if (lGrammCharacters.stream().anyMatch(c -> !alphabet.letters.contains(c))) {
                continue;
            }
            LGramm existingLGramm = mapOfLGramms.get(lGrammCharacters);
            if (existingLGramm != null) {
                existingLGramm.addPosition(i);
            } else {
                LGramm lGramm = new LGramm(lGrammCharacters);
                lGramm.addPosition(i);
                mapOfLGramms.put(lGrammCharacters, lGramm);
            }
        }
        List<LGramm> correctLGramms=  mapOfLGramms.values().stream().filter( x -> x.getFrequency() > 1).collect(Collectors.toList());

        final Integer[] max = {0};
        final Integer[] result = {0};

        Map<Integer,Integer> gdcs = new HashMap<>();

        for(LGramm i : correctLGramms) {
            int gcd = i.findGCD();
            gdcs.compute(gcd, (k,v ) -> {
                int temp;
                if(v == null) {
                    temp = 1;
                }
                else {
                    temp = v+1;
                }
                if (temp > max[0] && k != 1) {
                    max[0] = temp;
                    result[0] = k;
                }
                return temp;
            });

        }

        return result[0];
    }
}
