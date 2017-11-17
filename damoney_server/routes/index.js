var express = require('express');
var router = express.Router();

var dbhelper = require('../database/dbhelper');
var authhelper = require('../Auth/AuthHelper');
var ads = require('../Ads/Ads');

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

router.use(authhelper.auth);

// 로그인
// 토큰 발급을 해줘야 한다.
router.post('/signin', function(req,res, next) {
  var id = req.body['id'];
  var pw = req.body['pw'];
  dbhelper.loginAccount(id,pw, function(ret) {
    res.send(ret);
  });
});

router.get('/auth', function(req, res) {
  res.send({ret: 0, msg: 'auth complete'})
});

router.get('/getpoint', function(req, res) {
  dbhelper.getPoint(req.decoded._id, function(ret) {
    res.send(ret);
  })
})

router.get('/get/premiumlist', function(req, res) {
  var id = req.decoded._id;
  dbhelper.getPremiumList(id, function(ret) {
    res.send(ret);
  })
})

router.get('/viewad', function(req, res) {
  var id = req.decoded._id;
  var sn = req.query.sn;
  dbhelper.viewAd(id, sn, function(ret) {
    res.send(ret);
  })
})

router.get('/ads', ads.getAds);

module.exports = router;
