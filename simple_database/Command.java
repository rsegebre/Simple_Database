package simple_database;

import java.util.ArrayList;
import java.util.List;

/**
 * Complementary class to Database
 * 
 * @author Roberto Segebre 
 * @email rsegebre@colgate.edu
 * @link rsegebre.com
 */
public class Command {

	private String command;
	private List <String> parameters;
	/**
	 * Private constructor for Command Class.
	 * 
	 * @param command single-space separated command type
	 * @param parameters parameters for command provided as an ArrayList
	 */
	private Command(String command, List<String> parameters) {
		this.command = command;
		this.parameters = parameters;
	}	
	
	/**
	 * Obtains command.
	 * 
	 * @return command as a string
	 */
	public String get_command() {
		return this.command;
	}

	/**
	 * Obtains parameters.
	 * 
	 * @return paramater(s) for the current command
	 */
	public List<String> get_parameters(){
		return this.parameters;
	}
	
	/**
	 * Static constructor, parses string input to create a Command.

	 * @param c1 contains the command and parameters in string form
	 * @return new instance of Command
	 */
	public static Command form_command(String c1){
		String[] command_elements = c1.split(" ");
		ArrayList<String> parameters = new ArrayList<String>();
		
		if (command_elements.length > 3) {
			System.err.println("Too many parameters for the command.");
			return null;
		}
		
		for (int i = 1; i < command_elements.length; i++)
			parameters.add(command_elements[i]);
		
		if (command_elements[0].equals("SET")){
			if (!isNumber(command_elements[2])){
				System.err.println("Incorrect usage, should be: SET [variable_name->String] [value->Integer]");
				return null;
			}
		}
		
		if (command_elements[0].equals("NUMEQUALTO")) {
			if (!isNumber(command_elements[1])) {
				System.err.println("Incorrect usage, should be: NUMEQUALTO [value->Integer]");
				return null;
			}
		}
			
		return new Command(command_elements[0], parameters);
	}
	
	/**
	 * Checks if a string can be converted to an int
	 * 
	 * @param str string to check
	 * @return true if string is an integer or false otherwise
	 */
	private static Boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
