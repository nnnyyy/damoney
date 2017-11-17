/**
 * Created by nnnyyy-dev on 2014-06-19.
 */
/**
 * Created by nnnyyy-dev on 2014-06-19.
 */
var mysql = require('mysql');
var database = {};
var pool;

database.init = function() {
    pool = mysql.createPool({
        connectionLimit : 10,
        host     : 'localhost',
        user     : 'damoney',
        password : 'ekajslqlqjs',
        database : 'damoneydb',
        multipleStatements: true
    });
}

exports.db = function() {
    if(!pool) {
        database.init();
        return pool;
    }
    else {
        return pool;
    }
}