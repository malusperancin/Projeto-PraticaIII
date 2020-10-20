var express = require('express')
var app = express();
const session = require('express-session');

const cors = require('cors');
var bodyParser = require('body-parser');


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());
app.use(session({secret: 'teste123',saveUninitialized: true,resave: true}));

app.use(express.static('public'));

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});


var rotas = require('../app/rotas.js')

rotas(app);

module.exports = app;




