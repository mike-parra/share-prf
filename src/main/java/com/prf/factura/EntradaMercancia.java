package com.prf.factura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class EntradaMercancia{
    private int idmercancia;
    private int id_oc;
    private int idfactura;
}