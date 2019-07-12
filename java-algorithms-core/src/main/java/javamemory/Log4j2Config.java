package javamemory;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 //8722999841 - varadraraj surveyor
//reservations@rdgurugram.in
 
public class Log4j2Config {
    Level logLevel=Level.INFO;
    Logger log = LogManager.getLogger(Log4j2Config.class);

    ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(1);
    Log4j2Config(){
        setLevel(LogManager.ROOT_LOGGER_NAME, logLevel);
        ses.scheduleWithFixedDelay(this::gatherLevelAndSet, 0, 1, TimeUnit.SECONDS);
    }
    public static void main(String[] args) throws InterruptedException {
        Log4j2Config x = new Log4j2Config();

        x.log.info("1 INFO");
        x.log.debug("1 DEBUG");
        Thread.sleep(1000L);
        x.log.info("2 INFO");
        x.log.debug("2 DEBUG");
        Thread.sleep(1000L);
        x.log.info("3 INFO");
        x.log.debug("3 DEBUG");
    }
    public void gatherLevelAndSet(){
        setLevel(LogManager.ROOT_LOGGER_NAME, logLevel);
        logLevel = logLevel==Level.INFO?Level.DEBUG:Level.INFO;
    }
    /**
     * Set Level
     *
     * @param vlevel
     */
    public void setLevel(String loggerName, Level vlevel) {
        @SuppressWarnings("resource")
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);
        loggerConfig.setLevel(vlevel);
        ctx.updateLoggers();
    }

}