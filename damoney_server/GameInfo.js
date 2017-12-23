/**
 * Created by nnnyy on 2017-11-20.
 */
var exptable = [
    {exp: 0},   // 0
    {exp: 32},  // 1->2
    {exp: 70},  // 2->3
    {exp: 118},
    {exp: 180},
    {exp: 260},
    {exp: 362},
    {exp: 490},
    {exp: 648},
    {exp: 840},
    {exp: 1070},
    {exp: 1342},
    {exp: 1660},
    {exp: 2028},
    {exp: 2450},
    {exp: 2930},
    {exp: 3472},
    {exp: 4080},
    {exp: 4758},
    {exp: 5510},
    {exp: 6340},
    {exp: 7252},
    {exp: 8250},
    {exp: 9338},
    {exp: 10520},
    {exp: 11800},
    {exp: 13182},
    {exp: 14670},
    {exp: 16268},
    {exp: 17980},
    {exp: 19810},
];

var bonusItemInfo = [
]

exports.getLevelInfo = function(exp) {
    var level = 0;
    var nextExpMax = 0;
    var curExp = 0

    console.log(exp);

    for(var i = 0 ; i < exptable.length ; ++i) {
        if( exp < exptable[i].exp) {
            console.log(i);
            level = i;
            nextExpMax = exptable[i].exp - exptable[i-1].exp;
            curExp = exp - exptable[i-1].exp;
            return {level: level, nextExpMax: nextExpMax, curExp: curExp};
        }
    }
    return {level: 99, nextExpMax: 0, curExp: 0};
}

exports.initBonusItemInfo = function() {
    /*bonusItemInfo.push(createBonusItemInfo(0, '던킨도너츠', '먼치킨 박스', 1, '/images/bonus/icon_dunkin.png', [1000,1001,1002,1003,1004,1007,1008,1009,2003]));
    bonusItemInfo.push(createBonusItemInfo(1, '아리따움', '아리따움 3천원권', 1, '/images/bonus/icon_aritaum.png', [1001,1002,1003,1004,1005,1007,1008,1009,2001]));
    bonusItemInfo.push(createBonusItemInfo(2, 'belif', '수분 크림', 4, '/images/bonus/test.png', [1000,1001,1002,1003,1004,1007,1008,2001,2003]));
    bonusItemInfo.push(createBonusItemInfo(3, '샤넬', '립스틱 루즈코코', 4, '/images/bonus/icon_chanel.png', [1002,1003,1004,1005,1006,1007,1008,2002,2004]));
    bonusItemInfo.push(createBonusItemInfo(4, '도미노 피자', '포테이토M + 콜라1.25L', 4, '/images/bonus/icon_domino.png', [1001,1004,1005,1006,1007,1008,1009,2001,2002]));
    bonusItemInfo.push(createBonusItemInfo(5, '몽블랑', '남성 반지갑', 7, '/images/bonus/icon_montblanc.png', [1003,1004,1007,1008,1009,2003,2002,2004,3001]));
    bonusItemInfo.push(createBonusItemInfo(6, 'LG', '24인치 모니터', 7, '/images/bonus/test.png', [1002,1003,1007,1008,1009,2001,2002,2003,3002]));
    bonusItemInfo.push(createBonusItemInfo(7, 'SONY', '플레이스테이션 슬림 1TB', 7, '/images/bonus/test.png', [1002,1003,1007,1001,1008,2002,2004,2001,3003]));
    bonusItemInfo.push(createBonusItemInfo(8, '샤넬', '17FW 캐비어 백', 10, '/images/bonus/test.png', [1000,1002,1007,1009,2003,2002,2004,3001,3002]));
    bonusItemInfo.push(createBonusItemInfo(9, 'BMW', '미니 쿠퍼', 10, '/images/bonus/test.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));*/
    bonusItemInfo.push(createBonusItemInfo(0, '맥심 모카커피', '맥심', 1, '/images/bonus/b00.png', [1000,1001,1002,1003,1004,1007,1008,1009,2003],'https://www.giftishow.com/brand/brandGoodsDetail.mhows?goods_seq=G00000182379&brand_no=2'));
    bonusItemInfo.push(createBonusItemInfo(1, '김혜자 돈까스 치킨 도시락', 'YouUs', 1, '/images/bonus/b01.png', [1001,1002,1003,1004,1005,1007,1008,1009,2001],'http://midoyoo.blog.me/221142850550'));
    bonusItemInfo.push(createBonusItemInfo(2, '바나나 우유', '빙그레', 1, '/images/bonus/b02.png', [1000,1001,1002,1003,1004,1007,1008,2001,2003],'http://storefarm.naver.com/gifticon/products/2109454621?NaPm=ct%3Djaqpdtk8%7Cci%3Dba21d9c4e05639be6305aace3a1163d2a2614f25%7Ctr%3Dsls%7Csn%3D194555%7Chk%3D56b6e3a1ac8c9beff291f18ba9718c690315d9d3'));
    bonusItemInfo.push(createBonusItemInfo(3, '영화 관람권 2매', '메가박스', 2, '/images/bonus/b03.png', [1002,1003,1004,1005,1006,1007,1008,2002,2004],'http://item.gmarket.co.kr/Item?goodscode=417514702'));
    bonusItemInfo.push(createBonusItemInfo(4, '뿌링클 콜라 세트', 'BHC', 2, '/images/bonus/b04.png', [1001,1004,1005,1006,1007,1008,1009,2001,2002],'http://item.gmarket.co.kr/Item?goodscode=954839147'));
    bonusItemInfo.push(createBonusItemInfo(5, '스트로베리 생크림케이크', '투썸 플레이스', 3, '/images/bonus/b05.png', [1003,1004,1007,1008,1009,2003,2002,2004,3001],'http://item.gmarket.co.kr/Item?goodscode=986999816'));
    bonusItemInfo.push(createBonusItemInfo(6, '화이트골드 320T/커피/커피믹스', '맥심', 3, '/images/bonus/b06.png', [1002,1003,1007,1008,1009,2001,2002,2003,3002],'http://item.gmarket.co.kr/Item?goodscode=1155407944'));
    bonusItemInfo.push(createBonusItemInfo(7, '섬진강 메뚜기 쌀 20KG', '농협', 4, '/images/bonus/b07.png', [1002,1003,1007,1001,1008,2002,2004,2001,3003],'http://item.gmarket.co.kr/Item?goodscode=176225394'));
    bonusItemInfo.push(createBonusItemInfo(8, '준비중', '준비중', 5, '/images/bonus/b08.png', [1000,1002,1007,1009,2003,2002,2004,3001,3002],''));
    bonusItemInfo.push(createBonusItemInfo(9, '준비중', '준비중', 6, '/images/bonus/b09.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],''));
    bonusItemInfo.push(createBonusItemInfo(10, '준비중', '준비중', 7, '/images/bonus/b10.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],''));
    bonusItemInfo.push(createBonusItemInfo(11, '설화수 자음2종 기초세트', '아모레퍼시픽', 8, '/images/bonus/b11.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://storefarm.naver.com/beauti-seller/products/2281367034?NaPm=ct%3Djaqnx13k%7Cci%3Deab2eb6de2c4d103a31f11f8cd9dcdfe0b54e4b8%7Ctr%3Dslsl%7Csn%3D601456%7Cic%3D%7Chk%3D860e6ce65a010b246bd14696aab474cefbadedec'));
    bonusItemInfo.push(createBonusItemInfo(12, '무선이어폰 슈피겐 레가토 아크', '슈피겐', 8, '/images/bonus/b12.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://storefarm.naver.com/spigen/products/2277491667?NaPm=ct%3Djaqp37m8%7Cci%3D72c3aa903ba4a53953bbcaca3a319a379e1f2934%7Ctr%3Dslc%7Csn%3D165941%7Cic%3D%7Chk%3D8c5f81b98d22b10e2df7fffb7b4912402db86058'));
    bonusItemInfo.push(createBonusItemInfo(13, 'LG전자 전자레인지 20l', 'LG전자', 9, '/images/bonus/b13.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gBCyrc5'));
    bonusItemInfo.push(createBonusItemInfo(14, '노스페이스 남성 패딩', '노스페이스', 10, '/images/bonus/b14.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://www.lotteimall.com/goods/viewGoodsDetail.lotte?goods_no=1229046386&nv_ad=pla&chl_dtl_no=2540914&chl_no=141370#'));
    bonusItemInfo.push(createBonusItemInfo(15, '노스페이스 여성 패딩', '노스페이스', 10, '/images/bonus/b15.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://www.lotte.com/goods/viewGoodsDetail.lotte?goods_no=432904898&NaPm=ct%3Djaqowoa0%7Cci%3Dc18c1ee7b17723821d24f516c258a1a313f64cc4%7Ctr%3Dligh%7Csn%3D2%7Chk%3Dde9eb6a1dd8eb40643074b5a8dc123dfbda91482'));
    bonusItemInfo.push(createBonusItemInfo(16, '핸디스틱 무선 청소기 ', 'LG ', 11, '/images/bonus/b16.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gwsgff'));
    bonusItemInfo.push(createBonusItemInfo(17, '삼성 노트북', '삼성', 12, '/images/bonus/b17.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gBGC0VK'));
    bonusItemInfo.push(createBonusItemInfo(18, 'LG게이밍노트북', 'LG', 13, '/images/bonus/b18.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=1927002122&NaPm=ct=jbi8imtk|ci=6069283808207dd4256b6f8d3000543500090c41|tr=slc|sn=17703|hk=d6de51e0dd2075a7e57b85eb0d42bfce52e8aa1e&utm_term=&utm_campaign=%B3%D7%C0%CC%B9%F6pc_%B0%A1%B0%DD%BA%F1%B1%B3%B1%E2%BA%BB&utm_source=%B3%D7%C0%CC%B9%F6_PC_PCS&utm_medium=%B0%A1%B0%DD%BA%F1%B1%B3'));
    bonusItemInfo.push(createBonusItemInfo(19, '전자 드럼 세탁기 14KG', 'LG', 14, '/images/bonus/b19.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/g7KJfn'));
    bonusItemInfo.push(createBonusItemInfo(20, '무선 청소기 V8', '다이슨', 15, '/images/bonus/b20.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gBDtXAn'));
    bonusItemInfo.push(createBonusItemInfo(21, '갤럭시 8+ 64G', '삼성', 15, '/images/bonus/b21.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=1854816191&trTypeCd=20&trCtgrNo=585021&lCtgrNo=1001429&mCtgrNo=1002721'));
    bonusItemInfo.push(createBonusItemInfo(22, 'SK-II LXP 풀 라인업 세트', 'sk2 ', 16, '/images/bonus/b22.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gBGI-4_'));
    bonusItemInfo.push(createBonusItemInfo(23, '아이폰 x', 'APPLE', 16, '/images/bonus/b23.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://www.lotteimall.com/goods/viewGoodsDetail.lotte?goods_no=1273380121&NaPm=ct=jaqm03m8|ci=0d1205dca96c236697db09545375dcb76e6aa555|tr=slsl|sn=8|hk=536cedd3a4011687dba03bd1c7611064b8f0e59f&chl_dtl_no=2540914&chl_no=141370#'));
    bonusItemInfo.push(createBonusItemInfo(24, 'DIOS 양문형 냉장고', 'LG', 16, '/images/bonus/b24.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gBCdPkx'));
    bonusItemInfo.push(createBonusItemInfo(25, '라벨루쏘 토트 백', 'GUCCI', 17, '/images/bonus/b25.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://display.cjmall.com/p/item/47659127?channelCode=30001001&k=라벨루쏘 442622 A7M0T 1000 구찌 GG&shop_id=2002112507&pic=SEAR____list__goods-001__'));
    bonusItemInfo.push(createBonusItemInfo(26, 'TV 곡면형 65인치', '삼성', 17, '/images/bonus/b26.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://gmkt.kr/gunVd2'));
    bonusItemInfo.push(createBonusItemInfo(27, '가죽 미니 탑 핸들 백', 'GUCCI', 18, '/images/bonus/b27.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'https://www.gucci.com/kr/ko/pr/women/womens-handbags/frame-print-leather-mini-top-handle-bag-p-488667DT98X9176?position=33&listName=Handbags_KR&categoryPath=Women/Womens-Handbags'));
    bonusItemInfo.push(createBonusItemInfo(28, '클래식 블랙 캐비어', 'CHANEL', 19, '/images/bonus/b28.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'http://www.11st.co.kr/product/SellerProductDetail.tmall?method=getSellerProductDetail&prdNo=1794493651&trTypeCd=21&trCtgrNo=585021&lCtgrNo=1001307&mCtgrNo=1001808'));
    bonusItemInfo.push(createBonusItemInfo(29, '2017 스토닉 자동차', '기아자동차', 20, '/images/bonus/b29.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003],'https://auto.naver.com/car/main.nhn?yearsId=119765'));






}

function createBonusItemInfo(no, publisher, name, reqLevel, iconpath, reqGachaList, link) {
    return {no: no, pub: publisher, name: name, reqLevel: reqLevel, iconpath: iconpath, reqGachaList: reqGachaList, link: link};
}

exports.getBonusItemInfo = function() {
    return bonusItemInfo;
}