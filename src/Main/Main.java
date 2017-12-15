package Main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import modele.dao.Jdbc;
import vue.*;
import controleur.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author ychantreau
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Jdbc.creer("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:", "@localhost:1521:XE", "", "btssio", "btssio");
        final Properties prop = new Properties();
	InputStream input = null;
        
        try {

            input = new FileInputStream("src/domaine/properties/config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            String pilote = prop.getProperty("pilote");
            String protocole = prop.getProperty("protocole");
            String serveur = prop.getProperty("serveur");
            String base = prop.getProperty("base");
            String login = prop.getProperty("login");
            String mdp = prop.getProperty("mdp");
            Jdbc.creer(pilote, protocole, serveur, base, login, mdp);
            try {
                Jdbc.getInstance().connecter();
                VueRepresentation uneVue = new VueRepresentation();
                //VueLesClients vueClient = new VueLesClients();
                CtrlLesRepresentations unControleur = new CtrlLesRepresentations(uneVue);
                //CtrlLesClients controleurClient = new CtrlLesClients(vueClient);
                VueMenu vuMenu = new VueMenu();
                CtrlPrincipal unCtrlPrincipal = new CtrlPrincipal(vuMenu);
                // afficher la vue
                vuMenu.setVisible(true);
                uneVue.setVisible(false);
                //vueClient.setVisible(true);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Main - classe JDBC non trouvée");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Main - échec de connexion");
            }

        } catch (final IOException ex) {
            ex.printStackTrace();
	} finally {
            if (input != null) {
		try {
                    input.close();
		} catch (final IOException e) {
                    e.printStackTrace();
		}
            }
        }
        
        }
        
    }
