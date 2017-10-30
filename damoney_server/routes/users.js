var express = require('express');
var router = express.Router();

var dbhelper = require('../database/dbhelper');

/* GET users listing. */
router.get('/', function(req, res, next) {
  //dbhelper.auth(req.body.token);
});

module.exports = router;
