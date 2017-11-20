/**
 * Created by nnnyy on 2017-11-20.
 */
var exptable = [
    {exp: 0},   // 0
    {exp: 32},  // 1->2
    {exp: 70},  // 2->3
    {exp: 118},
    {exp: 180},
    {exp: 260},
    {exp: 362},
    {exp: 490},
    {exp: 648},
    {exp: 840},
    {exp: 1070},
    {exp: 1342},
    {exp: 1660},
    {exp: 2028},
    {exp: 2450},
    {exp: 2930},
    {exp: 3472},
    {exp: 4080},
    {exp: 4758},
    {exp: 5510},
    {exp: 6340},
    {exp: 7252},
    {exp: 8250},
    {exp: 9338},
    {exp: 10520},
    {exp: 11800},
    {exp: 13182},
    {exp: 14670},
    {exp: 16268},
    {exp: 17980},
    {exp: 19810},
];

exports.getLevelInfo = function(exp) {
    var level = 0;
    var nextExpMax = 0;
    var curExp = 0

    console.log(exp);

    for(var i = 0 ; i < exptable.length ; ++i) {
        if( exp < exptable[i].exp) {
            console.log(i);
            level = i;
            nextExpMax = exptable[i].exp - exptable[i-1].exp;
            curExp = exp - exptable[i-1].exp;
            return {level: level, nextExpMax: nextExpMax, curExp: curExp};
        }
    }
    return {level: 99, nextExpMax: 0, curExp: 0};
}