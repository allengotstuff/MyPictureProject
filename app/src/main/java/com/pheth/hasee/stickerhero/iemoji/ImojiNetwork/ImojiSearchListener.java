package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;

import java.util.List;

/**
 * Created by allengotstuff on 11/16/2016.
 */
public interface ImojiSearchListener {
    void onPostExecute(List arrayList);

    void onRequestCancle();
}
