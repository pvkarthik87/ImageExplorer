package com.karcompany.imageexplorer.views.adapters;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Recycler view adapter which displays image list data.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.karcompany.imageexplorer.ImageExplorerApplication;
import com.karcompany.imageexplorer.R;
import com.karcompany.imageexplorer.config.Constants;
import com.karcompany.imageexplorer.config.ViewType;
import com.karcompany.imageexplorer.events.BusEvents;
import com.karcompany.imageexplorer.events.RxBus;
import com.karcompany.imageexplorer.models.DisplaySize;
import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenter;
import com.karcompany.imageexplorer.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public class BrowseImagesAdapter extends RecyclerView.Adapter<ImageListItemViewHolder> {

	private Fragment mFragment;
	private Context mContext;
	private Map<String, Image> mImagesDataMap;
	private List<String> mImageIdList;
	private ViewType mCurrentViewType;
	private Random mRandom = new Random();

	private long mTotalListCount = 0;
	private String mSearchTerm;

	@Inject
	RxBus mEventBus;

	@Inject
	BrowseImagesPresenter mBrowseImagesPresenter;

	private int VIEW_TYPE_ITEM = 1;
	private int VIEW_TYPE_PROGRESS = 2;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			String id = mImageIdList.get(itemPosition);
			BusEvents.ImageClicked event =  new BusEvents.ImageClicked();
			event.image = mImagesDataMap.get(id);
			mEventBus.send(event);
		}
	};

	public BrowseImagesAdapter(Fragment fragment) {
		ImageExplorerApplication.getApplicationComponent().inject(this);
		mFragment = fragment;
		mContext = fragment.getContext();
		mImagesDataMap = new LinkedHashMap<>();
		mImageIdList = new ArrayList<>(4);
	}

	@Override
	public ImageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if(viewType == VIEW_TYPE_ITEM) {
			switch (mCurrentViewType) {
				case LIST: {
					view = LayoutInflater.from(mContext).inflate(R.layout.view_image_row_item_list, parent, false);
				}
				break;

				case GRID: {
					view = LayoutInflater.from(mContext).inflate(R.layout.view_image_row_item_grid, parent, false);
				}
				break;

				case STAGGERED: {
					view = LayoutInflater.from(mContext).inflate(R.layout.view_image_row_item_staggered, parent, false);
				}
				break;
			}
		} else {
			view = LayoutInflater.from(mContext).inflate(R.layout.loading_progress, parent, false);
		}
		return new ImageListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ImageListItemViewHolder holder, int position) {
		if(getItemViewType(position) == VIEW_TYPE_ITEM) {
			if (mCurrentViewType == ViewType.STAGGERED) {
				holder.imageImgView.getLayoutParams().height = getRandomIntInRange(300, 200);
			}
			holder.imageTitleTxtView.setText("");
			Glide.clear(holder.imageImgView);
			holder.imageImgView.setImageDrawable(null);
			if (position < mImageIdList.size()) {
				Image image = mImagesDataMap.get(mImageIdList.get(position));
				if (image != null) {
					if (!TextUtils.isEmpty(image.getTitle())) {
						holder.imageTitleTxtView.setText(image.getTitle());
					}
					List<DisplaySize> imageDisplaySizes = image.getDisplaySizes();
					if (imageDisplaySizes != null && imageDisplaySizes.size() > 0) {
						GlideUtils.loadImage(mFragment, imageDisplaySizes.get(0).getUri(), holder.imageImgView);
					}
				}
				holder.itemView.setTag(position);
				holder.itemView.setOnClickListener(mOnClickListener);
			}
		}
	}

	// Custom method to get a random number between a range
	protected int getRandomIntInRange(int max, int min){
		return mRandom.nextInt((max-min)+min)+min;
	}

	@Override
	public void onViewRecycled(ImageListItemViewHolder holder) {
		if(holder.imageImgView != null) {
			Glide.clear(holder.imageImgView);
			holder.imageImgView.setImageDrawable(null);
		}
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		if(isDataLoadCompleted()) {
			return mImageIdList.size();
		} else {
			return mImageIdList.size() + 1;
		}
	}

	public void clearData() {
		mImageIdList.clear();
		mImagesDataMap.clear();
	}

	public void setViewMode(ViewType viewType) {
		mCurrentViewType = viewType;
	}

	public ArrayList<Image> getImageList() {
		ArrayList<Image> imageList = new ArrayList<>();
		Iterator<Map.Entry<String, Image>> iterator = mImagesDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Image> entry = iterator.next();
			imageList.add(entry.getValue());
		}
		return imageList;
	}

	public void loadData(String searchTerm) {
		if(!TextUtils.isEmpty(searchTerm)) {
			clearData();
			mTotalListCount = -1;
			mSearchTerm = searchTerm;
			notifyDataSetChanged();
			loadMore();
		}
	}

	public void addData(ImageSearchApiResponse response) {
		if(response != null) {
			mTotalListCount = response.getResultCount();
			if(response.getImages() != null) {
				for (Image image : response.getImages()) {
					mImagesDataMap.put(image.getId(), image);
				}
				int oldSize = mImageIdList.size();
				mImageIdList.clear();
				mImageIdList.addAll(mImagesDataMap.keySet());
				int newSize = mImageIdList.size();
				if (oldSize > 0) {
					notifyItemRangeInserted(oldSize, newSize - oldSize);
					if(newSize == mTotalListCount) {
						notifyItemRemoved(mImageIdList.size());
					}
				} else {
					notifyDataSetChanged();
				}

			}
		}
	}

	public void addData(ArrayList<Image> imageList) {

	}

	private long getNextPageNumber() {
		return (mImageIdList.size() / Constants.NUM_ITEMS_IN_PAGE) + 1;
	}

	private boolean isDataLoadCompleted() {
		if(mTotalListCount == -1) return false;
		return mImageIdList.size() == mTotalListCount;
	}

	public void loadMore() {
		if(!isDataLoadCompleted()) {
			mBrowseImagesPresenter.loadImages(getNextPageNumber(), mSearchTerm);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(mTotalListCount == -1 && position == 0)
			return VIEW_TYPE_PROGRESS;
		if(mTotalListCount > mImageIdList.size() && position == mImageIdList.size()){
			return VIEW_TYPE_PROGRESS;
		}
		return VIEW_TYPE_ITEM;
	}

	public boolean isLoadingPos(int position) {
		return getItemViewType(position) == VIEW_TYPE_PROGRESS;
	}
}
