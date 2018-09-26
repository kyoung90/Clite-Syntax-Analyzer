/*	 ********************************************
     *  CPEN 457 - Programming Languages		*
     *  Project 3: CLite Lexer 					*
     *  Authors: Kenneth Young, Roberto Santana *
     *  Spring 2018								*
     ********************************************

     To run this code: 
     1- your terminal must have the path to the java jdk set. To do this, run in the cmd PATH=%PATH%;"C:\Program Files\Java\jdk1.8.0_121\bin";
*/

    import java.io.*;
    import java.util.*;

    public class CompilerLexerFinal {

	// Start of Lexer, we first ask the user to give us the filename as an argument at runtime and then we proceed to
	// extract the information from the file and seperate everything by spaces

    	public static void main(String[] args) throws Exception{

    		if (args.length != 1) {
    			System.err.println("Usage: java CompilerLexer <file>");
    			System.exit(1);
    		}
		// filename goes in this scanner from arguments
    		Scanner scanner = new Scanner(new FileInputStream(args[0]));
    		String text = "";

    		while (scanner.hasNextLine()) {
    			text += scanner.nextLine() + " ";
    		}

    		String[] tokens = text.split("\\s+");

    		for(int i = 0; i < tokens.length; i++){
			// System.out.println(tokens[i]);
    		}

		// Check the start and end of the code, the code should start with int main(){ 
    		if (tokens[0].equals("int") && tokens[1].equals("main") && tokens[2].equals("(") && tokens[3].equals(")")){
    			int i = 5;
    			String tempText = "";

    			//if we find a curly bracket
    			if(tokens[4].equals("{")){
    				int openingCurlyBracketCounter = 1;
    				int closingCurlyBracketCounter = 0;


    				//check for matching curly brackets
    				while(openingCurlyBracketCounter != closingCurlyBracketCounter && i < tokens.length){
    					if (tokens[i].equals("{")){
    						openingCurlyBracketCounter++;
    					}
    					else if(tokens[i].equals("}")){
    						closingCurlyBracketCounter++;
    					}

    					if(openingCurlyBracketCounter != closingCurlyBracketCounter){
    						tempText += tokens[i] + " ";
    						i++;
    					}
    				}
    				//checking if we iterated until program ending curly bracket
    				if (openingCurlyBracketCounter == closingCurlyBracketCounter && i == tokens.length-1 && tokens[i].equals("}")){
    					i = declarations(tempText) + 5;

    					tempText = "";

    					while (i < tokens.length - 1){
    						tempText += tokens[i] + " ";
    						i++;
    					}

    					if(isStatements(tempText)){
    						System.out.println("No errors in " + args[0] + " were found!");
    					}
    					else{
    						System.out.println("false");
    						System.out.println("An error in " + args[0] + " was found! (See above)");
    					}


					// System.out.print(isStatement("if ( a + c = b && a > 10 || c < 15 + 3 ) a = a + b ; else b = c ;"));

    				}
    				else{
    					System.out.println("Error: Amount of curly brackets in code do not match.");
    				}
    			}
    			else{
    				System.out.println("Missing a { after int main ( )");
    			}
    		}
    		else{
    			System.out.println("Error: code must start with int main ( ) {");
    		}

    	}
	// ********************************************************
	// ***********************FUNCTIONS************************
	// ********************************************************


	//********************** isFloat() ************************
	//Parameters: String t
	//Usage: Check if given string is of float type
    	static public boolean isFloat(String t){
    		String[] number = t.split(" ");

    		if (isInteger(number[0]) && number[1].equals(".") && isInteger(number[2])){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** isType() ************************
	//Parameters: String t
	//Usage: Check if given string declares a type: int, bool, float or char
    	static public boolean isType(String t){
    		if (t.equals("int") || t.equals("bool") || t.equals("float") || t.equals("char")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** isIdentifier() ************************
	//Parameters: String t
	//Usage: Check if given string is an identifier
    	static public boolean isIdentifier(String t){
    		if (Character.isLetter(t.charAt(0))){
    			return true;
    		}
    		else{
    			return false;
    		}
    	}	

	//********************** isBoolean() ************************
	//Parameters: String t
	//Usage: Check if given string is of boolean type
    	static public boolean isBoolean(String t){
    		if (t.equals("true") || t.equals("false")){
    			return true;
    		}
    		else{
    			return false;
    		}
    	}	

	//********************** isChar() ************************
	//Parameters: String t
	//Usage: Check if given string is of char type
    	static public boolean isChar(String t){
    		if (t.matches(".?")){
    			return true;
    		}
    		else{
    			return false;
    		}
    	}

	//********************** isInteger() ************************
	//Parameters: String t
	//Usage: Check if given string is of int type
    	static public boolean isInteger(String t){
    		try { 
    			Integer.parseInt(t); 
    		} catch(NumberFormatException e) { 
    			return false; 
    		} catch(NullPointerException e) {
    			return false;
    		}
     	// only got here if we didn't return false
    		return true;
    	}

	//********************** isEqualOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is an equality operator: == or !=
    	static public boolean isEqualOperator(String t){
    		if (t.equals("==") || t.equals("!=")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

    //********************** isLogicOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is a logic operator: && or ||
    	static public boolean isLogicOperator(String t){
    		if (t.equals("&&") || t.equals("||")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** isRelationOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is a relational operator: < , <= , > , >=
    	static public boolean isRelationOperator(String t){
    		if (t.equals("<") || t.equals("<=") || t.equals(">") || t.equals(">=")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** isAdditionOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is an addition operator: + or -
    	static public boolean isAdditionOperator(String t){
    		if (t.equals("+") || t.equals("-")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** isMultiplyingOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is a multiplying operator: * , / , %
    	static public boolean isMultiplyingOperator(String t){
    		if (t.equals("*") || t.equals("/") || t.equals("%")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** isUnaryOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is a unary operator: - , !
    	static public boolean isUnaryOperator(String t){
    		if (t.equals("-") || t.equals("!")){
    			return true;
    		}
    		else {
    			return false;
    		}
    	}

	//********************** declarations() ************************
	//Parameters: String t
	//Usage: Check if given string is one or more declarations
	//Returns the ending position of the last declaration
    	static public int declarations(String t){
    		String[] text = t.split(" ");
    		String tempText = "";

		//counter for declarations position
    		int endOfDeclarationsPosition = 0;

		//general loop counter
    		int i = 0;

		//assuring non empty string
    		if (text.length > 1){
    			do {
				tempText = "";	//emptying string for use and reuse

				do {
					if (i < text.length){	//verify if there's more to insert
						tempText += text[i] + " ";	//prepare the string for sending to isDeclaration()
						i++;
					}
				} while (i < text.length && !text[i - 1].equals(";")); //keep inserting while not end and we still haven't reached a ";"

				if (isDeclaration(tempText)){	//sending string to isDeclaration() to check for declarations
					endOfDeclarationsPosition = i;	//if string before was declaration, save position
				}

				System.out.println("declarations: " + tempText);
			} while (isDeclaration(tempText));	//keep evaluating while we find declarations

			return endOfDeclarationsPosition;
		}
		else{
			return 0;
		}
		


		
	}

	//********************** isDeclaration() ************************
	//Parameters: String t
	//Usage: Check if given string is a declaration: Type Identifier
	static public boolean isDeclaration(String t){
		String[] text = t.split(" "); 
		int i = 2;

		//checking for more than one entry in the array and making sure the next entry is a type followed by a legal identifier
		if (text.length > 1 && isType(text[0]) && isIdentifier(text[1])){
			if (isDeclarationHelper(t, i)){	//signaling DeclarationHelper() to check for multiple declarations
				return true;
			}
			else{
				return false;
			}
		}
		else {
			return false;
		}
	}

	//********************** isDeclarationHelper() ************************
	//Parameters: String t, int i
	//t: string to evaluate, i: position to start from
	//Usage: check if there is more than one declaration
	static public boolean isDeclarationHelper(String t,int i){
		String[] text = t.split(" ");

		// If we find a ; then no more declarations
		if (text[i].equals(";")){
			return true;
		}
		else if (text[i].equals(",")){	//If we find a , then check for more declarations
			if (isIdentifier(text[i + 1])){
				if (isDeclarationHelper(t, i + 2)){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else if (text[i].equals("[")){	//checking if declaration is declaring an array
			if (isInteger(text[i + 1]) && text[i + 2].equals("]")){
				if (isDeclarationHelper(t, i + 3)){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	//********************** isOperator() ************************
	//Parameters: String t
	//Usage: Check if given string is an operator. Calls operator functions.
	static public boolean isOperator(String t){
		if (isEqualOperator(t) || isMultiplyingOperator(t) || isAdditionOperator(t) || isRelationOperator(t) || isLogicOperator(t)){
			return true;
		}
		else{
			return false;
		}
	}

	//********************** isStatements() ************************
	//Parameters: String t
	//Usage: Check for multiple statements
	//Sends all statements to isStatement() to check for one by one
	static public boolean isStatements(String t){
		String[] text = t.split(" ");
		String tempText = "";

		int i = 0;

		//avoiding empty string
		if (text.length > 1){
			do {
				if (i < text.length){
					tempText = "";
					tempText += text[i] + " ";
					i++;
				}
				//checking for while statement
				if (text[i-1].equals("while")){
					if (text[i].equals("(")){
						int openingParenthesisCounter = 1;
						int closingParenthesisCounter = 0;

						tempText += text[i] + " "; 
						i++;

						//checking for matching parenthesis
						while(i < text.length && openingParenthesisCounter != closingParenthesisCounter){
						// Counting opening brackets
							if (text[i].equals("(")){
								openingParenthesisCounter++;
							}
						// Counting closing bracket
							else if(text[i].equals(")")){
								closingParenthesisCounter++;
							}

							// Save every iteration in a string
							tempText += text[i] + " ";
							i++;
						}

						System.out.println(tempText);
						// I'm at position after final parenthesis
						if (text[i].equals("if")){
							tempText += text[i] + " "; 
							i++;
							if (text[i].equals("(")){
								openingParenthesisCounter = 1;
								closingParenthesisCounter = 0;

								tempText += text[i] + " "; 
								i++;

								while(i < text.length && openingParenthesisCounter != closingParenthesisCounter){
							// Counting opening brackets
									if (text[i].equals("(")){
										openingParenthesisCounter++;
									}
								// Counting closing bracket
									else if(text[i].equals(")")){
										closingParenthesisCounter++;
									}

								// Save every iteration in a string
									tempText += text[i] + " ";
									i++;
								}

						// I'm at position after final parenthesis
								while (i < text.length && !text[i-1].equals(";")){
									tempText += text[i] + " ";
									i++;
								}

						// I'm at position after ;
								if (i < text.length && text[i].equals("else")){
									while (i < text.length && !text[i].equals(";")){
										tempText += text[i] + " ";
										i++;
									}

									if(i < text.length && text[i].equals(";")){
										tempText += "; ";
										i++;
									}
								}
								else{
									return false;
								}
							}
							


						}
						//handling while(){}
						else if(text[i].equals("{")){
							int openingCurlyBracketCounter = 1;
							int closingCurlyBracketCounter = 0;

							tempText += text[i] + " "; 
							i++;

							//check for matching curly brackets
							while(openingCurlyBracketCounter != closingCurlyBracketCounter && i < text.length){
								if (text[i].equals("{")){
									openingCurlyBracketCounter++;
								}
								else if(text[i].equals("}")){
									closingCurlyBracketCounter++;
								}

								tempText += text[i] + " ";
								i++;
							}
						}
						//no {} means check until next ";"
						else{
							while (i < text.length && !text[i-1].equals(";")){
								tempText += text[i] + " ";
								i++;
							}
						}
					}
					else{
						return false;
					}
				}
				//handling if statement
				else if (text[i-1].equals("if")){
					//making sure parenthesis exists and matches
					if (text[i].equals("(")){
						int openingParenthesisCounter = 1;
						int closingParenthesisCounter = 0;

						i++;

						//adding parentheis to keep code intact
						tempText += "( ";
						while(i < text.length && openingParenthesisCounter != closingParenthesisCounter){
						// Counting opening brackets
							if (text[i].equals("(")){
								openingParenthesisCounter++;
							}
						// Counting closing bracket
							else if(text[i].equals(")")){
								closingParenthesisCounter++;
							}

							// Save every iteration in a string
							tempText += text[i] + " ";
							i++;
						}
						// I'm at position after final parenthesis
						while (i < text.length && !text[i-1].equals(";")){
							tempText += text[i] + " ";
							i++;
						}

						// I'm at position after ;
						if (text[i].equals("else")){
							while (i < text.length && !text[i].equals(";")){
								tempText += text[i] + " ";
								i++;
							}
							//making sure we ended in a ";" and not an error
							if(text[i].equals(";")){
								tempText += "; ";	//adding ";" to keep code intact
								i++;
							}
						}

					}
					else{
						return false;
					}

				}
				//handling if(){}
				else if (text[i-1].equals("{")){
					int openingCurlyBracketCounter = 1;
					int closingCurlyBracketCounter = 0;

					tempText += text[i] + " "; 
					i++;

					//making sure curly brackets match
					while(openingCurlyBracketCounter != closingCurlyBracketCounter && i < text.length){
						if (text[i].equals("{")){
							openingCurlyBracketCounter++;
						}
						else if(text[i].equals("}")){
							closingCurlyBracketCounter++;
						}

						tempText += text[i] + " ";
						i++;
					}
				}
				//no {} means check until next ";"
				else{
					while (i < text.length && !text[i-1].equals(";")){
						tempText += text[i] + " ";
						i++;
					}
				}


			} while (isStatement(tempText) && i < text.length); //send each statement recursively to isStatement()

			if (isStatement(tempText)){	//check final string for statement
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}



	}

	//********************** isBlock() ************************
	//Parameters: String t
	//Usage: Check if given string is a Block
	//Will check if t is a block by checking if t contains opening and closing bracket with statements inside
	static public boolean isBlock(String t){
		String[] text = t.split(" ");
		String tempText = "";

	//checking for more than one entry in the array, and verifying that we have an opening curly bracket
		if(text.length > 1 && text[0].equals("{")){
			int openingCurlyBracketCounter = 1;
			int closingCurlyBracketCounter = 0;

			int i = 1;

			//we create this loop to iterate through the code and verify if the brackets match.
			while(openingCurlyBracketCounter != closingCurlyBracketCounter && i < text.length){
				if (text[i].equals("{")){
					openingCurlyBracketCounter++;
				}
				else if(text[i].equals("}")){
					closingCurlyBracketCounter++;
				}

				if(openingCurlyBracketCounter != closingCurlyBracketCounter){
					tempText += text[i] + " ";
					i++;
				}
			}

			//if the brackets did indeed match, then we can proceed to check their contents using isStatements() to chec
			//for multiple statements
			if (openingCurlyBracketCounter == closingCurlyBracketCounter && i == text.length-1 && text[i].equals("}")){
				if (isStatements(tempText)){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}

	}

	//********************** isStatement() ************************
	//Parameters: String t
	//Usage: Check if given string is a statement
	//Will check if t is a statement by checking if t contains ; || Block || Assignment || If Statement || while statement
	static public boolean isStatement(String t){
		System.out.println("isStatement: " + t);
		String[] text = t.split(" ");
		String tempText = "";

		int i = 0;

		while (i < text.length){
			tempText += text[i] + " ";
			i++;
		}

		//checking for single ";"
		if (text[0].equals(";")){
			return true;
		}
		//checking for assignment
		else if (isAssignment(tempText)){
			return true;
		}
		//checking for statement
		else if (isIfStatement(tempText)){
			return true;
		}
		//checking for while statement
		else if (isWhileStatement(tempText)){
			return true;
		}
		//checking for blocks
		else if (isBlock(tempText)){
			return true;
		}
		//if not any of the above, this is not a statement
		else{
			return false;
		}
	}

	//********************** isIfStatement() ************************
	//Parameters: String t
	//Usage: Check if given string is an if statement
	//Will check if t is an if statement by checking if t contains if ( expression ) statement [else statement]
	static public boolean isIfStatement(String t){
		String[] text = t.split(" ");
		String tempText = "";

		//assuring array not empty before checking for if in the first position
		if (text.length > 1 && text[0].equals("if")){
			if (text.length > 2 && text[1].equals("(")){
				// Defining parenthesis counters
				int openingParenthesisCounter = 1;
				int closingParenthesisCounter = 0;

				int i = 2;

				while(i < text.length && openingParenthesisCounter != closingParenthesisCounter){
					// Counting opening brackets
					if (text[i].equals("(")){
						openingParenthesisCounter++;
					}
					// Counting closing bracket
					else if(text[i].equals(")")){
						closingParenthesisCounter++;
					}

					// Put it inside  the string so at the last iteration it doesnt copy the final parenthesis
					if(openingParenthesisCounter != closingParenthesisCounter){
						// Save every iteration in a string
						tempText += text[i] + " ";
						i++;
					}
				}

				// After the while loop, the tempText string should have an expression
				if (isExpression(tempText)){
					// i should be at the final parenthesis, but we want to check whats after so we iterate to the next element
					i++;

					//Empty the tempText String to reuse it
					tempText = "";

					// Everything after i should be a statement or could possibly be an else at the end
					while(i < text.length && !text[i].equals("else")){
						if(!text[i].equals("else")){
							tempText += text[i] + " ";
							i++;
						}

					}

					// If it is a statement, then this was a while statement
					if (isStatement(tempText) && i == text.length){
						return true;
					}
					else if (isStatement(tempText) && text[i].equals("else")){
						// i should be at the else, but we want to check whats after so we iterate to the next element
						i++;

						//Empty the tempText String to reuse it
						tempText = "";

						// Everything after i should be a statement
						for(; i < text.length; i++){
							tempText += text[i] + " ";
						}

						// If it is a statement, then this was a if statement
						if (isStatement(tempText)){
							return true;
						}
					// If it wasnt, then it isnt a while statement
						else {
							return false;
						}
					}
					// If it wasnt, then it isnt a while statement
					else {
						return false;
					}
				}
				// There was an error in the expression
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}

	//********************** isIfStatement() ************************
	//Parameters: String t
	//Usage: Check if given string is a while statement
	//Will check if t is an if statement by checking if t contains while ( expression ) statement
	static public boolean isWhileStatement(String t){
		String[] text = t.split(" ");
		String tempText = "";

		// Check if string starts with the word while
		if (text.length > 1 && text[0].equals("while")){
			// After a while statement, the following part of the string must be ( expressions ), so
			// we will check for matching parenthesis and then check if whats between them is an expressions

			// Defining parenthesis counters
			int openingParenthesisCounter = 1;
			int closingParenthesisCounter = 0;

			int i = 2;

			// Check for parenthesis after while
			if (text[1].equals("(")){
				// While not at the end of the array or while we havent found matching brackets
				while(i < text.length && openingParenthesisCounter != closingParenthesisCounter){
					// Counting opening brackets
					if (text[i].equals("(")){
						openingParenthesisCounter++;
					}
					// Counting closing bracket
					else if(text[i].equals(")")){
						closingParenthesisCounter++;
					}

					// Put it inside  the string so at the last iteration it doesnt copy the final parenthesis
					if(openingParenthesisCounter != closingParenthesisCounter){
						// Save every iteration in a string
						tempText += text[i] + " ";
						i++;
					}
				}

				// After the while loop, the tempText string should have an expression
				if (isExpression(tempText)){
					// i should be at the final parenthesis, but we want to check whats after so we iterate to the next element
					i++;

					//Empty the tempText String to reuse it
					tempText = "";

					// Everything after i should be a statement
					for(; i < text.length; i++){
						tempText += text[i] + " ";
					}

					// If it is a statement, then this was a while statement
					if (isStatement(tempText)){
						return true;
					}
					// If it wasnt, then it isnt a while statement
					else {
						return false;
					}
				}
				// There was an error in the expression
				else{
					return false;
				}
			}
			// if a while isn't followed by an opening parenthesis then its not a while statemenet
			else{
				return false;
			}

		}
		// If it didn't contain while, the it is not a while statement
		else{
			return false;
		}
		// Eliminate this when its finished
		// return false;
	}

	static public boolean isAssignment(String t){
		System.out.println("isAssignment String:" + t);
		String[] text = t.split(" ");
		String tempText = "";

		//checking for identifier followed by an assignment
		if (text.length > 1 && isIdentifier(text[0])){
			//after handling identifiers, we shouldn't have leftover brackets
			if (text[1].equals("[")){
				return false;
			}
			//checking for assignment operator
			else if (text[1].equals("=")){
				int i = 2;
				//keep adding information until we reach the end of the assignment which is a ";"
				while (i < text.length && !text[i].equals(";")){
					tempText += text[i] + " ";
					i++;
				}	

				//checking if after the "=" there is an expression per the grammar
				if (i == text.length - 1 && text[i].equals(";") && isExpression(tempText)){
					return true;
				}
				//expected expression
				else{
					return false;
				}
			}
			//expected "=" operator
			else{
				return false;
			}
		}
		//expected identifier
		else{
			return false;
		}
	}

	//********************** isExpression() ************************
	//Parameters: String t
	//Usage: Check if given string is an expression
	//Uses recursion to check for Primaries and other expressions
	static public boolean isExpression(String t){
		System.out.println("isExpression: " + t);
		String[] text = t.split(" ");
		String tempText = "";

		//checking for unary operator at starting index
		if(isUnaryOperator(text[0])){
			for(int i = 1; i < text.length; i++){
				tempText += text[i] + " ";
			}
			//checking if after unary operator we have a primary
			if (isPrimary(tempText)){
				return true;
			}
			//if not a primary check for next operator
			else{
				int i = 0;
				String[] opText = tempText.split(" ");

				while(!(isOperator(opText[i]) || i == opText.length - 1)){
					i++;
				}
				String primaryCheck = "";
				if (isOperator(opText[i])){	//once we find the operator we check for primaries before the operator using the string primaryCheck
					for (int j = 0; j <= i - 1; j++){
						primaryCheck += opText[j] + " ";
					}

					//now we check after the operator
					if(isPrimary(primaryCheck)){
						// Check everything after the operator
						tempText = "";
						for(i++; i < opText.length; i++){
							tempText += opText[i] + " ";
						}
						//if it was an expression then all is good
						if(isExpression(tempText)){
							return true;
						}
						else{
							return false;
						}
					}
					else{
						System.out.println("Expected a primary before operator");
						return false;
					}
				}
				else{
					System.out.println("Expected an expression");
					return false;
				}
			}
		}
		//checking for expressions inside of brackets
		else if (text.length > 1 && isIdentifier(text[0]) && text[1].equals("[")){
			int openingBracketCounter = 1;
			int closingBracketCounter = 0;


			int i = 2;
			tempText += text[0] + " " + "[" + " ";

			//counting the amount of brackets and verifying if they match
			while(openingBracketCounter != closingBracketCounter && i < text.length){
				if (text[i].equals("[")){
					openingBracketCounter++;
				}
				else if(text[i].equals("]")){
					closingBracketCounter++;
				}

				//saving what's inside the brackets
				tempText += text[i] + " ";

				//increment only when brackets match
				if(openingBracketCounter != closingBracketCounter){
					i++;
				}
			}

			if(openingBracketCounter == closingBracketCounter){
				// Check if whatever was before the closing bracket was a primary
				if(isPrimary(tempText)){
					// If there is something after the ]
					if (text.length > i+1){
						// Check if its an operator after the ]
						if(isOperator(text[i+1])){
							tempText = "";
							// If there is something after the operator
							if (text.length > i + 2){
								// Store everything after it in a string
								for (i = i+2; i < text.length; i++){
									tempText += text[i] + " ";
								}

								// Check that whatever was after the operator is an expression
								if (isExpression(tempText)){
									return true;
								}
								else{
									System.out.println("Expected an expression after operator");
									return false;
								}
							}
							else{
								System.out.println("Expected an expression after operator");
								return false;
							}
						}
						else{
							System.out.println("Expected an operator");
							return false;
						}
					}
					else{
						return true;
					}
				}
				else{
					System.out.println("Expected a primary");
					return false;
				}
			}
			else{
				System.out.println("Expected ]");
				return false;
			}
		}
		//handling expressions inside parenthesis
		else if (text.length > 1 && isType(text[0]) && text[1].equals("(")){
			int openingParenthesisCounter = 1;
			int closingParenthesisCounter = 0;


			int i = 2;
			tempText += text[0] + " " + "(" + " ";

			//verifying that we have matching parenthesis
			while(openingParenthesisCounter != closingParenthesisCounter && i < text.length){
				if (text[i].equals("(")){
					openingParenthesisCounter++;
				}
				else if(text[i].equals(")")){
					closingParenthesisCounter++;
				}
				//saving everything inside the parenthesis
				tempText += text[i] + " ";

				if(openingParenthesisCounter != closingParenthesisCounter){
					i++;
				}
			}

			if(openingParenthesisCounter == closingParenthesisCounter){
				// Check if whatever was before the closing parenthesis was a primary
				if(isPrimary(tempText)){
					// If there is something after the )
					if (text.length > i+1){
						// Check if its an operator after the )
						if(isOperator(text[i+1])){
							tempText = "";
							// If there is something after the operator
							if (text.length > i + 2){
								// Store everything after it in a string
								for (i = i+2; i < text.length; i++){
									tempText += text[i] + " ";
								}

								// Check that whatever was after the operator is an expression
								if (isExpression(tempText)){
									return true;
								}
								else{
									System.out.println("Expected an expression after operator");
									return false;
								}
							}
							else{
								System.out.println("Expected an expression after operator");
								return false;
							}
						}
						else{
							System.out.println("Expected an operator");
							return false;
						}
					}
					else{
						return true;
					}
				}
				else{
					System.out.println("Expected a primary");
					return false;
				}
			}
			else{
				System.out.println("Expected ]");
				return false;
			}
		}
		//if first encounter is a parenthesis
		else if (text[0].equals("(")){
			int lParen = 1;
			int rParen = 0;

			int i = 1;
			tempText += text[0] + " ";

			//verifying matching parenthesis
			while(lParen != rParen && i < text.length){
				if (text[i].equals("(")){
					lParen++;
				}
				else if(text[i].equals(")")){
					rParen++;
				}

				tempText += text[i] + " ";
				//saving contents of the inside

				if(lParen != rParen){
					i++;
				}
			}

			if(lParen == rParen){
				// Check if whatever was before the closing parenthesis was a primary
				if(isPrimary(tempText)){
					// If there is something after the )
					if (text.length > i+1){
						// Check if its an operator after the )
						if(isOperator(text[i+1])){
							tempText = "";
							// If there is something after the operator
							if (text.length > i + 2){
								// Store everything after it in a string
								for (i = i+2; i < text.length; i++){
									tempText += text[i] + " ";
								}

								// Check that whatever was after the operator is an expression
								if (isExpression(tempText)){
									return true;
								}
								else{
									System.out.println("Expected an expression after operator");
									return false;
								}
							}
							else{
								System.out.println("Expected an expression after operator");
								return false;
							}
						}
						else{
							System.out.println("Expected an operator");
							return false;
						}
					}
					else{
						return true;
					}
				}
				else{
					System.out.println("Expected a primary");
					return false;
				}
			}
			else{
				System.out.println("Expected )");
				return false;
			}
		}
		else {
			//if we got here, we check if the original string t is a primary
			if(isPrimary(t)){
				return true;
			}
			else{
				int i = 0;
				//looking for the next operator
				while(!(isOperator(text[i]) || i == text.length - 1)){
					i++;
				}

				String primaryCheck = "";
				if (isOperator(text[i])){
					for (int j = 0; j <= i - 1; j++){
						primaryCheck += text[j] + " ";	//saving contents to check if primary later
					}

					if(isPrimary(primaryCheck)){
						// Check everything after the operator
						tempText = "";
						for(i++; i < text.length; i++){
							tempText += text[i] + " ";
						}
						if(isExpression(tempText)){
							return true;
						}
						else{
							return false;
						}
					}
					else{
						System.out.println("Expected a primary before operator");
						return false;
					}
				}
				else{
					System.out.println("Expected an expression");
					return false;
				}
			}
		}
	}

	//********************** isPrimary() ************************
	//Parameters: String t
	//Usage: Check if given string is a Primary.
	//Uses recursion to check for expressions which also checks for primaries.
	static public boolean isPrimary(String t){	
		String[] text = t.split(" ");
		String tempText = "";
		int i = 2;

		// Check if t was an empty string
		if (text[0] == ""){
			return false;
		}
		else{
			// Handle explicit casting
			if(isType(text[0]) && text[1].equals("(")){
				int lParen = 1;
				int rParen = 0;

				i = 2;
				tempText += "";
				//checking for matching parenthesis
				while(lParen != rParen && i < text.length){
					if (text[i].equals("(")){
						lParen++;
					}
					else if(text[i].equals(")")){
						rParen++;
					}

					// Put it inside so at the last iteration it doesnt copy the final parenthesis
					if(lParen != rParen){
						tempText += text[i] + " ";
						i++;
					}
				}
				//verify for expression and that we also reached the closing parenthesis correctly
				if (isExpression(tempText) && text[i].equals(")")){
					if(i == text.length - 1){
						return true;
					}
					else{
						System.out.println("Expected an operator or end of expression");
						return false;
					}
				}
				else{
					return false;
				}
			}
			//handling identifier [ expression ]
			else if(isIdentifier(text[0])){
				if(text.length > 1 && text[1].equals("[")){
					i = 2;
					int openingBracketCounter = 1;
					int closingBracketCounter = 0;

					tempText = "";
					//making sure brackets match
					while(openingBracketCounter != closingBracketCounter && i < text.length){
						if (text[i].equals("[")){
							openingBracketCounter++;
						}
						else if(text[i].equals("]")){
							closingBracketCounter++;
						}

						//only increment counter when not equal to not save closing bracket
						if(openingBracketCounter != closingBracketCounter){
							tempText += text[i] + " ";
							i++;
						}
					}
					//check for expression inside brackets and verify if we reached ending bracket
					if (isExpression(tempText) && openingBracketCounter == closingBracketCounter){
						if(i == text.length - 1){
							return true;
						}
						else{
							// System.out.println("Expected an operator or end of expression");
							return false;
						}
					}
					else{
						return false;
					}
				}
				else if (text.length == 1){
					return true;
				}
				else{
					// System.out.println("Expected an operator or end of expression after identifier or [expression]");
					return false;
				}
			}
			//check for types and lenght to verify that it is the only thing in the line
			else if ((isBoolean(text[0]) && text.length == 1) || (isInteger(text[0]) && text.length == 1) || (isChar(text[0]) && text.length == 1) || (text.length == 3 && isFloat(text[0] + " " + text[1] + " " + text[2]) )){
				return true;
			}
			//else check for type parenthesis
			else if(text[0].equals("(")){
				int lParen = 1;
				int rParen = 0;

				i = 1;
				tempText += "";

				//check for matching parenthesis
				while(lParen != rParen && i < text.length){
					if (text[i].equals("(")){
						lParen++;
					}
					else if(text[i].equals(")")){
						rParen++;
					}

					// Put it inside so at the last iteration it doesnt copy the final parenthesis
					if(lParen != rParen){
						tempText += text[i] + " ";
						i++;
					}
				}
				//verify that what we saved is an expression and that we also reached the ending parenthesis in the iteration
				if (isExpression(tempText) && text[i].equals(")")){
					if(i == text.length - 1){
						return true;
					}
					else{
						// System.out.println("Expected an operator or end of expression");
						return false;
					}
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		}
	}
}