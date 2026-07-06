package com.TAF.Utils.Logs;

import com.TAF.Utils.report.AllureConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogsManager {

    public static final String LOGS_PATH = AllureConstant.USER_DIR + "/TestOutput/logs/";

    private static Logger logger() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getMethodName());
    }
    // info("error message" + "" + "e.getMessage()")  it will be info("error message", "e.getMessage()")
    public static void info(String ...message) {
        logger().info(String.join("\n", message));
    }

    public static void error(String ...message) {
        logger().error(String.join("\n", message));
    }

    public static void debug(String ...message) {
        logger().debug(String.join("\n", message));
    }

    public static void warn(String ...message) {
        logger().warn(String.join("\n", message));
    }

    public static void fatal(String ...message) {
        logger().fatal(String.join("\n", message));
    }

}