package com.karcompany.imageexplorer.presenters;

import com.karcompany.imageexplorer.mvputils.Presenter;
import com.karcompany.imageexplorer.views.ImageDialogView;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter interface which helps in showing selected image caption.
 *
 */

public interface ImageDialogPresenter extends Presenter {

	void setView(ImageDialogView imageDialogView);

}
