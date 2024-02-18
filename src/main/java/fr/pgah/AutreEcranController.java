package fr.pgah;

import java.io.IOException;
import javafx.fxml.FXML;

public class AutreEcranController {

  @FXML
  private void btnRetour() throws IOException {
    App.setRoot("liste_visiteurs");
  }
}
