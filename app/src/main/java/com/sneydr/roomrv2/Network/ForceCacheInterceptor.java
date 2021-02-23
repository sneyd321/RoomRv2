package com.sneydr.roomrv2.Network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sneydr.roomrv2.App.App;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ForceCacheInterceptor implements Interceptor {



    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (!isNetworkAvailable(App.getApplication())) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }

    public boolean isNetworkAvailable(Application application) {
        final ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
