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
    private boolean mShowImageAlways;

    public GlideLoader() {
        mShowImageAlways = PreferencesUtils.isShowImageAlways();
    }

    public GlideLoader(boolean isShowImageAlaways) {
        this.mShowImageAlways = isShowImageAlaways;
    }

    @Override
    public void loadImage(LoaderOptions options) {
        this.options = options;
        if (options.getContext() != null) {
            with = Glide.with(options.getContext());
        } else if (options.getActivity() != null) {
            with = Glide.with(options.getActivity());
        } else if (options.getFragment() != null) {
            with = Glide.with(options.getFragment());
        } else {
            with = Glide.with(AndroidApplication.getContext());
        }
//------------------------加载url开始--------------------------
        if (mShowImageAlways) {
            if (options.getUrl() != null) {
                load = with.load(options.getUrl());
            } else if (options.getUri() != null) {
                load = with.load(options.getUri());
            } else if (options.getDrawableResId() != 0) {
                load = with.load(options.getDrawableResId());
            } else if (options.getFile() != null) {
                load = with.load(options.getFile());
            }
            if (options.getTargetHeight() > 0 && options.getTargetWidth() > 0) {
                load.override(options.getTargetWidth(), options.getTargetHeight());
            }
        } else {
            load = with.load(DefIconFactory.provideIcon());
        }
//------------------------加载url结束--------------------------
        if (options.isFitCenter()) {
            load.fitCenter();
        } else if (options.isCenterCrop()) {
            load.centerCrop();
        }

//        if (options.config != null) {
//            load.config(options.config);
//        }
        if (options.getErrorResId() != 0) {
            load.error(options.getErrorResId());
        }
        if (options.getPlaceholderResId() != 0) {
            load.placeholder(options.getPlaceholderResId());
        }
        if (options.getBitmapAngle() != 0) {
            GlideRoundTransform glideRoundTransform = null;
            if (options.getContext() != null) {
                glideRoundTransform = new GlideRoundTransform(options.getContext(), options.getBitmapAngle());
            }
            if (options.getActivity() != null) {
                glideRoundTransform = new GlideRoundTransform(options.getActivity(), options.getBitmapAngle());
            }
            if (options.getFragment() != null) {
                glideRoundTransform = new GlideRoundTransform(options.getFragment().getContext(), options.getBitmapAngle());
            }
            load.transform(glideRoundTransform);
        }
        switch (options.getDiskCacheStrategy()) {
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
        if (options.getListener() != null) {
            load.listener(options.getListener());
        }
        if (options.isDontAnimate()) {
            load.dontAnimate();
        }
        //        load.skipMemoryCache(options.skipLocalCache);
        load.into(((ImageView) options.getTargetView()));

//                .load(url).centerCrop().dontAnimate().listener(listener).into(view);

    }

    @Override
    public void clearMemoryCache() {
        if (options.getContext() != null) {
            Glide.get(options.getContext()).clearMemory();//清理内存缓存 可以在UI主线程中进行
        } else if (options.getActivity() != null) {
            Glide.get(options.getActivity()).clearMemory();//清理内存缓存 可以在UI主线程中进行
        } else if (options.getFragment() != null) {
            Glide.get(options.getFragment().getContext()).clearMemory();//清理内存缓存 可以在UI主线程中进行
        }
    }

    @Override
    public void clearDiskCache() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (options.getContext() != null) {
                    Glide.get(options.getContext()).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                } else if (options.getActivity() != null) {
                    Glide.get(options.getActivity()).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
                } else if (options.getFragment() != null) {
                    Glide.get(options.getFragment().getContext()).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
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
