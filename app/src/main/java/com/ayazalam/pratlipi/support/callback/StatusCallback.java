package com.ayazalam.pratlipi.support.callback;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public abstract class StatusCallback {

    public abstract void onSuccess();

    public abstract void onFailure(String msg);

    public void onError(String msg){}
}
