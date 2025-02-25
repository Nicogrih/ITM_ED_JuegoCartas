import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Carta
{
    private int Indice;

    public Carta (Random r)
    {
        Indice = r.nextInt(52) + 1;
    }

    public int getIndice() {
        return Indice;
    }
        
    public Pinta getPinta()
    {
        if (Indice<=13)
        {
            return Pinta.TREBOL;
        }else if (Indice <=26) {
            return Pinta.PICA;
        }else if (Indice<=39) {
            return Pinta.CORAZON;
        }else {
            return Pinta.DIAMANTE;
        }
    }

    public NombreCarta getNombreCarta()
    {
        int posicion = Indice % 13;
        if (posicion == 0){
            posicion= 13;
        }
        return NombreCarta.values()[posicion-1];
    }

    public void mostrar(JPanel pn1, int x, int y)
    {
        String nombreArchivo = "/Imagenes/CARTA" + String.valueOf(Indice) +".jpg";
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreArchivo));
        JLabel lbl = new JLabel(imagen);
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());
        pn1.add(lbl);
    }
}
