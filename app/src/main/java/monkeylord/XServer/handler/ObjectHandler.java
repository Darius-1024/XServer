package monkeylord.XServer.handler;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import monkeylord.XServer.objectparser.GenericParser;
import monkeylord.XServer.objectparser.StoredObjectParser;
import monkeylord.XServer.utils.Utils;

import static monkeylord.XServer.XServer.parsers;

//处理对象相关内容
public class ObjectHandler {
    public static HashMap<String, Object> objects = new HashMap<String, Object>();

    public static Object storeObject(Object obj, String name) {
        return objects.put(name, obj);
    }

    public static String saveObject(Object obj){
        if(obj==null)return "Null";
        if(parsers.get(Utils.getTypeSignature(obj.getClass()))!=null){
            return Utils.getTypeSignature(obj.getClass())+"#"+parsers.get(Utils.getTypeSignature(obj.getClass())).generate(obj);
        }else{
            String objname;
            try{
                objname=""+obj.hashCode();
            }catch (Exception e){
                objname= ""+new Random().nextLong();
            }
            storeObject(obj,""+objname);
            return Utils.getTypeSignature(obj.getClass())+"#"+objname;
        }
    }

    public static Object getObject(String name) {
        return objects.get(name);
    }

    public static Object parseObject(String Object){
        if(Object.equals("Null"))return null;
        if(Object==null)return null;
        if(Object.indexOf("#")<0)return null;
        String type=Object.substring(0,Object.indexOf("#"));
        String raw=Object.substring(Object.indexOf("#")+1);
        if(parsers.get(type)!=null)return parsers.get(type).parse(raw);
        else return new StoredObjectParser().parse(raw);
    }

    public static Object[] getObjects(String name, String type) {
        return null;
    }

    public static Object removeObject(String name) {
        return objects.remove(name);
    }

    public static Object removeObject(Object object) {
        for (Map.Entry entry : objects.entrySet()) {
            if (entry.getValue().equals(object)) return objects.remove(entry.getKey());
        }
        return null;
    }
}
