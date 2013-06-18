/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */

package Framework.CodeRobot;

/**
 *Singleton類，產生編碼。
 * @author Administrator
 * @date 2010/4/6, 下午 03:20:33
 */
import Framework.util.GUID;
public class Coder {
	private static final Coder _coder = new Coder();

    private  Coder() {
    	
    }
    public static Coder getCoder(){
        return _coder;
    }
    public String  getGUID(){
        return  GUID.NewId();
    }
}
