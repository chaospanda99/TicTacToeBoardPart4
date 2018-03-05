// ============================================================================
//     Taken From: http://programmingnotes.org/
// ============================================================================
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class BusinessLogic
{
	public static void GetMove(int currentMove, int remainingMoves, Font font, JButton btnEmpty[], 
			String startingPlayer)
	{// gets the current move "X" or "O" for the user & displays to screen
		btnEmpty[currentMove].setFont(font);
 
		if(startingPlayer.equals("Hioh"))
		{
			if(remainingMoves % 2 != 0)
			{				
				btnEmpty[currentMove].setText("Hioh");

			}
			else
			{
				btnEmpty[currentMove].setText("Pioh");
			}
		}
		else
		{
			if(remainingMoves % 2 != 0)
			{
				btnEmpty[currentMove].setText("Pioh");
			}
			else
			{
				btnEmpty[currentMove].setText("Hioh");
			}
		}
	}// End of GetMove
	
	public static void ShowGame(JPanel pnlSouth, JPanel pnlPlayingField)
	{// shows the Playing Field
		pnlSouth.setLayout(new BorderLayout());
		pnlSouth.add(pnlPlayingField, BorderLayout.CENTER);
		pnlPlayingField.requestFocus();	
	}// End of ShowGame
	
	public static void ClearPanelSouth(JPanel pnlSouth, JPanel pnlTop, 
		 JPanel pnlNewGame, JPanel pnlPlayingField, JPanel pnlBottom, JPanel radioPanel)	
	{// clears any posible panels on screen
		pnlSouth.remove(pnlTop); 
		pnlSouth.remove(pnlBottom);
		pnlSouth.remove(pnlPlayingField);
		pnlTop.remove(pnlNewGame);
		pnlSouth.remove(radioPanel);
	}//	End of ClearPanelSouth 
}