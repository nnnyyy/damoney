/**
 * Created by nnnyy on 2017-10-31.
 */

var jwt = require('jsonwebtoken');

exports.auth = function(req,res, next) {
    var token = req.query.token;
    if(token == undefined || token == '') {
        res.send({ret: -98, msg: 'token argument is empty'});
        return;
    }
    jwt.verify(token, 'damoneysecret', function(err, decoded) {
        if(err != null) {
            res.send({ret: -99, msg: err})
            return;
        }
        next();
    })
};