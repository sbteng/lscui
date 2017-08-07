package org.lsc.ui.utils;

import java.io.File;

public class Const
{
  public static int WIDTH = 1000;
  public static final float RADIO_FONT_SIZE = 18.0F;
  public static final float TIP_FONT_SZIE = 18.0F;
  public static boolean isOuSynced = false;
  public static boolean isPeopleSynced = false;
  public static boolean isGroupSynced = false;

  public static String BaseCMD()
  {
    String home = System.getenv("LSC_HOME");
    if ((home == null) || (home.length() == 0))
    {
      System.err.println("\nLSC_HOME is not defined!!! Please call ui.bat!!");
      return "";
    }
    return home + File.separator + "bin" + File.separator + "lsc.bat -f " + home + File.separator + "etc -s";
  }

  public static String OU = "OU";
  public static String PEOPLE = "People";
  public static String GROUP = "Group";
  public static String ALL = " all";
}
