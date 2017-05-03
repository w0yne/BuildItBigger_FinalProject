package com.udacity.gradle.builditbigger;

import android.content.Intent;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.jokesactivity.JokeActivity;

public class GetJokeTask extends BaseJokeTask {

    private InterstitialAd mInterstitialAd;

    public GetJokeTask(final LoadingListener loadingListener) {
        super(loadingListener);
    }

    @Override
    protected void onPostExecute(final String s) {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mLoadingListener != null) {
                    mLoadingListener.onFinishLoading();
                }
                mInterstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                mContext.startActivity(new Intent(mContext, JokeActivity.class)
                        .putExtra(JokeActivity.EXTRA_JOKE, s));
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
