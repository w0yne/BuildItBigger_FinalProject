package com.udacity.gradle.builditbigger;

import android.content.Intent;

import com.udacity.gradle.jokesactivity.JokeActivity;

public class GetJokeTask extends BaseJokeTask {
    public GetJokeTask(final LoadingListener loadingListener) {
        super(loadingListener);
    }

    @Override
    protected void onPostExecute(final String s) {
        super.onPostExecute(s);
        mContext.startActivity(new Intent(mContext, JokeActivity.class)
                .putExtra(JokeActivity.EXTRA_JOKE, s));
    }
}
