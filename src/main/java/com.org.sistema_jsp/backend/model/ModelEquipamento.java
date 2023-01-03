package com.org.sistema_jsp.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ModelEquipamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private BigDecimal tipo_equipamento;
    private LocalDate data_nf;
    private String numero_nf;
    private String cod_objetocusto;
    private String solicitante_equipamento;
    private String usuario_cadastro;
    private LocalDate data_cadastro;

    public boolean isNovoEquipamento() {
        if (this.id == null) {
            return true; /* Inserir novo */
        } else if (this.id != null && this.id > 0) {
            return false;
            /* Atualizar */
        }
        return id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getTipo_equipamento() {
        return tipo_equipamento;
    }

    public void setTipo_equipamento(BigDecimal tipo_equipamento) {
        this.tipo_equipamento = tipo_equipamento;
    }

    public LocalDate getData_nf() {
        return data_nf;
    }

    public void setData_nf(LocalDate data_nf) {
        this.data_nf = data_nf;
    }

    public String getNumero_nf() {
        return numero_nf;
    }

    public void setNumero_nf(String numero_nf) {
        this.numero_nf = numero_nf;
    }

    public String getCod_objetocusto() {
        return cod_objetocusto;
    }

    public void setCod_objetocusto(String cod_objetocusto) {
        this.cod_objetocusto = cod_objetocusto;
    }

    public String getSolicitante_equipamento() {
        return solicitante_equipamento;
    }

    public void setSolicitante_equipamento(String solicitante_equipamento) {
        this.solicitante_equipamento = solicitante_equipamento;
    }

    public String getUsuario_cadastro() {
        return usuario_cadastro;
    }

    public void setUsuario_cadastro(String usuario_cadastro) {
        this.usuario_cadastro = usuario_cadastro;
    }

    public LocalDate getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(LocalDate data_cadastro) {
        this.data_cadastro = data_cadastro;
    }
}
