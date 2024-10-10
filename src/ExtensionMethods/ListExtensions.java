package ExtensionMethods;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ListExtensions {
    private static final Random rng = new Random();

    public static <T> void shuffle(List<T> list) {
        int n = list.size();
        while (n > 1) {
            n--;
            int k = rng.nextInt(n + 1); 
            T value = list.get(k);
            list.set(k, list.get(n));
            list.set(n, value);
        }
    }
}
