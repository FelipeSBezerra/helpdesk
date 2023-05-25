package com.felipe.helpdesk.domain.enums;

import com.felipe.helpdesk.service.exception.IllegalArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    FECHADA(2, "FECHADA");

    private Integer codigo;
    private String descricao;

    public static Status toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (Status p : Status.values()) {
            if (codigo.equals(p.getCodigo())) {
                return p;
            }
        }
        throw new IllegalArgumentException("Codigo de status inválido!");
    }
}
