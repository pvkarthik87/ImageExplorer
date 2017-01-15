package com.karcompany.imageexplorer;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Android Application.
 */

import android.app.Application;

import com.karcompany.imageexplorer.di.components.ApplicationComponent;
import com.karcompany.imageexplorer.di.components.DaggerApplicationComponent;
import com.karcompany.imageexplorer.di.modules.ApplicationModule;
import com.karcompany.imageexplorer.networking.NetworkModule;

public class ImageExplorerApplication extends Application {

	private static ApplicationComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeInjector();
	}

	private void initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.networkModule(new NetworkModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public static ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
}

