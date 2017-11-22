/**
 * Created by nnnyy on 2017-10-28.
 */
var dbpool = require('./database').db();
var authHelper = require('../Auth/AuthHelper');
var gacha = require('../Gacha');
var gameinfo = require('../GameInfo');
var async = require('async');

// 계정 생성
exports.createAccount = function(id, pw, nick, cb) {
    dbpool.query('CALL CreateAccount(?,?,?,?)',[id, pw,nick,0], function(err,rows,fields){
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        cb({ret:0});
    });
}

// 로그인
exports.loginAccount = function(id, pw, cb) {
    dbpool.query('CALL LoginAccount(?,?)',[id, pw], function(err,rows,fields){
        if(err) {
            console.log("LoginAccount Failed : " + err);
            cb({ret:-1, err:err});
            return;
        }

        if(rows[0][0].cnt == 0) {
            cb({ret:-99});
            return;
        }

        var curtime = new Date();
        var token = authHelper.makeToken(id);

        cb({ret:0, access_token: token});
    });
}

exports.getInfo = function(id, cb) {
    dbpool.query('CALL GetInfo(?)',[id], function(err,rows,fields){
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        if(rows[0] && rows[0].length == 0) {
            console.log("getPoint Failed : not exist rows - " + id);
            cb({ret:-99});
            return;
        }

        var result = rows[0][0];
        var levelinfo = gameinfo.getLevelInfo(result.exp);
        console.log(result);

        cb({ret:0, point: result.point, gacha: result.gacha_cnt, level: levelinfo.level, expMax: levelinfo.nextExpMax, curExp: levelinfo.curExp });
    });
}

exports.getPremiumList = function(id, cb) {
    dbpool.query('CALL GetPremiumList(?)', [id], function(err,rows,fields) {
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        var aData = rows[0];
        var result = [];
        for(var i = 0 ; i < aData.length ; ++i) {
            var data = aData[i];
            result.push({
                sn: data.sn,
                name: data.name,
                iconpath: data.iconpath,
                type: data.type,
                reward: data.reward,
                link: data.link,
                desc: data.desc
            });
        }

        cb({ret:0, list:result});
    });
}

exports.getCouponList = function(id, cb) {
    dbpool.query('CALL GetCouponList(?)', [id], function(err,rows,fields) {
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        var aData = rows[0];
        var result = [];
        for(var i = 0 ; i < aData.length ; ++i) {
            var data = aData[i];
            result.push({
                sn: data.sn,
                name: data.name,
                iconpath: data.iconpath,
                type: data.type,
                reward: data.reward,
                link: data.link,
                desc: data.desc
            });
        }

        console.log(rows);

        cb({ret:0, list:result});
    });
}

exports.getItemList = function(type, cb) {
    dbpool.query('select * from items where type = ?', [type], function(err,rows,fields) {
        if (err) {
            cb({ret: -1, err: err});
            return;
        }

        var list = [];
        for(var i = 0 ; i < rows.length ; ++i) {
            list.push({
                sn: rows[i].sn,
                type: rows[i].type,
                publisher: rows[i].publisher,
                name: rows[i].name,
                iconpath: rows[i].iconpath,
                price: rows[i].price,
                regdate: rows[i].regdate,
                enddate: rows[i].enddate,
            });
        }

        cb({ret: 0, list: list});
    });
}

exports.getBuyList = function(id, cb) {
    dbpool.query('select * from useritems a, items b where a.listsn = b.sn and id = ?', [id], function(err,rows,fields) {
        if (err) {
            cb({ret: -1, err: err});
            return;
        }

        var list = [];
        for(var i = 0 ; i < rows.length ; ++i) {
            list.push({
                sn: rows[i].sn,
                type: rows[i].type,
                publisher: rows[i].publisher,
                name: rows[i].name,
                iconpath: rows[i].iconpath,
                price: rows[i].price,
                regdate: rows[i].regdate,
                enddate: rows[i].enddate,
            });
        }

        cb({ret: 0, list: list});
    });
}

exports.getMyGacha = function(id, cb) {
    dbpool.query('SELECT * FROM usergacha a, gachalist b where a.itemid = b.itemid and id = ?', [id], function(err,rows,fields) {
        if (err) {
            cb({ret: -1, err: err});
            return;
        }

        var list = [];
        for(var i = 0 ; i < rows.length ; ++i) {
            list.push({
                name: rows[i].name,
                iconpath: rows[i].iconpath,
                level: rows[i].level,
                grade: rows[i].grade,
                regdate: rows[i].regdate,
            });
        }

        cb({ret: 0, list: list});
    });
}

exports.viewAd = function(id, sn, cb) {
    dbpool.query('CALL ViewAd(?,?,@ret, @incExp); select @ret, @incExp;', [id,sn], function(err,rows,fields) {
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        var ret = rows[rows.length - 1][0]['@ret'];
        var incExp = rows[rows.length - 1][0]['@incExp'];

        cb({ret: ret, incExp: incExp});
    })
}

exports.viewMainAd = function(id, cb) {
    dbpool.query('CALL ViewMainAd(?,@ret, @incExp); select @ret, @incExp;', [id], function(err,rows,fields) {
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        var ret = rows[rows.length - 1][0]['@ret'];
        var incExp = rows[rows.length - 1][0]['@incExp'];
        cb({ret: ret, incExp: incExp});
    })
}

exports.buyItem = function(id, itemsn, cb) {
    // 가챠 종류와 일반 아이템 종류를 구분하자.
    dbpool.query('CALL  BuyItem(?,?,@ret); select @ret;', [id, itemsn], function(err, rows) {
        if(err) {
            cb({ret: -99});
            return;
        }

        console.log(rows);
        var ret = rows[rows.length - 1][0]['@ret'];
        console.log("buyitem : " + ret);
        cb({ret: ret});
    });
}


exports.getSN = function(cb) {
    // 아이템 sn
    dbpool.query('select sn from itemsn where idx = 0', function(err,rows,fields) {
        if (err) {
            cb({ret: -1, err: err});
            return;
        }

        cb({ret: 0, sn: rows[0].sn});
    });
}

exports.loadGachaList = function(cb) {
    dbpool.query('select * from gachalist', function(err, rows) {
        if(err) {
            cb({ret: -1});
            return;
        }

        var gachalist = [];
        for(var i = 0 ; i < rows.length ; ++i) {
            gachalist.push({
                id: rows[i].itemid,
                name: rows[i].name,
                iconpath: rows[i].iconpath,
                price: rows[i].price,
                droprate: rows[i].droprate,
                level: rows[i].level,
                grade: rows[i].grade
            });
        }

        cb({ret: 0, list: gachalist});
    })
}

exports.useGacha = function(id, cb) {
    var tasks = [
        function(callback) {
            dbpool.query('CALL UseGachaPoint(?, @ret); select @ret;', [id], function(err, rows) {
                if(err) {
                    return callback({ret: -1});
                }

                var ret = rows[rows.length - 1][0]['@ret'];
                var selected = {};
                if(ret == 0) {
                    selected = gacha.getGacha();
                }

                callback(null, {ret: ret, gachainfo: selected});
            })
        },
        function(data, callback) {
            console.log(data);
            dbpool.query('insert into usergacha (id, itemid, regdate) values (?,?,now())', [id, data.gachainfo.id], function (err, rows) {
                if (err) {
                    console.log(err);
                    return callback({ret: -2});
                }

                console.log("userGacha - insert success");
                callback(null, data);
            });
        },
        function(data, callback) {
            cb(data);
        }
    ];

    async.waterfall(tasks, function(err) {
        console.log("waterfall end");
        if(err) {
            cb(err);
        }
    });
}