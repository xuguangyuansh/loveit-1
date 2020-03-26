package cn.net.third.track;

import android.app.Activity;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class UMTrack {

    private final WeakReference<Context> contextWeakReference;

    private UMTrack(Context ctx){
        contextWeakReference = new WeakReference<Context>(ctx);
    }

    private static UMTrack instance;

    public static UMTrack getInstance(Context ctx){
        if (instance==null){
            instance=new UMTrack(ctx);
        }
        return instance;
    }

    public void onResume(){
        if (contextWeakReference==null||contextWeakReference.get()==null)
            return;
        MobclickAgent.onResume(contextWeakReference.get());
    }

    public void onPause(){
        if (contextWeakReference==null||contextWeakReference.get()==null)
            return;
        MobclickAgent.onPause(contextWeakReference.get());
    }

    public void onPageStart(String page){
        MobclickAgent.onPageStart(page);
    }

    public void onPageEnd(String page){
        MobclickAgent.onPageEnd(page);
    }


    public void onEvent(String eventId){
        if (contextWeakReference==null||contextWeakReference.get()==null)
            return;
        MobclickAgent.onEvent(contextWeakReference.get(),eventId);
    }

    public void onEvent(String eventId, Map<String,String> map){
        if (contextWeakReference==null||contextWeakReference.get()==null)
            return;
        MobclickAgent.onEvent(contextWeakReference.get(),eventId,map);
    }

    public void onKillProcess(){
        if (contextWeakReference==null||contextWeakReference.get()==null)
            return;
        MobclickAgent.onKillProcess(contextWeakReference.get());
    }

    public void setOpenGLContext(GL10 gl){
        MobclickAgent.setOpenGLContext(gl);
    }

    public void setLocation(double latitude, double longitude){
        MobclickAgent.setLocation(latitude,longitude);
    }
}
