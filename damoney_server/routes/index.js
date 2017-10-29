var express = require('express');
var router = express.Router();

var dbhelper = require('../database/dbhelper');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

// 가입
router.post('/signup', function(req, res, next) {
  var id = req.body['id'];
  var pw = req.body['pw'];
  var nick = req.body['nick'];
  dbhelper.createAccount(id,pw,nick, function(ret){
    res.send(ret);
  });
});

// 로그인
// 토큰 발급을 해줘야 한다.
router.post('/signin', function(req,res, next) {
  var id = req.body['id'];
  var pw = req.body['pw'];
  dbhelper.loginAccount(id,pw, function(ret) {
    res.send(ret);
  });
});

module.exports = router;
