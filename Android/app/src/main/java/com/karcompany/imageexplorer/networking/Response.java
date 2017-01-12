package com.karcompany.imageexplorer.networking;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * JSON POJO.
 */

import com.google.gson.annotations.SerializedName;

public class Response {
	@SerializedName("status")
	public String status;

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	@SuppressWarnings({"unused", "used by Retrofit"})
	public Response() {
	}

	public Response(String status) {
		this.status = status;
	}
}

