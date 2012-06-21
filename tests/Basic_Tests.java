package tests;

import junit.framework.TestCase;
import simple_database.Database;
/**
 * Tests provided in assignment description in {@link http://www.thumbtack.com/challenges}
 * 
 * @author Roberto Segebre 
 * @email rsegebre@colgate.edu
 * @link rsegebre.com
 */
public class Basic_Tests extends TestCase {
	Database database;
	String[] test_commands;
	String output;

	public void setUp() {
		database = new Database();
		output = "";
	}

	public void test_basic1() {
		String[] test_commands = {
				"SET a 10",
				"GET a",
				"UNSET a", 
				"GET a",
				"END"};
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("10\nNULL\n",output);
	}

	public void test_basic2() {
		String[] test_commands = {
				"SET a 10",
				"SET b 10",
				"NUMEQUALTO 10",
				"NUMEQUALTO 20",
				"UNSET a",
				"NUMEQUALTO 10", 
				"SET b 30",
				"NUMEQUALTO 10",
				"END"};
		
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("2\n0\n1\n0\n",output);
	}
	
	public void test_advanced1() {
		String[] test_commands = {
				"BEGIN",
				"SET a 10",
				"GET a",
				"BEGIN",
				"SET a 20", 
				"GET a",
				"ROLLBACK",
				"GET a", 
				"ROLLBACK", 
				"GET a", 
				"END"};
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("10\n20\n10\nNULL\n",output);
	}
	
	public void test_advanced2() {
		String[] test_commands = {
				"BEGIN",
				"SET a 30",
				"BEGIN",
				"SET a 40",
				"COMMIT",
				"GET a",
				"ROLLBACK",
				"END"};
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("40\nINVALID ROLLBACK\n",output);
	}
	
	public void test_advanced3() {
		String[] test_commands = {
				"SET a 50", 
				"BEGIN",
				"GET a", 
				"SET a 60",
				"BEGIN",
				"UNSET a",
				"GET a",
				"ROLLBACK",
				"GET a", 
				"COMMIT",
				"GET a", 
				"END"
				};
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("50\nNULL\n60\n60\n",output);
	}
	
	public void test_advanced4() {
		String[] test_commands = {
				"SET a 10",
				"BEGIN",
				"NUMEQUALTO 10",
				"BEGIN",
				"UNSET a",
				"NUMEQUALTO 10",
				"ROLLBACK",
				"NUMEQUALTO 10",
				"END"
				};
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("1\n0\n1\n",output);
	}
	
	public void test_invalid_commands(){
		String[] test_commands = {
				"SET a 10adf",
				"BEGIN",
				"NUMEQUALTO lkj19kj",
				"BEGIN",
				"UNSET 43",
				"SET a 10",
				"GET a",
				"ROLLBACK",
				"GET a",
				"END"
				};
		for (String c1: test_commands)
			output += database.process_command(c1);
		assertEquals("10\nNULL\n",output);
		
	}
	
}
