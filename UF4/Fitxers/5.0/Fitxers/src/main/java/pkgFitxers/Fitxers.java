package pkgFitxers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;


import static java.lang.Thread.sleep;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Fitxers {


    //<editor-fold desc="Constructors">

    public Fitxers() {
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FUNCIONS IO">


    /**
     * busca una cadena de text en el fitxer i retorna un true si existeix o false si no existeix
     *
     * @param fitxer            fitxer d'entrada
     * @param text              cadena a cercar
     * @param caracterSeparador caràcter que separa les columnes al fitxer csv
     * @param columna           Número de columna de l'arxiu on volem cercar
     * @return true: si existeix.  false: si no existeix
     * @throws FileNotFoundException arxiu no trobat
     * @throws IOException           excepcio entrada sortida
     */
    public boolean cercaEnFitxerText(String fitxer,
                                     String text,
                                     String caracterSeparador,
                                     int columna)
            throws FileNotFoundException, IOException {
        // Cerca un String dintre d'un fitxer. Retorna true si trobat o false si no trobat

        //boolean trobat = false;     // per dir si la paraula està al fitxer o no
        String linia;                   // per recollir la línia
        String[] noms;                  // per particionar la linia
        // /////////////////////////// //
        // FITXERS CHARACTER STREAMS //
        // ///////////////////////// //
        try ( // BLOC DE TRY .. CATCH
              FileReader in = new FileReader(fitxer);) {
            Scanner input = new Scanner(in);
//
//
//            // input.hasNextLine()  // RETORNA TRUE MENTRE HI HAGEN LINIES
//            // input.nextLine       // RETORNA UNA LINIA SENCERA
//            //STRING.contains(text) // RETORNA TRUE SI L'string CONTÉ EL TEXT (text)
//            /**
//             * FORMA 1 PODEM TENIR EL ERROR QUE SI POSEM UNA PARAULA CURTA,
//             * SURTEN TOTES. per exemple, si pose: "A" es cercarà totes les
//             * paraules del fitxer que continguen una A*
//             */
//            /*
//
//            while (input.hasNextLine()) {   // Mentre hi hagen línies a l'arxiu ...
//                p = input.nextLine();       // Extrau una línia del fitxer
//                if (p.contains(text))       // Cerca una subseqüència dintre la línia
//
//                // en cas que vullguem 2 seqüencies, per exemple nom i cognom
//                //if (p.contains(text1) && p.contains(text2))
//                    return trobat = true;
//            }
            /** FORMA 2, MÉS CORRECTA: AMB PARAULES EXACTES **/
            while (input.hasNextLine()) {           // Mentre hi hagen línies a l'arxiu ...
                linia = input.nextLine();               // Agafa una línia
                noms = linia.split(caracterSeparador);  // Parteix per columnes
                if (text.equals(noms[columna])) {         // noms[0]-> nom; noms[1]-> cognom; nom[2]-> edat, etc...
                    return true;
                }
            }

        } catch (Exception e) {
            System.err.println(";-(. Algun error en el fitxer");

        }

        return false;
    }


    /**
     * Retorna un fitxer de text en una llista d'String.
     *
     * @param fitxer ruta del fitxer de text a llegir
     * @return List d'String amb el contingut del fitxer
     */
    public List<String> retornaFitxerTextEnLlista(String fitxer) {
        List<String> LFitxer = new ArrayList<>();       // per acumular les línies
        String linia;                                   // per recollir la línia

        try ( // BLOC DE TRY .. CATCH
              FileReader in = new FileReader(fitxer);) {
            Scanner input = new Scanner(in);
            while (input.hasNextLine()) {               // Mentre hi hagen línies a l'arxiu ...
                linia = input.nextLine();               // Agafa una línia
                LFitxer.add(linia);                     // afegim la línia a la llista
            }
        } catch (Exception e) {
            System.err.println(";-(. Algun error en el fitxer");
        }
        return LFitxer;
    }

    /**
     * Retorna un fitxer de text en una array d'String.
     *
     * @param fitxer            ruta del fitxer de text a llegir
     * @param caracterSeparador caràcter que separa els diferents camps
     * @return array d'String amb el contingut del fitxer
     */
    public String[] retornaFitxerTextEnArray(String fitxer,
                                             String caracterSeparador) {
        String linia;       // per recollir la línia
        int totalLinies = 0;  // per contar les línies del fitxer i fer l'array


        // calculem les línies
        try ( // BLOC DE TRY .. CATCH
              FileReader in = new FileReader(fitxer);) {
            Scanner input = new Scanner(in);
            while (input.hasNextLine()) {           // Mentre hi hagen línies a l'arxiu ...
                linia = input.nextLine();
                totalLinies++;
            }
        } catch (Exception e) {
            System.err.println(";-(. Algun error en el fitxer");

        }
        String text[] = new String[totalLinies];      // per acumular les línies
        totalLinies = 0;                              // l'utilitzem ara per contador
        // introduïm les línes al array d'String
        try ( // BLOC DE TRY .. CATCH
              FileReader in = new FileReader(fitxer);) {
            Scanner input = new Scanner(in);
            while (input.hasNextLine()) {           // Mentre hi hagen línies a l'arxiu ...
                linia = input.nextLine();
                text[totalLinies] = linia;
                totalLinies++;
            }
        } catch (Exception e) {
            System.err.println(";-(. Algun error en el fitxer");

        }
        return text;
    }


    /**
     * Elimina un registre d'un fitxer de text, a partir del text, el caràcter separador
     * i la posició en la columna (0,1,2, etc...)
     *
     * @param fitxer            fitxer a obrir
     * @param text              text a cercar
     * @param caracterSeparador caràcter separador de columnes
     * @param posicio           posició de la columna, partint de la 0
     */
    public void eliminaRegistreFitxerText(String fitxer,
                                          String text,
                                          String caracterSeparador,
                                          int posicio) {
        String linia;
        String fitxer_tmp = ".tmp";
        String[] camps;

        try ( // BLOC DE TRY .. CATCH

              FileReader in = new FileReader(fitxer);) {
            Scanner input = new Scanner(in);

            while (input.hasNextLine()) {           // Mentre hi hagen línies a l'arxiu ...
                linia = input.nextLine();           // Agafa una línia
                camps = linia.split(caracterSeparador);          // Parteix per columnes

                if (!text.equals(camps[posicio])) {
                    escriuTextFitxer(fitxer_tmp, linia, true);
                }
            }
            //EliminarFitxerDirectori(fitxer);
            moureFitxerDirectori(fitxer_tmp, fitxer);
        } catch (Exception e) {
            System.err.println(";-(");
        }

    }

    /**
     * @param fitxer            ruta del fitxer de text a llegir
     * @param caracterSeparador caràcter que separa els diferents camps
     * @return List d'String amb el contingut del fitxer
     * @deprecated NO ACABAT!!! FUNCIONA!!
     */
    @Deprecated
    public List<Object> llegeixFitxerEnLlistaObject(String fitxer,
                                                    String caracterSeparador) {

        List<Object> LFitxer = new ArrayList<>();   // per retornar una llista d'objectes
        Object obj = new Object();                    // per encapsular cada objecte
        String linia;                               // per recollir la línia
        String[] v;                                 // per fer l'split


        try ( // BLOC DE TRY .. CATCH
              FileReader in = new FileReader(fitxer);) {
            Scanner input = new Scanner(in);
            while (input.hasNextLine()) {           // Mentre hi hagen línies a l'arxiu ...
                linia = input.nextLine();           // Agafa una línia
                v = linia.split(caracterSeparador);   // Partim la línia en les posicions del vector
                LFitxer.add(v);                     // afegim la línia a la llista
            }

        } catch (Exception e) {
            System.err.println(";-(. Algun error en el fitxer");
        }


        return LFitxer;
    }



    // DATA STREAM //


    /**
     * Tanquem el fitxer object OutputStream passat per paràmetre
     *
     * @param out fitxer OutputStream a tancar
     * @throws IOException excepció d'Entrada/Sortida
     */
    private void tancaFitxerObjecte(ObjectOutputStream out) throws IOException {
        // tanquem els fitxer (en el SO windows s'ha de tancar el fitxer.)
        out.flush();
        out.close();
        out = null;
    }


    /**
     * Escrivim un Objecte (dona igual la forma) a un fitxer binari
     * En aquesta funció utilitzem una classe creada a partir de ObjectOutputStrem, anomenada MeuObjecteOutpusStream
     * El problema és que al escriure un objecte s'escriu també una capçalera del fitxer.
     * Si afegim un altre objecte també s'afegiex aquesta capçalera, i farà l'arxiu incomprensible per la seva extracció
     * S'ha optat per crear un mètode que herete de ObjectOutputStrem però que no escrigui aquesta capçalera
     * Per això MeuObjectOutputStream és una classe amb el séu mètode writeStreamHeader en blanc
     *
     * @param obj    objecte a escriure
     * @param arxiu  arxiu binari (normalment .dat)
     * @param afegir si afegir==true afegirem. Si afegir==false sobreescrivim
     * @throws IOException excepció d'E/S
     */
    public void escriuObjecteFitxer(Object obj,
                                    String arxiu,
                                    boolean afegir) throws IOException {
        // Si hem posat afegir=false o si no existeix l'arxiu (sobreescrivim el fitxer)
        if (!afegir || !existeix(arxiu)) {
            // PER CREAR EL PRIMER OBJECTE
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arxiu, afegir));
            out.writeObject(obj);
            tancaFitxerObjecte(out);    //tanquem el fitxer d'objectes
        } else {
            // PER AFEGIR LA RESTA D'OBJECTES.  Per afegir TAMBÉ posem true després el nom del fitxer
            MeuObjecteOutputStream out2 = new MeuObjecteOutputStream(new
                    FileOutputStream(arxiu, afegir));
            out2.writeObject(obj);
            tancaFitxerObjecte(out2);    //tanquem el fitxer d'objectes
        }
    }


    /**
     * Escrivim una llista d'Objectes (dona igual la forma) a un fitxer binari
     * En aquesta funció utilitzem una classe creada a partir de ObjectOutputStrem, anomenada MeuObjecteOutpusStream
     * El problema és que al escriure un objecte s'escriu també una capçalera del fitxer.
     * Si afegim un altre objecte també s'afegiex aquesta capçalera, i farà l'arxiu incomprensible per la seva extracció
     * S'ha optat per crear un mètode que herete de ObjectOutputStrem però que no escrigui aquesta capçalera
     * Per això MeuObjectOutputStream és una classe amb el séu mètode writeStreamHeader en blanc
     *
     * @param LObjs llista d'objectes a escriure
     * @param arxiu arxiu binari (normalment .dat)
     * @throws IOException excepció d'E/S
     */
    public void escriuLlistaObjecteFitxer(List<Object> LObjs,
                                          String arxiu) throws IOException {

        // Si hem posat afegir=false o si no existeix l'arxiu (sobreescrivim el fitxer)
        int i;
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arxiu, false));
        MeuObjecteOutputStream out2 = new MeuObjecteOutputStream(new FileOutputStream(arxiu, true));


        out.writeObject(LObjs.get(0));          // escrivim el primer:
        tancaFitxerObjecte(out);                //tanquem el fitxer d'objectes
        for (i = 1; i < LObjs.size(); i++) {
            out2.writeObject(LObjs.get(i));     // escrivim la resta
        }
        tancaFitxerObjecte(out2);               //tanquem el fitxer d'objectes
    }

    /**
     * Deprecada, ja que aquesta funció ens retornarà una llista d'Objectes
     * però després haurem de convertir-los a una objectes d'una classe específica
     * Tenim un mètode millor:
     * <p>
     * public 'classeObjecte' Object retornaFitxerObjecteEnLlista(String arxiu,Class classeObjecte)
     * <p>
     * que ja em retornarà la llista, formatada en la classe que vull
     * <p>
     * Llegeix un arxiu binari i retorna els seus components en objectes Object
     *
     * @param arxiu ruta de l'arxiu a llegir
     * @return Llista d'Objectes Object a partir de l'arxiu
     * @throws IOException            Excepció d'E/S
     * @throws InterruptedException   Excepció d'Interrupcións
     * @throws ClassNotFoundException Classe no trobada
     */
    @Deprecated
    public List<Object> retornaFitxerObjecteEnLlista(String arxiu) throws IOException,
            InterruptedException, ClassNotFoundException {
        List<Object> LObjs = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(arxiu));
            do {
                Object obj = in.readObject();
                LObjs.add(obj);
            } while (in != null);
            in.close();                     // tanquem el fitxer d'objectes
            in = null;                      // i l'alliberem de memòria
        } catch (IOException e) {
            //e.printStackTrace();
            //System.err.println("\nFinal Fitxer");
        }
        return LObjs;
    }


    /**
     * Llegeix un arxiu binari i retorna els seus components en objectes Object
     * Funció polimòrfica on podem passar-li una classe sencera
     * i convertir els objectes object, en objectes de la classe passada per parametre.
     * <p>
     * Per cridar-lo ho farem així:
     * <p>
     * Llist 'classeQueVolem' llista;
     * llista=(List 'classeQueVolem')retornaFitxerObjecteEnLlista(rutaArxiu,classeQueVolem.Class);
     * exemple:
     * List'Autor' LAutors = (List'Autor') retornaFitxerObjecteEnLlista(rutaFitxer,Autor.class);
     *
     * @param arxiu ruta de l'arxiu a llegir
     * @param Class classe a la que volem convertir els elements objectes del fitxer
     * @param <T>   classe objecte
     * @return llista d'objectes de la classe objecte
     * @throws ClassNotFoundException excepció
     */
    public <T> Object retornaFitxerObjecteEnLlista(String arxiu,
                                                   T Class) throws ClassNotFoundException {

             /*
             Per cridar al mèteode:
             Llist<Object>llista;
             llista=(List <classeQueVolem>)retornaFitxerObjecteEnLlista(rutaArxiu,classeQueVolem.Class);
             */


        List<T> LObjs = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(arxiu));
            do {
                T obj = (T) in.readObject();
                LObjs.add(obj);
            } while (in != null);
            in.close();                     // tanquem el fitxer d'objectes
            in = null;                      // i l'allibrerem de memòria

        } catch (IOException e) {
            //e.printStackTrace();
            //System.err.println("\nFinal Fitxer");
        }
        return LObjs;
    }

    /**
     * Llegeix un arxiu binari i retorna els seus components en objectes Object
     * Funció polimòrfica on podem passar-li una classe sencera
     * i convertir els objectes object, en objectes de la classe passada per parametre.
     * <p>
     * Per cridar-lo ho farem així:
     * <p>
     * Llist 'classeQueVolem' llista;
     * llista=(List 'classeQueVolem')retornaFitxerObjecteEnLlista(rutaArxiu,classeQueVolem.Class);
     * exemple:
     * List'Autor' LAutors = (List'Autor') retornaFitxerObjecteEnLlista(rutaFitxer,Autor.class);
     *
     * @param arxiu ruta de l'arxiu a llegir
     * @param Class classe a la que volem convertir els elements objectes del fitxer
     * @param <T>   classe objecte
     * @return llista d'objectes de la classe objecte
     * @throws ClassNotFoundException excepció
     */
    public <T> Object retornaFitxerObjecteEnPila(String arxiu,
                                                 T Class) throws ClassNotFoundException {

             /*
             Per cridar al mèteode:
             Llist<Object>llista;
             llista=(List <classeQueVolem>)retornaFitxerObjecteEnLlista(rutaArxiu,classeQueVolem.Class);
             */


        Stack<T> LObjs = new Stack<>();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(arxiu));
            do {
                T obj = (T) in.readObject();
                LObjs.push(obj);
            } while (in != null);
            in.close();                     // tanquem el fitxer d'objectes
            in = null;                      // i l'allibrerem de memòria

        } catch (IOException e) {
            //e.printStackTrace();
            //System.err.println("\nFinal Fitxer");
        }
        return LObjs;
    }


    /**
     * Llegeix un arxiu binari i retorna els seus components en objectes Object
     * Funció polimòrfica on podem passar-li una classe sencera
     * i convertir els objectes object, en objectes de la classe passada per parametre.
     * <p>
     * Per cridar-lo ho farem així:
     * <p>
     * Llist 'classeQueVolem' llista;
     * llista=(List 'classeQueVolem')retornaFitxerObjecteEnLlista(rutaArxiu,classeQueVolem.Class);
     * exemple:
     * List'Autor' LAutors = (List'Autor') retornaFitxerObjecteEnLlista(rutaFitxer,Autor.class);
     *
     * @param arxiu ruta de l'arxiu a llegir
     * @param Class classe a la que volem convertir els elements objectes del fitxer
     * @param <T>   classe objecte
     * @return llista d'objectes de la classe objecte
     * @throws ClassNotFoundException excepció
     */
    public <T> Object retornaFitxerObjecteEnCua(String arxiu,
                                                T Class) throws ClassNotFoundException {

             /*
             Per cridar al mèteode:
             Llist<Object>llista;
             llista=(List <classeQueVolem>)retornaFitxerObjecteEnLlista(rutaArxiu,classeQueVolem.Class);
             */


        Queue<T> LObjs = new LinkedList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(arxiu));
            do {
                T obj = (T) in.readObject();
                LObjs.add(obj);
            } while (in != null);
            in.close();                     // tanquem el fitxer d'objectes
            in = null;                      // i l'allibrerem de memòria

        } catch (IOException e) {
            //e.printStackTrace();
            //System.err.println("\nFinal Fitxer");
        }
        return LObjs;
    }


    /**
     * !!! No funciona!!!
     * <p>
     * Funció polimòrfica. Entra una classe i depenent d'aquesta classe convertià els objectes del fitxer
     * Retornarà una llista d'objectes ja convertits a aquesta classe
     * <p>
     * Per cridar al mètode, per exemple des d'objectes Llibre:
     * <p>
     * Llibre lli;
     * lli=cercaObjecteFitxer(rutaArxiu, ObjecteLlibreACercar, class.Llibre);
     * Cerca un objecte al fitxer
     * i retorna aquest objecte si està al fitxer
     * retornarà null si no el troba
     *
     * @param arxiu ruta del fitxer on estan les dades
     * @param obj   objecte a cercar
     * @param T     referència a la classe de l'objecte que entrarà al mètode
     * @param <T>   referència a la classe de l'objecte que retornarà
     * @return null si no troba l'objecte. L'objecte si el troba
     * @throws InterruptedException   excepció
     * @throws IOException            excepció
     * @throws ClassNotFoundException excepció
     * @deprecated !!! No funciona!!!
     */
    @Deprecated
    public <T> T cercaObjecteFitxer(String arxiu, T obj, Class T)
            throws InterruptedException, IOException, ClassNotFoundException {
        List<T> LObj = (List<T>) retornaFitxerObjecteEnLlista(arxiu);
        T obj2;
        int i;
        for (i = 0; i < LObj.size(); i++) {
            obj2 = LObj.get(i);

            if (obj2.equals(obj)) {
                return (T) obj;
            }
        }
        return null;
    }

    /**
     * !!! No funciona!!!
     * <p>
     * Cerca un objecte al fitxer
     * i retorna aquest objecte si està al fitxer
     * retornarà null si no el troba
     *
     * @param arxiu ruta del fitxer on estan les dades
     * @param obj   objecte a cercar
     * @return null si no troba l'objecte. L'objecte si el troba
     * @throws InterruptedException   excepció
     * @throws IOException            excepció
     * @throws ClassNotFoundException excepció
     *                                <p>
     *                                !!! No funciona!!!
     */
    @Deprecated
    public Object cercaObjecteFitxer(String arxiu, Object obj) throws InterruptedException, IOException, ClassNotFoundException {
        List<Object> LObj = retornaFitxerObjecteEnLlista(arxiu);
        Object obj2;
        int i;
        for (i = 0; i < LObj.size(); i++) {
            obj2 = LObj.get(i);

            if (obj2.equals(obj)) {
                return obj;
            }
        }
        return null;
    }


    /**
     * Elimina un objecte del fitxer d'Objectes
     *
     * @param fitxer fitxer d'objectes
     * @param Obj    Objecte a eliminar
     * @throws InterruptedException   excepció
     * @throws IOException            excepció
     * @throws ClassNotFoundException excepció
     */
    public void eliminaRegistreFitxerObjecte(String fitxer,
                                             Object Obj) throws InterruptedException, IOException, ClassNotFoundException {

        List<Object> LObj = retornaFitxerObjecteEnLlista(fitxer);
        int i;
        boolean primerRegistreGravat = true;
        for (i = 0; i < LObj.size(); i++) {
            if (!LObj.get(i).equals(Obj)) {
                if (primerRegistreGravat) {
                    escriuObjecteFitxer(Obj, fitxer, false);
                    primerRegistreGravat = false;
                } else {
                    escriuObjecteFitxer(Obj, fitxer, true);
                }
            }
        }
    }

    /**
     * Guarda una llista d'Objectes al fitxer d'Objectes
     *
     * @param arxiu Ruta de l'arxiu
     * @param LObj  Llista d'objectes
     * @throws IOException Excepció d'E/S
     */
    public void guardaLlistaObjecteFitxerObjecte(String arxiu,
                                                 List<Object> LObj) throws IOException {
        int i;
        for (i = 0; i < LObj.size(); i++) {
            if (i == 0) {
                escriuObjecteFitxer(LObj.get(i), arxiu, false);
            } else {
                escriuObjecteFitxer(LObj.get(i), arxiu, true);
            }
        }
    }


    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FUNCIONS NIO">

    /**
     * retorna true o false si el directori o fitxer passat per paràmetre existeix
     *
     * @param ruta directori o fitxer a comprovar
     * @return true si existeix. false si no existeix
     */
    public boolean existeix(String ruta) {
        // Comprova la existència d'un directori o un fitxer
        Path fitxer = Paths.get(ruta);         // Path --> import java.nio.file.Path;
        // Paths --> import java.nio.file.Paths
        return Files.exists(fitxer);    // Files --> import java.nio.file.Files
    }

    /**
     * Crea un directori a la ruta indicada
     *
     * @param ruta ruta del directori
     * @throws IOException Excepció d'Entrada / Sortida
     */
    public void creaDirectori(String ruta) throws IOException {
        Path path = Paths.get(ruta);         // Path --> import java.nio.file.Path;
        // Paths --> import java.nio.file.Paths
        Files.createDirectory(path);    // Files --> import java.nio.file.Files
    }

      /**
     * Elimina un fitxer o directori passat per paràmetre
     *
     * @param fitx fitxer o directori a eliminar
     * @throws IOException excepció d'entrada/sortida
     */
    public void eliminarFitxerDirectori(String fitx) throws IOException {

        // Borrem un fitxer o directori
        try {
            Path path = Paths.get(fitx);


            Files.delete(path);                 // No veiem encara la clàusula try .. catch .. finally


        } catch (NoSuchFileException x) {
            // No existeix el fitxer
        } catch (DirectoryNotEmptyException x) {
            // El directori no està buit i no es pot borrar
        } catch (IOException x) {
            // Altres tipus d'errors. Per exemple no tenir permissos per borrar
        }
    }

    /**
     * Copia un fitxer o directori a un altre lloc
     *
     * @param origen fitxer o directori a copiar
     * @param desti  nou fitxe o directori que es copiarà
     * @throws IOException excepció d'Entrada/Sortida
     */
    public void copiarFitxerDirectori(String origen,
                                      String desti) throws IOException {

        Path pathOrigen = Paths.get(origen);
        Path pathDesti = Paths.get(desti);

        Files.copy(pathOrigen, pathDesti, REPLACE_EXISTING);

    }

    /**
     * Mou un fitxer o directori a altre lloc
     *
     * @param origen fitxer a moure
     * @param desti  fitxer on es mourà
     * @throws IOException excepció d'E/S
     */
    public void moureFitxerDirectori(String origen,
                                     String desti) throws IOException {

        int eleccio;

        // copia un fitxer (origen) en un fitxer (desti)
        Path pathOrigen = Paths.get(origen);
        Path pathDesti = Paths.get(desti);

//        if (existeix(desti)){
//            eleccio=Pregunta("El arxiu ja existeix.\n" +
//                    "vols sobreescriure'l?");
//            if (eleccio==0)
        Files.move(pathOrigen, pathDesti, REPLACE_EXISTING);
//            else
//                MissatgeInfo("OK. No sobreescrivim el fitxer");

//        }
    }

    /**
     * Retorna en String algunes metadadades d'un fitxer passat per paràmetre
     *
     * @param fitx ruta del fitxer a analitzar
     * @return String amb les metadades
     * @throws IOException excepció d'E/S
     */
    public String metadadesFitxer(String fitx) throws IOException {

        String cadena = "";
        if (existeix(fitx)) {
            Path path = Paths.get(fitx);
            cadena = "Metadades del fitxer " + fitx + "\n";
            cadena = cadena + "Tamany: " + Files.size(path) + " Bytes\n";
            cadena = cadena + "Directory?: " + Files.isDirectory(path) + "\n";
            cadena = cadena + "Fitxer?: " + Files.isRegularFile(path) + "\n";
            cadena = cadena + "Simbòlic: " + Files.isSymbolicLink(path) + "\n";
            cadena = cadena + "Ocult: " + Files.isHidden(path) + "\n";
            cadena = cadena + "Ultima Modificació: " + Files.getLastModifiedTime(path) + "\n";
            cadena = cadena + "Propietari: " + Files.getOwner(path) + "\n";
        } else {
            cadena = "No existeix el fitxer";
        }
        return cadena;
    }

    /**
     * per llistar els arxius d'un directori i la resta baix d'ell de forma recursiva
     *
     * @param fitx ruta del directori
     */
    public void recorrerArbreDirectoris(String fitx) {
        //
        // NO NIVELL ENCARA
        // NO UTILITZAT EN PROGRAMA EXEMPLE
        //
        File arx = new File(fitx);
        File listFile[] = arx.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    recorrerArbreDirectoris(listFile[i].toString());        //  RECURSIU  //
                } else {
                    System.out.println(listFile[i].getPath());
                }
            }
        }
    }


    /**
     * Escriu el text passat per paràmetre al fitxer "fitx"
     * !!EN AQUEST CAS S'ESCRIU EN UN JOC DE CARÀCTERS: UTF-8 !!
     *
     * @param fitx   ruta del fitxer de text
     * @param text   cadena String a escriure al fitxer
     * @param afegir true si volem afegir a l'arxiu. false si volem reescriure'l
     * @throws IOException excepció si hi ha algun error
     */
    public void escriuTextFitxer(String fitx,
                                 String text,
                                 boolean afegir) throws IOException {
        Path path = Paths.get(fitx);
        String linia = null;

        List<StringBuffer> dades = new ArrayList<StringBuffer>();
        dades.add(new StringBuffer(text));

        // Escrivim dintre del fitxer finalment
        if (afegir)
            Files.write(path, dades, Charset.forName("UTF-8"), Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        else
            Files.write(path, dades, Charset.forName("UTF-8"));
    }



    /**
     * Retorna el contingut d'un fitxer de text en una cua d'String
     * !!EN AQUEST CAS HA D'ESTAR ESCRIT EN UN JOC DE CARÀCTERS: UTF-8 !!
     *
     * @param fitx ruta del fitxer de text a llegir
     * @return Cua d'String amb el contingut del fitxer
     * @throws IOException excepció d'E/S
     */
    public Queue<String> retornaFitxerTextEnCua(String fitx) throws IOException {
        Path path = Paths.get(fitx);
        Queue<String> CFitxer = new LinkedList<>();   // per acumular les línies
        String linia;                               // per recollir la línia

        Charset charset = Charset.forName("UTF-8"); // definim el charset
        BufferedReader reader = Files.newBufferedReader(path, charset);
        while ((linia = reader.readLine()) != null) {
            CFitxer.add(linia);                     // linia a linia la posem a la CUA
        }

        return CFitxer;
    }

    // </editor-fold>



}
