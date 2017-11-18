/**
 * Created by nnnyy on 2017-11-18.
 */
var dbhelper = require('./database/dbhelper');

var startSn = 0;


exports.init = function() {
    dbhelper.getSN(function(ret) {
        if(ret == 0) {
            startSn = ret.sn;
            return true;
        }
        else return false;
    });
}