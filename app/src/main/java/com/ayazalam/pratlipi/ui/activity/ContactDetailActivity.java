package com.ayazalam.pratlipi.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;

import com.ayazalam.pratlipi.model.impl.ContactModel;
import com.ayazalam.pratlipi.support.callback.DataCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ayazalam.pratlipi.R;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.presenter.IDetailPresenter;
import com.ayazalam.pratlipi.presenter.impl.DetailPresenter;
import com.ayazalam.pratlipi.support.Constants;
import com.ayazalam.pratlipi.utils.PhotoHelper;
import com.ayazalam.pratlipi.view.IDetailView;

import java.io.InputStream;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class ContactDetailActivity
        extends BaseListActivity<IDetailView, IDetailPresenter>
        implements IDetailView {

    @BindView(R.id.detail_mobile) TextView detailMobile;
    @BindView(R.id.detail_work) TextView detailWork;
    @BindView(R.id.detail_personal_email) TextView detailPersonalEmail;
    @BindView(R.id.detail_nick_name) TextView detailNickName;
    @BindView(R.id.detail_address) TextView detailAddress;
    @BindView(R.id.detail_other) TextView detailOther;
    @BindView(R.id.detail_pic) ImageView detailPic;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.detail_fab) FloatingActionButton detailFab;
    private Contact mContact;

    public static void startActivityWithContact(Context context, Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.Extras.CONTACT, contact);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,Constants.Requests
                .ADD_CONTACT);
    }


    @Override protected IDetailPresenter createPresenter() {
        return new DetailPresenter();
    }


    @Override protected void initViewsAndEvents() {
        mContact = getIntent().getParcelableExtra(Constants.Extras.CONTACT);
        initToolbar();
        detailMobile.setText(mContact.getPhoneNumber());
        detailWork.setText(mContact.getPhoneNumber());
        ContactModel.getEmailById(this, mContact.getId(), new DataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if(s==null||s.equals("data1")||s.equals(""))
                    s ="Not Available";
                detailPersonalEmail.setText(s);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        ContactModel.getNickNameById(this, mContact.getId(), new DataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if(s.equals("data1"))
                    s="Not Available";
                detailNickName.setText(s);
            }

            @Override
            public void onFailure(String msg) {

            }
        });

        ContactModel.getAddressById(this, mContact.getId(), new DataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if(s.equals("data1")) s ="Not Available";
                detailAddress.setText(s);
            }

            @Override
            public void onFailure(String msg) {

            }
        });
        detailAddress.setText(mContact.getAddress());

        InputStream d =PhotoHelper.openDisplayPhoto(Long.parseLong(mContact.getId()),this);
        Glide.with(this)
                .load(BitmapFactory.decodeStream(d))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .centerCrop()
                .dontAnimate()
                .into(detailPic);
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(mContact.getName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override protected int getLayoutId() {
        return R.layout.activity_contact_detail;
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_detail, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:
                AddContactActivity.startActivityWithContact(this,mContact);
                break;
            default:
                break;
        }
        return true;
    }
}
