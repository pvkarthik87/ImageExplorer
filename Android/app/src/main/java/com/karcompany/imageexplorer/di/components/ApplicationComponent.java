package com.karcompany.imageexplorer.di.components;

import android.content.Context;

import com.karcompany.imageexplorer.ImageExplorerApplication;
import com.karcompany.imageexplorer.di.modules.ApplicationModule;
import com.karcompany.imageexplorer.networking.NetworkModule;
import com.karcompany.imageexplorer.views.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

	void inject(ImageExplorerApplication imageExplorerApplication);

	void inject(BaseActivity baseActivity);
	
	//Exposed to sub-graphs.
	Context context();
}
