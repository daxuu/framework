/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.lang;

import java.util.*;

/**
 *
 * @author Administrator
 * @date 2010/4/6, 上午 11:19:30
 */
public class Translator {

    public static String GetWord(String argCode) {
        String ret = argCode;
        String lang_c = "zh";
        String lang_a = "TW";
        //Locale locale = Locale.getDefault();
        Locale locale = new Locale(lang_c,lang_a);

        ResourceBundle localResource = ResourceBundle.getBundle("Message", locale);

        ret = localResource.getString(argCode);
        //System.out.println("ResourceBundle: " + ret);
        return ret;

    }
}
