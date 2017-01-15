package com.karcompany.imageexplorer.presenters;

import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.mvputils.Presenter;
import com.karcompany.imageexplorer.views.BrowseImagesView;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Presenter interface which helps in getting images from server.
 *
 */

public interface BrowseImagesPresenter extends Presenter {

	void setView(BrowseImagesView browseImagesView);

	void loadImages(long pageNo, String searchTerm);

	boolean isLoading();

	void onNewSearchQuery(String searchTerm);

	Image getSelectedImage();

	void setSelectedImage(Image image);

}
