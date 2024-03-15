package com.prf.factura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Factura{
    private int idfactura;
    private String uuid;
    private int folio;
    private String moneda;
    private String serie;
    private double monto;   
    private String fecha_expedicion;
    private String fecha_timbrado;
    private String razonsocial_emisor;
    private String rfc_emisor;
    private String razonsocial_receptor;
    private String rfc_receptor;
    private String domicilio_fiscal;
    private String clave_regimenfiscal;
    private String metodo_pago;
    private String forma_pago;
    private String estatus;
}
