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
  app.get("/api", function (req, res) {
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

    req.end(function (res) {
      if (res.error) throw new Error(res.error);
      console.log(res.body.data[0].result_object);
    });
  });

  app.get("/paises", function (req, res) {
    var req = unirest("GET", "https://restcountries.eu/rest/v2/all");
    req.end(function (res) {
      if (res.error) throw new Error(res.error);

      console.log(res.body);
    });
  });

  app.get("/", function (req, res) {
    conexao.query(`select * from Paises`, (err, result) => {
      console.log(result);
    });
  });

  app.get("/atracao", function (req, res) {
    var req = unirest(
      "GET",
      "https://tripadvisor1.p.rapidapi.com/attractions/list"
    );

    req.query({
      location_id: "303631",
    });

    req.headers({
      "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
      "x-rapidapi-key": "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
      useQueryString: true,
    });

    req.end(function (res) {
      if (res.error) throw new Error(res.error);

      console.log(res.body.data[0]);
    });
  });

  app.get("/pais", function (req, res) {
    conexao.query(`select * from paises`, (err, result) => {
      var ret = [];

      for (var i = 0; i < result.recordset.length; i++) {
        // console.log(result.recordset[i].nome);
        var res = sync(
          "GET",
          "https://restcountries.eu/rest/v2/name/" +
            result.recordset[i].nome.toLowerCase()
        );

        res = JSON.parse(res.body.toString("utf-8"));

        // console.log(res);

        ret.push({
          id: result.recordset[i].id,
          codAPI: result.recordset[i].codAPI,
          nome: result.recordset[i].nome,
          bandeira: res[0].flag,
          moeda:
            res[0].currencies[0].name +
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
        });
        console.log(ret[i]);
      }
    });
  });

  app.get("/cidades/pais/:busc", function (req, res) {
    var busc = req.params.busc;
    conexao.query(
      `select * from cidades where idPais = ` + busc,
      (err, result) => {
        var ret = [];
        for (var i = 0; i < result.recordset.length; i++) {
          var res = sync(
            "GET",
            "https://tripadvisor1.p.rapidapi.com/locations/search?query=" +
              result.recordset[i].nome.toLowerCase(),
            {
              headers: {
                "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
                "x-rapidapi-key":
                  "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
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
          });
          console.log(res.photo.images.original.url);

          console.log(ret[i]);
        }
      }
    );
  });

  app.get("/cidades/atracoes/:busc", function (req, res) {
    var busc = req.params.busc;
      var res = sync(
        "GET",
        "https://tripadvisor1.p.rapidapi.com/attractions/list?location_id=" +
          busc,
        {
          headers: {
            "x-rapidapi-host": "tripadvisor1.p.rapidapi.com",
            "x-rapidapi-key":
              "23df1f685dmshd882459ff3ac6b1p1f63cdjsn9329a5dafe02",
            useQueryString: true,
          },
        }
      );

      res = JSON.parse(res.body.toString("utf-8")).data[0];
       //console.log(res);
   var ret={
         nome: res.nasme,
         url: res.booking.url,
         foto: res.photo.images.original.url,
       };

       console.log(ret);
  });
};
