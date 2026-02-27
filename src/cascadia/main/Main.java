package cascadia.main;



import cascadia.game.GameLoop;
import cascadia.game.GamePlay;
import cascadia.game.GraphicGameLoop;

public class Main {

  public static void main(String[] args) {
  		String firstPlayerName = GamePlay.askName();
  		String secondPlayerName = GamePlay.askName();
  		boolean isGraphicMode = GamePlay.askGameVersion();
  		boolean isFamilyVariant = GamePlay.askVariant();
  		
  		if(isGraphicMode) {
  			GraphicGameLoop.runGame(firstPlayerName,secondPlayerName,isFamilyVariant);
  		}
  		else {
  			GameLoop gameLoop = new GameLoop(firstPlayerName, secondPlayerName, isFamilyVariant);
  			gameLoop.runGame();
  		}
      
  }


}
  






  


