package com.ayazalam.pratlipi.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import com.ayazalam.pratlipi.presenter.IPresenter;
import com.ayazalam.pratlipi.view.IBaseView;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public abstract  class BaseListFragment<M, V extends IBaseView<M>, P extends IPresenter<V>> extends Fragment {

    protected P mPresenter;

    protected abstract P createPresenter();

    protected abstract void initViewsAndEvents();

    protected abstract int getLayoutId();

    protected void parseArguments() {

    }


    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();

        mPresenter.attachView((V) this);
        initViewsAndEvents();
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
    }


    @Override public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
