package com.karcompany.imageexplorer.presenters;

import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;
import com.karcompany.imageexplorer.networking.ApiRepo;
import com.karcompany.imageexplorer.networking.NetworkError;
import com.karcompany.imageexplorer.views.BrowseImagesView;
import com.karcompany.imageexplorer.views.ImageDialogView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter implementation which handles core features (shows selected image caption).
 */

public class ImageDialogPresenterImpl implements ImageDialogPresenter {

	private ImageDialogView mView;
	private BrowseImagesPresenter mBrowseImagesPresenter;

	@Inject
	public ImageDialogPresenterImpl(BrowseImagesPresenter browseImagesPresenter) {
		mBrowseImagesPresenter = browseImagesPresenter;
	}

	@Override
	public void setView(ImageDialogView browseUsersView) {
		mView = browseUsersView;
	}

	@Override
	public void onStart() {
		loadImageDetails();
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
	}

	private void loadImageDetails() {
		Image selectedImage = mBrowseImagesPresenter.getSelectedImage();
		if(selectedImage != null) {
			if(mView != null) {
				mView.updateImageDetails(selectedImage);
			}
		}
	}
}
