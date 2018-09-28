let http = require("http");
let express = require("express");
let app = express();
let mysql = require("mysql");
let bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

let serial = 0;

let pool = mysql.createPool({
    connectionLimit: 20,
    host: 'localhost',
    user: 'root',
    password: 'sks9331509',
    database: 'amugja',
    debug: false
});

app.get('/', function (req, res) {
    res.sendStatus(200);
});

app.get('/foods', function (req, res) {
    console.log('음식 리스트 검색');

    let item = req.param('item');

    pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
                throw err;
            }
        }

        conn.query('SELECT * FROM food as f LEFT JOIN keyword as k ON f.serial=k.serial WHERE f.name like "%' + item + '%" || k.word=?', [item], function (err, rows) {
            if (err) {
                res.sendStatus(400);
            } else if (rows.length !== 0) {
                console.log(rows);
                res.setHeader('Context-type', 'Application/json');
                res.send(rows);
            } else {
                res.sendStatus(404);
            }
        })
    })
});

app.post('/likes', function (req, res) {
    console.log('음식 좋아요');

    let serial = req.body.item;

    pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
                throw err;
            }
        }

        conn.query('UPDATE food as f SET f.like = f.like + 1 WHERE f.serial = ?', [serial], function (err) {
            if (err) {
                res.sendStatus(400);
            } else {
                res.sendStatus(200);
            }
        })

    })
});

app.get('/foods/detail', function (req, res) {
    let serial = req.param('serial');

    pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
        }

        conn.query('SELECT * FROM food WHERE serial=?', [serial], function (err, foods) {
            if (err) {
                res.sendStatus(400);
            } else if (foods.length > 0) {
                conn.query('SELECT word FROM keyword WHERE serial=?', [serial], function (err, keywords) {
                    if (err) {
                        res.sendStatus(400);
                    } else if (keywords.length !== 0) {
                        res.setHeader('Context-type', 'Application/json');
                        res.send({
                            foods,
                            keywords
                        })
                    }
                })
            } else {
                res.sendStatus(404);
            }
        })
    })
});

/*app.get('/insert/food',function (req,res) {
    let name = req.param("name");
    let context = req.param("context");

    pool.getConnection(function (err,conn) {
        conn.query('INSERT INTO food (serial, name, context) VALUES(?,?,?)',[serial,name,context],function (err) {
            if(err){
                res.sendStatus(400);
                throw err;
            }else{
                serial++;
                res.sendStatus(200);
            }
        });
    })
});*/

/*app.get('/insert/keyword',function (req,res) {
    let name = req.param("name");
    let keyword = req.param("keyword");

    pool.getConnection(function (err,conn) {
        conn.query('SELECT serial FROM food WHERE name=?',[name],function (err,rows) {
            if(err){
                res.sendStatus(400);
                throw err;
            }else if(rows.length !== 0){
                let serial = rows[0].serial;
                console.log(serial);
                conn.query('INSERT INTO keyword (serial, word) VALUES(?,?)',[serial,keyword],function (err) {
                    if(err){
                        res.sendStatus(400);
                        throw err;
                    }else{
                        res.sendStatus(200);
                    }
                })
            }

        });
    })
});*/

let PORT = process.env.PORT || 2207;
let localhost = "192.168.1.46";
http.createServer(app).listen(PORT, localhost, function () {
    console.log("Create Server, Port is " + PORT);
});