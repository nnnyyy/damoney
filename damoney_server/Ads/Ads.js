/**
 * Created by nnnyy on 2017-10-31.
 */
var dbpool = require('../database/database').db();

exports.getAds = function(req, res) {
    var serial = req.query.adserial;
    res.send({ret:0, adpath:'/testads.mp4'});
}