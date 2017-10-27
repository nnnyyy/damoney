var express = require('express');
var router = express.Router();

var db = require('../database/database').db();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/signup', function(req, res, next) {
  console.log(req.body['id']);
  res.send({ret:0});
});

module.exports = router;
