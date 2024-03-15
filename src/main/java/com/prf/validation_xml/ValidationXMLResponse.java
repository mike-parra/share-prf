package com.prf.validation_xml;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ValidationXMLResponse {

	private String status;
	private List<DetailHeader> detail;
	private String cadenaOriginalSAT;
	private String cadenaOriginalComprobante;
	private String uuid;
	private String statusSat;
	private String statusCodeSat;
	private String isCancelable;
	private String statusCancelation;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class DetailHeader{
		private List<Detail> detail;
		private String section;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class Detail{
		private String message;
		private String messageDetail;
		private Integer type;
		private String typeValue;
	}
	
}

/* Response Body From https://developers.sw.com.mx/ValidaXmlWithoutCatcha.php
 * {
	"status": "success",
	"detail": [
		{
			"detail": [
				{
					"message": "OK",
					"messageDetail": "Validacion de Estructura Correcta",
					"type": 1,
					"typeValue": "Information"
				}
			],
			"section": "CFDI40 - Validacion de Estructura"
		},
		{
			"detail": [
				{
					"message": "CFDI40106 - EL certificado no cumple con alguno de los valores permitidos.",
					"messageDetail": "Comprobante.Certificado: No es un valor base64 valido.",
					"type": 0,
					"typeValue": "Error"
				},
				{
					"message": "CFDI40102 - El resultado de la digestión debe ser igual al resultado de la desencripción del sello.",
					"messageDetail": "Value cannot be null.\r\nParameter name: certificate",
					"type": 0,
					"typeValue": "Error"
				},
				{
					"message": "CFDI40138 - El campo Nombre del emisor, debe encontrarse en la lista de RFC inscritos no cancelados en el SAT.",
					"messageDetail": null,
					"type": 2,
					"typeValue": "Warning"
				},
				{
					"message": "305 - La fecha de emisión no está dentro de la vigencia del CSD del Emisor.",
					"messageDetail": null,
					"type": 0,
					"typeValue": "Error"
				},
				{
					"message": "308 - El CSD del Emisor no ha sido firmado por uno de los Certificados de Autoridad de SAT.",
					"messageDetail": null,
					"type": 0,
					"typeValue": "Error"
				},
				{
					"message": "303 - El RFC del CSD del Emisor no corresponde al RFC que viene como Emisor en el Comprobante.",
					"messageDetail": null,
					"type": 0,
					"typeValue": "Error"
				}
			],
			"section": "CFDI40 - Validaciones Proveedor Comprobante ( CFDI40 )"
		},
		{
			"detail": [
				{
					"message": "OK",
					"messageDetail": "CFDI40 - Validaciones Proveedor Complemento  Correcta",
					"type": 1,
					"typeValue": "Information"
				}
			],
			"section": "CFDI40 - Validaciones Proveedor Complemento "
		}
	],
	"cadenaOriginalSAT": "||1.1|1EFC53BA-900E-49A1-A58F-D37E82071DAA|2024-02-04T15:42:01|DCD090706E42|dKD9sdmLqhtKO54lvaEMnUAuAH\/64gpGr8TgHFM1MNg4xD8aaO53uvGVyf5zLAPKlX2GZXMEvgkYCOF4kmVyIqZ0BlrRt1v2UU+UVumO1FxvWK7gnJI1YMwe\/QzF\/3vjsLMACiJllJlqMsN\/bXDygRLHitHli8VTLqH3Eg\/ILCrnOnr1FCAycfLiNgDGH+FJ75ZgAkYh8jKJM2mXzMAt321WQbfhRPaDXiqxWefonaSbg69bZ9GYIFZYiKVodVd++f5gxWZg\/rlimb28uO5m0VrqzB7OtXYZQ\/\/X6etXYRlls6O6\/I+dkdK2qrNwOzQ==|00001000000507237013||",
	"cadenaOriginalComprobante": "||4.0|JPA|249988|2024-02-04T15:41:30|04|00001000000505862238|420.80|MXN|486.00|I|01|PUE|59370|SSG981118LR6|SUPER SERVICIO GARBRI|601|PATM980309Q77|MIGUEL ANDRES PARRA TORRES|59310|626|G03|15101515|PL\/6084\/EXP\/ES\/2015-4280004|20.000|LTR|LITRO|SUPREME+|21.0400|420.80|02|407.50|002|Tasa|0.160000|65.20|407.50|002|Tasa|0.160000|65.20|65.20||",
	"uuid": "1EFC53BA-900E-49A1-A58F-D37E82071DAA",
	"statusSat": "Vigente",
	"statusCodeSat": "S - Comprobante obtenido satisfactoriamente.",
	"isCancelable": "Cancelable sin aceptación",
	"statusCancelation": ""
}
 */


