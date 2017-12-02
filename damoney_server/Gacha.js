/**
 * Created by nnnyy on 2017-11-19.
 */
var dbhelper = require('./database/dbhelper');
var rn = require('random-number');

var probsum = 0;
var gachalist;

exports.init = function(cb) {
    dbhelper.loadGachaList(function(ret) {
        gachalist = ret.list;

        for(var i = 0 ; i < gachalist.length ; ++i) {
            probsum += gachalist[i].droprate;
        }
        cb(ret.ret);
    });
}

exports.getGachaList = function() {
    return gachalist;
}

exports.getGacha = function() {

    var temp = probsum;
    var selected;
    for(var i = 0 ; i < gachalist.length ; ++i) {
        console.log(temp);
        var rnd = rn.generator({
            min: 0,
            max: temp,
            integer: true
        })();

        if(gachalist[i].droprate > rnd) {
            selected = gachalist[i];
            break;
        }

        temp -= gachalist[i].droprate;
    }

    return selected;
}