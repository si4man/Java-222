package learning.itstep.java.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RandomService.class)
                .annotatedWith(Names.named("util"))
                .to(UtilRandomService.class);
        bind(RandomService.class)
                .annotatedWith(Names.named("lc"))
                .to(LcRandomService.class);
        bind(OtpGenerator.class)
                .annotatedWith(Names.named("digit"))
                .to(DigitOtpGenerator.class);
        bind(FilenameGenerator.class)
                .annotatedWith(Names.named("filename"))
                .to(RandomFilenameGenerator.class);
    }
}
