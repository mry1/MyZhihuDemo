package com.louis.myzhihudemo.utils.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by JohnsonFan on 2018/3/7.
 */

public interface BitmapCallBack {

	void onBitmapLoaded(Bitmap bitmap);

	void onBitmapFailed(Drawable errorDrawable);

	public static class EmptyCallback implements BitmapCallBack {


		@Override
		public void onBitmapLoaded(Bitmap bitmap) {

		}

		@Override
		public void onBitmapFailed(Drawable errorDrawable) {

		}
	}
}