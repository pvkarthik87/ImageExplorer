package com.karcompany.imageexplorer.networking;

import com.karcompany.imageexplorer.logging.DefaultLogger;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	public interface GetImagesApiCallback {
		void onSuccess(ImageSearchApiResponse response);

		void onError(NetworkError networkError);
	}

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

	/*
	    Retrives images from server
	 */
	public Subscription getImages(long pageNo, String searchTerm, final GetImagesApiCallback callback) {
		return mRestService.getImages(pageNo, searchTerm)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends ImageSearchApiResponse>>() {
					@Override
					public Observable<? extends ImageSearchApiResponse> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<ImageSearchApiResponse>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						NetworkError ne = new NetworkError(e);
						callback.onError(ne);
						DefaultLogger.d(TAG, "onError "+ne.getAppErrorMessage());
					}

					@Override
					public void onNext(ImageSearchApiResponse response) {
						DefaultLogger.d(TAG, "onNext");
						callback.onSuccess(response);
					}
				});
	}

}
