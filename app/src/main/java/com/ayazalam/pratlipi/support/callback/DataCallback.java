package com.ayazalam.pratlipi.support.callback;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public abstract class DataCallback<T> {

    public abstract void onSuccess(T t);

    public abstract void onFailure(String msg);

    public void onError(String msg){}
}
