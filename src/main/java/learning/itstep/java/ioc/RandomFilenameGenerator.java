package learning.itstep.java.ioc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class RandomFilenameGenerator implements FilenameGenerator {

    private final RandomService randomService;
    private static final char[] ALLOWED = initAllowed();

    @Inject
    public RandomFilenameGenerator(@Named("util") RandomService randomService) {
        this.randomService = randomService;
        System.out.println("RandomFilenameGenerator: "
                + randomService.getClass().getName()
                + " "
                + randomService.hashCode());
    }

    @Override
    public String generate(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("length must be >= 8");
        }
        char[] r = new char[length];
        for (int i = 0; i < r.length; i++) {
            int idx = (int)(randomService.value() * ALLOWED.length);
            r[i] = ALLOWED[idx];
        }
        return new String(r);
    }

    private static char[] initAllowed() {
        StringBuilder sb = new StringBuilder();
        for (char c = '0'; c <= '9'; c++) sb.append(c);
        for (char c = 'A'; c <= 'Z'; c++) sb.append(c);
        for (char c = 'a'; c <= 'z'; c++) sb.append(c);
        sb.append('-').append('_').append('.');
        return sb.toString().toCharArray();
    }
}
