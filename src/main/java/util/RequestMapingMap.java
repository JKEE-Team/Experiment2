package util;

import java.util.HashMap;
import java.util.Map;

/**
* @ClassName: RequestMapingMap
* @Description: �洢�����ķ���·��
* @author: �°�����
* @date: 2014-11-16 ����6:31:43
*
*/
public class RequestMapingMap {

    /**
    * @Field: requesetMap
    *          ���ڴ洢�����ķ���·��
    */
    private static Map<String, Class<?>> requesetMap = new HashMap<String, Class<?>>();

    public static Class<?> getClassName(String path) {
        return requesetMap.get(path);
    }

    public static void put(String path, Class<?> className) {
        requesetMap.put(path, className);
    }

    public static Map<String, Class<?>> getRequesetMap() {
        return requesetMap;
    }
}