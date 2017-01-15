package com.karcompany.imageexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Images fragment which displays server data in a recycler view.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karcompany.imageexplorer.R;
import com.karcompany.imageexplorer.config.ViewType;
import com.karcompany.imageexplorer.di.components.ApplicationComponent;
import com.karcompany.imageexplorer.events.BusEvents;
import com.karcompany.imageexplorer.events.RxBus;
import com.karcompany.imageexplorer.logging.DefaultLogger;
import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenter;
import com.karcompany.imageexplorer.views.BrowseImagesView;
import com.karcompany.imageexplorer.views.adapters.BrowseImagesAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BrowseImagesFragment extends BaseFragment implements BrowseImagesView {

	private static final String TAG = DefaultLogger.makeLogTag(BrowseImagesFragment.class);

	private static final String CURRENT_VIEW_TYPE = "CURRENT_VIEW_TYPE";
	private static final String CURRENT_IMAGE_LIST = "CURRENT_IMAGE_LIST";

	private static final String IMAGE_DLG_TAG = "IMAGE_DLG_TAG";

	@Bind(R.id.image_list)
	RecyclerView mImagesRecyclerView;

	@Inject
	BrowseImagesPresenter mBrowseImagesPresenter;

	@Bind(R.id.fabbutton)
	FloatingActionButton mFabBtn;

	private BrowseImagesAdapter mAdapter;
	private ViewType mCurrentViewType = ViewType.LIST;

	private LinearLayoutManager mLayoutManager;
	private GridLayoutManager mGridLayoutManager;
	private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

	@Inject
	RxBus mEventBus;

	private Subscription mBusSubscription;
	private long mVisibleThreshold = 5;

	private ImageDialog mImageDialog;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_browse_images, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_VIEW_TYPE)) {
				mCurrentViewType = ViewType.get(savedInstanceState.getString(CURRENT_VIEW_TYPE, ViewType.LIST.toString()));
			}
		}
		setUpPresenter();
		setUpFabBtn();
		mAdapter = new BrowseImagesAdapter(this);
		setUpRecyclerView();
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_IMAGE_LIST)) {
				ArrayList<Image> imageList = savedInstanceState.getParcelableArrayList(CURRENT_IMAGE_LIST);
				mAdapter.addData(imageList);
			}
		}
		mImagesRecyclerView.addOnScrollListener(mScrollListener);
	}

	private void setUpPresenter() {
		mBrowseImagesPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		switch(mCurrentViewType) {
			case LIST: {
				mLayoutManager = new LinearLayoutManager(
						getActivity(), LinearLayoutManager.VERTICAL, false);
				mImagesRecyclerView.setLayoutManager(mLayoutManager);
			}
			break;

			case GRID: {
				mGridLayoutManager = new GridLayoutManager(
						getActivity(), 2);
				mImagesRecyclerView.setLayoutManager(mGridLayoutManager);
				mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						return (mAdapter.isLoadingPos(position)) ? mGridLayoutManager.getSpanCount() : 1;
					}
				});
			}
			break;

			case STAGGERED: {
				mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
				mImagesRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
			}
			break;
		}
		mImagesRecyclerView.setAdapter(mAdapter);
		mAdapter.setViewMode(mCurrentViewType);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mBrowseImagesPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mBrowseImagesPresenter.onResume();
		autoUnsubBus();
		subscribeBus();
	}

	private void subscribeBus() {
		mBusSubscription = mEventBus.toObserverable()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						new Action1<Object>() {
							@Override
							public void call(Object o) {
								handlerBus(o);
							}
						}
				);
	}

	private void autoUnsubBus() {
		if (mBusSubscription != null && !mBusSubscription.isUnsubscribed()) {
			mBusSubscription.unsubscribe();
		}
	}

	private void handlerBus(Object o) {
		if(o instanceof BusEvents.ImageClicked) {
			BusEvents.ImageClicked event = (BusEvents.ImageClicked)o;
			onImageClicked(event.image);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mBrowseImagesPresenter.onPause();
		autoUnsubBus();
	}

	@Override
	public void onStop() {
		super.onStop();
		mBrowseImagesPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBrowseImagesPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mImagesRecyclerView.setAdapter(null);
		mImagesRecyclerView.removeOnScrollListener(mScrollListener);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(ImageSearchApiResponse response) {
		mAdapter.addData(response);
		if(response != null && response.getImages() != null && response.getImages().size() > 0) {
			mFabBtn.setVisibility(View.VISIBLE);
		} else {
			mFabBtn.setVisibility(View.GONE);
		}
	}

	@Override
	public void onFailure(String errorMsg) {

	}

	@OnClick(R.id.fabbutton)
	public void onToogleViewClicked() {
		switch(mCurrentViewType) {
			case LIST: {
				mCurrentViewType = ViewType.GRID;
			}
			break;

			case GRID: {
				mCurrentViewType = ViewType.STAGGERED;
			}
			break;

			case STAGGERED: {
				mCurrentViewType = ViewType.LIST;
			}
			break;
		}
		setUpRecyclerView();
		setUpFabBtn();
	}

	private void setUpFabBtn() {
		switch(mCurrentViewType) {
			case LIST: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_grid_on_white_36dp));
			}
			break;

			case GRID: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_dashboard_white_36dp));
			}
			break;

			case STAGGERED: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_view_list_white_36dp));
			}
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CURRENT_VIEW_TYPE, mCurrentViewType.getCode());
		outState.putParcelableArrayList(CURRENT_IMAGE_LIST, mAdapter.getImageList());
	}

	private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			int totalItemCount = 0;
			int lastVisibleItem = 0;
			switch (mCurrentViewType) {
				case LIST: {
					totalItemCount = mLayoutManager.getItemCount();
					lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
				}
				break;

				case GRID: {
					totalItemCount = mGridLayoutManager.getItemCount();
					lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
				}
				break;

				case STAGGERED: {
					totalItemCount = mStaggeredGridLayoutManager.getItemCount();
					int[] positions = new int[2];
					positions = mStaggeredGridLayoutManager.findLastVisibleItemPositions(positions);
					lastVisibleItem = positions[1] > positions[0] ? positions[1] : positions[0];
				}
				break;
			}
			if (!mBrowseImagesPresenter.isLoading()
					&& totalItemCount <= (lastVisibleItem + mVisibleThreshold)) {
				//End of the items
				mAdapter.loadMore();
			}
		}
	};

	@Override
	public void onNewSearchQuery(String searchTerm) {
		mAdapter.loadData(searchTerm);
	}

	private void onImageClicked(Image image) {
		mBrowseImagesPresenter.setSelectedImage(image);
		if(mImageDialog == null || mImageDialog.isDetached()) {
			mImageDialog = ImageDialog.newInstance();
		}
		mImageDialog.show(getFragmentManager(), IMAGE_DLG_TAG);
	}
}
