package com.pheth.hasee.stickerhero.iemoji.ImojiNetwork;

import java.util.List;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2016
 * @创建人: 孙旭光
 * @创建时间: xxx(2016-09-21 14:11)
 * @Email: 410073261@qq.com
 * <p>
 * 描述:com.fotoable.keyboard.emoji.ui.iemoji.ImojiNetwork-ImojiBaseData
 * <p>
 * <p>
 * *************************************************************************
 */

public interface ImojiBaseData {
    void startRequest(RequestInfo info);
    void onPostExecute(List arrayList);
    void onCancel();
    void onRefresh();
}
