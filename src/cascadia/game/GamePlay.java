package cascadia.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * Manage the players interactions with the game.
 */

public class GamePlay {
	
  /**
   * Asks the user to enter a player name.
   *
   * @return the entered player name
   */
	public static String askName() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String scannedName = null;

    while (scannedName == null || scannedName.isBlank()) {
        try {
            System.out.println("Choisir le pseudo du joueur :");
            scannedName = reader.readLine().trim(); 
            if (scannedName.isBlank()) {
                System.out.println("Le pseudo ne peut pas être vide. Veuillez entrer un pseudo valide.");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de l'entrée : " + e.getMessage());
        }
    }
    return scannedName;
}
  
  
  /**
   * Asks the user to enter the names of two players.
   *
   * @return a list containing the names of the two players
   */
  public static List<String> askNames() {
      String firstPlayerName, secondPlayerName;
      firstPlayerName = askName();
      secondPlayerName = askName();
      return List.of(firstPlayerName, secondPlayerName);
  }
  
  
  /**
   * Asks the user to choose the game variant.
   *
   * @return true if the family variant is chosen, false otherwise
   */
  public static boolean askGameVersion() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int scannedVal = -1; 

    while (scannedVal != 0 && scannedVal != 1) {
        System.out.println("Choisir la version Graphique en tapant 1 ou Console en tapant 0:");
        try {
            String input = reader.readLine(); 
            scannedVal = Integer.parseInt(input.trim()); 
            if (scannedVal != 0 && scannedVal != 1) {
                System.out.println("Valeur invalide. Veuillez entrer 0 ou 1.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez saisir un nombre entier.");
        } catch (IOException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
            break; 
        }
    }
    return scannedVal == 1; 
}
  
  
  /**
   * Asks the user to choose the game variant.
   *
   * @return true if the family variant is chosen, false otherwise
   */
public static boolean askVariant() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int scannedVal = -1;

    while (scannedVal != 1 && scannedVal != 0) {
        System.out.println("Choisir la variante Familiale en tapant 1 ou Intermédiaire en tapant 0:");
        try {
            String input = reader.readLine().trim(); 
            scannedVal = Integer.parseInt(input); 
            if (scannedVal != 1 && scannedVal != 0) {
                System.out.println("Valeur invalide. Veuillez entrer 0 ou 1.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez saisir un nombre entier (0 ou 1).");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de l'entrée : " + e.getMessage());
            break; 
        }
    }
    return scannedVal == 1; 
}

  /**
   * Asks the user to choose a lot from the available options.
   *
   * @return the index of the chosen lot
   */
  public static int askChooseLot() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int scannedVal = -1; 

    while (scannedVal < 0 || scannedVal > 3) {
        System.out.println("Choisir parmi les 4 lots en tapant 0, 1, 2, ou 3 :");
        try {
            String input = reader.readLine().trim(); 
            scannedVal = Integer.parseInt(input); 
            if (scannedVal < 0 || scannedVal > 3) {
                System.out.println("Valeur invalide. Veuillez entrer un nombre entre 0 et 3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. Veuillez saisir un nombre entier (0, 1, 2 ou 3).");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de l'entrée : " + e.getMessage());
            break; 
        }
    }
    return scannedVal;
}



}
