/**
 * WalletAccountEntity.java
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

import java.io.Serializable;

public class WalletAccountEntity implements Serializable {

    private static final long serialVersionUID = -8987274333431116909L;
    private double totalMoney;
    private int haveSetPasswd;

    public int getHaveSetPasswd() {

        return haveSetPasswd;
    }

    public void setHaveSetPasswd(int haveSetPasswd) {

        this.haveSetPasswd = haveSetPasswd;
    }

    public double getTotalMoney() {

        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {

        this.totalMoney = totalMoney;
    }
}
