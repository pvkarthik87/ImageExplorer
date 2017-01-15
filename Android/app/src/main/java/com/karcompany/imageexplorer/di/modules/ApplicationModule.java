package com.karcompany.imageexplorer.di.modules;

import android.content.Context;

import com.karcompany.imageexplorer.ImageExplorerApplication;
import com.karcompany.imageexplorer.events.RxBus;
import com.karcompany.imageexplorer.networking.ApiRepo;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenter;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenterImpl;
import com.karcompany.imageexplorer.presenters.ImageDialogPresenter;
import com.karcompany.imageexplorer.presenters.ImageDialogPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
	private final ImageExplorerApplication application;

	public ApplicationModule(ImageExplorerApplication application) {
		this.application = application;
	}

	@Provides @Singleton
	Context provideApplicationContext() {
		return this.application;
	}

	@Provides @Singleton
	BrowseImagesPresenter provideBrowseImagesPresenter(ApiRepo apiRepo) {
		return new BrowseImagesPresenterImpl(apiRepo);
	}

	@Provides @Singleton
	RxBus provideRxBus() {
		return new RxBus();
	}

	@Provides @Singleton
	ImageDialogPresenter provideImageDialogPresenter(BrowseImagesPresenter browseImagesPresenter) {
		return new ImageDialogPresenterImpl(browseImagesPresenter);
	}
}
