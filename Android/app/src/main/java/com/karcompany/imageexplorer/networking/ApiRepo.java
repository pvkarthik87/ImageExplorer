package com.karcompany.imageexplorer.networking;

import com.karcompany.imageexplorer.logging.DefaultLogger;

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

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

}
