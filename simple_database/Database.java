package simple_database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


/**
 * This a solution to Thumbtack's challenge # 3 described in http://www.thumbtack.com/challenges.
 * 
 * @author Roberto Segebre 
 * @email rsegebre@colgate.edu
 * @link rsegebre.com
 */
public class Database {
	
	private HashMap<String,String> database;
	private HashMap<Integer, Integer> reverse_database;
	private List<String> transaction_block;
	private Stack<List<String>> transaction_stack;
	private Boolean begin, rollback;
	
	/**
	 * Constructs a new Database object.
	 */	
	public Database() {
		database = new HashMap<String,String>();
		reverse_database = new HashMap<Integer, Integer>();
		transaction_block = new ArrayList<String>();
		transaction_stack = new Stack<List<String>>();
		begin = false;
		rollback = true;
	}
	
	/**
	 * Calls the corresponding method, {SET,GET,UNSET,NUMEQUALTO,BEGIN,ROLLBACK,COMMIT,END} described in the command.
	 * 
	 * @param user_command single command as a string with a single space between parameters if any
	 * @return a string containing the output of the command or "" if the command doesn't return anything
	 */	
	public String process_command(String user_command) {
		//Takes the given command and executes the desired operation on the database...
		String output = "";
		Command command_c1 = Command.form_command(user_command);
		
		//Invalid command... Ignoring.
		if (command_c1 == null)
			return output;
		
		String temp_c1 = command_c1.get_command();
		
		if ( temp_c1.equals("GET")) {
			output = (this.get(command_c1.get_parameters().get(0)) + "\n"); 
		}
		if (temp_c1.equals("SET")){
			this.set(command_c1.get_parameters().get(0),command_c1.get_parameters().get(1));
		}
		if (temp_c1.equals("UNSET")){
			this.unset(command_c1.get_parameters().get(0));
		}
		if (temp_c1.equals("NUMEQUALTO")){
			output += (this.numequalto(command_c1.get_parameters().get(0)) + "\n");
		}
		if (temp_c1.equals("END")){
			this.end();
		}
		if (temp_c1.equals("BEGIN")){
			this.begin();
		}
		if (temp_c1.equals("ROLLBACK")){
			output = this.rollback();
		}
		if (temp_c1.equals("COMMIT")){
			this.commit();
		}	
		return output;	
	}
	
	/**
	 * Obtains the value mapped to variable_name.
	 * 
	 * @param variable_name variable to be perform query against database
	 * @return a string value mapped to variable or "NULL" if non-existent
	 */	
	protected String get(String variable_name) {
		String temp = database.get(variable_name);
		if (temp != null)
			return temp;
		return "NULL";
	}
	
	/**
	 * Stores a variable name and its corresponding value.
	 * 
	 * @param variable_name variable name to add to  database
	 * @param value string value that will be mapped to variable_name
	 */	
	protected void set(String variable_name, String value) {
		String temp_value = database.get(variable_name);
		String rev_command = "";
		Integer temp_counter = 0;
		
		//Check if variable_name is already in database.
		if (temp_value == null) {
			database.put(variable_name, value);	
			rev_command = "UNSET " + variable_name;
		}
		//If overwriting value then adjust count reverse_database and generate reverse command
		else {
			temp_counter = reverse_database.get(Integer.parseInt(temp_value));
			reverse_database.put(Integer.parseInt(temp_value), temp_counter - 1);			
			database.put(variable_name, value);	
			rev_command = "SET " + variable_name + " " + temp_value;
		}
		//Add to current transactional block
		if (begin && rollback)
			transaction_block.add(rev_command);
		
		//Update count accordingly to our reverse hashmap.
		temp_counter = reverse_database.get(Integer.parseInt(value));
		if (temp_counter != null) {
			reverse_database.put(Integer.parseInt(value), ++temp_counter);			
		}
		else {
			reverse_database.put(Integer.parseInt(value), 1);
		}
	}
	
	
	/**
	 * Delete given variable from the database.
	 * 
	 * @param variable_name variable to delete
	 */	
	protected void unset(String variable_name){
		String temp_value = database.get(variable_name);
		Integer temp_counter = null;
		String rev_command = "";
		
		if (temp_value != null ) {
			database.remove(variable_name);
			temp_counter = reverse_database.get(Integer.parseInt(temp_value));
			reverse_database.put(Integer.parseInt(temp_value), --temp_counter); //Adjust count
			
			if (begin && rollback) {
				rev_command = "SET " + variable_name + " " + temp_value;
				transaction_block.add(rev_command);
			}
		}
		else 
			System.err.println("Variable: " + variable_name + ", couldn't be unset because it doesn't exist in your database.");
			
	}
	
	
	/**
	 * Return the total number of variables associated with a value.
	 * 
	 * @param value value to use for lookup
	 * @return the number of variables that contain the given value
	 */	
	protected int numequalto(String value) {
		Integer temp_counter = reverse_database.get(Integer.parseInt(value));
		if (temp_counter != null)
			return temp_counter;
		return 0;
	}
	
	
	/**
	 * Start a new transaction block, that can be reversed if necessary.
	 * 
	 */	
	protected void begin(){
		if (!begin)
			begin = true;
		else {
			if (!transaction_block.isEmpty()) {
				transaction_stack.push(transaction_block);
				transaction_block = new ArrayList<String>();
			}
		}
		
	}
	
	/**
	 * Make current transaction block irreversible.
	 * 
	 */	
	protected void commit(){
		begin = false;
		transaction_stack.removeAllElements();
	}
	
	/**
	 * Reverse all the commands in the current transactional block.
	 * 
	 * @return a string containing "INVALID ROLLBACK" if transaction can't be reversed or "" if success
	 */	
	protected String rollback(){
		if (begin){
			Collections.reverse(transaction_block);
			
			//Reverse changes.
			rollback = false; //The reverse commands don't get placed in a transaction block.
			for (String c1: transaction_block)
				process_command(c1);
			
			rollback = true;
			
			//Switch current transactional block to element in stack.
			try {
				transaction_block = transaction_stack.pop();
			}
			//No more transactional blocks so every command is committed.
			catch (EmptyStackException e) {
				begin = false;
			}
		}
		else
			return ("INVALID ROLLBACK\n");
		return "";
	}
	
	/**
	 * Commits all transactional blocks and ends the program (i.e return null).
	 * 
	 * @return returns null to calling method
	 */	
	protected String end(){
		for (int i = 0; i <= transaction_stack.size();i++)
				commit();
		return null;
	}
}
