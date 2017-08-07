package org.lsc.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jarvis on 2017/8/5.
 */
public class ProcessUtil {

    public static String[] matchEntryResult(String target) {
        int start = target.indexOf("All entries");
        if (start != -1) {
            String entryResult = target.substring(start);
            Pattern p = Pattern.compile(".*?(\\d+).*?(\\d+).*?(\\d+).*?(\\d+).*?");
            Matcher m = p.matcher(entryResult);
            if (m.find()) {
                System.out.println("m group(): " + m.group(0) + "; " + m.group(1) + "; " + m.group(2) + "; " + m.group(3) + "; " + m.group(4));
                return new String[] {m.group(3), m.group(4)};
            }
        }

        return new String[] {"0", "0"};
    }
}
