package com.prf.documento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Documento {
    private Integer iddocumento;
    private String nombre_documento;
    private String descripcion_documento;
    private String[] formato_aceptados;
    private String tamanyo_documento;
}
