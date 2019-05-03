package com.ayazalam.pratlipi.presenter.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.model.IContactModel;
import com.ayazalam.pratlipi.model.impl.ContactModel;
import com.ayazalam.pratlipi.presenter.IAddPresenter;
import com.ayazalam.pratlipi.support.callback.DataCallback;
import com.ayazalam.pratlipi.support.callback.StatusCallback;
import com.ayazalam.pratlipi.view.IAddView;

import java.util.List;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class AddPresenter extends BasePresenter<IAddView> implements
        IAddPresenter {

    private IContactModel mIContactModel;
    private Context mContext;

    public AddPresenter(Context context){
        mContext = context;
        mIContactModel = new ContactModel(mContext, new DataCallback<List<Contact>>() {
            @Override
            public void onSuccess(List<Contact> contacts) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override public void addContacts(final Contact contact) {
        mIContactModel.addContact(mContext, contact, new StatusCallback() {
            @Override public void onSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().finishActivity(contact);
                    }
                });
            }


            @Override public void onFailure(final String msg) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().showError(msg);
                    }
                });
            }
        });
    }


    @Override public void deleteContacts(final Contact contact) {
        mIContactModel.deleteContact(mContext, contact, new StatusCallback() {
            @Override public void onSuccess() {
                getView().finishActivity(contact);
            }


            @Override public void onFailure(String msg) {
                getView().showError(msg);
            }
        });
    }


    @Override public void editContacts(final Contact contact) {
        mIContactModel.editContact(mContext, contact, new StatusCallback() {
            @Override public void onSuccess() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().finishActivity(contact);
                    }
                });
            }


            @Override public void onFailure(final String msg) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        getView().showError(msg);
                    }
                });
            }
        });
    }
}
