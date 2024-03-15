# Reciboo-rest
Back-End para le portal de recepcion de factura.

Back-end con integracion hacia sap por medio de OData services, para consultar catalogos e insertar informacion.


** Add https://developers.sw.com.mx/ certificate **
___

- Certificate Error  

> Untrusted: Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

- Solve this issue following [this Baeldung guide](https://www.baeldung.com/jvm-certificate-store-errors#manually-adding-a-certificate)

> :bulb: Example commands (Linux OS with OpenJDK):  
> `sudo keytool -import -trustcacerts -file sw_com_mx.pem -alias ALIAS -keystore /usr/lib/jvm/java-17-openjdk-amd64/lib/security/cacerts`