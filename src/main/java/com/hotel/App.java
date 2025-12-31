package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            System.out.println("Seleccione una opció: ");
            opcio = sc.nextInt();
            sc.nextLine();
            //llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus(){
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       //TODO:
        switch (opcio) {
            case 1:
                reservarHabitacio();
                break;
            case 2:
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                obtindreReservaPerTipus();
                break;
            case 5:
                obtindreReserva();
                break;
            case 6:
                break;
            default:
                System.out.println("Opció no vàlida, introduïsca un número de l'1 al 6");
                break;
        }
       //demanar opció i asociarla a un valor del 1 al 5
       //retornar opció seleccionada
       //
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        //TODO:
        //devuelve tipo de habitacion
        String tipusHabitacio = seleccionarTipusHabitacioDisponible();
        //devuelve arrraylist de servicios
        ArrayList<String> serveisSeleccionats = seleccionarServeis();
        //devuelve precio total
        float preuTotal = calcularPreuTotal(tipusHabitacio, serveisSeleccionats);
        String preuTot = Float.toString(preuTotal);
        //crear arraylist con estos datos
        ArrayList<String> infoReserves = new ArrayList<>(); 
        infoReserves.add(tipusHabitacio); // pos 0
        infoReserves.add(preuTot);      // pos 1

            for (int i = 0; i < serveisSeleccionats.size(); i++) {
                String servei = serveisSeleccionats.get(i);
            infoReserves.add(servei);     // pos 2, 3, 4, 5
            }

        System.out.println("Reserva creada amb èxit!");
        //devuelve codigo
        int codi=generarCodiReserva();
        System.out.println("Codi de reserva: "+codi);
        reserves.put(codi,infoReserves);

    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        //TODO:
        System.out.println("Seleccione tipus d’habitació:");
        // tomar el tipo de habitacion
        int tipusDeHabitaciot = sc.nextInt();
        sc.nextLine();
                //si disponibilidad es igual mayor a 0 retornar el número selecc
        switch (tipusDeHabitaciot){
            case 1: 
                return TIPUS_ESTANDARD; 
            case 2:
                return TIPUS_SUITE; 
            case 3: 
                return TIPUS_DELUXE; 
            default:
                System.out.println("Per favor, introduïsca un número de l'1 al 3");
                return null;
        }            
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        //TODO:
        //Tipus d’habitació disponibles:
        //Estàndard - 30 disponibles - 50€
        System.out.println("1. " + TIPUS_ESTANDARD + " - " + disponibilitatHabitacions.get(TIPUS_ESTANDARD) + " disponibles - " + preusHabitacions.get(TIPUS_ESTANDARD) + "€");
        //Suite - 20 disponibles - 100€
        System.out.println("2. " + TIPUS_SUITE + " - " + disponibilitatHabitacions.get(TIPUS_SUITE) + " disponibles - " + preusHabitacions.get(TIPUS_SUITE) + "€");
        //Deluxe - 10 disponibles - 150€
        System.out.println("3. " + TIPUS_DELUXE + " - " + disponibilitatHabitacions.get(TIPUS_DELUXE) + " disponibles - " + preusHabitacions.get(TIPUS_DELUXE) + "€");
        
        //Seleccione tipus d’habitació: 2
        //toma la opción del usuario y retorna el tipo de hab seleccionada
        String tipusHabitacio = seleccionarTipusHabitacio(); 
        
        Integer actual = disponibilitatHabitacions.get(tipusHabitacio);

                if (actual != null && actual > 0){
                    disponibilitatHabitacions.put(tipusHabitacio, actual - 1);
                    System.out.println("Has seleccionat l'habitació"+ tipusHabitacio);
                    // ¿que retorna?
                    return tipusHabitacio;
                }else{
                    System.out.println("No hi ha disponibilitat d'aquest tipus d'habitació.");
                    return null;
                }
            
        
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        //TODO:
        /**Serveis addicionals
        public static final String SERVEI_ESMORZAR = "Esmorzar";
        public static final String SERVEI_GIMNAS = "Gimnàs";
        public static final String SERVEI_SPA = "Spa";
        public static final String SERVEI_PISCINA = "Piscina";

        Scanner únic
        public static Scanner sc = new Scanner(System.in);

        hashmap preus
        public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

        */

        ArrayList<String> serveisSeleccionats = new ArrayList<>();
        System.out.println("Serveis addicionals (0-4):");
        System.out.println("0. Finalitzar");
        System.out.println("1. Esmorzar (10\u20AC)");
        System.out.println("2. Gimnàs (15\u20AC)");
        System.out.println("3. Spa (20\u20AC)");
        System.out.println("4. Piscina (25\u20AC)");
   
        //bucle do while
        String respuesta;

        do {
            System.out.println("Vol afegir un servei? (s/n):");
            respuesta = sc.nextLine().trim();

            if(respuesta.equalsIgnoreCase("s")){
                System.out.println("Seleccione servei:");
                int tipoServei = sc.nextInt();
                sc.nextLine();
                String nouServei = null;
                switch(tipoServei){
                    case 0:
                        respuesta = "n"; // para salir del do while
                        break;
                    case 1:
                        nouServei = SERVEI_ESMORZAR;
                        break;
                    case 2:
                        nouServei = SERVEI_GIMNAS;
                        break;
                    case 3:
                        nouServei = SERVEI_SPA;
                        break;
                    case 4:
                        nouServei = SERVEI_PISCINA;
                        break;
                    default:
                        System.out.println("Opció no vàlida");
                }
                if (nouServei != null) {
                    if (!serveisSeleccionats.contains(nouServei)) {
                        serveisSeleccionats.add(nouServei);
                        System.out.println("Servei afegit: " + nouServei);
                    }   else {
                        System.out.println("Servei ja seleccionat");
                        }
                }
            }
        } while (respuesta.equalsIgnoreCase("s"));                    
        
        return serveisSeleccionats;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        //TODO:
        System.out.println("Calculem el total...");
        float preuTotal = 0f;
        System.out.println("Preu habitació: "+preusHabitacions.get(tipusHabitacio));
        System.out.println("Serveis: ");
        for (int i = 0; i < serveisSeleccionats.size(); i++) {
            String servei = serveisSeleccionats.get(i);
            Float preu = preusServeis.get(servei);
            System.out.print(servei + " (" + preu + "\u20AC)");
            //para que aparezca con comas y no imprima la ultima
                if (i < serveisSeleccionats.size() - 1) {
                    System.out.print(", ");
                }
        }
        System.out.println();
        // precio habitación, considerar que el campo podria estar vacio
        Float preuHab = preusHabitacions.get(tipusHabitacio);
        if (preuHab != null) {
        preuTotal += preuHab;
        }
        // precio servicios
        for (int i = 0; i < serveisSeleccionats.size(); i++) {
            String servei = serveisSeleccionats.get(i);
            Float preuServei = preusServeis.get(servei);
            if (preuServei != null) {
                preuTotal += preuServei;
            }
        }
        System.out.println("Subtotal: "+preuTotal+"\u20AC");
        Float preuIVA=preuTotal*IVA;
        Float preuTotalIVA=preuTotal+preuIVA;
        System.out.println("IVA (21%): "+preuIVA+"\u20AC");
        System.out.println("TOTAL: "+preuTotalIVA+"\u20AC");
        return preuTotalIVA;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        /**TODO:
        Generador de nombres aleatoris per als codis de reserva
        public static Random random = new Random();
        */
        int codi; 
            do {
                codi = random.nextInt(900) + 100;
            } while (reserves.containsKey(codi));
        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva
        System.out.println("Introdueix el codi de la reserva:");
            int codi = sc.nextInt();
            sc.nextLine(); // limpiar buffer

        if (reserves.containsKey(codi)) {
            // liberar la habitación
            System.out.println("Reserva trobada!");
            // incrementar disponibilidad
            String tipusHabitacio = reserves.get(codi).get(0);
            // eliminar la reserva del hashmap
            reserves.remove(codi);
            System.out.println("Habitació alliberada correctament.");
            // incrementar disponibilidad
            Integer actual = disponibilitatHabitacions.get(tipusHabitacio);
            disponibilitatHabitacions.put(tipusHabitacio, actual + 1);
            System.out.println("Disponibilitat actualitzada.");
        } else {
            System.out.println("No existeix cap reserva amb aquest codi.");
            }
    }
    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
    System.out.println("\n===== DISPONIBILITAT D'HABITACIONS ====="); 
    //hab lliures es igual al disponible
 // Capacitats inicials
   
    //hab ocupadas es el total menos el disponible
    System.out.println("\nTipus: "+TIPUS_ESTANDARD+" \tLliures: "+disponibilitatHabitacions.get(TIPUS_ESTANDARD)+" \tOcupades: "+(capacitatInicial.get(TIPUS_ESTANDARD)-disponibilitatHabitacions.get(TIPUS_ESTANDARD))); 
    System.out.println("\nTipus: "+TIPUS_SUITE+" \tLliures: "+disponibilitatHabitacions.get(TIPUS_SUITE)+" \tOcupades: "+(capacitatInicial.get(TIPUS_SUITE)-disponibilitatHabitacions.get(TIPUS_SUITE)));
    System.out.println("\nTipus: "+TIPUS_DELUXE+" \tLliures: "+disponibilitatHabitacions.get(TIPUS_DELUXE)+" \tOcupades: "+(capacitatInicial.get(TIPUS_DELUXE)-disponibilitatHabitacions.get(TIPUS_DELUXE)));
    }

    /**
     * Tipus	Lliures	Ocupades
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         // TODO: Implementar recursivitat
        
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
        System.out.println("\nIntrodueix el codi de reserva:");
        int codi = sc.nextInt();
            sc.nextLine(); // limpiar buffer
        mostrarDadesReserva(codi);
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
   
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva
        if(reserves.containsKey(codi)) {
            System.out.println("\nDades de la reserva:");
            System.out.println("\nTipus d'habitació: "+reserves.get(codi).get(0));
            System.out.println("\nCost total: "+reserves.get(codi).get(1)+"€");
            System.out.println("\nServeis addicionals:");
            // Servicios desde la posición 2 en adelante
                for (int i = 2; i < reserves.get(codi).size(); i++) {
                    if (reserves.get(codi).get(i) != null && !reserves.get(codi).get(i).isEmpty()) {
                    System.out.println("          * " + reserves.get(codi).get(i));
                    }
                }
        } else {System.out.println("\nNo s'ha trobat cap reserva amb aquest codi.");}
}
    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
