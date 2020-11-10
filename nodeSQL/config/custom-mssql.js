var mssql = require('mssql');
const config = {
user: 'BD19173',
password: '36101922',
server: 'regulus.cotuca.unicamp.br',
database: 'viaxar'
};
mssql.connect(config)
.then(conexao => global.conexao = conexao)
.catch(erro => console.log(erro));
module.exports = mssql;

