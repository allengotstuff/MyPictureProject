package com.pheth.hasee.stickerhero.BaseData.Factory;

import com.pheth.hasee.stickerhero.BaseData.Data.BaseData;

/**
 * Created by allengotstuff on 12/5/2016.
 */
public interface BaseFactory<T extends BaseData,H> {

    T produce(H data);
}
