package com.ayazalam.pratlipi.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import com.ayazalam.pratlipi.presenter.IPresenter;
import com.ayazalam.pratlipi.view.IView;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public abstract class BaseListActivity<V extends IView, P extends
        IPresenter<V>>
        extends AppCompatActivity {

    protected P mPresenter;

    protected abstract P createPresenter();

    protected abstract void initViewsAndEvents();

    protected abstract int getLayoutId();


    @SuppressWarnings("unchecked") @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if(mPresenter != null){
            mPresenter.attachView((V) this);
        }
        initViewsAndEvents();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
        }
    }
}
