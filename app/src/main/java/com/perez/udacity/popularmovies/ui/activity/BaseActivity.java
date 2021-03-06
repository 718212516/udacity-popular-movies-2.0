
package com.perez.udacity.popularmovies.ui.activity;

import android.app.Activity;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.perez.udacity.popularmovies.MoviesApplication;
import com.perez.udacity.popularmovies.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.app.AppObservable;
import timber.log.Timber;

/**
 * Base class for all activities.
 * Binds Views
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable @Bind(R.id.toolbar) Toolbar mToolbar;

    @CallSuper
    @Override protected void onDestroy() {
        super.onDestroy();
        MoviesApplication.get(this).getRefWatcher().watch(this);
    }

    @CallSuper
    @Override public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupToolbar();
    }

    @Nullable
    public final Toolbar getToolbar() {
        return mToolbar;
    }

    private void setupToolbar() {
        if (mToolbar == null) {
            Timber.w("Didn't find a toolbar");
            return;
        }

        ViewCompat.setElevation(mToolbar, getResources().getDimension(R.dimen.toolbar_elevation));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
    }

    /**
     * Binds the given source sequence to an Activity.
     *
     * @see AppObservable#bindActivity(Activity, Observable)
     */
    protected final <T> Observable<T> bind(Observable<T> source) {
        return AppObservable.bindActivity(this, source);
    }
}
