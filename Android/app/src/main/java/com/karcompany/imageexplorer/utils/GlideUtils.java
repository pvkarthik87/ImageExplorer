package com.karcompany.imageexplorer.utils;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.karcompany.imageexplorer.R;
import com.karcompany.imageexplorer.logging.DefaultLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pvkarthik on 2017-01-12.
 */
public class GlideUtils {

	public static String TAG = DefaultLogger.makeLogTag(GlideUtils.class);
	private static int mColorIndex = 0;

	public static final List<Integer> mColourList;

	static {
		List<Integer> colourList = new ArrayList<>(4);
		colourList.add(R.color.accent_brand);
		colourList.add(R.color.neutral_medium_soft_dark);
		colourList.add(R.color.light_brand);
		colourList.add(R.color.medium_brand);
		mColourList = Collections.unmodifiableList(colourList);
	}

	public static void loadImageWithNoCache(final Fragment fragment, String url, final ImageView imageView) {
		if (TextUtils.isEmpty(url)) return;
		Glide
				.with(fragment)
				.load(url)
				.asBitmap()
				.placeholder(getColorIndex())
				.skipMemoryCache(true)
				.error(getColorIndex())
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.centerCrop().into(new BitmapImageViewTarget(imageView) {
					@Override
					protected void setResource(Bitmap resource) {
						RoundedBitmapDrawable circularBitmapDrawable =
								RoundedBitmapDrawableFactory.create(fragment.getResources(), resource);
						circularBitmapDrawable.setCircular(true);
						imageView.setImageDrawable(circularBitmapDrawable);
					}
				});
	}

	private static int getColorIndex() {
		int colour = mColourList.get(mColorIndex);
		mColorIndex++;
		if (mColorIndex == mColourList.size()) mColorIndex = 0;
		return colour;
	}

}
