/**
 * Created by nnnyy on 2017-10-28.
 */
var dbpool = require('./database').db();
var authHelper = require('../Auth/AuthHelper');

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

exports.getPoint = function(id, cb) {
    dbpool.query('CALL GetPoint(?)',[id], function(err,rows,fields){
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        if(rows[0] && rows[0].length == 0) {
            console.log("getPoint Failed : not exist rows - " + id);
            cb({ret:-99});
            return;
        }
        console.log("getPoint Success : " + rows[0][0].id);

        cb({ret:0, point: rows[0][0].point});
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

        console.log(result);

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

exports.viewAd = function(id, sn, cb) {
    dbpool.query('CALL ViewAd(?,?,@ret); select @ret;', [id,sn], function(err,rows,fields) {
        if(err) {
            cb({ret:-1, err:err});
            return;
        }

        var ret = rows[3][0]['@ret'];

        cb({ret: ret});
    })
}