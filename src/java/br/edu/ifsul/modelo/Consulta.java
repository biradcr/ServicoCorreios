package br.edu.ifsul.modelo;


import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ubiratan
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Consulta implements Serializable{
    
    private String codigoServico;
    private String cepOrigem;
    private String cepDestino;
    private String peso;
    private Integer formato;
    private BigDecimal comprimento;
    private BigDecimal altura;
    private BigDecimal largura;
    private BigDecimal diametro;
    private String maoPropria;
    private BigDecimal valorDeclarado;
    private String avisoRecebimento;    
    private Double frete;    
    private Integer prazoEntrega;    
    
    public Consulta(){
        
    }
    public Consulta(String codServ, String cepDest, Double frete, Integer entrega){
        this.codigoServico = codServ;
        this.cepDestino = cepDest;
        this.frete = frete;
        this.prazoEntrega = entrega;
        
    }

    public String getCodigoServico() {
        return codigoServico;
    }

    public void setCodigoServico(String codigoServico) {
        this.codigoServico = codigoServico;
    }

    public String getCepOrigem() {
        return cepOrigem;
    }

    public void setCepOrigem(String cepOrigem) {
        this.cepOrigem = cepOrigem;
    }

    public String getCepDestino() {
        return cepDestino;
    }

    public void setCepDestino(String cepDestino) {
        this.cepDestino = cepDestino;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Integer getFormato() {
        return formato;
    }

    public void setFormato(Integer formato) {
        this.formato = formato;
    }

    public BigDecimal getComprimento() {
        return comprimento;
    }

    public void setComprimento(BigDecimal comprimento) {
        this.comprimento = comprimento;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public BigDecimal getLargura() {
        return largura;
    }

    public void setLargura(BigDecimal largura) {
        this.largura = largura;
    }

    public BigDecimal getDiametro() {
        return diametro;
    }

    public void setDiametro(BigDecimal diametro) {
        this.diametro = diametro;
    }

    public String getMaoPropria() {
        return maoPropria;
    }

    public void setMaoPropria(String maoPropria) {
        this.maoPropria = maoPropria;
    }

    public BigDecimal getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(BigDecimal valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public String getAvisoRecebimento() {
        return avisoRecebimento;
    }

    public void setAvisoRecebimento(String avisoRecebimento) {
        this.avisoRecebimento = avisoRecebimento;
    }   

    public Double getFrete() {
        return frete;
    }

    public void setFrete(Double frete) {
        this.frete = frete;
    }

    public Integer getPrazoEntrega() {
        return prazoEntrega;
    }

    public void setPrazoEntrega(Integer prazoEntrega) {
        this.prazoEntrega = prazoEntrega;
    }
    
    
}
