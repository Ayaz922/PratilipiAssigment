package com.ayazalam.pratlipi.presenter;

import com.ayazalam.pratlipi.view.IView;

/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public interface IPresenter<V extends IView> {

    void attachView(V view);

    void detachView();
}
