
package conecta4_m12_gerardlorente;
import java.util.Scanner;


import java.util.Random;

public class Conecta4 {
    private static final int FILAS = 6;
    private static final int COLUMNAS = 7;
    private static final char JUGADOR_1 = 'X';
    private static final char JUGADOR_2 = 'O';
    private static final char VACIO = '.';

    public static void main(String[] args) {
        char[][] tablero = new char[FILAS][COLUMNAS];
        inicializarTablero(tablero);
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Elige el modo de juego:");
        System.out.println("1. Jugador vs Jugador");
        System.out.println("2. Jugador vs IA");
        int modoJuego = sc.nextInt();

        boolean juegoTerminado = false;
        char jugadorActual = JUGADOR_1;

        while (!juegoTerminado) {
            imprimirTablero(tablero);
            int columna;

            if (jugadorActual == JUGADOR_1 || modoJuego == 1) {
                columna = obtenerMovimiento(jugadorActual);
            } else {
                System.out.println("Turno de la IA...");
                columna = obtenerMovimientoIA(tablero);
            }

            if (insertarFicha(tablero, columna, jugadorActual)) {
                if (verificarVictoria(tablero, jugadorActual)) {
                    imprimirTablero(tablero);
                    System.out.println("¡El jugador " + jugadorActual + " ha ganado!");
                    juegoTerminado = true;
                } else if (esEmpate(tablero)) {
                    imprimirTablero(tablero);
                    System.out.println("¡El juego ha terminado en empate!");
                    juegoTerminado = true;
                } else {
                    jugadorActual = (jugadorActual == JUGADOR_1) ? JUGADOR_2 : JUGADOR_1;
                }
            } else {
                System.out.println("Columna llena. Intenta otra vez.");
            }
        }
    }

    // Método para inicializar el tablero vacío
    public static void inicializarTablero(char[][] tablero) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tablero[i][j] = VACIO;
            }
        }
    }

    // Método para imprimir el tablero en la consola
    public static void imprimirTablero(char[][] tablero) {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Método para obtener el movimiento del jugador
    public static int obtenerMovimiento(char jugador) {
        Scanner sc = new Scanner(System.in);
        int columna;
        do {
            System.out.println("Jugador " + jugador + ", elige una columna (0 a " + (COLUMNAS - 1) + "): ");
            columna = sc.nextInt();
        } while (columna < 0 || columna >= COLUMNAS);
        return columna;
    }

    // Método para insertar la ficha en la columna seleccionada
    public static boolean insertarFicha(char[][] tablero, int columna, char jugador) {
        for (int i = FILAS - 1; i >= 0; i--) {
            if (tablero[i][columna] == VACIO) {
                tablero[i][columna] = jugador;
                return true;
            }
        }
        return false; // La columna está llena
    }

    // Método para verificar si un jugador ha ganado
    public static boolean verificarVictoria(char[][] tablero, char jugador) {
        // Verificación horizontal
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (tablero[i][j] == jugador && tablero[i][j + 1] == jugador && 
                    tablero[i][j + 2] == jugador && tablero[i][j + 3] == jugador) {
                    return true;
                }
            }
        }

        // Verificación vertical
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j] == jugador &&
                    tablero[i + 2][j] == jugador && tablero[i + 3][j] == jugador) {
                    return true;
                }
            }
        }

        // Verificación diagonal hacia la derecha
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 0; j < COLUMNAS - 3; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j + 1] == jugador &&
                    tablero[i + 2][j + 2] == jugador && tablero[i + 3][j + 3] == jugador) {
                    return true;
                }
            }
        }

        // Verificación diagonal hacia la izquierda
        for (int i = 0; i < FILAS - 3; i++) {
            for (int j = 3; j < COLUMNAS; j++) {
                if (tablero[i][j] == jugador && tablero[i + 1][j - 1] == jugador &&
                    tablero[i + 2][j - 2] == jugador && tablero[i + 3][j - 3] == jugador) {
                    return true;
                }
            }
        }

        return false;
    }

    // Método para verificar si el tablero está lleno (empate)
    public static boolean esEmpate(char[][] tablero) {
        for (int j = 0; j < COLUMNAS; j++) {
            if (tablero[0][j] == VACIO) {
                return false;
            }
        }
        return true;
    }

    // Método para la IA (elige una columna válida aleatoria)
    public static int obtenerMovimientoIA(char[][] tablero) {
        Random rand = new Random();
        int columna;
        do {
            columna = rand.nextInt(COLUMNAS); // Elegir una columna aleatoria
        } while (!columnaValida(tablero, columna));
        return columna;
    }

    // Método para verificar si la columna elegida por la IA es válida
    public static boolean columnaValida(char[][] tablero, int columna) {
        return tablero[0][columna] == VACIO; // Si la fila superior está vacía, la columna es válida
    }
}
