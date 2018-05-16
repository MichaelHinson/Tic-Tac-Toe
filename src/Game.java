import java.util.Random;
import java.util.Scanner;

public class Game {
 /*
Had a late thought to maybe try and create some sort of algorithm that tries to read the array and determine where to place O.
Aka if it does not need to block or win it would look and see where it should place to gain an advantage.
Need more thought on this, could shorten logic section significantly as it would be easier to loop
 
 Board's
 Player entered values    Array Placement
  1|2|3						  0|1|2
 --|-|--					 --|-|--
  4|5|6						  3|4|5
 --|-|--					 --|-|--
  7|8|9 					  6|7|8

 */
	public static Random rand = new Random();
	public static int playerWins = 0, computerWins = 0;
	public static Scanner kb = new Scanner(System.in);
public static void main(String[] args)
{
	int fullLoop = 0;
	String input = "";
	
	//Title and player symbol assignment
	System.out.println("\t\t\tTic Tac Toe");
	System.out.println("Player is X and computer is O\n");
	
	do//Replay game loop
	{
		char c[] = { ' ',' ',' ',' ',' ',' ',' ',' ',' '};//Array storing the slots on the board
		int playerChoice = 0;
		int npcChoice = 0;
		int loop = 0; //Inner loop containing gameplay
		

		boardBuilder(c);//Displays board
		while (loop >= 0)
		{
			//Prompt for player choice and input
			System.out.print("Select a position on the board:  ");
			input = kb.nextLine();
			playerChoice = (int) Double.parseDouble(input);
			
			playerChoice = playerVacancyChecker(c, playerChoice);//Ensures player can not overwrite an occupied slot
			c[playerChoice - 1] = 'X';//Once choice is unoccupied puts an x in correct array element

			loop = winChecker(c, 1);//Returns -1 if game is complete
			if (loop >= -1)//Won't execute if game is won by player or tied
			{
				npcChoice = smartComputer(c, 'O');//Returns -1 if no options were valid. Checks to see if computer can win
				if (npcChoice == -1)
				{
					npcChoice = smartComputer(c, 'X');//Checks to see if player can win and computer must block
					if (npcChoice == -1)
					{
						npcChoice = computerVacancyChecker(c, npc());//Prevents computer from overwriting occupied slots
					}
				}
				c[npcChoice - 1] = 'O';//Assigns computer choice to board
				loop = winChecker(c, 2);//Checks to see if computer won
			}
			boardBuilder(c);//Builds board
			
		}
		System.out.println("\n\nPlayer Wins: " + playerWins + " Computer Wins: " + computerWins);
		System.out.println("Would you like to play again?   1.Yes 2.No");
		input = kb.nextLine();
		fullLoop = (int) Double.parseDouble(input);

	} while (fullLoop == 1);
}
static void boardBuilder(char c[])//builds example board and current board
{
	System.out.println(" 1|2|3 \t" + " " + c[0] + "|" + c[1] + "|" + c[2]);
	System.out.println("--|-|--\t" + "--|-|--");
	System.out.println(" 4|5|6 \t" + " " + c[3] + "|" + c[4] + "|" + c[5]);
	System.out.println("--|-|--\t" + "--|-|--");
	System.out.println(" 7|8|9 \t" + " " + c[6] + "|" + c[7] + "|" + c[8]);
}
static int npc()//Random section for if no logic applies
{
	int cpuChoice = rand.nextInt(9) + 1;

	return cpuChoice;
}
static int smartComputer(char c[], char letter)//Computer Logic section
{
	int value;//All values returned are one higher than desired array element as this allowed me to maintain parity with player selection methods
	//Could change to array value if I modify gameplay loop slightly(Use npcChoice instead of npcChoice-1 and modifiy starting point of npc() Roll)
	if (c[0] == ' ')
		value = 1;
	else if (c[4] == ' ' && c[0] == letter)//Attempts to place in the middle if it is not occupied on first move
		value = 5;

	//Horizontal Top Row
	else if (c[0] == c[1] && c[0] == letter && c[2] == ' ')//Empty 3rd slot
		value = 3;
	else if (c[0] == c[2] && c[0] == letter && c[1] == ' ')//Empty 2nd slot
		value = 2;
	else if (c[2] == c[1] && c[2] == letter && c[0] == ' ')//Empty 1st slot
		value = 1;

	//Middle Horizontal Row
	else if (c[3] == c[4] && c[3] == letter && c[5] == ' ')//Empty 3rd slot
		value = 6;
	else if (c[3] == c[5] && c[3] == letter && c[4] == ' ')//Empty 2nd slot
		value = 5;
	else if (c[4] == c[5] && c[4] == letter && c[3] == ' ')//Empty 1st slot
		value = 4;

	//Bottom Horizontal Row
	else if (c[6] == c[7] && c[6] == letter && c[8] == ' ')//Empty 3rd slot
		value = 9;
	else if (c[6] == c[8] && c[6] == letter && c[7] == ' ')//Empty 2nd slot
		value = 8;
	else if (c[7] == c[8] && c[7] == letter && c[6] == ' ')//Empty 1st slot
		value = 7;
	
	//Left Vertical Column
	else if (c[0] == c[6] && c[0] == letter && c[3] == ' ')//Empty Middle slot
		value = 4;
	else if (c[0] == c[3] && c[0] == letter && c[6] == ' ')//Empty Bottom Slot
		value = 7;
	else if (c[3] == c[6] && c[3] == letter && c[0] == ' ')//Empty Top slot
		value = 1;

	//Middle Vertical Column
	else if (c[1] == c[4] && c[1] == letter && c[7] == ' ')//Empty Bottom Slot
		value = 8;
	else if (c[1] == c[7] && c[1] == letter && c[4] == ' ')//Empty Middle Slot
		value = 5;
	else if (c[4] == c[7] && c[4] == letter && c[1] == ' ')//Empty Top Slot
		value = 2;

	//Right Vertical Column
	else if (c[2] == c[5] && c[2] == letter && c[8] == ' ')//Empty Bottom Slot
		value = 9;
	else if (c[2] == c[8] && c[2] == letter && c[5] == ' ')//Empty Middle Slot
		value = 6;
	else if (c[5] == c[8] && c[5] == letter && c[2] == ' ')//Empty Top Slot
		value = 3;
	
	//Top left to Bottom right diagonal
	else if (c[0] == c[4] && c[0] == letter && c[8] == ' ')//Empty Bottom Right Slot
		value = 9;
	else if (c[0] == c[8] && c[0] == letter && c[4] == ' ')//Empty Middle Slot
		value = 5;
	else if (c[4] == c[8] && c[4] == letter && c[0] == ' ')//Empty Top Left Slot
		value = 1;

	//Bottom left to Top right diagonal
	else if (c[6] == c[4] && c[6] == letter && c[2] == ' ')//Empty Top Right Slot
		value = 3;
	else if (c[6] == c[2] && c[6] == letter && c[4] == ' ')//Empty Middle Slot
		value = 5;
	else if (c[2] == c[4] && c[2] == letter && c[6] == ' ')//Empty Bottom Left Slot
		value = 7;

	else
	{
		value = -1;
	}
	return value;
}
static int playerVacancyChecker(char c[], int choice)
{
	while (c[choice - 1] != ' ' )//If the location is not a ' ' then it is occupied
	{
		System.out.print("That location is occupied, please select another location:  ");
		String input = kb.nextLine();
		choice = (int) Double.parseDouble(input);		
	}
	return choice;
}
static int computerVacancyChecker(char c[], int choice)
{
	while (c[choice - 1] != ' ')//If the location is not a ' ' then it is occupied
	{
		choice = npc();//Rerolls choice
	}
	return choice;
}
static int winChecker(char c[], int p_Or_C)
{
	int value = 0;
	//-1 value = a win I should have used a bool probably
	//Horizontal Wins
	if (c[0] == c[1] && c[0] == c[2] && c[0] != ' ')//Top Horizontal Win
		value = -1;	
	else if (c[3] == c[4] && c[3] == c[5] && c[3] != ' ')//Middle Horizontal Win
		value = -1;	
	else if (c[6] == c[7] && c[6] == c[8] && c[6] != ' ')//Bottom Horizontal Win
		value = -1;

	//Vertical Wins
	else if (c[0] == c[3] && c[0] == c[6] && c[0] != ' ')//Left Vertical win
		value = -1;
	else if (c[1] == c[4] && c[1] == c[7] && c[1] != ' ')//Middle Vertical Win
		value = -1;
	else if (c[2] == c[5] && c[2] == c[8] && c[2] != ' ')//Right Vertical Win
		value = -1;

	//Diagonal Wins
	else if (c[0] == c[4] && c[0] == c[8] && c[0] != ' ')//Top left to bottom right Win
		value = -1;
	else if (c[2] == c[4] && c[2] == c[6] && c[2] != ' ')//Bottom Left to Top right Win
		value = -1;


	else if (c[0] != ' ' && c[1] != ' ' && c[2] != ' '
		  && c[3] != ' ' && c[4] != ' ' && c[5] != ' '
		  && c[6] != ' ' && c[7] != ' ' && c[8] != ' ')//All Spaces filled no winner
	{
		System.out.println("\n\t\tTIE GAME");
		value = -5;
		return value;
	}
	if (value == -1)//Value = -1 if there is a winning set of 3
	{
		if (p_Or_C == 1)//Hardcoded value allowing easy check of who is attempting to win
		{
			System.out.println("You won!");
			playerWins++;
		}
		else
		{
			System.out.println("The computer won :(");
			computerWins++;
		}
	}
	return value;//Returns value so if there is a winner it skips to the play again dialogue
}
}


//Look at http://neverstopbuilding.com/minimax
//https://stackoverflow.com/questions/125557/what-algorithm-for-a-tic-tac-toe-game-can-i-use-to-determine-the-best-move-for

