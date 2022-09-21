/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgFitxers;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author vicent
 */
public class MeuObjecteOutputStream extends ObjectOutputStream{
    /**
     * Constructor que rep OutputStream
     * @param out sortida outputstream
     * @throws IOException Excepció d'E/S
     */
    public MeuObjecteOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    /**
     * Constructor sense paràmetres
     * @throws IOException Excepció d'E/S
     * @throws SecurityException Excepció de seguretat
     */
    protected MeuObjecteOutputStream() throws IOException, SecurityException
    {
        super();
    }

    /**
     * Redefinició del mètode d'escriure la capçalera per a que no faci res.
     * @throws IOException Excepció d'E/S
     */
    protected void writeStreamHeader() throws IOException
    {
        // AQUEST MÈTODE ORIGINAL ESCRIU UNA CAPÇALERA
        // AQUI EL REESCRIC EN BLANC. NO FA RES
        // EL MÈTODE EXISTEIX, PERÒ NO ESCRIU LA CAPÇALERA
        // I ENS PERMETRÀ AFEGIR UN OBJECTE A UN FITXER DE FORMA NORMAL
    }
    
}