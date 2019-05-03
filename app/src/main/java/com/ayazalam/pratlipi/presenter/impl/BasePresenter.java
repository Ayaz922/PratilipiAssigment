package com.ayazalam.pratlipi.presenter.impl;

import com.ayazalam.pratlipi.presenter.IPresenter;
import com.ayazalam.pratlipi.view.IView;
import java.lang.ref.WeakReference;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {

    private WeakReference<V> viewRef;


    @Override public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    public V getView(){
        return viewRef == null ? null : viewRef.get();
    }

    public boolean isViewAttached(){
        return viewRef != null && viewRef.get() != null;
    }

    @Override public void detachView() {
        if(viewRef != null){
            viewRef.clear();
            viewRef = null;
        }
    }
}
