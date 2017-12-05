package com.dacom.damoney;

/**
 * Created by nnnyy on 2017-11-11.
 */

public class AdsDistItem {
    public AdsDistItem(int resid, String title, String idandregion, String date, int price, boolean soldout) {
        ResId = resid;
        sTitle = title;
        sIdAndRegion = idandregion;
        sRegdate = date;
        nPrice = price;
        bSoldout = soldout;
    }

    public int ResId;
    public String sTitle;
    public String sIdAndRegion;
    public String sRegdate;
    public int nPrice;
    public boolean bSoldout;
}
