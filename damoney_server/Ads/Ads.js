/**
 * Created by nnnyy on 2017-10-31.
 */
var dbhelper = require('../database/dbhelper');
var serialmaker = require('node-serial-key');

exports.getAds = function(req, res) {
    var serial = req.query.adserial;
    res.send({ret:0, adpath:'/testads.mp4'});
}

exports.viewAd = function(req, res) {
    var id = req.decoded._id;
    var sn = req.query.sn;
    dbhelper.viewAd(id, sn, function(ret) {
        res.send(ret);
    })
};

var makeMainAds = function(cb) {
    //  사용자 반응성 광고 중에 하나를 만든다.
    serialmaker.getSerial(function(err, output) {
        //  일단 동일한 시리얼로 발급한다.
        var serial = serialmaker.generateSerial(output, 'damoneysecret');
        cb({ret:0, serial: serial});
    });
}

exports.getMainAd = function(req, res) {
    var id = req.decoded._id;
    console.log("getMainAd");
    makeMainAds(function(result) {
        res.send(result);
    });
}

//  사용자 반응성 광고를 봤을 때
exports.viewMainAd = function(req, res) {
    var id = req.decoded._id;
    var sn = req.query.sn;

    serialmaker.getSerial(function(err, output) {
        if(!serialmaker.isValid(sn, 'damoneysecret')) {
            res.send({ret: -1});
            return;
        }
        dbhelper.viewMainAd(id, function(ret) {
            res.send(ret);
        })
    });

};