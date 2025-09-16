package learning.itstep.java;

import com.google.inject.Guice;
import com.google.inject.Injector;
import learning.itstep.java.ioc.ConfigModule;
import learning.itstep.java.ioc.IoC;
import learning.itstep.java.newpackage.Db;
/**
 *
 * @author night
 */
public class Java222 {

    /**
     * Entry Point
     * @param args cmd-line arguments
     */
    public static void main(String[] args) {
//        new Intro().demo();
        // new Db().demo();
        Injector injector = Guice.createInjector(new ConfigModule());
        IoC ioc = injector.getInstance(IoC.class);
        ioc.demo();
        
    }
}
