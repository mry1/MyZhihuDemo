package com.louis.myzhihudemo.utils.imageloader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.louis.myzhihudemo.AndroidApplication;
import com.louis.myzhihudemo.utils.DefIconFactory;
import com.louis.myzhihudemo.utils.PreferencesUtils;

public class GlideLoader implements ILoaderStrategy {

    private RequestManager with;
    private DrawableTypeRequest load;
    private LoaderOptions options;

    @Override
    public void loadImage(LoaderOptions options) {
        this.options = options;
        if (options.context != null) {
            with = Glide.with(options.context);
        } else if (options.activity != null) {
            with = Glide.with(options.activity);
        } else if (options.fragment != null) {
            with = Glide.with(options.fragment);
        } else {
            with = Glide.with(AndroidApplication.getContext());
        }
//------------------------加载url开始--------------------------
        if (PreferencesUtils.isShowImageAlways()) {
            if (options.url != null) {
                load = with.load(options.url);
            } else if (options.uri != null) {
                load = with.load(options.uri);
            } else if (options.drawableResId != 0) {
                load = with.load(options.drawableResId);
            } else if (options.file != null) {
                load = with.load(options.file);
            }
            if (options.targetHeight > 0 && options.targetWidth > 0) {
                load.override(options.targetWidth, options.targetHeight);
            }
        } else {
            load = with.load(DefIconFactory.provideIcon());
        }
//------------------------加载url结束--------------------------

        if (options.isFitCenter) {
            load.fitCenter();
        } else if (options.isCenterCrop) {
            load.centerCrop();
        }

//        if (options.config != null) {
//            load.config(options.config);
//        }
        if (options.errorResId != 0) {
            load.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            load.placeholder(options.placeholderResId);
        }
        if (options.bitmapAngle != 0) {
            GlideRoundTransform glideRoundTransform = null;
            if (options.context != null) {
                glideRoundTransform = new GlideRoundTransform(options.context, options.bitmapAngle);
            }
            if (options.activity != null) {
                glideRoundTransform = new GlideRoundTransform(options.activity, options.bitmapAngle);
            }
            if (options.fragment != null) {
                glideRoundTransform = new GlideRoundTransform(options.fragment.getContext(), options.bitmapAngle);
            }
            load.transform(glideRoundTransform);
        }
        switch (options.diskCacheStrategy) {
            case LoaderOptions.DiskCacheStrategy.ALL:
                load.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case LoaderOptions.DiskCacheStrategy.NONE:
                load.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case LoaderOptions.DiskCacheStrategy.RESULT:
                load.diskCacheStrategy(DiskCacheStrategy.RESULT);
                break;
            case LoaderOptions.DiskCacheStrategy.SOURCE:
                load.diskCacheStrategy(DiskCacheStrategy.SOURCE);
                break;
        }
        if (options.listener != null) {
            load.listener(options.listener);
        }
        if (options.dontAnimate) {
            load.dontAnimate();
        }
        //        load.skipMemoryCache(options.skipLocalCache);
        load.into(((ImageView) options.targetView));

//                .load(url).centerCrop().dontAnimate().listener(listener).into(view);

    }

    @Override
    public void clearMemoryCache() {
        if (options.context != null) {
            Glide.get(options.context).clearMemory();//清理内存缓存 可以在UI主线程中进行
        } else if (options.activity != null) {
            Glide.get(options.activity).clearMemory();//清理内存缓存 可以在UI主线程中进行
        } else if (options.fragment != null) {
            Glide.get(options.fragment.getContext()).clearMemory();//清理内存缓存 可以在UI主线程中进行
        }
    }

    @Override
    public void clearDiskCache() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (options.context != null) {
                    Glide.get(options.context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                } else if (options.activity != null) {
                    Glide.get(options.activity).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                } else if (options.fragment != null) {
                    Glide.get(options.fragment.getContext()).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                }
            }
        }.start();

    }


    public static class GlideRoundTransform extends BitmapTransformation {

        public static final int DEFAULT_CORNER_RADIO = 10;

        private static float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, DEFAULT_CORNER_RADIO);
        }

        public GlideRoundTransform(Context context, float dp) {
            super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }


}
