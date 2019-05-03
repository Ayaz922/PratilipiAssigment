package com.ayazalam.pratlipi.presenter.impl;

import android.content.Context;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.model.impl.ContactModel;
import com.ayazalam.pratlipi.presenter.IMainPresenter;
import com.ayazalam.pratlipi.support.callback.DataCallback;
import com.ayazalam.pratlipi.view.IMainView;
import java.util.List;

/**
 * Created by Ayaz Alam on 2019/5/03.
 */
public class MainPresenter extends BasePresenter<IMainView>
        implements IMainPresenter {

    private ContactModel mIMainModel;
    private Context mContext;


    public MainPresenter(Context context) {
        mContext = context;
    }


    @SuppressWarnings("unchecked") @Override
    public void loadContacts(final boolean pullToRefresh) {
        mIMainModel =new ContactModel(mContext, new DataCallback<List<Contact>>() {
            @Override
            public void onSuccess(List<Contact> contacts) {
                getView().hideLoading();
                getView().setData(contacts);
            }

            @Override
            public void onFailure(String msg) {
                getView().hideLoading();
                getView().showError(msg, pullToRefresh);

            }
        });
        getView().showLoading(pullToRefresh);
        mIMainModel.execute();

    }
}
