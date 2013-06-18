/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
    
/**
 *
 * @author Administrator
 * @date 2010/3/30, 上午 10:33:17
 */
public class Configer {
    private static ResourceBundle bundle;

    /**
     * init()
     */
    public Configer()
    {
    }

    /**
     * @function getString
     * @param s String
     * @return String
     */
    public static String getString(String s){
        String s1 = null;
        try{
            s1 = getResourceBundle().getString(s);
        }catch(MissingResourceException missingresourceexception){
            System.out.println("ConfigBundle:getString error!"+missingresourceexception.toString());
        }
        return s1;
    }

    /**
     * @function getResourceBundle
     * @return ResourceBundle
     */
    private static ResourceBundle getResourceBundle(){
        if(bundle == null)
            bundle = ResourceBundle.getBundle("appConfig");
        return bundle;
    }
}



