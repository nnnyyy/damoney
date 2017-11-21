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
    //  ����� ������ ���� �߿� �ϳ��� �����.
    serialmaker.getSerial(function(err, output) {
        //  �ϴ� ������ �ø���� �߱��Ѵ�.
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

//  ����� ������ ���� ���� ��
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