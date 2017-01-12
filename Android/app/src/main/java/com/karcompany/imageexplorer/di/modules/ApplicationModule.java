package com.karcompany.imageexplorer.di.modules;

import android.content.Context;

import com.karcompany.imageexplorer.ImageExplorerApplication;

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
}
