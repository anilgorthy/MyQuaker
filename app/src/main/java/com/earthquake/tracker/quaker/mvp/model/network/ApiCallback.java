package com.earthquake.tracker.quaker.mvp.model.network;

import com.earthquake.tracker.quaker.mvp.model.ApiResponse;

public interface ApiCallback<T> {

    void onResponse(ApiResponse<T> apiResponse);

    void onFailure(String message);

}
