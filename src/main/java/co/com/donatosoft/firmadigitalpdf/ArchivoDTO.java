/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.donatosoft.firmadigitalpdf;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author daniel
 */
@XmlRootElement
public class ArchivoDTO {
    
    private String nombre;
    private byte[] archivo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
    
    
    
}
