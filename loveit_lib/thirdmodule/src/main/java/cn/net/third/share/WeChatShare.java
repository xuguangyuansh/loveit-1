package cn.net.third.share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.UUID;


import static cn.net.third.Constants.WX_APP_ID;

public class WeChatShare {

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    public static final int SHARE_TO_FRIENDS=1;
    public static final int SHARE_TO_TIMELINE=2;

    private final WeakReference<Context> wxContextWeakReference;

    private WeChatShare(Context ctx){
        wxContextWeakReference = new WeakReference<Context>(ctx);
    }
    private static WeChatShare instance;
    public static WeChatShare getInstance(Context ctx){
        if (instance==null){
            instance=new WeChatShare(ctx);
        }
        return instance;
    }

    public void regToWx() {
        if (wxContextWeakReference==null||wxContextWeakReference.get()==null){
            return;
        }
        Context ctx=wxContextWeakReference.get();
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(ctx, WX_APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(WX_APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        ctx.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(WX_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    public void doShare(int type, Map<String,String> data){
        if (api!=null){
            boolean wxAppInstalled = api.isWXAppInstalled();
            int wxAppSupportAPI = api.getWXAppSupportAPI();
            if (wxAppInstalled&&wxAppSupportAPI!=0){
                int mTargetScene;
                if (type==SHARE_TO_FRIENDS){
                    mTargetScene= SendMessageToWX.Req.WXSceneSession;
                }else{
                    mTargetScene= SendMessageToWX.Req.WXSceneTimeline;
                }
                sendMessage(mTargetScene,data);
            }
        }
    }

    private void _share(final int scene, final Map<String,String> data) {
        if (wxContextWeakReference==null||wxContextWeakReference.get()==null){
            _share(scene, data, null);
            return;
        }
        Uri uri = null;
        if (data.containsKey("thumbImage")) {
            String imageUrl = data.get("thumbImage");

            try {
                uri = Uri.parse(imageUrl);
                // Verify scheme is set, so that relative uri (used by static resources) are not handled.
                if (uri.getScheme() == null) {
                    uri = getResourceDrawableUri(wxContextWeakReference.get(), imageUrl);
                }
            } catch (Exception e) {
                // ignore malformed uri, then attempt to extract resource ID.
            }
        }

        if (uri != null) {
            RequestOptions options=new RequestOptions();
            options.override(100, 100);
            this._getImage(uri, options, new ImageCallback() {
                @Override
                public void invoke(@Nullable Bitmap bitmap) {
                    _share(scene, data, bitmap);
                }
            });
        } else {
            _share(scene, data, null);
        }
    }

    private void _getImage(Uri uri, RequestOptions requestOptions, final ImageCallback imageCallback) {
//        BaseBitmapDataSubscriber dataSubscriber = new BaseBitmapDataSubscriber() {
//            @Override
//            protected void onNewResultImpl(Bitmap bitmap) {
//                bitmap = bitmap.copy(bitmap.getConfig(), true);
//                imageCallback.invoke(bitmap);
//            }
//
//            @Override
//            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
//                imageCallback.invoke(null);
//            }
//        };

        Glide.with(wxContextWeakReference.get()).asBitmap().load(uri).apply(requestOptions).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                imageCallback.invoke(null);
                return true;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                resource = resource.copy(resource.getConfig(), true);
                imageCallback.invoke(resource);
                return true;
            }
        }).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

            }
        });


//        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
//        if (resizeOptions != null) {
//            builder = builder.setResizeOptions(resizeOptions);
//        }
//        ImageRequest imageRequest = builder.build();
//
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, null);
//        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }

    private static Uri getResourceDrawableUri(Context context, String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        name = name.toLowerCase().replace("-", "_");
        int resId = context.getResources().getIdentifier(
                name,
                "drawable",
                context.getPackageName());

        if (resId == 0) {
            return null;
        } else {
            return new Uri.Builder()
                    .scheme("res")
                    .path(String.valueOf(resId))
                    .build();
        }
    }

    private void sendMessage(int mTargetScene, Map<String, String> data) {
        if (data==null||data.size()==0){
            return;
        }
        _share(mTargetScene,data);
    }

    private void _share(int mTargetScene, Map<String, String> data,final Bitmap thumbImage){
        if (!data.containsKey("type")){
            return;
        }

        String type = data.get("type");

        WXMediaMessage.IMediaObject mediaObject = null;
        if (type.equals("news")) {
            mediaObject = _jsonToWebpageMedia(data);
        } else if (type.equals("text")) {
            mediaObject = _jsonToTextMedia(data);
        } else if (type.equals("imageUrl") || type.equals("imageResource")) {
            __jsonToImageUrlMedia(data, new MediaObjectCallback() {
                @Override
                public void invoke(@Nullable WXMediaMessage.IMediaObject mediaObject) {
                    if (mediaObject == null) {
//                        callback.invoke(INVALID_ARGUMENT);
                    } else {
                        _share(mTargetScene, data, thumbImage, mediaObject);
                    }
                }
            });
            return;
        } else if (type.equals("imageFile")) {
            __jsonToImageFileMedia(data, new MediaObjectCallback() {
                @Override
                public void invoke(@Nullable WXMediaMessage.IMediaObject mediaObject) {
                    if (mediaObject == null) {
//                        callback.invoke(INVALID_ARGUMENT);
                    } else {
                        _share(mTargetScene, data, thumbImage, mediaObject);
                    }
                }
            });
            return;
        } else if (type.equals("video")) {
            mediaObject = __jsonToVideoMedia(data);
        } else if (type.equals("audio")) {
            mediaObject = __jsonToMusicMedia(data);
        } else if (type.equals("file")) {
            mediaObject = __jsonToFileMedia(data);
        } else if (type.equals("miniProgram")) {
            __jsonToMiniProgramMedia(data, new MediaObjectCallback1() {
                @Override
                public void invoke(@Nullable WXMediaMessage.IMediaObject mediaObject, @Nullable Bitmap bitmap) {
                    if (mediaObject == null) {
//                        callback.invoke(OPEN_MINI_PROGRAM_FAILED);
                    } else {
                        if (bitmap != null) {
                            _share(mTargetScene, data, bitmap, mediaObject);
                        } else {
                            _share(mTargetScene, data, thumbImage, mediaObject);
                        }
                    }
                }
            });
            return;
        }


        if (mediaObject == null) {
//            callback.invoke(INVALID_ARGUMENT);
        } else {
            _share(mTargetScene, data, thumbImage, mediaObject);
        }
    }

    private void _share(int mTargetScene, Map<String,String> data, Bitmap thumbImage, WXMediaMessage.IMediaObject mediaObject) {

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = mediaObject;

        if (thumbImage != null) {
            message.setThumbImage(thumbImage);
        }

        if (data.containsKey("title")) {
            message.title = data.get("title");
        }
        if (data.containsKey("description")) {
            message.description = data.get("description");
        }
        if (data.containsKey("mediaTagName")) {
            message.mediaTagName = data.get("mediaTagName");
        }
        if (data.containsKey("messageAction")) {
            message.messageAction = data.get("messageAction");
        }
        if (data.containsKey("messageExt")) {
            message.messageExt = data.get("messageExt");
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = message;
        req.scene = mTargetScene;
        req.transaction = UUID.randomUUID().toString();

        api.sendReq(req);
//        callback.invoke(null, );
    }

    private void __jsonToMiniProgramMedia(Map<String,String> data, final MediaObjectCallback1 callback) {

        WXMiniProgramObject ret = new WXMiniProgramObject();
        ret.webpageUrl = data.get("webpageUrl");
        ret.userName = data.get("userName");
        ret.path = data.get("path");
        try {
            ret.miniprogramType = Integer.parseInt(data.get("miniprogramType"));
        }catch (Exception e){
            ret.miniprogramType=WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
        }
        ret.withShareTicket = true;

        if (!data.containsKey("imageUrl")) {
            callback.invoke(ret, null);
            return;
        }

        String imageUrl = data.get("imageUrl");
        Uri imageUri;
        try {
            imageUri = Uri.parse(imageUrl);
            // Verify scheme is set, so that relative uri (used by static resources) are not
            // handled.
            if (wxContextWeakReference==null||wxContextWeakReference.get()==null){
                imageUri=null;
            }else {
                if (imageUri.getScheme() == null) {
                    imageUri = getResourceDrawableUri(wxContextWeakReference.get(), imageUrl);
                }
            }
        } catch (Exception e) {
            imageUri = null;
        }

        if (imageUri == null) {
            callback.invoke(ret, null);
            return;
        }

        this._getImage(imageUri, null, new ImageCallback() {
            @Override
            public void invoke(@Nullable Bitmap bitmap) {
                callback.invoke(ret, bitmap);
            }
        });
    }


    private WXTextObject _jsonToTextMedia(Map<String,String> data) {
        if (!data.containsKey("description")) {
            return null;
        }

        WXTextObject ret = new WXTextObject();
        ret.text = data.get("description");
        return ret;
    }

    private WXWebpageObject _jsonToWebpageMedia(Map<String,String> data) {
        if (!data.containsKey("webpageUrl")) {
            return null;
        }

        WXWebpageObject ret = new WXWebpageObject();
        ret.webpageUrl = data.get("webpageUrl");
        if (data.containsKey("extInfo")) {
            ret.extInfo = data.get("extInfo");
        }
        return ret;
    }

    private void __jsonToImageMedia(String imageUrl, final MediaObjectCallback callback) {
        Uri imageUri;
        try {
            imageUri = Uri.parse(imageUrl);
            if (wxContextWeakReference==null||wxContextWeakReference.get()==null){
                imageUri=null;
            }else {
                // Verify scheme is set, so that relative uri (used by static resources) are not handled.
                if (imageUri.getScheme() == null) {
                    imageUri = getResourceDrawableUri(wxContextWeakReference.get(), imageUrl);
                }
            }
        } catch (Exception e) {
            imageUri = null;
        }

        if (imageUri == null) {
            callback.invoke(null);
            return;
        }

        this._getImage(imageUri, null, new ImageCallback() {
            @Override
            public void invoke(@Nullable Bitmap bitmap) {
                callback.invoke(bitmap == null ? null : new WXImageObject(bitmap));
            }
        });
    }

    private void __jsonToImageUrlMedia(Map<String,String> data, MediaObjectCallback callback) {
        if (!data.containsKey("imageUrl")) {
            callback.invoke(null);
            return;
        }
        String imageUrl = data.get("imageUrl");
        __jsonToImageMedia(imageUrl, callback);
    }

    private void __jsonToImageFileMedia(Map<String,String> data, MediaObjectCallback callback) {
        if (!data.containsKey("imageUrl")) {
            callback.invoke(null);
            return;
        }

        String imageUrl = data.get("imageUrl");
        if (!imageUrl.toLowerCase().startsWith("file://")) {
            imageUrl = "file://" + imageUrl;
        }
        __jsonToImageMedia(imageUrl, callback);
    }

    private WXMusicObject __jsonToMusicMedia(Map<String,String> data) {
        if (!data.containsKey("musicUrl")) {
            return null;
        }

        WXMusicObject ret = new WXMusicObject();
        ret.musicUrl = data.get("musicUrl");
        return ret;
    }

    private WXVideoObject __jsonToVideoMedia(Map<String,String> data) {
        if (!data.containsKey("videoUrl")) {
            return null;
        }

        WXVideoObject ret = new WXVideoObject();
        ret.videoUrl = data.get("videoUrl");
        return ret;
    }

    private WXFileObject __jsonToFileMedia(Map<String,String> data) {
        if (!data.containsKey("filePath")) {
            return null;
        }
        return new WXFileObject(data.get("filePath"));
    }

    private interface ImageCallback {
        void invoke(@Nullable Bitmap bitmap);
    }

    private interface MediaObjectCallback {
        void invoke(@Nullable WXMediaMessage.IMediaObject mediaObject);
    }

    private interface MediaObjectCallback1 {
        void invoke(@Nullable WXMediaMessage.IMediaObject mediaObject, @Nullable Bitmap bitmap);
    }

}
