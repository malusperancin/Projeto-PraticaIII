var PessoaDao = require('./HelpaDAO');
//var conexao = require('../config/custom-mssql');
const session = require('express-session');
var senhaLocal;
var emailLogado = "vazio";
var pessoaDao = new PessoaDao(conexao, emailLogado);

var sess;
var jsonSenha = 0;

function execSQL(sql, resposta) {
    global.conexao.request()
        .query(sql)
        .then(resultado => resposta.json(resultado.recordset))
        .catch(erro => resposta.json(erro));
}

module.exports = (app) => {

    app.post('/', function (req, res) { //rota para realizar a doação. Pega a quantidade, para qual entidade foi doado e qual foi a doação

        const doacao = req.body.doacao;
        const entidade = req.body.codDaEntidade;
        const qtd = req.body.quantidade;

        conexao.query(`select codigo from HPessoas where email='${emailLogado}'`, (err, result) => {
            var codLogado = result.recordset[0].codigo;
            execSQL(`Insert into HDoacoes values (${codLogado},'${doacao}',${entidade},GETUTCDATE(),'N','${qtd}')`, res);
        });
        execSQL(`UPDATE HEntidades set visualizacoes=visualizacoes+1 where codigo = '${entidade}'`, res);

        res.redirect('/#entidades');
    });


    app.get('/', function (req, res) { //rota para entrar na pági principal (primeiro contato do cliente com o site)

        sess = req.session;
        sess.email;
        sess.senha;
        sess.codigo;

        pessoaDao.lista(function (erro, resultados) {
            pessoaDao.listaDoacoes(function (erro, resultados2) {
                pessoaDao.informacoesSobreLogado(function (erro, resultados3) {
                    res.render('paginas/home', {
                        lista: resultados["recordset"],
                        listaDoacoes: resultados2["recordset"],
                        informacoesSobreLogado: resultados3["recordset"]
                    })
                })
            });
        });
    });

    app.get('/cadastro', function (req, resp) { //carrega a pagina do cadastro
        if (typeof sess.email == "undefined") { //se ainda n estiver logado, pode fazer cadastro
            resp.render("paginas/cadastro");
        }
        else {
            resp.redirect("/#"); //se ja estiver logado, vai para página inicial
        }

    });

    app.get('/pesquisar/:busc', function (req, resp) { //faz a pesquisa de entidades
        var busc = req.params.busc;
        console.log("Busca_sp '" + busc + "'");
        execSQL("Busca_sp '" + busc + "'", resp);
    });

    app.post('/cadastro', function (req, resp) { //realiza o cadastro de alguem com as informacoes nos campos dos inputs 
        const nome = req.body.nome.substring(0, 50);
        const email = req.body.email.substring(0, 50);
        const endereco = req.body.endereco.substring(0, 100);
        const cidade = req.body.cidade.substring(0, 40);
        const uf = req.body.uf.substring(0, 2);
        const senha = req.body.senha.substring(0, 15);
        const telefone = req.body.telefone.substring(0, 14);

        conexao.query(`SELECT * FROM HPessoas where email = '${email}'`, (err, result) => { //verifica se o email informado já existe no banco
            if (result.rowsAffected == 0) { //se não existe, cadastra
                execSQL(`INSERT INTO HPessoas VALUES('${nome}','${email}','${endereco}','${cidade}','${uf}','${senha}','${telefone}')`, resp);
                resp.render("paginas/login");
            } else
            {
                jsonSenha = 1;
                 resp.render("paginas/cadastro"); //se existe, recarrega a pagina do cadastro para pessoa fazer novamente
            }
              
        });
    });

    app.get('/login', function (req, resp) { //vai até a pagina de login se a pessoa ainda não estiver logada
        sess = req.session;
        if (typeof sess.email == "undefined") {
            resp.render("paginas/login");
        }
        else {
            resp.redirect("/"); //se nao envia para a pagina inicial
        }
    });

    app.post('/login', function (req, resp) { //loga a pessoa de acordo com as informacoes fornecidas 
        sess = req.session;

        emailLogado = req.body.email.substring(0, 50);
        senhaLocal = req.body.senha.substring(0, 15);

        pessoaDao = new PessoaDao(conexao, emailLogado);
        pessoaDao.buscarPorEmail(emailLogado, function (erro, resultados) {

            if (erro) {
                console.log("erro no login");
            }
            else if (resultados.recordset.length != 0 && resultados.recordset[0].senha == senhaLocal) { //verifica de o email existe e se a senha bate com a real
                sess.email = emailLogado; //se sim, faz a sessao receber as informacoes
                sess.senha = senhaLocal;
                resp.redirect("/"); //recarrega a pagina inicial
            }
            else {
                jsonSenha = 1;
                resp.redirect('/login');
            }
        });
    });

    app.get('/sair', (req, res) => { //rota para deslogar
        sess = req.session;
        if (typeof sess.email == "undefined") { //se tiver um email logado ele desloga, se nao vai para o login
            res.render("paginas/login");
        }
        else {
            req.session.destroy((err) => { //destoi a sessao
                if (err) {
                    return console.log(err);
                }
                emailLogado = "vazio";
                senhaLocal = undefined;
                pessoaDao = new PessoaDao(conexao, emailLogado); //recarrega a dao para atualizar as infos sobre o logado
                pessoaDao.lista(function (erro, resultados) {
                    pessoaDao.listaDoacoes(function (erro, resultados2) {
                        pessoaDao.informacoesSobreLogado(function (erro, resultados3) {
                            res.render('paginas/home', {
                                lista: resultados["recordset"],
                                listaDoacoes: resultados2["recordset"],
                                informacoesSobreLogado: resultados3["recordset"]
                            })
                        })
                    });
                });
            });
        }
    });

    app.get('/editarInformacoes', function (req, resp) { //abre a pagina de editar as informacoes do logado apenas se ja estiver alguem logado
        sess = req.session;
        if (typeof sess.email == "undefined") {
            resp.render("paginas/login");
        }
        else {
            pessoaDao = new PessoaDao(conexao, emailLogado);
            pessoaDao.informacoesSobreLogado(function (erro, resultados) {
                console.log(resultados);
                resp.render('paginas/editarInformacoes', {
                    informacoesSobreLogado: resultados["recordset"]
                });
            });
        }
    });

    app.post('/editarInformacoes', function (req, resp) { //envia as novas informacoes do pessoa para o banco
        const nome = req.body.nome.substring(0, 50);
        const email = req.body.email.substring(0, 50);
        const endereco = req.body.endereco.substring(0, 100);
        const cidade = req.body.cidade.substring(0, 40);
        const uf = req.body.uf.substring(0, 2);
        const telefone = req.body.telefone.substring(0, 14);

        pessoaDao = new PessoaDao(conexao, emailLogado);
        pessoaDao.buscarPorEmail(emailLogado, function (erro, resultados) {
            if (erro) {
                console.log("Erro para Atualizar");
            }
            if (emailLogado != email) { //se ela trocou o email, verifica se é valido (ainda não tem no banco de dados)
                conexao.query(`SELECT * FROM HPessoas where email = '${email}'`, (err, result) => {
                    console.log(result);
                    if (result.rowsAffected == 0) { //se não tem (ou seja, esta tudo bem), ele faz o update e volta para a pagina de consultas
                        execSQL(`update HPessoas set email='${email}',nome='${nome}',endereco='${endereco}',telefone='${telefone}',cidade='${cidade}',UF='${uf}' where email = '${emailLogado}'`, resp);
                        emailLogado = email;
                        resp.redirect('/consulta');
                    } else{//se não recarrega a de trocar as infos e exibe q o email ja existe
                        jsonSenha = 1;  
                         resp.redirect('/editarInformacoes');
                    }
                 

                });
            }
            else { //caso a pessoa não mudou o email, apenas da um update com as novas informacoes
                execSQL(`update HPessoas set nome='${nome}',endereco='${endereco}',telefone='${telefone}',cidade='${cidade}',UF='${uf}' where email = '${email}'`, resp);
                resp.redirect('/consulta');
            }
        });
    });

    app.get('/editarSenha', function (req, resp) { //abre a parte de editar senha senha
        sess = req.session;
        if (typeof sess.email == "undefined") { //verifica se ja tem alguem logado para poder carregar a pagina
            resp.render("paginas/login");
        }
        else { //carrega a pagina
            pessoaDao = new PessoaDao(conexao, emailLogado);
            pessoaDao.informacoesSobreLogado(function (erro, resultados) {
                resp.render('paginas/editarSenha', {
                    informacoesSobreLogado: resultados["recordset"]
                });
            });
        }
    });

    app.post('/editarSenha', function (req, resp) { //envia as informacoes da pessoa que quer editar a senha
        const nsenha1 = req.body.nsenha1.substring(0, 15);
        const nsenha2 = req.body.nsenha2.substring(0, 15);
        const senha = req.body.senha.substring(0, 15);
        
        pessoaDao = new PessoaDao(conexao, emailLogado);
        pessoaDao.buscarPorEmail(emailLogado, function (erro, resultados) {
            if (erro) {
                console.log("erro no login");
            }
            else if (resultados.recordset[0].senha == senha && nsenha1 == nsenha2) { //deu certo, vai para a consulta novamente
                sess.senha = nsenha1;
                senhaLocal = nsenha1;
                execSQL(`update HPessoas set senha='${nsenha1}' where email = '${emailLogado}'`, resp);
                resp.redirect('/consulta');
            }
            else {
                jsonSenha = 1; //se deu errado ele coloca 1 no json senha para falar q a senha esta incorreta
                resp.redirect('/editarSenha');
            }

        });
    });

    /*rotas para avisar q ta errada a senha ou o email*/

    app.get('/resultSenha', function (req, resp) { 
        resp.json(jsonSenha);
        jsonSenha = 0;
    })

    app.get('/resultEmailSenha', function (req, resp) {
        resp.json(jsonSenha);
        jsonSenha = 0;
    })

    
    app.get('/resultEmailCadastro', function (req, resp) {
        resp.json(jsonSenha);
        jsonSenha = 0;
    })
    
    app.get('/resultEmailAlterar', function (req, resp) {
        resp.json(jsonSenha);
        jsonSenha = 0;
    })

    /*------------------------------------------------*/

    app.get('/consulta', function (req, resp) { //abre a aba de consulta
        sess = req.session;
        if (typeof sess.email == "undefined") { //se n tiver ninguem logado ele envia para o login
            resp.render("paginas/login");
        }
        else {
            pessoaDao = new PessoaDao(conexao, emailLogado);
            pessoaDao.informacoesSobreLogado(function (erro, resultados) {
                pessoaDao.listaDeDoacoesFeitas(function (erro, resultados2) {
                    resp.render('paginas/consultaDoacoes', {
                        informacoesSobreLogado: resultados["recordset"],
                        listaDeDoacoesFeitas: resultados2["recordset"]

                    });
                });

            });
        }
    });
}

