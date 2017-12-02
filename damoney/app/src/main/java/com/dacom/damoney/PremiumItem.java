package com.dacom.damoney;

/**
 * Created by nnnyy on 2017-11-11.
 */

public class PremiumItem {
    public int sn;
    public String iconPath;
    public int iconResId;
    public String title;
    public int type;       // 설치형, 가입형, 실행형
    public int point;      // 얻는 포인트
    public String sURL;    // 연결 될 URL
    public boolean isUsed = false; // 사용한 광고
}
