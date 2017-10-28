/**
 * Created by nnnyy on 2017-10-28.
 */
var dbpool = require('./database').db();

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

        cb({ret:0, val: rows[0][0].cnt});
    });
}