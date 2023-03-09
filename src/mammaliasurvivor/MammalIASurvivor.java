package mammaliasurvivor;

/**
 *
 * @author AyA
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MammalIASurvivor extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static final int ANCHO = 800;
    private static final int ALTO = 600;
    private static final int TIEMPO_ESPECIE = 200; // tiempo en milisegundos para la aparición de nuevas especies
    private static final int CANTIDAD_INICIAL_MAMIFEROS = 5;
    private static final int CANTIDAD_INICIAL_ESPECIES = 10;
    private ArrayList<Especie> especies;
    private ArrayList<Mamifero> mamiferos;
    private Timer timer;
    private Random random;

    public MammalIASurvivor() {
        especies = new ArrayList<>();
        mamiferos = new ArrayList<>();
        random = new Random();
        setBackground(Color.WHITE);
        setBounds(0, 0, ANCHO, ALTO);
        setLayout(null);
        inicializarEspecies();
        inicializarMamiferos();
        timer = new Timer(TIEMPO_ESPECIE, this);
        timer.start();
    }

    private void inicializarEspecies() {
        for (int i = 0; i < CANTIDAD_INICIAL_ESPECIES; i++) {
            Especie especie = new Especie(random.nextInt(ANCHO - 40), random.nextInt(ALTO - 40));
            especies.add(especie);
        }
    }

    private void inicializarMamiferos() {
        for (int i = 0; i < CANTIDAD_INICIAL_MAMIFEROS; i++) {
            Mamifero mamifero = new Mamifero(random.nextInt(ANCHO - 40), random.nextInt(ALTO - 40));
            mamiferos.add(mamifero);
        }
    }

    public boolean estaComiendo(Especie e, Mamifero m) {
        return (e.getBody().intersects(m.body.getBounds()));
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (Mamifero mamifero : mamiferos) {
            mamifero.pintar(g);
        }

        for (Especie especie : especies) {
            especie.pintar(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            Especie especie = new Especie(random.nextDouble(ANCHO - 40), random.nextDouble(ALTO - 40));
            for (Mamifero m : mamiferos) {
                if (estaComiendo(especie, m)) {
                    especie.setAtacado(true);
                    System.out.println("Un mamífero se ha comido una especie");
                }
            }
            especies.add(especie);
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulacion IA");
        MammalIASurvivor simulacion = new MammalIASurvivor();
        frame.getContentPane().add(simulacion);
        frame.setSize(ANCHO, ALTO);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class Especie {

    private double x;
    private double y;
    private static final double ANCHO = 20.0;
    private static final double ALTO = 20.0;
    private boolean atacado;
    Ellipse2D.Double body;

    public Especie(double x, double y) {
        this.x = x;
        this.y = y;
        this.atacado = false;
        body = new Ellipse2D.Double(x, y, ANCHO, ALTO);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static double getANCHO() {
        return ANCHO;
    }

    public static double getALTO() {
        return ALTO;
    }

    public Ellipse2D.Double getBody() {
        return body;
    }

    public boolean isAtacado() {
        return atacado;
    }

    public void setAtacado(boolean atacado) {
        this.atacado = atacado;
    }

    public void pintar(Graphics g) {
        if (g != null) {
            Graphics2D gr = (Graphics2D) g;
            if(isAtacado())
                gr.setColor(Color.orange.brighter());
            else
                gr.setColor(Color.blue);
            gr.fill(body);
        }
    }
}

class Mamifero extends Especie {

    public Mamifero(int x, int y) {
        super(x, y);
    }

    @Override
    public void pintar(Graphics g) {
        if (g != null) {
            Graphics2D gr = (Graphics2D) g;
            gr.setColor(Color.red);
            gr.fill(body);
        }
    }
}
