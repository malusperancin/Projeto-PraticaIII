var express = require('express')
var app = express();
const session = require('express-session');

var bodyParser = require('body-parser');
var expressLayouts = require('express-ejs-layouts');

app.set('view engine', 'ejs');

app.use(expressLayouts);
app.use(bodyParser.urlencoded({extended: true}));
app.use(session({secret: 'teste123',saveUninitialized: true,resave: true}));

app.use(express.static('public'));

var rotas = require('../app/rotas.js')

rotas(app);

module.exports = app;




