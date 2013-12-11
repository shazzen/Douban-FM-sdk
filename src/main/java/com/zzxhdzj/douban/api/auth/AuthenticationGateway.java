package com.zzxhdzj.douban.api.auth;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiRespErrorCode;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.LoginResp;
import com.zzxhdzj.http.*;
import org.apache.http.Header;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationGateway extends BaseApiGateway {

    public AuthenticationGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.R;
    }

    public void signOut() {
    }

    public void signIn(LoginParams login, Callback responseCallback) {
        apiGateway.makeRequest(new AuthenticationRequest(login),
                new AuthenticationApiResponseCallback(responseCallback));
    }

    class AuthenticationApiResponseCallback implements ApiResponseCallbacks<TextApiResponse> {
        private final Callback callback;

        public AuthenticationApiResponseCallback(Callback responseCallback) {
            this.callback = responseCallback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            LoginResp loginResp = gson.fromJson(response.getResp(), LoginResp.class);
            if (loginResp.r == 0) {
                Header[] headers = response.getHeaders();
                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    if (header.getName().equals(HttpHeaders.SET_COOKIE)) {
                        String token = header.getValue();
                        douban.getDoubanSharedPreferences().edit().putString(Constants.COOKIE, token).commit();
                        break;
                    }
                }
                callback.onSuccess();
            } else {
                douban.apiRespErrorCode = new ApiRespErrorCode(loginResp.errNo, loginResp.errMsg);
            }
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}
