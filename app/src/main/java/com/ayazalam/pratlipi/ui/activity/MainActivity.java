package com.ayazalam.pratlipi.ui.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.ayazalam.pratlipi.R;
import com.ayazalam.pratlipi.presenter.IPresenter;
import com.ayazalam.pratlipi.support.Constants;
import com.ayazalam.pratlipi.ui.fragment.MainFragment;

/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class MainActivity extends BaseListActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainFragment mMainFragment;


    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody") @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.about) {
            startActivity(new Intent(this,About.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_add:
                startActivityForResult(new Intent(this,AddContactActivity
                        .class), Constants.Requests.ADD_CONTACT);
                break;
            case R.id.main_swipe_refresh_layout:
                mMainFragment.showLoading(true);
                mMainFragment.loadData(true);
            default:
                break;
        }
        return true;
    }


    @Override protected IPresenter createPresenter() {
        return null;
    }


    @Override protected void initViewsAndEvents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        mMainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,
                mMainFragment).commit();
    }


    @Override protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMainFragment.onActivityResult(requestCode,resultCode,data);
    }
}
