package com.karcompany.imageexplorer.networking;

import com.karcompany.imageexplorer.config.Constants;
import com.karcompany.imageexplorer.models.ImageSearchApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * REST service interface which Retrofit uses to communicate to a rest end point.
 */

public interface RestService {

	@Headers("Api-Key:"+Constants.SERVER_API_KEY)
	@GET("v3/search/images?page_size="+ Constants.NUM_ITEMS_IN_PAGE)
	Observable<ImageSearchApiResponse> getImages(@Query("page") long pageNo, @Query("phrase") String searchTerm);

}