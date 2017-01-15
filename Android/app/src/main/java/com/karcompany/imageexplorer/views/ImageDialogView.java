package com.karcompany.imageexplorer.views;

import com.karcompany.imageexplorer.models.Image;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface ImageDialogView {

	void updateImageDetails(Image image);

}
