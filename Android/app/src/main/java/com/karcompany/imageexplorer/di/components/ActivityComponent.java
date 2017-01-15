package com.karcompany.imageexplorer.di.components;

import android.app.Activity;

import com.karcompany.imageexplorer.di.PerActivity;
import com.karcompany.imageexplorer.di.modules.ActivityModule;

import dagger.Component;


/**
 * A base component upon which fragment's components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation:
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  //Exposed to sub-graphs.
  Activity activity();
}
