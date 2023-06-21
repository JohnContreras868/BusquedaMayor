package busquedamayor;

/**
 *
 * @author johnc
 */

import java.util.Arrays;

public class BusquedaMayor {

    private static int[] vector = new int[1000]; // Vector de 1000 elementos

    public static void main(String[] args) {
        // Llenamos el vector con números aleatorios
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (int) (Math.random() * 1000);
            System.out.println(vector[i]);
        }

        // Creamos los dos hilos para buscar el número mayor en las diferentes mitades del vector
        BusquedaHilo hilo1 = new BusquedaHilo(Arrays.copyOf(vector, vector.length / 2));
        BusquedaHilo hilo2 = new BusquedaHilo(Arrays.copyOfRange(vector, vector.length / 2, vector.length));

        // Iniciamos los hilos llamando al método start()
        hilo1.start();
        hilo2.start();
        
        // Usamos un método estático de la clase System para medir el tiempo de la búsqueda
        long iniciarTiempo = System.currentTimeMillis(); //Tiempo de inicio de la búsqueda

        try {
            // Esperamos a que ambos hilos terminen
            hilo1.join();
            hilo2.join();

            // Obtenemos el resultado de cada hilo
            int resultado1 = hilo1.getResultado();
            int resultado2 = hilo2.getResultado();

            // Comparamos los resultados para determinar el número mayor
            int numeroMayor = Math.max(resultado1, resultado2);

            // Imprimimos el número mayor
            System.out.println("El número mayor es: " + numeroMayor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long finTiempo = System.currentTimeMillis(); // Tiempo de fin de la búsqueda
        long tiempoTotal = finTiempo - iniciarTiempo; // Tiempo total de la búsqueda en milisegundos
        System.out.println("Tiempo total de búsqueda: " + tiempoTotal + " milisegundos");
    }
}

class BusquedaHilo extends Thread {
    private int[] arreglo;
    private int resultado;

    public BusquedaHilo(int[] arreglo) {
        this.arreglo = arreglo;
    }

    @Override
    public void run() {
        resultado = buscarMayor(arreglo);
    }

    public int getResultado() {
        return resultado;
    }

    private int buscarMayor(int[] arreglo) {
        int mayor = arreglo[0];
        for (int i = 1; i < arreglo.length; i++) {
            if (arreglo[i] > mayor) {
                mayor = arreglo[i];
            }
        }
        return mayor;
    }
}