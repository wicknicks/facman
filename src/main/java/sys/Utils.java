package sys;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Logger Utils. Single point of initialization. Helps avoid creating multiple appenders.
 */

public class Utils {

    private static boolean isLoggerInitialized = false;

    public static void initLogging() {
        if ( !isLoggerInitialized ) {
            BasicConfigurator.configure();
            Logger.getRootLogger().setLevel(Level.INFO);
            isLoggerInitialized = true;
        }
    }
}