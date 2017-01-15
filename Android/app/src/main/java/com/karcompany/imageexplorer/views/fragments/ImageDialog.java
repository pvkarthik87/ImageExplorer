package com.karcompany.imageexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Image dialog which displays selected image caption in dialog.
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karcompany.imageexplorer.R;
import com.karcompany.imageexplorer.di.components.ApplicationComponent;
import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.presenters.ImageDialogPresenter;
import com.karcompany.imageexplorer.views.ImageDialogView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by sammyer on 2017-01-15.
 */

public class ImageDialog extends BaseDialogFragment implements ImageDialogView {

	static ImageDialog newInstance() {
		return new ImageDialog();
	}

	@Inject
	ImageDialogPresenter mImageDialogPresenter;

	@Bind(R.id.imageCaption)
	TextView mImageCaption;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_dialog, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		setUpPresenter();
		getDialog().setCanceledOnTouchOutside(true);
	}

	private void setUpPresenter() {
		mImageDialogPresenter.setView(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mImageDialogPresenter.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		mImageDialogPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageDialogPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mImageDialogPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mImageDialogPresenter.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void updateImageDetails(Image image) {
		if(!TextUtils.isEmpty(image.getCaption())) {
			mImageCaption.setText(image.getCaption());
		}
		// Set title for this dialog
		if(!TextUtils.isEmpty(image.getTitle())) {
			getDialog().setTitle(image.getTitle());
		}
	}

	@OnClick(R.id.dimissBar)
	public void onDismissViewClicked() {
		dismiss();
	}
}
