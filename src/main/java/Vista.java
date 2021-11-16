import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase Vista, se genera la interfaz para el usuario
 */
public class Vista {
    private JPanel panel;
    private JTextField textField_Longitud;
    private JTextField textField_Numero;
    private JTextField textField_Minimo;
    private JTextField textField_Maximo;
    private JButton boton_RealizarOperacion;
    private JButton boton_BorrarDatos;
    private JLabel etiqueta_Titulo;
    private JLabel etiqueta_Longitud;
    private JLabel etiqueta_Numero;
    private JLabel etiqueta_Min;
    private JLabel etiqueta_Max;
    private JLabel etiqueta_Datos;
    private JLabel etiqueta_Resultados;
    private JLabel etiqueta_Rango;
    private JTable tabla_DatosCompletos;
    private JTable tabla_Busqueda;
    private JScrollPane scroll_Resultados;
    private JScrollPane scroll_Busqueda;

    private Object[][] matriz;
    private final Object[] columnas = new Object[]{"Posición", "Número"};
    private DefaultTableModel modelo;

    private Object[][] matriz_Busqueda;
    private final Object[] columnas_Busqueda = new Object[]{"Números encontrados"};
    private DefaultTableModel modelo_Busqueda;

    /**
     * Método constructor donde se definen las funciones de los botones y características de la vista
     */
    public Vista() {
        etiqueta_Titulo.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        etiqueta_Rango.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        // Asignación de íconos a los JLabel
        etiqueta_Min.setIcon(Iconos.Companion.getMenor());
        etiqueta_Max.setIcon(Iconos.Companion.getMayor());
        etiqueta_Numero.setIcon(Iconos.Companion.getLupa());
        etiqueta_Longitud.setIcon(Iconos.Companion.getLongitud());

        // ActionListener, funciona para iniciar las operaciones al momento de ser presionado, imprime los datos
        // obtenidos a través de las operaciones realizadas
        boton_RealizarOperacion.addActionListener(e -> {
            try {
                if (textField_Longitud.getText().equals("") || textField_Maximo.getText().equals("") || textField_Minimo.getText().equals("") || textField_Numero.getText().equals("")) {
                    throw new NullPointerException();
                }

                // Obtiene los valores de los JTextField
                int longitud = Integer.parseInt(textField_Longitud.getText());
                int numero_ABuscar = Integer.parseInt(textField_Numero.getText());
                int minimo = Integer.parseInt(textField_Minimo.getText());
                int maximo = Integer.parseInt(textField_Maximo.getText());

                // Comprueba que los datos escritos sean admitidos
                Excepciones_Generales.Companion.comprobar_Rango(minimo, maximo);
                Excepciones_Generales.Companion.restringir_Negativos(longitud);

                // Realiza la instancia de la clase Lista
                Lista lista = new Lista(longitud, minimo, maximo, numero_ABuscar);

                // Se guardan los datos de la lista muteable a la primera JTable donde vendrán todos los valores generados
                for (int i = 0; i < lista.getLista().size(); i++) {
                    Object[] temp = {"Lista en Posición " + i + " ", lista.getLista().get(i)};
                    modelo.addRow(temp);
                }

                // Guarda los resultados de la búsqueda en la segunda JTable
                for (int i = 0; i < lista.getLista_Busqueda().size(); i++) {
                    Object[] temp = {"Es " + lista.getBuscar() + " en la posición: " + lista.getLista_Busqueda().get(i)};
                    modelo_Busqueda.addRow(temp);
                }

                // Bloquea el botón de "Realizar Operación" al momento de mostrar todos los resultados
                boton_RealizarOperacion.setEnabled(false);
            } catch (NumberFormatException numberFormatException) {
                generar_MensajesDeError("Ingrese solo números en las casillas");
            } catch (NullPointerException nullPointerException) {
                generar_MensajesDeError("No deje casillas vacías");
            } catch (Excepciones_Generales excepciones) {
                generar_MensajesDeError(excepciones.getMessage());
            };

        });

        // Botón para borrar todos los valores en la vista
        boton_BorrarDatos.addActionListener(e -> {
            boton_RealizarOperacion.setEnabled(true);
            modelo = new DefaultTableModel(matriz, columnas);
            tabla_DatosCompletos.setModel(modelo);

            modelo_Busqueda = new DefaultTableModel(matriz_Busqueda, columnas_Busqueda);
            tabla_Busqueda.setModel(modelo_Busqueda);

            propiedades_DeTablas();
            borrar_ContenidoDeCasillas();
        });
    }

    /**
     * Clase principal para ejecutar el programa
     * @param args argumentos de la clase
     */
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Generador de Lista Aleatoria y Búsqueda");
        ventana.setContentPane(new Vista().panel);
        ventana.pack();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

    }

    /**
     * Inicializa los valores de las tablas para tener una vista normal antes de realizar cualquier operación
     */
    private void createUIComponents() {
        matriz = new Object[][]{};
        modelo = new DefaultTableModel(matriz, columnas);
        tabla_DatosCompletos = new JTable(modelo);

        matriz_Busqueda = new Object[][]{};
        modelo_Busqueda = new DefaultTableModel(matriz_Busqueda, columnas_Busqueda);
        tabla_Busqueda = new JTable(modelo_Busqueda);

        propiedades_DeTablas();

    }

    /**
     * Método para asignar propiedades específicas a los JTable
     */
    private void propiedades_DeTablas() {
        tabla_DatosCompletos.getColumn("Número").setPreferredWidth(10);
        tabla_DatosCompletos.getTableHeader().setReorderingAllowed(false);
        tabla_Busqueda.getTableHeader().setReorderingAllowed(false);

        tabla_DatosCompletos.getTableHeader().setBackground(new Color(85, 89, 84));
        tabla_Busqueda.getTableHeader().setBackground(new Color(85, 89, 84));

        tabla_DatosCompletos.getTableHeader().setForeground(new Color(206, 203, 203));
        tabla_Busqueda.getTableHeader().setForeground(new Color(206, 203, 203));
    }

    /**
     * Método para inicializar valoes en los JTextField, es usado cuando se reinician los valores
     */
    private void borrar_ContenidoDeCasillas() {
        textField_Longitud.setText("");
        textField_Maximo.setText("");
        textField_Minimo.setText("");
        textField_Numero.setText("");
    }

    /**
     * Método que genera un mensaje de error en un JOptionPane dependiendo de la excepción que se genere
     * @param mensaje mensaje que tendrá
     */
    private void generar_MensajesDeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE, Iconos.Companion.getRoboterror());
    }
}
