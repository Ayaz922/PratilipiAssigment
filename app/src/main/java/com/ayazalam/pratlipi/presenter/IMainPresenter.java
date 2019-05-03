package com.ayazalam.pratlipi.presenter;

import com.ayazalam.pratlipi.view.IMainView;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public interface IMainPresenter extends IPresenter<IMainView>{

    void loadContacts(final boolean pullToRefresh);
}
