package learning.itstep.java.ioc;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class IoC {

    private final RandomService utilRandomService;
    private final RandomService lcRandomService;
    private final SaltGenerator saltGenerator;
    private final OtpGenerator digitOtpGenerator;
    private final FilenameGenerator filenameGenerator;

    @Inject
    public IoC(@Named("util") RandomService utilRandomService,
               @Named("lc") RandomService lcRandomService,
               SaltGenerator saltGenerator,
               @Named("digit") OtpGenerator digitOtpGenerator,
               @Named("filename") FilenameGenerator filenameGenerator) {
        this.utilRandomService = utilRandomService;
        this.lcRandomService = lcRandomService;
        this.saltGenerator = saltGenerator;
        this.digitOtpGenerator = digitOtpGenerator;
        this.filenameGenerator = filenameGenerator;
        System.out.println("IoC: "
                + utilRandomService.getClass().getName()
                + " "
                + utilRandomService.hashCode());
    }

    public void demo() {
        System.out.println(utilRandomService.value());
        System.out.println(lcRandomService.value());
        System.out.println(saltGenerator.getSalt(10));
        System.out.println(digitOtpGenerator.otp(8));
        System.out.println(filenameGenerator.generate(16));
    }
}
