/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.servico;

import br.edu.ifsul.modelo.Consulta;
import com.google.gson.Gson;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author ubiratan
 */
@Stateless
@Path("servico")
public class ServicoConsulta implements Serializable {

    private Gson gson;
    private CalcPrecoPrazoWS correios;

    public ServicoConsulta() {
        gson = new Gson();
        correios = new CalcPrecoPrazoWS();
    }

    @POST
    @Path("calcular")
    @Consumes("application/json; charset=ISO-8859-1")
    @Produces("application/json; charset=ISO-8859-1")
    public Response gerar(Consulta obj) {
        try {
            System.out.println("Entrou!");
            if (obj.getCepDestino() == null) {
                System.out.println("Entrou Cep Destino");
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }

            CResultado retorno = correios.getCalcPrecoPrazoWSSoap().calcPrecoPrazo("", "",
                    obj.getCodigoServico(),
                    "99034020",
                    obj.getCepDestino(),
                    "3",
                    1,
                    new BigDecimal("20"),
                    new BigDecimal("35"),
                    new BigDecimal("50"),
                    new BigDecimal("0.0"),
                    "n",
                    new BigDecimal("0.0"),
                    "n");
           
            obj.setFrete(Double.parseDouble(retorno.getServicos().getCServico().get(0).getValor().replace(",", ".")));
            obj.setPrazoEntrega(Integer.parseInt(retorno.getServicos().getCServico().get(0).getPrazoEntrega()));
        } catch (NumberFormatException e) {
            System.out.println("Retorno final: " + e);
        }
        return Response.ok(gson.toJson(obj)).build();
    }
}
