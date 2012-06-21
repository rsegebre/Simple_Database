package simple_database;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Class containing main that takes user inputs and calls upon Database to generate output.
 * 
 * @author Roberto Segebre 
 * @email rsegebre@colgate.edu
 * @link rsegebre.com
 */
public class Input_Handler {

	
	public static void main(String[] args){
		ArrayList<String> user_commands = new ArrayList<String>();
		Database database = new Database();
		String input = "";
		Scanner scan = new Scanner(System.in);
		
		//Receive all commands until END
		while (!input.equals("END")) {
			input = scan.nextLine();
			user_commands.add(input);
		}
	
		//Finished receiving input now process all commands.
		for (String c1 : user_commands)
			System.out.print(database.process_command(c1));
		
	}
		
}
