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

            CResultado retorno = correios.getCalcPrecoPrazoWSSoap().calcPrecoPrazo("", "",
                    obj.getCodigoServico(),
                    "04180112",
                    obj.getCepDestino(),
                    "3",
                    1,
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(35),
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(0),
                    "s",
                    BigDecimal.valueOf(0),
                    "s");
            
            if (!retorno.getServicos().getCServico().get(0).getMsgErro().isEmpty()) {
                String res = retorno.getServicos().getCServico().get(0).getMsgErro();

                switch (res) {
                    case "CEP de destino invalido.":
                        return Response.status(Response.Status.FOUND).build();//302
                    case "Serviço indisponível para o trecho informado.":
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();//500
                    default:
                        return Response.status(Response.Status.PRECONDITION_FAILED).build();//412
                }
            }
            obj.setFrete(Double.parseDouble(retorno.getServicos().getCServico().get(0).getValor().replace(",", ".")));
            System.out.println("Valor Frete: " + obj.getFrete());
            obj.setPrazoEntrega(Integer.parseInt(retorno.getServicos().getCServico().get(0).getPrazoEntrega()));
            System.out.println("Prazo: " + obj.getPrazoEntrega());
        } catch (NumberFormatException ex) {
            System.out.println("**ERRO**: " + ex);
            ex.printStackTrace();
        }
        return Response.ok(gson.toJson(obj)).build();
    }
}
