package com.udacity.gradle.builditbigger;

import static org.junit.Assert.*;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.jokeslib.Jokes;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BaseJokeTaskTest {

    @Test
    public void testGetJoke() {
        BaseJokeTask task = new BaseJokeTask();
        String result = task.doInBackground(InstrumentationRegistry.getTargetContext());
        String expected = Jokes.provideJoke();
        assertNotNull(result);
        assertEquals(expected, result);
    }
}