package com.ayazalam.pratlipi.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

import com.ayazalam.pratlipi.R;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.presenter.IMainPresenter;
import com.ayazalam.pratlipi.presenter.impl.MainPresenter;
import com.ayazalam.pratlipi.support.Constants;
import com.ayazalam.pratlipi.ui.activity.ContactDetailActivity;
import com.ayazalam.pratlipi.ui.adapter.BaseRecyclerAdapter;
import com.ayazalam.pratlipi.ui.adapter.ContactAdapter;
import com.ayazalam.pratlipi.utils.ContactSyncCallback;
import com.ayazalam.pratlipi.utils.ContactSyncHelper;
import com.ayazalam.pratlipi.view.IMainView;
import java.util.List;

/**
 * Created by Ayaz Alam on 2019/5/02.
 */
public class MainFragment
        extends BaseListFragment<List<Contact>, IMainView, IMainPresenter>
        implements IMainView{

    @BindView(R.id.loadingView) ProgressBar loadingView;
    @BindView(R.id.errorView) TextView errorView;
    @BindView(R.id.main_recycler_view)
    RecyclerView mainRecyclerView;

    private List<Contact> mContactList;
    private ContactAdapter mContactAdapter;
    private ContactSyncCallback callback;
    private ContactSyncHelper syncHelper;


    @Override protected IMainPresenter createPresenter() {
        return new MainPresenter(getActivity());
    }


    @Override protected void initViewsAndEvents() {
        loadData(false);
    }


    @Override protected int getLayoutId() {
        return R.layout.fragment_main;
    }


    @Override public void showLoading(boolean pullToRefresh) {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        mainRecyclerView.setVisibility(View.GONE);
    }


    @Override public void hideLoading() {
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        mainRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override public void showError(String msg, boolean pullToRefresh) {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        mainRecyclerView.setVisibility(View.GONE);
    }


    @Override public void setData(List<Contact> data) {
        mContactList = data;
        mContactAdapter = new ContactAdapter(getActivity(),R.layout
                .item_contact,mContactList);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                ContactDetailActivity.startActivityWithContact(getActivity(),
                        mContactList.get(position));
            }
        });
        mainRecyclerView.setAdapter(mContactAdapter);
    }


    @Override public void loadData(boolean pullToRefresh) {
        mPresenter.loadContacts(pullToRefresh);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            mContactAdapter.add((Contact) data.getParcelableExtra(Constants.Extras
                    .ADD_CONTACT));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        callback=null;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(syncHelper!=null)
        if(syncHelper.getStatus()== AsyncTask.Status.RUNNING||syncHelper.getStatus()==AsyncTask.Status.PENDING) {
            syncHelper.cancel(true);
            syncHelper=null;
        }

        callback = (int size) -> {
            if(mContactList!=null)
            if(size>mContactList.size())
            {
                showLoading(true);
                loadData(true);
            }

        };
        syncHelper =new ContactSyncHelper(getContext(),callback);
        syncHelper.execute();
    }
}
