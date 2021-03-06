package com.zzxhdzj.http.mock;

import com.zzxhdzj.http.ApiResponseCallbacks;
import com.zzxhdzj.http.JsonApiResponse;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 9:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestApiResponseCallbacks implements ApiResponseCallbacks<JsonApiResponse> {

    public JsonApiResponse successResponse;
    public JsonApiResponse failureResponse;
    public Boolean onCompleteWasCalled;
    public Boolean onBizCallBackFailedWasCalled;
    public Boolean onProcessFailureWasCalled;
    public Boolean onCallBackFailureWasCalled;

    public void onSuccess(JsonApiResponse successResponse) throws IOException {
        this.successResponse = successResponse;
    }

    @Override
    public void onRequestFailure(JsonApiResponse response) {
        this.failureResponse = response;
    }

    @Override
    public void onProcessFailure(JsonApiResponse response) {
        onProcessFailureWasCalled = true;
        this.failureResponse = response;
    }

    @Override
    public void onCallbackFailure(JsonApiResponse response) {
        onCallBackFailureWasCalled = true;
        this.failureResponse = response;
    }

    public void onComplete() {
        onCompleteWasCalled = true;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onBizFailure(JsonApiResponse response) {
        this.failureResponse = response;
        onBizCallBackFailedWasCalled = true;
    }
}
