package com.karcompany.imageexplorer.presenters;

import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;
import com.karcompany.imageexplorer.networking.ApiRepo;
import com.karcompany.imageexplorer.networking.NetworkError;
import com.karcompany.imageexplorer.views.BrowseImagesView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features (fetches images from server).
 */

public class BrowseImagesPresenterImpl implements BrowseImagesPresenter, ApiRepo.GetImagesApiCallback {

	private BrowseImagesView mView;

	@Inject
	ApiRepo mApiRepo;

	private boolean mIsLoading;
	private CompositeSubscription subscriptions;

	private Image mSelectedImage;

	@Inject
	public BrowseImagesPresenterImpl(ApiRepo apiRepo) {
		mApiRepo = apiRepo;
	}

	@Override
	public void setView(BrowseImagesView browseUsersView) {
		mView = browseUsersView;
		subscriptions = new CompositeSubscription();
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
		if(subscriptions != null) {
			subscriptions.unsubscribe();
		}
	}

	@Override
	public void loadImages(long pageNo, String searchTerm) {
		mIsLoading = true;
		Subscription subscription = mApiRepo.getImages(pageNo, searchTerm, this);
		subscriptions.add(subscription);
	}

	@Override
	public void onSuccess(ImageSearchApiResponse response) {
		mIsLoading = false;
		if (mView != null) {
			mView.onDataReceived(response);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	@Override
	public void onNewSearchQuery(String searchTerm) {
		if(mView != null) {
			mView.onNewSearchQuery(searchTerm);
		}
	}

	@Override
	public void setSelectedImage(Image image) {
		mSelectedImage = image;
	}

	@Override
	public Image getSelectedImage() {
		return mSelectedImage;
	}
}
