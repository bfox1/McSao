package io.github.bfox1.SwordArtOnline.common.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;



/**
 * Created by bfox1 on 4/17/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class LogHelper
{
    public static void log(Level logLevel, String text)
    {
        LogManager.getLogger().log(logLevel, text);
    }

    public static void all(String text)
    {
        log(Level.ALL, text);
    }

    public static void debug(String text)
    {
        log(Level.DEBUG, text);
    }

    public static void error(String text)
    {
        log(Level.ERROR, text);
    }

    public static void fatal(String text)
    {
        log(Level.FATAL, text);
    }

    public static void info(String text)
    {
        log(Level.INFO, text);
    }

    public static void off(String text)
    {
        log(Level.OFF, text);
    }

    public static void trace(String text)
    {
        log(Level.TRACE, text);
    }

    public static void warn(String text)
    {
        log(Level.WARN, text);
    }



}
