package my_utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Wrapper class for log4j2 logger. Used for disabling all loggers if isLogOn is false.
 */
public class MyLogger {
    private static boolean isLogOn = false;

    private final Logger logger;

    /**
     * Creates log4j2 logger.
     * @param clazz Class object for which logger will be created.
     */
    public MyLogger(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    /**
     * Calls log4j2 method trace() if logging is on.
     * @param message will be sent to log4j2 method.
     */
    public void trace(String message) {
        if (isLogOn)
            logger.trace(message);
    }

    /**
     * Calls log4j2 method debug() if logging is on.
     * @param message will be sent to log4j2 method.
     */
    public void debug(String message) {
        if (isLogOn)
            logger.debug(message);
    }

    /**
     * Calls log4j2 method info() if logging is on.
     * @param message will be sent to log4j2 method.
     */
    public void info(String message) {
        if (isLogOn)
            logger.info(message);
    }

    /**
     * Calls log4j2 method warn() if logging is on.
     * @param message will be sent to log4j2 method.
     */
    public void warn(String message) {
        if (isLogOn)
            logger.warn(message);
    }

    /**
     * Calls log4j2 method error() if logging is on.
     * @param message will be sent to log4j2 method.
     */
    public void error(String message) {
        if (isLogOn)
            logger.error(message);
    }

    /**
     * Calls log4j2 method fatal() if logging is on.
     * @param message will be sent to log4j2 method.
     */
    public void fatal(String message) {
        if (isLogOn)
            logger.fatal(message);
    }

    public static void setLogOn(boolean isLogOn) { MyLogger.isLogOn = isLogOn; }
}
