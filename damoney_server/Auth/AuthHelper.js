/**
 * Created by nnnyy on 2017-10-31.
 */
var jwt = require('jsonwebtoken');

exports.makeToken = function(id) {
    return jwt.sign({_id: id}, 'damoneysecret', {expiresIn: 60 * 60 * 24});
}

exports.auth = function(req,res, next) {
    var token = req.query.token;
    if(token == undefined) {
        next();
        return;
    }

    if(token == '') {
        res.send({ret: -99, msg: "token"});
        return;
    }
    jwt.verify(token, 'damoneysecret', function(err, decoded) {
        if(err != null) {
            res.send({ret: -99, msg: err})
            return;
        }
        req.decoded = decoded;
        next();
    })
};