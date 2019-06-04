package SERVER_RESTAURANT.VIEW;

import java.util.Scanner;

public class Consola {

    private static Consola consola;
    private Scanner entrada;

    private Consola() {
        this.entrada = new Scanner(System.in);
    }

    public static Consola getSingletonInstance() {
        if (consola == null) {
            consola = new Consola();
        }
        return consola;
    }

    public String leerString(String pregunta) {
        String respuesta;
        this.escribir(pregunta);
        respuesta = this.entrada.nextLine();
        return respuesta;
    }

    public void escribir(String cadena) {
        System.out.print(cadena);
    }

    public void escribirSL(String cadena) {
        System.out.println(cadena);
    }
}
