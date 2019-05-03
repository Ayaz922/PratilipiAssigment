package com.ayazalam.pratlipi.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ayazalam.pratlipi.R;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.presenter.IAddPresenter;
import com.ayazalam.pratlipi.presenter.impl.AddPresenter;
import com.ayazalam.pratlipi.support.Constants;
import com.ayazalam.pratlipi.view.IAddView;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */


public class AddContactActivity
        extends BaseListActivity<IAddView,
        IAddPresenter> implements IAddView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_contact_name)
    TextInputLayout addContactName;
    @BindView(R.id.add_contact_nick_name) TextInputLayout addContactNickName;
    @BindView(R.id.add_contact_phone) TextInputLayout addContactPhone;
    @BindView(R.id.add_contact_email) TextInputLayout addContactEmail;
    @BindView(R.id.add_contact_address) TextInputLayout addContactAddress;
    @BindView(R.id.add_contact_cancel) TextView addContactCancel;
    @BindView(R.id.add_contact_save) TextView addContactSave;
    private Contact mContact;


    public static void startActivityWithContact(Context context, Contact contact) {
        Intent intent = new Intent(context, AddContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Extras.CONTACT, contact);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,Constants.Requests
                .ADD_CONTACT);
    }

    @Override protected IAddPresenter createPresenter() {
        return new AddPresenter(this);
    }


    @Override protected void initViewsAndEvents() {
        initToolbar();
        mContact = getIntent().getParcelableExtra(Constants.Extras.CONTACT);
        if(mContact != null){
            addContactName.getEditText().setText(mContact.getName());
            addContactNickName.getEditText().setText(mContact.getNickName());
            addContactPhone.getEditText().setText(mContact.getPhoneNumber());
            addContactEmail.getEditText().setText(mContact.getEmail());
            addContactAddress.getEditText().setText(mContact.getAddress());
        }
    }


    @Override protected int getLayoutId() {
        return R.layout.activity_add_contact;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:

            default:
                break;
        }
        return true;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @OnClick(R.id.add_contact_cancel)
    public void clickToCancel(){
        finish();
    }

    @OnClick(R.id.add_contact_save)
    public void clickToSave(){
        Contact contact = new Contact();
        contact.setName(addContactName.getEditText().getText().toString());
        contact.setEmail(addContactEmail.getEditText().getText().toString());
        contact.setNickName(addContactNickName.getEditText().getText().toString());
        contact.setAddress(addContactAddress.getEditText().getText().toString());
        contact.setPhoneNumber(addContactPhone.getEditText().getText().toString());
        if(mContact == null){
            mPresenter.addContacts(contact);
        }else {
            contact.setId(mContact.getId());
            mPresenter.editContacts(contact);
        }

    }


    @Override public void finishActivity(Contact contact) {
        if(mContact == null){
            Snackbar.make(addContactSave,"Add Contact Success",Snackbar
                    .LENGTH_SHORT).show();
        }else {
            Snackbar.make(addContactSave,"Edit Contact Success",Snackbar
                    .LENGTH_SHORT)
                    .show();
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Extras.ADD_CONTACT, contact);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override public void showError(String msg) {
        Snackbar.make(addContactSave,"Something error:"+msg,Snackbar
                .LENGTH_SHORT)
                .show();
    }
}
