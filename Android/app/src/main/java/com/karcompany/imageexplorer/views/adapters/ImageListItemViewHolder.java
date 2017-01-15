package com.karcompany.imageexplorer.views.adapters;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * View holder.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.karcompany.imageexplorer.R;

public class ImageListItemViewHolder extends RecyclerView.ViewHolder {

	public TextView imageTitleTxtView;
	public ImageView imageImgView;

	public ImageListItemViewHolder(View itemView) {
		super(itemView);
		imageTitleTxtView = (TextView) itemView.findViewById(R.id.name);
		imageImgView = (ImageView) itemView.findViewById(R.id.image);
	}

}
