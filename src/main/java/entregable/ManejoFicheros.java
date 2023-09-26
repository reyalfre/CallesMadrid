package entregable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManejoFicheros {
    public static String rutaArchivo = "src/main/resources/calles-madrid.txt";


    public static void main(String[] args) {
        System.out.println("Bienvenido al manejo de ficheros de las calles de madrid");
        ejercicio1();
        ejercicio2();
        ejercicio3();
        ejercicio4();
        ejercicio5();
        System.out.println("Fin del programa");
    }

    /**
     * 1. Cuántos caracteres tiene el fichero?
     */
    private static void ejercicio1() {
        System.out.println("Ejercicio 1");
        int contadorCaracteres = 0;

        FileReader archivo = null;

        try {
            archivo = new FileReader(rutaArchivo);
            int caracter;

            while ((caracter = archivo.read()) != -1) {
                contadorCaracteres++;
            }

            System.out.println("How many characters in the file: " + contadorCaracteres);
        } catch (IOException e) {
            System.err.println("Fail to read the file: " + e.getMessage());
        } finally {
            try {
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException e) {
                System.err.println("Fail to close the file: " + e.getMessage());
            }
        }
        System.out.println("Fin ejercicio 1\n");
    }

    /**
     * 2. Cuántas vías empiezan por cada letra o número?
     */
    private static void ejercicio2() {
        System.out.println("Ejercicio 2");


        Map<Character, Integer> conteo = new HashMap<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            Pattern patron = Pattern.compile(".*?([A-ZÁÉÍÓÚÑ0-9])(?:.*?([A-ZÁÉÍÓÚÑ0-9]))?.*");

            while ((linea = lector.readLine()) != null) {
                Matcher matcher = patron.matcher(linea);
                if (matcher.matches()) {
                    char segundaMayuscula = matcher.group(2) != null ? matcher.group(2).charAt(0) : matcher.group(1).charAt(0);
                    segundaMayuscula = quitarTildes(segundaMayuscula);

                    conteo.put(segundaMayuscula, conteo.getOrDefault(segundaMayuscula, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Character, Integer> entrada : conteo.entrySet()) {
            char caracter = entrada.getKey();
            int cantidad = entrada.getValue();
            System.out.println("Carácter: " + caracter + ", Cantidad: " + cantidad);
        }
        System.out.println("Fin ejercicio 2\n");
    }

    /**
     * Function to delete accent marks
     *
     * @param c
     * @return character in uppercase
     */
    public static char quitarTildes(char c) {
        String s = String.valueOf(c);
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[^\\p{ASCII}]", "");
        return Character.toUpperCase(s.charAt(0));
    }

    /**
     * 3. Cuántos tipos de vías hay? (carretera, calle...)
     */
    private static void ejercicio3() {
        System.out.println("Ejercicio 3 (puse primero las calles y al final el total que fue 58)");
        Set<String> viaTypes = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                String[] palabritas = linea.split(" ");

                if (palabritas.length > 0) {
                    String tipoVia = palabritas[0];
                    viaTypes.add(tipoVia);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tipos de vía únicos encontrados:");
        for (String tipoVia : viaTypes) {
            System.out.println(tipoVia);
        }
        System.out.println("Número total de tipos de vía diferentes es de: " + viaTypes.size());
        System.out.println("Fin ejercicio 3\n");
    }

    /**
     * 4. Cuantas vías hay de cada tipo?
     */
    private static void ejercicio4() {
        System.out.println("Ejercicio 4");


        List<String> ListaDirecciones = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                ListaDirecciones.add(linea);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Integer> viasPorTipo = new HashMap<>();


        for (String direccion : ListaDirecciones) {
            String[] palabras = direccion.split(" ");

            if (palabras.length > 0) {
                String ViaTypes = palabras[0];

                if (viasPorTipo.containsKey(ViaTypes)) {
                    viasPorTipo.put(ViaTypes, viasPorTipo.get(ViaTypes) + 1);
                } else {
                    viasPorTipo.put(ViaTypes, 1);
                }
            }
        }

        System.out.println("La Cantidad de vías por tipo es de:");
        for (Map.Entry<String, Integer> entry : viasPorTipo.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Fin ejercicio 4\n");
    }

    /**
     * 5. Cuántas vías tiene cada localidad? (Collado Villalba, Quijorna...)
     */
    private static void ejercicio5() {
        System.out.println("Ejercicio 5");
        Map<String, Integer> contadorDeLocalidades = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
            String line;

            while ((line = reader.readLine()) != null) {
                int startIndexParentesis = line.lastIndexOf('(');
                int endIndexParentesis = line.lastIndexOf(')');
                if (startIndexParentesis != -1 && endIndexParentesis != -1 && startIndexParentesis < endIndexParentesis) {
                    String locality = line.substring(startIndexParentesis + 1, endIndexParentesis);
                    contadorDeLocalidades.put(locality, contadorDeLocalidades.getOrDefault(locality, 0) + 1);
                }
            }

            reader.close();

            for (Map.Entry<String, Integer> entry : contadorDeLocalidades.entrySet()) {
                System.out.println("La localidad: " + entry.getKey() + ", Tiene: " + entry.getValue()+" Vías");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fin ejercicio 5\n");
    }
}
