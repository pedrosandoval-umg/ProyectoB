package umg.proyectob.io;

import umg.proyectob.Usuario;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LectorUsuarios {

    public static List<Usuario> leerDesdeArchivo(String rutaArchivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try {
            // Preparamos el parser XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Cargamos y normalizamos el archivo XML
            Document doc = builder.parse(rutaArchivo);
            doc.getDocumentElement().normalize();

            // Obtenemos todos los nodos <usuario>
            NodeList listaUsuarios = doc.getElementsByTagName("usuario");

            // Recorremos cada nodo <usuario> para extraer sus campos
            for (int i = 0; i < listaUsuarios.getLength(); i++) {
                Node nodo = listaUsuarios.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;

                    // Extraemos los campos del XML
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    String usuario = elemento.getElementsByTagName("nombreUsuario").item(0).getTextContent();
                    String contrasena = elemento.getElementsByTagName("contraseña").item(0).getTextContent();
                    String rolTexto = elemento.getElementsByTagName("rol").item(0).getTextContent();

                    // Convertimos el texto del rol en entero
                    int rol = rolTexto.equalsIgnoreCase("Administrador") ? 1 : 2;

                    // Creamos y agregamos el usuario a la lista
                    usuarios.add(new Usuario(nombre, usuario, contrasena, rol));
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer XML: " + e.getMessage());
        }

        return usuarios;
    }

    public static List<Usuario> leerConSelector() {
        // Crea un JFileChooser para que el usuario seleccione el archivo XML
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo XML de usuarios");

        int resultado = selector.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            return leerDesdeArchivo(selector.getSelectedFile().getAbsolutePath());
        }

        return new ArrayList<>(); // Si el usuario cancela, retorna lista vacía
    }

    public static void guardarComoXML(List<Usuario> usuarios, String rutaArchivo) {
        try {
            // Preparamos el documento XML desde cero
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Creamos el nodo raíz <usuarios>
            Element raiz = doc.createElement("usuarios");
            doc.appendChild(raiz);

            // Recorremos la lista de usuarios y generamos el XML
            for (Usuario u : usuarios) {
                Element usuarioElem = doc.createElement("usuario");

                // Creamos subelementos para cada campo
                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(u.getNombre()));
                usuarioElem.appendChild(nombre);

                Element usuario = doc.createElement("nombreUsuario");
                usuario.appendChild(doc.createTextNode(u.getUsuario()));
                usuarioElem.appendChild(usuario);

                Element contrasena = doc.createElement("contraseña");
                contrasena.appendChild(doc.createTextNode(u.getPassword()));
                usuarioElem.appendChild(contrasena);

                Element rol = doc.createElement("rol");
                rol.appendChild(doc.createTextNode(u.getRol() == 1 ? "Administrador" : "Vendedor"));
                usuarioElem.appendChild(rol);

                // Agregamos <usuario> al nodo raíz
                raiz.appendChild(usuarioElem);
            }

            // Preparamos el transformador que escribe el XML al archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // Le damos formato bonito (con indentación)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            // Indicamos que se escriba en la ruta proporcionada
            DOMSource source = new DOMSource(doc);
            FileOutputStream output = new FileOutputStream(rutaArchivo);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);
            output.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar XML: " + e.getMessage());
        }
    }

    public static void guardarConSelector(List<Usuario> usuarios) {
        // Abre un JFileChooser para seleccionar dónde guardar el XML
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar usuarios como XML");

        int resultado = selector.showSaveDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();

            // Asegura extensión .xml
            if (!ruta.endsWith(".xml")) {
                ruta += ".xml";
            }

            // Llama al método para generar y guardar el XML
            guardarComoXML(usuarios, ruta);
        }
    }
}