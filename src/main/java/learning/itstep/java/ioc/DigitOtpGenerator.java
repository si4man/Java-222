package learning.itstep.java.ioc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class DigitOtpGenerator implements OtpGenerator {

    private final RandomService randomService;

    @Inject
    public DigitOtpGenerator(@Named("lc") RandomService randomSerivce) {
        this.randomService = randomSerivce;
        System.out.println("DigitOtpGenerator: "
                + randomService.getClass().getName()
                + " "
                + randomService.hashCode());
    }

    @Override
    public String otp(int length) {
        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (48 + (int) (randomService.value() * 10));
        }
        return new String(chars);
    }
}
