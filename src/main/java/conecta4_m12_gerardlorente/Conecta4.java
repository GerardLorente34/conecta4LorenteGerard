package conecta4_m12_gerardlorente;

import java.util.*;

public class Conecta4 {

    // Declarmos las variables de las fichas y la de los huecos
    private static char jugador_1 = 'X';
    private static char jugador_2 = 'O';
    private static char hueco = '.';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pedimos las dimensiones del tablero
        System.out.println("Introduce el número de filas para el tablero:");
        int filas = sc.nextInt();

        System.out.println("Introduce el número de columnas para el tablero:");
        int columnas = sc.nextInt();

        // Lo inicilizamos
        char[][] tablero = new char[filas][columnas];
        inicializarTablero(tablero);

        // Escojemos el modo de juego
        System.out.println("Elige el modo de juego:");
        System.out.println("1. Jugador vs Jugador");
        System.out.println("2. Jugador vs IA");
        int modoJuego = sc.nextInt();

        // Declaramos las variables que controlan el estado del juego

        boolean juegoTerminado = false; // Indica si el juego ha terminado o no
        char jugadorActual = jugador_1; // Define quién es el jugador actual

        // Ejecuta el juego mientras no se haya terminado.

        while (!juegoTerminado) {

            /**
             * Imprime el estado actual del tablero de juego para que los jugadores
             * puedan ver dónde están las fichas colocadas.
             */
            imprimirTablero(tablero);

            int columna; // Variable que almacena la columna seleccionada

            /**
             * Comprueba si el turno actual pertenece a una persona o a la IA.
             */
            if (jugadorActual == jugador_1 || modoJuego == 1) {
                // Obtiene la columna de jugada a partir de la entrada del jugador
                columna = obtenerMovimiento(jugadorActual, tablero[0].length);
            } else {
                // Turno de la IA
                System.out.println("Turno de la IA...");
                columna = obtenerMovimientoIA(tablero); // Llama a la IA para obtener la jugada
            }

            /**
             * Intenta insertar la ficha del jugador actual en la columna seleccionada.
             * 
             * Si la inserción es válida (columna no llena), verifica si el jugador actual
             * ha ganado
             * 
             * O si tambien el tablero ha quedado lleno (empate). Si no, alterna al otro jugador.
             * Si la columna está llena, se notifica al jugador para que intente otra vez.
            
             */
            if (insertarFicha(tablero, columna, jugadorActual)) {

                /**
                 * Verifica si el jugador actual ha ganado tras insertar su ficha.
                 * Si el jugador ha ganado, termina el juego.
                 */
                if (verificarVictoria(tablero, jugadorActual)) {
                    imprimirTablero(tablero); // Imprime el tablero final
                    System.out.println("¡El jugador " + jugadorActual + " ha ganado!");
                    juegoTerminado = true; // El juego ha terminado debido a una victoria
                }

                /**
                 * Si no hay un ganador, verifica si el tablero está lleno, lo que indica un
                 * empate.
                 * En ese caso, el juego también termina.
                 */
                 
                else if (empate(tablero)) {
                    imprimirTablero(tablero); // Imprime el tablero final
                    System.out.println("¡El juego ha terminado en empate!");
                    juegoTerminado = true; // El juego ha terminado debido a un empate
                }

                // Si no hay victoria ni empate, cambia el turno al otro jugador
                else {
                    jugadorActual = (jugadorActual == jugador_1) ? jugador_2 : jugador_1;
                }
            } else {
                // Notifica al jugador que la columna está llena y debe intentar nuevamente
                System.out.println("Columna llena. Intenta otra vez.");
            }
        }
    }

    /**
     * Método para inicializar el tablero vacío.
     *
     * @param tablero Se inicializa llena de huecos para luego colocar las fichas 
     */
    public static void inicializarTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = hueco;
            }
        }
    }

    /**
     * Método para imprimir el tablero en la consola.
     *
     * @param tablero Ahora lo necesario sera hacer separacion y mostrarlo
     */
    public static void imprimirTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Método para obtener el movimiento del jugador a través de la consola.
     *
     * @param jugador  El carácter que representa al jugador ('X' o 'O').
     * @param columnas El número de columnas del tablero.
     * @return El número de columna seleccionada por el jugador.
     */
    public static int obtenerMovimiento(char jugador, int columnas) {
        Scanner sc = new Scanner(System.in);
        int columna;
        do {
            System.out.println("Jugador " + jugador + ", elige una columna (0 a " + (columnas - 1) + "): ");
            columna = sc.nextInt();
        } while (columna < 0 || columna >= columnas);
        return columna;
    }

    /**
     * Método para insertar la ficha en la columna seleccionada.
     *
     * @param tablero La array de caracteres que representa el tablero del juego.
     * @param columna La columna donde el jugador quiere insertar la ficha.
     * @param jugador El carácter que representa al jugador (por ejemplo, 'X' o 'O').
     * @return true si la ficha se inserta con éxito, false si la columna está llena.
     */
    public static boolean insertarFicha(char[][] tablero, int columna, char jugador) {
        for (int i = tablero.length - 1; i >= 0; i--) {
            if (tablero[i][columna] == hueco) {
                tablero[i][columna] = jugador;
                return true;
            }
        }
        return false; // La columna está llena
    }

    /**
     * Método para verificar si un jugador ha ganado.
     *
     * @param tablero La array de caracteres que representa el tablero del juego.
     * @param jugador El carácter que representa al jugador (por ejemplo, 'X' o 'O').
     * @return true si el jugador ha ganado, false en caso contrario.
     */
    public static boolean verificarVictoria(char[][] tablero, char jugador) {
        // Verificación horizontal
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length - 3; j++) {
                if (tablero[i][j] == jugador && tablero[i][j + 1] == jugador &&
                        tablero[i][j + 2] == jugador && tablero[i][j + 3] == jugador) {
                    return true;
                }
            }
        }

        // Verificación vertical
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j] == jugador &&
                        tablero[i + 2][j] == jugador && tablero[i + 3][j] == jugador) {
                    return true;
                }
            }
        }

        // Verificación diagonal hacia la derecha
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 0; j < tablero[i].length - 3; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j + 1] == jugador &&
                        tablero[i + 2][j + 2] == jugador && tablero[i + 3][j + 3] == jugador) {
                    return true;
                }
            }
        }

        // Verificación diagonal hacia la izquierda
        for (int i = 0; i < tablero.length - 3; i++) {
            for (int j = 3; j < tablero[i].length; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j - 1] == jugador &&
                        tablero[i + 2][j - 2] == jugador && tablero[i + 3][j - 3] == jugador) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Método para verificar si el tablero está lleno, lo que indicaría un empate.
     *
     * @param tablero La array de caracteres que representa el tablero del juego.
     * @return true si todas las columnas están llenas, lo que significa que hay un empate.
     */
    public static boolean empate(char[][] tablero) {
        for (int j = 0; j < tablero[0].length; j++) {
            if (tablero[0][j] == hueco) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método para obtener el movimiento de la IA, seleccionando una columna válida aleatoriamente.
     *
     * @param tablero La matriz de caracteres que representa el tablero del juego.
     * @return El número de columna seleccionado por la IA.
     */
    public static int obtenerMovimientoIA(char[][] tablero) {
        Random rand = new Random();
        int columna;
        do {
            columna = rand.nextInt(tablero[0].length); // Elegir una columna aleatoria
        } while (!columnaValida(tablero, columna));
        return columna;
    }

    /**
     * Método para verificar si la columna elegida es válida para insertar una
     * ficha.
     *
     * @param tablero La array de caracteres que representa el tablero del juego.
     * @param columna El número de columna a verificar.
     * @return true si la columna es válida (no está llena), false en caso contrario.
     */
    public static boolean columnaValida(char[][] tablero, int columna) {
        return tablero[0][columna] == hueco; // Si la fila superior está vacía, la columna es válida
    }
}
