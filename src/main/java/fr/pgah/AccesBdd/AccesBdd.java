package fr.pgah.AccesBdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.pgah.Logger.Logger;
import fr.pgah.Model.Visiteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccesBdd {

  private static String nom = "gsb_etudiants";
  private static String serveur = "localhost";
  private static int port = 3306;
  private static String urlConnexion = "jdbc:mysql://" + serveur + ":" + port + "/" + nom;
  private static String nomUtilisateur = "appadmin";
  private static String mdpUtilisateur = "MonpasswdAPPADMIN";

  /**
   * Retourne une connexion à la base de données.
   *
   * @return une connexion à la base de données
   */
  public static Connection getConnection() {

    Connection conn = null;
    try {
      conn = DriverManager.getConnection(urlConnexion, nomUtilisateur, mdpUtilisateur);
      if (conn != null) {
        Logger.log("Connexion OK");
      }
    } catch (SQLException ex) {
      Logger.log("Connexion BDD : échec");
      Logger.log(ex.getMessage());
      return null;
    }
    return conn;
  }

  /**
   * Retourne la liste des visiteurs depuis la BDD.
   *
   * @return la liste de visiteurs
   */
  public static ObservableList<Visiteur> getVisiteurs() {
    Connection conn = getConnection();
    // On sort immédiatement si la connexion a échoué
    if (conn == null) {
      return null;
    }

    // La liste qui va être renvoyée par la méthode
    ObservableList<Visiteur> visiteurs = FXCollections.observableArrayList();
    // Code de requête SQL pour récupérer les visiteurs encapsulé dans une structure try/catch
    // (l'accès à la BDD peut échouer)
    try {
      String sql = "SELECT vi_matricule, vi_nom, vi_prenom FROM visiteur";
      Statement requete = conn.createStatement();
      ResultSet res = requete.executeQuery(sql);
      // On parcourt le ResultSet avec un while pour traiter chaque visiteur récupéré
      while (res.next()) {
        // On récupère chaque propriété du visiteur dans des variables
        String matricule = res.getString("vi_matricule");
        String nom = res.getString("vi_nom");
        String prenom = res.getString("vi_prenom");
        // On instancie un objet Visiteur avec ces propriétés
        Visiteur v = new Visiteur(matricule, nom, prenom);
        // Et on l'ajoute à la liste
        visiteurs.add(v);
      }
    } catch (SQLException ex) { // le bloc catch est exécuté en cas d'erreur survenue dans le try
      Logger.log("Récupération visiteurs : échec.");
      Logger.log(ex.getMessage());
      return null;
    }
    // Finalement on renvoie à l'appelant la liste de visiteurs construite
    Logger.log("Récupération visiteurs OK");
    return visiteurs;
  }
}
