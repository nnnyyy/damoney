package com.dacom.damoney;

import java.util.ArrayList;

/**
 * Created by nnnyy on 2017-11-11.
 */

public class BonusItemData extends BonusItemBase {
    public BonusItemData() {
        super.type = BIType.BI_ITEM;
    }
    public int no;
    public String publisher;
    public String name;
    public int reqLevel;
    public String iconPath;
    public ArrayList<Integer> aGacha;
    public String link;
}
