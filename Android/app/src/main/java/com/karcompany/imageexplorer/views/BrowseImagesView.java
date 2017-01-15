package com.karcompany.imageexplorer.views;

import com.karcompany.imageexplorer.models.ImageSearchApiResponse;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface BrowseImagesView {

	void onDataReceived(ImageSearchApiResponse response);

	void onFailure(String errorMsg);

	void onNewSearchQuery(String searchTerm);

}
