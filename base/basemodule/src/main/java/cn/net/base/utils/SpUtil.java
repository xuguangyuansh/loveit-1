package cn.net.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

    private final SharedPreferences sp;

    public static String KEY_HOT_FIX_COUNT="KEY_HOT_FIX_COUNT";
    public static String KEY_TOKEN="KEY_TOKEN";

    private SpUtil(Context ctx){
        sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
    }

    private static SpUtil instance;
    public static SpUtil getInstance(Context ctx){
        if (instance==null){
            instance=new SpUtil(ctx);
        }
        return instance;
    }

    public String getString(String name){
        if (sp!=null){
            return sp.getString(name,"");
        }
        return "";
    }

    public void addString(String key,String value){
        if (sp!=null){
            sp.edit().putString(key,value).commit();
        }
    }


    public void removeValue(String key){
        if (sp!=null){
            sp.edit().remove(key).commit();
        }
    }








}
