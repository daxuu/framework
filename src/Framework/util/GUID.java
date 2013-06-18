/*
 * copyright_1
 * copyright_2
 * Copyright (c) 2010 Sun , Inc. All Rights Reserved.
 */
package Framework.util;

import java.util.UUID;

/**
 *
 * @author Administrator
 * @date 2010/4/2, 上午 08:12:38
 */
public class GUID {

    public static String NewId() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
