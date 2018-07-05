package com.louis.myzhihudemo.utils.imageloader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.louis.myzhihudemo.AndroidApplication;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

public class PicassoLoader implements ILoaderStrategy {
	private volatile static Picasso sPicassoSingleton;
	private final String PICASSO_CACHE = "picasso-cache";
	private static LruCache sLruCache = new LruCache(AndroidApplication.getContext());

	private static Picasso getPicasso() {
		if (sPicassoSingleton == null) {
			synchronized (PicassoLoader.class) {
				if (sPicassoSingleton == null) {
					sPicassoSingleton = new Picasso.Builder(AndroidApplication.getContext()).memoryCache(sLruCache).build();
				}
			}
		}
		return sPicassoSingleton;
	}


	@Override
	public void clearMemoryCache() {
		sLruCache.clear();
	}

	@Override
	public void clearDiskCache() {
		File diskFile = new File(AndroidApplication.getContext().getCacheDir(), PICASSO_CACHE);
		if (diskFile.exists()) {
			//这边自行写删除代码
//	        FileUtil.deleteFile(diskFile);
		}
	}

	@Override
	public void loadImage(LoaderOptions options) {
		RequestCreator requestCreator = null;
		if (options.getUrl() != null) {
			requestCreator = getPicasso().load(options.getUrl());
		} else if (options.getFile() != null) {
			requestCreator = getPicasso().load(options.getFile());
		}else if (options.getDrawableResId() != 0) {
			requestCreator = getPicasso().load(options.getDrawableResId());
		} else if (options.getUri() != null){
			requestCreator = getPicasso().load(options.getUri());
		}

		if (requestCreator == null) {
			throw new NullPointerException("requestCreator must not be null");
		}
		if (options.getTargetHeight() > 0 && options.getTargetWidth() > 0) {
			requestCreator.resize(options.getTargetWidth(), options.getTargetHeight());
		}
		if (options.isCenterInside()) {
			requestCreator.centerInside();
		} else if (options.isCenterCrop()) {
			requestCreator.centerCrop();
		}
		if (options.getConfig() != null) {
			requestCreator.config(options.getConfig());
		}
		if (options.getErrorResId() != 0) {
			requestCreator.error(options.getErrorResId());
		}
		if (options.getPlaceholderResId() != 0) {
			requestCreator.placeholder(options.getPlaceholderResId());
		}
		if (options.getBitmapAngle() != 0) {
			requestCreator.transform(new PicassoTransformation(options.getBitmapAngle()));
		}
		if (options.isSkipLocalCache()) {
			requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
		}
		if (options.isSkipNetCache()) {
			requestCreator.networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
		}
		if (options.getDegrees() != 0) {
			requestCreator.rotate(options.getDegrees());
		}

		if (options.getTargetView() instanceof ImageView) {
			requestCreator.into(((ImageView)options.getTargetView()));
		} else if (options.getCallBack() != null){
			requestCreator.into(new PicassoTarget(options.getCallBack()));
		}
	}

	class PicassoTarget implements Target {
		BitmapCallBack callBack;

		protected PicassoTarget(BitmapCallBack callBack) {
			this.callBack = callBack;
		}

		@Override
		public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
			if (this.callBack != null) {
				this.callBack.onBitmapLoaded(bitmap);
			}
		}

		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
			if (this.callBack != null) {
				this.callBack.onBitmapFailed(errorDrawable);
			}
		}


		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {

		}
	}

	class PicassoTransformation implements Transformation {
		private float bitmapAngle;

		protected PicassoTransformation(float corner){
			this.bitmapAngle = corner;
		}

		@Override
		public Bitmap transform(Bitmap source) {
			float roundPx = bitmapAngle;//圆角的横向半径和纵向半径
			Bitmap output = Bitmap.createBitmap(source.getWidth(),
					source.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, source.getWidth(),source.getHeight());
			final RectF rectF = new RectF(rect);
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(source, rect, rect, paint);
			source.recycle();
			return output;
		}

		@Override
		public String key() {
			return "bitmapAngle()";
		}
	}

}