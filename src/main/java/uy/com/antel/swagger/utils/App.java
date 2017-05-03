package uy.com.antel.swagger.utils;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Clase encargada de unir los archivos yaml en uno solo
 */
public class App {
    private static final String LOCATION = "/home/gfaller/prueba swagger/";
    private static final String REF_TAG = "$ref: ./";
    private static final boolean DEBUG_ENEABLED = false;

    public static void main(String[] args) throws IOException, URISyntaxException {
        File file = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        String location = file.getParentFile().getAbsolutePath();
        System.out.println("Ejecutando desde: " + location);

        if (DEBUG_ENEABLED) {
            String s = recorrerArchivos(LOCATION + "index.yaml");
            writeFile(LOCATION + "NEWindex.yaml", s);
        } else {
            if (args.length == 0) {
                System.err.println("ERROR: no se encontraron parametros de entrada");
                return;
            }

            String filename = args[0];
            String s = recorrerArchivos(location + "/" + filename);
            writeFile("NEW" + filename, s);
        }
    }


    private static String recorrerArchivos(String filePath) throws IOException {
        System.out.println("Recorriendo: " + filePath);

        File fs = new File(filePath);
        String dirPath = fs.getParentFile().getAbsolutePath();


        String file = readFile(filePath);
        String respuesta = file.trim();

        if (DEBUG_ENEABLED) {
            System.out.println("dirName: " + dirPath);
            System.out.println("Archivo: \n" + file);
        }


        int indI = file.indexOf(REF_TAG);
        while (indI > 0) {

            file = file.substring(indI + REF_TAG.length());
            int indF = file.indexOf("\n");
            String substring = file.substring(0, indF);

            //System.out.println("Se encuentra: " + substring);
            String remplazo = recorrerArchivos(dirPath + "/" + substring);
            remplazo = remplazo.replaceAll("\r", "").replaceAll("\n", "\n  ") + "\n";

            respuesta = respuesta.replace(REF_TAG + substring, remplazo);


            indI = file.indexOf(REF_TAG);
        }
        return respuesta;
    }

    private static void writeFile(String filename, String content) throws IOException {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
            bw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }


}
