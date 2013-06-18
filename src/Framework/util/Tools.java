/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.util;

import java.util.regex.*;

/**
 *
 * @author Administrator
 * @date 2010/4/6, 下午 04:35:03
 */
public class Tools {

    public static String replaceByRegex(String input, String toChg, String chgTo) {

        StringBuffer sb = null;

        if (input == null || input.length() == 0) {
            return input;
        }

        if (toChg == null || toChg.length() == 0) {
            return input;
        }

        Pattern chagePattern = Pattern.compile(toChg);

        Matcher inputMatcher = chagePattern.matcher(input);

        sb = new StringBuffer();

        while (inputMatcher.find()) {

            inputMatcher.appendReplacement(sb, chgTo);

        }

        inputMatcher.appendTail(sb);

        return sb.toString();

    }

    public static String replace(String _old, String _str, String _new) {
        if (_old == null) {
            return null;
        }

        StringBuffer _temp = new StringBuffer();

        int i = 0;

        int j = 0;

        while ((j = _old.indexOf(_str, 0)) != -1) {

            _temp.append(_old.substring(0, j) + _new);

            _old = _old.substring(j + _str.length());

        }

        _temp.append(_old);

        return _temp.toString();

    }

    public static String replaceByString(String _old, String _str, String _new) {

        if (_old == null) {
            return null;
        }

        String _temp = "";

        int i = 0;

        int j = 0;

        while ((j = _old.indexOf(_str, 0)) != -1) {

            _temp += _old.substring(0, j) + _new;

            _old = _old.substring(j + _str.length());

        }

        _temp += _old;

        return _temp;

    }

    public static String format(int num){
        String str = String.format("%04d", num);  //0010
        return str;
    }
}
