/**
 * WalletUserInfoModel.java
 * com.celt.estation.bean
 * <p>
 * Function： TODO
 * <p>
 * ver date author
 * ──────────────────────────────────
 * 2016-6-29 00012927
 * <p>
 * Copyright (c) 2016, TNT All Rights Reserved.
 */

package com.hmh0512.testorange.bean;

import com.hmh0512.orangeretrofit.http.HttpBaseModel;

/**
 * ClassName:WalletUserInfoModel
 * Function: TODO ADD FUNCTION
 * Reason: TODO ADD REASON
 *
 * @author 00012927
 * @Date 2016-6-29 上午10:07:14
 * @see
 */
public class WalletAccountModel extends HttpBaseModel {

    private WalletAccountEntity data;

    public WalletAccountEntity getData() {

        return data;

    }

    public void setData(WalletAccountEntity data) {

        this.data = data;

    }
}
