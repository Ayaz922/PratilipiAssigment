package com.ayazalam.pratlipi.view;


public interface IBaseView<M> extends IView {

    void showLoading(boolean pullToRefresh);

    void hideLoading();

    void showError(String msg, boolean pullToRefresh);

    void setData(M data);

    void loadData(boolean pullToRefresh);

}
