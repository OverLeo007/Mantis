package ru.paskal.MantisManager.utils;

import org.springframework.stereotype.Component;

@Component
public class TestLogger {
  public static final String RESET = "\033[0m";
  public static final String GREEN = "\033[32m";
  public static final String YELLOW = "\033[33m";

  public static final String MAGENTA = "\033[35m";


  //TODO: Rolling File сделать
  public static void log(Object o, String who) {

    System.out.println(
        String.format(
            "%s[LOG]%s --- %s<%s>%s -> %s",
            GREEN, RESET, YELLOW, who, RESET, MAGENTA
        )
            + o + RESET
    );
  }

  public static void log(Object o) {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    if (stackTrace.length >= 3) {
      String methodName = stackTrace[2].getMethodName();
      String fullClassName = stackTrace[2].getClassName();
      String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
      log(o, String.format("%s.%s", simpleClassName, methodName));
    } else {
      log(o);
    }
  }

}
