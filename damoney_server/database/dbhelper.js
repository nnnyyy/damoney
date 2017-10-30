/**
 * Created by nnnyy on 2017-10-28.
 */
var dbpool = require('./database').db();
var jwt = require('jsonwebtoken');

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

        var token = jwt.sign({_id: id}, 'damoneysecret', {expiresIn: 5 * 60});

        cb({ret:0, access_token: token});
    });
}