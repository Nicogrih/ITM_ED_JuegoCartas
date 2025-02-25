import java.util.Random;


import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Jugador {

    private int DISTANCIA = 40;
    private int MARGEN = 10;
    private int TOTAL_CARTAS = 10;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random();
    private int MINIMA_CANTIDAD_GRUPO =2;
    private String MENSAJE_PREDETERMINADO ="No se encontraron grupos";
    private String ENCABEZADO_MENSAJE = "SE ENCONTRARON LOS SIGUIENTES GRUPOS:\n";
    private String MENSAJE_ESCALERA = "No se encontraron escaleras.";
    
    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int x = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, x, MARGEN);
            x -= DISTANCIA;
        }

        pnl.repaint();

    }

    public String getGrupos()
    {
        String mensaje = MENSAJE_PREDETERMINADO;
        int[] contadores= new int[NombreCarta.values().length];
        for (Carta carta : cartas)
        {
            contadores[carta.getNombreCarta().ordinal()]++;//ordinal es la posicion de cada carta
        }
        boolean hayGrupos=false;
        for(int contador:contadores)
        {
            if (contador>=MINIMA_CANTIDAD_GRUPO) {
                hayGrupos=true;
                break;
            }
        }
        if (hayGrupos)
        {
            mensaje = ENCABEZADO_MENSAJE;
            int posicion = 0;
            for(int contador:contadores)
            {
                if (contador >= MINIMA_CANTIDAD_GRUPO){
                    mensaje += Grupo.values()[contador] + " de "  + NombreCarta.values()[posicion] +  "\n";
                }
                posicion++;
            }
            
        }
        return mensaje;
    }


    public Carta[] getEscaleras() {
        CartasOrdenadas(); // Ordena por pinta y valor
        Carta[] escalera = new Carta[TOTAL_CARTAS];
        int contador = 0;
    
        for (int i = 0; i < TOTAL_CARTAS - 1; i++) {
            if (contador == 0) {
                escalera[contador] = cartas[i];
                contador++;
            }
    
            if (cartas[i].getPinta() == cartas[i + 1].getPinta() &&
                cartas[i].getNombreCarta().ordinal() + 1 == cartas[i + 1].getNombreCarta().ordinal()) {
                
                escalera[contador] = cartas[i + 1];
                contador++;
    
                if (contador >= 3) { // Si hay escalera valida
                    break;
                }
            } else {
                contador = 0; // Reiniciamos si la secuencia se rompe
            }
        }
    
        // Si no se encontro una escalera valida
        if (contador < 3) {
            return new Carta[0];
        }
    
        // tamaño del arreglo a la cantidad de cartas encontradas
        Carta[] resultado = new Carta[contador];
        for (int i = 0; i < contador; i++) {
            resultado[i] = escalera[i];
        }
    
        return resultado;
    }

    public void CartasOrdenadas() // para ordenar las cartas
    {
        int n = cartas.length;
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - i - 1; j++){
                boolean intercambiar = false;
                if (cartas[j].getPinta().ordinal() > cartas[j+1].getPinta().ordinal()) {
                    intercambiar = true;
                }
                else if(cartas[j].getPinta().ordinal() == cartas[j + 1].getPinta().ordinal() && cartas[j].getNombreCarta().ordinal() > cartas[j + 1].getNombreCarta().ordinal()){
                    intercambiar = true;
                }
                if (intercambiar)
                {
                    Carta temp = cartas[j];
                    cartas[j] = cartas[j + 1];
                    cartas[j + 1] = temp;
                }
            }
        }
            
    }

    public void mostrarEscalera(JPanel pnl) //mostrar la escalera
    {
        pnl.removeAll();
        Carta[] escalera = getEscaleras();// Obtener la escalera antes de mostrarla

        if (escalera.length == 0) {
            JOptionPane.showMessageDialog(null,MENSAJE_ESCALERA);
            return;
        }
        int x = MARGEN + (escalera.length - 1) * DISTANCIA;
        for (Carta carta : escalera) {
            if (carta != null) {
                carta.mostrar(pnl, x, MARGEN);
                x -= DISTANCIA;
            }
        }
        pnl.repaint();
    }
    
    public int ValorCarta (Carta cartas ) //darle el valor a la cartas
    {
        switch (cartas.getNombreCarta()) {
            case AS:
                return 10;
            case JAACK:
                return 10;
            case QUEEN:
                return 10;
            case KING:
                return 10;
            default:
                return cartas.getNombreCarta().ordinal() + 1;
        }
    }

    public int Puntaje()
    {
        int puntaje = 0;
        boolean [] enFigura = new boolean[TOTAL_CARTAS];
        int [] contadores = new int[NombreCarta.values().length];
        Carta [] escalera = getEscaleras();
        for ( Carta carta : cartas ){
            contadores[carta.getNombreCarta().ordinal()] ++ ;
        }
        for ( int i = 0; i < TOTAL_CARTAS; i++){ // por ternas, tercera, cuartas, etc...
            if( contadores[cartas[i].getNombreCarta().ordinal()] >= 3){
                enFigura[i] = true;
            }
        }
        if (escalera != null) { // por escalera
            for( int i= 0; i < escalera.length;i++){
                for( int j= 0; j < TOTAL_CARTAS;j++){
                    if (cartas[j] == escalera[i]){
                        enFigura[j] = true;
                    }
                }
            }
        }
        //Calcular el puntaje fuera de figuras
        for ( int i = 0; i < TOTAL_CARTAS; i++){
            if (!enFigura[i]) {
                puntaje += ValorCarta(cartas[i]);
            }
        }
        return puntaje;
    }
}
