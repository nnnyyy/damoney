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
  var id = req.decoded._id;
  res.send({ret: 0, token: authhelper.makeToken(id)})
});

router.get('/getinfo', function(req, res) {
  dbhelper.getInfo(req.decoded._id, function(ret) {
    res.send(ret);
  })
})

router.get('/get/premiumlist', function(req, res) {
  var id = req.decoded._id;
  dbhelper.getPremiumList(id, function(ret) {
    res.send(ret);
  })
})

router.get('/get/couponlist', function(req, res) {
  var id = req.decoded._id;
  dbhelper.getCouponList(id, function(ret) {
    res.send(ret);
  })
})

router.get('/get/itemlist', function(req, res) {
  var id = req.decoded._id;
  var type = req.query.type;
  dbhelper.getItemList(type, function(ret) {
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

router.get('/buy', function(req, res) {
  var id = req.decoded._id;
  var itemsn = req.query.itemsn;
  dbhelper.buyItem(id, itemsn, function(ret) {
    res.send(ret);
  })
})

router.get('/useGacha', function(req, res) {
  var id = req.decoded._id;
  dbhelper.useGacha(id, function(ret) {
    res.send(ret);
  })
})

router.get('/ads', ads.getAds);

module.exports = router;
