package com.karcompany.imageexplorer;

/**
 * Created by pvkarthik on 2017-01-15.
 *
 * Presenter unit test cases.
 */

import com.karcompany.imageexplorer.models.Image;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;
import com.karcompany.imageexplorer.networking.ApiRepo;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenter;
import com.karcompany.imageexplorer.presenters.BrowseImagesPresenterImpl;
import com.karcompany.imageexplorer.views.BrowseImagesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class BrowseImagesPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private BrowseImagesView view;

	private BrowseImagesPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new BrowseImagesPresenterImpl(model);
		presenter.setView(view);
		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				ImageSearchApiResponse imageSearchApiResponse = new ImageSearchApiResponse();
				List<Image> images = new ArrayList<>();
				Image hotel = new Image();
				images.add(hotel);
				imageSearchApiResponse.setImages(images);
				((ApiRepo.GetImagesApiCallback) presenter).onSuccess(imageSearchApiResponse);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getImages(0, "test", (ApiRepo.GetImagesApiCallback) presenter);
	}

	/**
	 Verify if model.getUserList was called once.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchImages() {
		presenter.loadImages(0, "test");
		// verify can be called only on mock objects
		verify(model, times(1)).getImages(0, "test", (ApiRepo.GetImagesApiCallback) presenter);
		verify(view, times(1)).onDataReceived(any(ImageSearchApiResponse.class));
	}

}
