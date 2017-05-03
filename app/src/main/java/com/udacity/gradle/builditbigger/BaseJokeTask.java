package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.backend.myApi.MyApi;
import com.udacity.gradle.jokeslib.Jokes;

import java.io.IOException;

public class BaseJokeTask extends AsyncTask<Context, Void, String> {

    private static MyApi sApi = null;

    protected Context mContext;
    protected LoadingListener mLoadingListener;

    public BaseJokeTask() {
        this(null);
    }

    public BaseJokeTask(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    @Override
    protected void onPreExecute() {
        if (mLoadingListener != null) {
            mLoadingListener.onStartLoading();
        }
    }

    @Override
    protected String doInBackground(final Context... params) {
        if (sApi == null) {
            MyApi.Builder builder =
                    new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
            sApi = builder.build();
        }
        mContext = params[0];
        try {
            return sApi.getJoke(Jokes.provideJoke()).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(final String s) {
        if (mLoadingListener != null) {
            mLoadingListener.onFinishLoading();
        }
    }

    public interface LoadingListener {
        void onStartLoading();

        void onFinishLoading();
    }
}
