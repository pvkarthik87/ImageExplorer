package com.karcompany.imageexplorer.events;

import com.karcompany.imageexplorer.models.Image;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * Bus Events
 */

public class BusEvents {

	public static class ImageClicked {
		public Image image;
	}

}
