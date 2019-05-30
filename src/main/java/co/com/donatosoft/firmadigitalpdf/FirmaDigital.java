/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.donatosoft.firmadigitalpdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Daniel Aperador
 */
@Path("/FirmaDigital")
public class FirmaDigital {
    
    
    public static final char[] CLAVE = "123456".toCharArray();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArchivoDTO firmarPDF(ArchivoDTO pdf){
        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            //inicia lectura del certificado
            KeyStore ks = KeyStore.getInstance("PKCS12");//.getInstance("jks");
            ks.load(FirmaDigital.class.getResourceAsStream("certificado.p12"), CLAVE);
            String alias = (String) ks.aliases().nextElement();
            PrivateKey key = (PrivateKey) ks.getKey(alias, CLAVE);
            Certificate[] chain = ks.getCertificateChain(alias);
            
            
            PdfReader reader = new PdfReader(pdf.getArchivo());
            PdfStamper stp = PdfStamper.createSignature(reader, baos, '\0');
            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
            stp.close();
            
            byte[] bytes = baos.toByteArray();
            pdf.setArchivo(bytes);
            
            return pdf;
        } catch (Exception ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
