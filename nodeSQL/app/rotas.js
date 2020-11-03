var conexao = require("../config/custom-mssql");
var unirest = require("unirest");
var sync = require("sync-request");

function execSQL(sql, resposta) {
    global.conexao
        .request()
        .query(sql)
        .then((resultado) => resposta.json(resultado.recordset))
        .catch((erro) => resposta.json(erro));
}

module.exports = (app) => {
    app.get("/api", function(req, resp) {
        var req = unirest(
            "GET",
            "https://tripadvisor1.p.rapidapi.com/locations/search"
        );

        req.query({
            location_id: "1",
            limit: "30",
            sort: "relevance",
            offset: "0",
            lang: "en_US",
            currency: "USD",
            units: "km",
            query: "pattaya",
        });

        req.headers({
            "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
            "x-rapidapi-key": "ad35bee9e9msh7565809bcd96cb0p1f9931jsn72612d74af34",
            useQueryString: true,
        });

        req.end(function(res) {
            if (res.error) throw new Error(res.error);
            resp.send(res.body.data[0].result_object);
        });
    });


    app.post('/api/usuario/post', (requisicao, resposta) => { //ADICIONA USUARIO
       // console.log(requisicao);
        const senha = requisicao.body.senha;
        const email = requisicao.body.email;
        global.conexao.query("SELECT * from Usuarios WHERE email='" + email + "'", (err, result) => {
            if (result.recordset[0])
                resposta.send(result.recordset[0]);
            else
                execSQL(`INSERT INTO Usuarios (senha, email) VALUES('${senha}','${email}')`, resposta);
        });
    });

    app.get('/api/usuario/login/:email/:senha', (requisicao, resposta) => { //PEGA O USUARIO PARA FAZER LOGIN
        global.conexao.query("SELECT * from Usuarios WHERE email='" + requisicao.params.email + "'", (err, result) => {
            if (!result.recordset[0])
                resposta.send(null);
            else
            if (requisicao.params.senha == result.recordset[0].senha)
                resposta.json(result.recordset[0]);
            else
                resposta.send(null);
        });
    });

    app.put('/api/usuario/put', function(req, resp) { //envia as novas informacoes do pessoa para o banco
        const usuario = req.body;
        const email = usuario.email;
        const celular = usuario.celular;

        //console.log(email);
        //console.log(celular);
        //console.log(usuario.id);

        conexao.query(`SELECT * FROM Usuarios where email = '${email}'`, (err, result) => {
            if (result.body == undefined)
                execSQL(`update Usuarios set email='${email}', celular='${celular}' where id=${usuario.id}`, resp);
            else
            if (result.body.id == usuario.id)
                execSQL(`update Usuarios set celular='${celular}' where id = '${usuario.id}'`, resp);
            else
                resp.send("Erro");

        });
    });

    app.put('/api/usuario/editarSenha/:senhaOld/:senhaNew', function(req, resp) { //envia as novas informacoes do pessoa para o banco
        const usuarioId = req.body.id;
        const senhaOld = req.params.senhaOld;
        const senhaNew = req.params.senhaNew;

        conexao.query(`SELECT * FROM Usuarios where id = ${usuarioId}`, (err, result) => {
            //console.log(result.recordset[0].senha);
            if (result.recordset[0].senha == senhaOld)
                execSQL(`update Usuarios set senha='${senhaNew}' where id = '${usuarioId}'`, resp);
            else
                resp.send("Erro");
        });
    });

    app.get("/", function(req, res) {
        conexao.query(`select * from Paises`, (err, result) => {
            res.send(result.recordset);
        });
    });

    app.get("/api/paises/favoritos/:id", function(req, resp) {
        var idUsuario = req.params.id;
        var paises;
        conexao.query(`select p.* from Favoritos f, Paises p where f.idUsuario=${idUsuario} and f.idPais=p.id`, (err, result) => {
            if (result.recordset[0] == undefined)
                console.log("nao tem favs");
            paises = result.recordset;
            for (var i = 0; i < paises.length; i++) {
                var res = sync(
                    "GET",
                    "https://restcountries.eu/rest/v2/name/" +
                    result.recordset[i].nome.toLowerCase()
                );
                res = JSON.parse(res.body.toString("utf-8"));
                paises[i].bandeira = res[0].flag;
                paises[i].idioma = res[0].languages[0].name;
                paises[i].continente = res[0].region;
                paises[i].moeda =
                    res[0].currencies[0].name + " (" + res[0].currencies[0].symbol + ")";
                paises[i].populacao = res[0].population;
                paises[i].clima = result.recordset[i].clima;
                paises[i].sigla = res[0].alpha3Code;
                paises[i].capital = res[0].capital;
                paises[i].ddd = res[0].callingCodes[0];
                paises[i].lat = res[0].latlng[0];
                paises[i].lng = res[0].latlng[1];
            }
            resp.send(paises);
        })
    });

    app.get("/api/atracoes/:local", function(requi, resp) {
        var req = unirest(
            "GET",
            "https://tripadvisor1.p.rapidapi.com/attractions/list"
        );

        req.query({
            location_id: requi.params.local,
        });

        req.headers({
            "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
            "x-rapidapi-key": "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
            useQueryString: true,
        });

        req.end(function(res) {
            if (res.error) throw new Error(res.error);

            resp.send(res.body.data);
        });
    });

    app.get("/api/paises", function(req, res) {
        conexao.query(`select * from paises`, (err, result) => {
            var ret = [];

            for (var i = 0; i < result.recordset.length; i++) {
                var res = sync(
                    "GET",
                    "https://restcountries.eu/rest/v2/name/" +
                    result.recordset[i].nome.toLowerCase()
                );

                res = JSON.parse(res.body.toString("utf-8"));

                ret.push({
                    id: result.recordset[i].id,
                    nome: result.recordset[i].nome,
                    bandeira: res[0].flag,
                    moeda: res[0].currencies[0].name +
                        " (" +
                        res[0].currencies[0].symbol +
                        ")",
                    idioma: res[0].languages[0].name,
                    populacao: res[0].population,
                    clima: result.recordset[i].clima,
                    religiao: result.recordset[i].religiao,
                    lgbt: result.recordset[i].lgbt,
                    sigla: res[0].alpha3Code,
                    capital: res[0].capital,
                    continente: res[0].region,
                    descricao: result.recordset[i].descricao,
                    foto: result.recordset[i].foto,
                    ddd: res[0].callingCodes[0],
                    codAPI: result.recordset[i].codAPI,
                    lat: res[0].latlng[0],
                    lng: res[0].latlng[1]
                });
            }
        });

        res.send(ret);
    });

    app.get("/api/paises/:id", function(req, resp) {
      conexao.query(`select * from paises where id = ${req.params.id}`, (err, result) => {
          var res = sync(
              "GET",
              "https://restcountries.eu/rest/v2/name/" +
              result.recordset[0].nome.toLowerCase()
          );

          res = JSON.parse(res.body.toString("utf-8"));

          ret = [{
              id: result.recordset[0].id,
              nome: result.recordset[0].nome,
              bandeira: res[0].flag,
              moeda: res[0].currencies[0].name +
                  " (" +
                  res[0].currencies[0].symbol +
                  ")",
              idioma: res[0].languages[0].name,
              populacao: res[0].population,
              clima: result.recordset[0].clima,
              religiao: result.recordset[0].religiao,
              lgbt: result.recordset[0].lgbt,
              sigla: res[0].alpha3Code,
              capital: res[0].capital,
              continente: res[0].region,
              descricao: result.recordset[0].descricao,
              foto: result.recordset[0].foto,
              ddd: res[0].callingCodes[0],
              codAPI: result.recordset[0].codAPI,
              lat: res[0].latlng[0],
              lng: res[0].latlng[0]
          }];

         // console.log(ret);

          resp.send(ret);
      });
  });

    app.get("/api/cidades/pais/:busc", function(req, res) {
        var busc = req.params.busc;
        conexao.query(
            `select * from cidades where idPais = ` + busc,
            (err, result) => {
                var ret = [];
                for (var i = 0; i < result.recordset.length; i++) {
                    var res = sync(
                        "GET",
                        "https://tripadvisor1.p.rapidapi.com/locations/search?query=" +
                        result.recordset[i].nome.toLowerCase(), {
                            headers: {
                                "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
                                "x-rapidapi-key": "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
                                useQueryString: true,
                            },
                        }
                    );

                    res = JSON.parse(res.body.toString("utf-8")).data[0].result_object;
                    // console.log(res);
                    ret.push({
                        id: result.recordset[i].id,
                        nome: result.recordset[i].nome,
                        codAPI: res.location_id,
                        descricao: res.geo_description,
                        foto: res.photo.images.original.url,
                        idPais: result.recordset[i].idPais,
                        estado: res.location_string
                    });

                    //console.log(ret[i]);
                }
            }
        );
    });

    app.get("/api/pais/atracoes/:busc", function(req, res) {
        var busc = req.params.busc;
        var res = sync(
            "GET",
            "https://tripadvisor1.p.rapidapi.com/attractions/list?location_id=" +
            busc, {
                headers: {
                    "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
                    "x-rapidapi-key": "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
                    useQueryString: true,
                },
            }
        );
        var ret = [];
        res = JSON.parse(res.body.toString("utf-8"))
        for (var i = 0; i < 5; i++) {
            linha = res.data[i];
            ret.push({
                nome: linha.name,
                url: linha.web_url,
                foto: linha.photo.images.original.url
            });
        }
      //  console.log(ret);
        return ret;
    });

    app.get("/api/pais/atra/:busc", function(req, res) {
        var busc = req.params.busc;

        var res = sync(
            "GET",
            "https://tripadvisor1.p.rapidapi.com/attractions/list?location_id=" +
            busc, {
                headers: {
                    "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
                    "x-rapidapi-key": "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
                    useQueryString: true,
                },
            }
        );
        var ret = [];
        res = JSON.parse(res.body.toString("utf-8"))
        for (var i = 0; i < 5; i++) {
            linha = res.data[i];
            ret.push({
                nome: linha.name,
                url: linha.web_url,
                foto: linha.photo.images.original.url
            });
        }
        //console.log(ret);
        return ret;
    });

    app.get("/api/paises/continente/:nome", function(req, resp) {
      var cont = req.params.nome.substring(0, 2).toUpperCase(); 
      var continente = req.params.nome;
      conexao.query(`select * from paises where continente ='${cont}'`, (err, result) => {
        var ret = [];

        for (var i = 0; i < result.recordset.length; i++) {
            var res = sync(
                "GET",
                "https://restcountries.eu/rest/v2/name/" +
                result.recordset[i].nome
            );

            res = JSON.parse(res.body.toString("utf-8"));

            ret.push({
                id: result.recordset[i].id,
                nome: result.recordset[i].nome,
                bandeira: res[0].flag,
                moeda: res[0].currencies[0].name +
                    " (" +
                    res[0].currencies[0].symbol +
                    ")",
                idioma: res[0].languages[0].name,
                populacao: res[0].population,
                clima: result.recordset[i].clima,
                religiao: result.recordset[i].religiao,
                lgbt: result.recordset[i].lgbt,
                sigla: res[0].alpha3Code,
                capital: res[0].capital,
                continente: res[0].region,
                descricao: result.recordset[i].descricao,
                foto: result.recordset[i].foto,
                ddd: res[0].callingCodes[0],
                codAPI: result.recordset[i].codAPI,
                lat: res[0].latlng[0],
                lng: res[0].latlng[1]
            });
        }
        resp.send(ret);
    });
  });

  app.delete('/api/usuario/desfavoritar/:idUsuario/:idPais', (requisicao, resposta) => { 
    const idUsuario = requisicao.params.idUsuario;
    const idPais = requisicao.params.idPais;
    execSQL(`delete from Favoritos where idUsuario = ${idUsuario} and idPais = ${idPais}`, resposta);
});

app.get("/api/paises/favoritos/checar/:idPais/:idUsuario", function(req, resp) {
    const idUsuario = req.params.idUsuario;
    const idPais = req.params.idPais;
    console.log(idUsuario);
    console.log(idPais);
    conexao.query(`select * from favoritos where idUsuario =${idUsuario} and idPais = ${idPais}`, (err, result) => {
        if(result.rowsAffected[0] == 1)
        {
            console.log("deu true");
             resp.send(true);
        }
        else 
            resp.send(false);
  });
});

app.post('/api/usuario/favoritar/:idUsuario/:idPais', (requisicao, resposta) => { 
    const idUsuario = requisicao.params.idUsuario;
    const idPais = requisicao.params.idPais;
    execSQL(`insert into Favoritos values(${idUsuario}, ${idPais})`, resposta);
});

  }
   