package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EPC-Nils on 17.05.2016.
 */
public class RegexMatcher {

    public String findMatch(String matchString, String regex) {
        Pattern my_pattern = Pattern.compile(regex);
        Matcher m = my_pattern.matcher(matchString);
        String s = null;
        while (m.find()) {
            s = m.group(1);
        }
        return s;
    }
}
