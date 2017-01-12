package com.karcompany.imageexplorer.views.activities;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Initial launch activity which will get displayed on first launch of application.
 */

import android.os.Bundle;

import com.karcompany.imageexplorer.R;
import com.karcompany.imageexplorer.di.HasComponent;
import com.karcompany.imageexplorer.di.components.ApplicationComponent;

public class ImageSearchActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setTitle(getString(R.string.app_name));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
