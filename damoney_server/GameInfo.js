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
    bonusItemInfo.push(createBonusItemInfo(0, '던킨도너츠', '먼치킨 박스', 1, '/bonus/test.png', [1000,1001,1002,1003,1004,1007,1008,1009,2003]));
    bonusItemInfo.push(createBonusItemInfo(1, '아리따움', '아리따움 3천원권', 1, '/bonus/test.png', [1001,1002,1003,1004,1005,1007,1008,1009,2001]));
    bonusItemInfo.push(createBonusItemInfo(2, 'belif', '수분 크림', 4, '/bonus/test.png', [1000,1001,1002,1003,1004,1007,1008,2001,2003]));
    bonusItemInfo.push(createBonusItemInfo(3, '샤넬', '립스틱 루즈코코', 4, '/bonus/test.png', [1002,1003,1004,1005,1006,1007,1008,2002,2004]));
    bonusItemInfo.push(createBonusItemInfo(4, '도미노 피자', '포테이토M + 콜라1.25L', 4, '/bonus/test.png', [1001,1004,1005,1006,1007,1008,1009,2001,2002]));
    bonusItemInfo.push(createBonusItemInfo(5, '몽블랑', '남성 반지갑', 7, '/bonus/test.png', [1003,1004,1007,1008,1009,2003,2002,2004,3001]));
    bonusItemInfo.push(createBonusItemInfo(6, 'LG', '24인치 모니터', 7, '/bonus/test.png', [1002,1003,1007,1008,1009,2001,2002,2003,3002]));
    bonusItemInfo.push(createBonusItemInfo(7, 'SONY', '플레이스테이션 슬림 1TB', 7, '/bonus/test.png', [1002,1003,1007,1001,1008,2002,2004,2001,3003]));
    bonusItemInfo.push(createBonusItemInfo(8, '샤넬', '17FW 캐비어 백', 10, '/bonus/test.png', [1000,1002,1007,1009,2003,2002,2004,3001,3002]));
    bonusItemInfo.push(createBonusItemInfo(9, 'BMW', '미니 쿠퍼', 10, '/bonus/test.png', [1001,1004,1008,2003,2002,2004,3001,3002,3003]));
}

function createBonusItemInfo(no, publisher, name, reqLevel, iconpath, reqGachaList) {
    return {no: no, pub: publisher, name: name, reqLevel: reqLevel, iconpath: iconpath, reqGachaList: reqGachaList};
}
