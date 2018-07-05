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
                    "99034020",
                    obj.getCepDestino(),
                    "3",
                    1,
                    BigDecimal.valueOf(20),
                    BigDecimal.valueOf(35),
                    BigDecimal.valueOf(50),
                    BigDecimal.valueOf(0),
                    "n",
                    BigDecimal.valueOf(0),
                    "n");
            if (!retorno.getServicos().getCServico().get(0).getMsgErro().isEmpty()) {
                String res = retorno.getServicos().getCServico().get(0).getErro();
                System.out.println("Retornou do serviço o seguinte erro: " + res);
                if (res.equals(-2)) {//CEP de origem inválido
                    return Response.status(Response.Status.BAD_REQUEST).build();
                } else if (res.equals(-3)) {//CEP de destino inválido
                    return Response.status(Response.Status.BAD_GATEWAY).build();
                } else if (res.equals(-4)) {//Peso excedido
                    return Response.status(Response.Status.GONE).build();
                } else if (res.equals(-15)) {//O comprimento não pode ser maior que 105 cm
                    return Response.status(Response.Status.MOVED_PERMANENTLY).build();
                } else if (res.equals(-16)) {//A largura não pode ser maior que 105 cm
                    return Response.status(Response.Status.NO_CONTENT).build();
                } else if (res.equals(-17)) {//A altura não pode ser maior que 105 cm
                    return Response.status(Response.Status.PAYMENT_REQUIRED).build();
                } else if (res.equals(-18)) {//A altura não pode ser inferior a 2 cm
                    return Response.status(Response.Status.FORBIDDEN).build();
                } else if (res.equals(-20)) {//A largura não pode ser inferior a 11 cm
                    return Response.status(Response.Status.CREATED).build();
                } else if (res.equals(-22)) {//O comprimento não pode ser inferior a 16 cm
                    return Response.status(Response.Status.EXPECTATION_FAILED).build();
                } else if (res.equals(-23)) {//A soma resultante do comprimento + largura + altura não deve superar a 200 cm
                    return Response.status(Response.Status.HTTP_VERSION_NOT_SUPPORTED).build();
                }else {
                    return Response.status(Response.Status.PRECONDITION_FAILED).build();//412
                }
            }

            obj.setFrete(Double.parseDouble(retorno.getServicos().getCServico().get(0).getValor().replace(",", ".")));
            obj.setPrazoEntrega(Integer.parseInt(retorno.getServicos().getCServico().get(0).getPrazoEntrega()));
        } catch (Exception ex) {
            System.out.println("**ERRO**: " + ex);
        }
        return Response.ok(gson.toJson(obj)).build();
    }
}
