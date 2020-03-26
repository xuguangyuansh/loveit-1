package cn.net.third.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.net.third.Constants;
import cn.net.third.R;

public class WeiBoShare {

    private WbShareHandler handler;

    private final WeakReference<Activity> activityWeakReference;


    private WeiBoShare(Activity ctx){
        activityWeakReference = new WeakReference<Activity>(ctx);
    }

    private static WeiBoShare instance;

    public static WeiBoShare getInstance(Activity ctx){
        if (instance==null){
            instance=new WeiBoShare(ctx);
        }
        return instance;
    }

    public void installWeiBo(){
        if (activityWeakReference==null||activityWeakReference.get()==null){
            return;
        }
        WbSdk.install(activityWeakReference.get(),new AuthInfo(activityWeakReference.get(), Constants.WB_APP_KEY, Constants.WB_REDIRECT_URL, Constants.WB_SCOPE));
    }


    public void registerApp(){
        try {
            //微博sdk
            if (activityWeakReference != null && activityWeakReference.get() != null) {
                handler = new WbShareHandler(activityWeakReference.get());
                handler.registerApp();
            }
        }catch (Exception e){

        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    public void sendMessage(Map<String, Object> data) {
        try {
            if (data == null || data.size() == 0) {
                return;
            }
            sendMultiMessage(data);
        }catch (Exception e){

        }
    }


    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(Map<String, Object> data) {

        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (data.containsKey("text")) {
            TextObject textObj = getTextObj(data);
            if (textObj!=null)
                weiboMessage.textObject = textObj;
        }
        if (data.containsKey("image")) {
            ImageObject imageObj = getImageObj(data);
            if (imageObj!=null)
                weiboMessage.imageObject = imageObj;
        }
        if(data.containsKey("multiImage")){
            MultiImageObject multiImageObject = getMultiImageObject(data);
            if (multiImageObject!=null)
                weiboMessage.multiImageObject =multiImageObject;
        }
        if(data.containsKey("video")){
            VideoSourceObject videoObject = getVideoObject(data);
            if (videoObject!=null)
                weiboMessage.videoSourceObject = videoObject;
        }

        if (handler!=null)
            handler.shareMessage(weiboMessage, false);

    }

    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     * @param
     */
    private TextObject getTextObj(Map<String, Object> data) {
        Object text = data.get("text");
        if (text instanceof String) {
            String shareText= (String) text;
            TextObject textObject = new TextObject();
            textObject.text = shareText;
            if (data.containsKey("title")&&data.get("title") instanceof String)
                textObject.title = (String) data.get("title");
            if (data.containsKey("actionUrl")&&data.get("actionUrl") instanceof String)
                textObject.actionUrl = (String) data.get("actionUrl");
            return textObject;
        }
        return null;
    }

    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     * @param data
     */
    private ImageObject getImageObj(Map<String, Object> data) {
        try {
            Object image = data.get("image");
            if (image instanceof String&&activityWeakReference!=null&&activityWeakReference.get()!=null) {
                String shareImage= (String) image;
                ImageObject imageObject = new ImageObject();
                Bitmap bitmap=Glide.with(activityWeakReference.get()).asBitmap().load(shareImage).submit().get();
                imageObject.setImageObject(bitmap);
                return imageObject;
            }
        }catch (Exception e){

        }
        return null;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title ="测试title";
        mediaObject.description = "测试描述";
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = "http://news.sina.com.cn/c/2013-10-22/021928494669.shtml";
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    /***
     * 创建多图
     * @return
     * @param data
     */
    private MultiImageObject getMultiImageObject(Map<String, Object> data){
        //pathList设置的是本地本件的路径,并且是当前应用可以访问的路径，现在不支持网络路径（多图分享依靠微博最新版本的支持，所以当分享到低版本的微博应用时，多图分享失效
        // 可以通过WbSdk.hasSupportMultiImage 方法判断是否支持多图分享,h5分享微博暂时不支持多图）多图分享接入程序必须有文件读写权限，否则会造成分享失败
//        if (activityWeakReference!=null&&activityWeakReference.get()!=null){
//            Object multiImage = data.get("multiImage");
//            if (WbSdk.supportMultiImage(activityWeakReference.get())&& multiImage instanceof List){
//                MultiImageObject multiImageObject = new MultiImageObject();
//                ArrayList<Uri> pathList = new ArrayList<Uri>();
//                for (int i = 0; i < ((List) multiImage).size(); i++) {
//                    String o = (String) ((List) multiImage).get(i);
//                    pathList.add(Uri.parse(o));
//                }
//                multiImageObject.setImageList(pathList);
//                return multiImageObject;
//            }
//        }
        return null;
    }

    private VideoSourceObject getVideoObject(Map<String, Object> data){
        //获取视频
        Object video = data.get("video");
        if (video instanceof String) {
            VideoSourceObject videoSourceObject = new VideoSourceObject();
            videoSourceObject.videoPath = Uri.parse((String) video);
            return videoSourceObject;
        }
        return null;
    }


    public void handleResult(Intent intent){
        try {
            if (handler != null) {
                handler.doResultIntent(intent, new WbShareCallback() {
                    @Override
                    public void onWbShareSuccess() {
                        Toast.makeText(activityWeakReference.get(),"分享成功",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onWbShareCancel() {
                        Toast.makeText(activityWeakReference.get(),"分享取消",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onWbShareFail() {
                        Toast.makeText(activityWeakReference.get(),"分享失败",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }catch (Exception e){

        }
    }

}
