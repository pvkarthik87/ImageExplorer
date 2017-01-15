package com.karcompany.imageexplorer.views.activities;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Initial launch activity which will get displayed on first launch of application.
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.karcompany.imageexplorer.R;
import com.karcompany.imageexplorer.di.HasComponent;
import com.karcompany.imageexplorer.di.components.ApplicationComponent;
import com.karcompany.imageexplorer.events.BusEvents;
import com.karcompany.imageexplorer.events.RxBus;
import com.karcompany.imageexplorer.logging.DefaultLogger;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ImageSearchActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	private static final String TAG = DefaultLogger.makeLogTag(ImageSearchActivity.class);

	@Inject
	BrowseImagesPresenter mBrowseImagesPresenter;

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setTitle(getString(R.string.app_name));
		handleIntent(getIntent());
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_browse_images, menu);

		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView =
				(SearchView) MenuItemCompat.getActionView(searchItem);
		SearchManager searchManager =
				(SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView.setSearchableInfo(
				searchManager.getSearchableInfo(getComponentName()));
		return true;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			DefaultLogger.d(TAG, "User query "+query);
			mBrowseImagesPresenter.onNewSearchQuery(query);
			//use the query to search your data somehow
		}
	}

}
