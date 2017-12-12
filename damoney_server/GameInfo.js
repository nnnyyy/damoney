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
    bonusItemInfo.push(createBonusItemInfo(0, '맥심 모카커피', '맥심', 1, '/images/bonus/b1.png', [1000,1001,1002,1003,1004,1007,1008,1009,2003]));
    bonusItemInfo.push(createBonusItemInfo(1, '김혜자 돈까스 치킨 도시락', 'YouUs', 1, '/images/bonus/b2.png', [1001,1002,1003,1004,1005,1007,1008,1009,2001]));
    bonusItemInfo.push(createBonusItemInfo(2, '바나나 우유', '빙그레', 1, '/images/bonus/b3.png', [1000,1001,1002,1003,1004,1007,1008,2001,2003]));
    bonusItemInfo.push(createBonusItemInfo(3, '영화 관람권 2매', '메가박스', 2, '/images/bonus/b4.png', [1002,1003,1004,1005,1006,1007,1008,2002,2004]));
    bonusItemInfo.push(createBonusItemInfo(4, '뿌링클 콜라 세트', 'BHC', 2, '/images/bonus/b5.png', [1001,1004,1005,1006,1007,1008,1009,2001,2002]));
    bonusItemInfo.push(createBonusItemInfo(5, '스트로베리 생크림케이크', '투썸 플레이스', 3, '/images/bonus/b6.png', [1003,1004,1007,1008,1009,2003,2002,2004,3001]));
    bonusItemInfo.push(createBonusItemInfo(6, '화이트골드 320T/커피/커피믹스', '맥심', 3, '/images/bonus/b7.png', [1002,1003,1007,1008,1009,2001,2002,2003,3002]));
    bonusItemInfo.push(createBonusItemInfo(7, '섬진강 메뚜기 쌀 20KG', '농협', 4, '/images/bonus/b8.png', [1002,1003,1007,1001,1008,2002,2004,2001,3003]));
    bonusItemInfo.push(createBonusItemInfo(8, '준비중', '준비중', 5, '/images/bonus/b9.png', [1000,1002,1007,1009,2003,2002,2004,3001,3002]));
    bonusItemInfo.push(createBonusItemInfo(9, '준비중', '준비중', 6, '/images/bonus/b10.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(10, '준비중', '준비중', 7, '/images/bonus/b11.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(11, '설화수 자음2종 기초세트', '아모레퍼시픽', 8, '/images/bonus/b12.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(12, '무선이어폰 슈피겐 레가토 아크', '슈피겐', 8, '/images/bonus/b13.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(13, 'LG전자 전자레인지 20l', 'LG전자', 9, '/images/bonus/b14.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(14, '노스페이스 남성 패딩', '노스페이스', 10, '/images/bonus/b15.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(15, '노스페이스 여성 패딩', '노스페이스', 10, '/images/bonus/b16.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(16, '핸디스틱 무선 청소기 ', 'LG ', 11, '/images/bonus/b17.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(17, '삼성 노트북', '삼성', 12, '/images/bonus/b18.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(18, 'LG게이밍노트북', 'LG', 13, '/images/bonus/b19.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(19, '전자 드럼 세탁기 14KG', 'LG', 14, '/images/bonus/b20.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(20, '무선 청소기 V8', '다이슨', 15, '/images/bonus/b21.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(21, '갤럭시 8+ 64G', '삼성', 15, '/images/bonus/b22.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(22, 'SK-II LXP 풀 라인업 세트', 'sk2 ', 16, '/images/bonus/b23.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(23, '아이폰 x', 'APPLE', 16, '/images/bonus/b24.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(24, 'DIOS 양문형 냉장고', 'LG', 16, '/images/bonus/b25.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(25, '라벨루쏘 토트 백', 'GUCCI', 17, '/images/bonus/b26.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(26, 'TV 곡면형 65인치', '삼성', 17, '/images/bonus/b27.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(27, '가죽 미니 탑 핸들 백', 'GUCCI', 18, '/images/bonus/b28.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(28, '클래식 블랙 캐비어', 'CHANEL', 19, '/images/bonus/b29.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
    bonusItemInfo.push(createBonusItemInfo(29, '2017 스토닉 자동차', '기아자동차', 20, '/images/bonus/b30.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));



}

function createBonusItemInfo(no, publisher, name, reqLevel, iconpath, reqGachaList) {
    return {no: no, pub: publisher, name: name, reqLevel: reqLevel, iconpath: iconpath, reqGachaList: reqGachaList};
}

exports.getBonusItemInfo = function() {
    return bonusItemInfo;
}