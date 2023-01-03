package com.org.sistema_jsp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ModelEquipamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;
    private BigDecimal tipo_equipamento;
    private LocalDate data_nf;
    private BigDecimal numero_nf;
    private BigDecimal cod_objetocusto;
    private String solicitante_equipamento;
    private BigDecimal usuario_cadastro;
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
}
