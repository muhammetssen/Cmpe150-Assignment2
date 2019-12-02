import java.util.Scanner;


public class Assignment2v2{
    public static void main(String[] args)  {
        Scanner lineScanner = new Scanner(System.in); //Scanner to read user input
        //Each line contains a variable. I removed all spaces in order to work easier with indexes.
		String line1 = lineScanner.nextLine().replace(" ", "").toLowerCase(); 
		String line2 = lineScanner.nextLine().replace(" ", "").toLowerCase();
		String line3 = lineScanner.nextLine().replace(" ", "").toLowerCase();
        String calculation_line = lineScanner.nextLine().replace(" ", "").toLowerCase();
        //I added a plus because Math_level2 method assumes every element has a sign before it. Also removed the semicolon.
        calculation_line ="+"+calculation_line.substring(0,calculation_line.length()-1);
        //I found the indexes of equal signs to spare keys and values
		int index_of_sign_1 = line1.indexOf("=");
		int index_of_sign_2 = line2.indexOf("=");
		int index_of_sign_3 = line3.indexOf("=");
		String var1_name,var2_name,var3_name;
        //Next lines check if the variable is an integer or double and modify the calculation line by changing names with values 
		if(line1.charAt(0)=='i'){// I checked the first letter of a given line to learn it's an integer or double
			var1_name = line1.substring(3,index_of_sign_1);
			int var1_value = Integer.parseInt(line1.substring(index_of_sign_1+1,line1.indexOf(";")));
			calculation_line =calculation_line.replace(var1_name, Integer.toString(var1_value));
        }
		else if(line1.charAt(0)=='d'){
			var1_name = line1.substring(6,index_of_sign_1);
			double var1_value = Double.parseDouble(line1.substring(index_of_sign_1+1,line1.indexOf(";")));
			calculation_line = calculation_line.replace(var1_name, Double.toString(var1_value));
		}

		if(line2.charAt(0)=='i'){
			var2_name = line2.substring(3,index_of_sign_2);
			int var2_value = Integer.parseInt(line2.substring(index_of_sign_2+1,line2.indexOf(";")));
			calculation_line = calculation_line.replace(var2_name, Integer.toString(var2_value));
		}
		else if(line2.charAt(0)=='d'){
			var2_name = line2.substring(6,index_of_sign_2);
			double var2_value = Double.parseDouble(line2.substring(index_of_sign_2+1,line2.indexOf(";")));
			calculation_line = calculation_line.replace(var2_name, Double.toString(var2_value));
		}

		if(line3.charAt(0)=='i'){
			var3_name = line3.substring(3,index_of_sign_3);
			int var3_value = Integer.parseInt(line3.substring(index_of_sign_3+1,line3.indexOf(";")));
			calculation_line = calculation_line.replace(var3_name, Integer.toString(var3_value));
		}
		else if(line3.charAt(0)=='d'){
			var3_name = line3.substring(6,index_of_sign_3);
			double var3_value = Double.parseDouble(line3.substring(index_of_sign_3+1,line3.indexOf(";")));
			calculation_line = calculation_line.replace(var3_name, Double.toString(var3_value));
        }
        //These lines call primary methods. First I removed parentheses, calculated multiplications and divisions, and finally summations and subtractions.
        String without_parent = parenthesis(calculation_line);
        String without_multi_div = math_level2(without_parent);
        String answer = math_level1(without_multi_div);
        System.out.println(answer);
    }
/*This method uses a for loop to find the first sign of a given string and returns the index of that sign.If there are
no signs in the string, it gives the length because I want substring method to take all of the remain string in such a case. */
public static int finder(String line){
    for (int i = 0; i < line.length(); i++) {//i is the current index 
        char current_char = line.charAt(i);
        if(current_char == '+' || current_char == '-' || current_char == '*' || current_char == '/' ){
            return i;
        }
    }
    return line.length();
}
/*This method returns the given string reversed. */
public static String reverser(String line) {
    String result="";
    for (int i = line.length()-1; i >=0 ; i--) {
        result += line.charAt(i);
    }
    return result;
}

public static String math_level1(String line) {
    int current_index = 0;//I will be iterating over and over the string. I used a while loop instead of a for loop because I need to use more than one control statement
    boolean isnegative = false; /*Because it is easier to work with positive numbers, when all the numbers are negative 
    I change all of them to positive and make this boolean true*/
    while(current_index<line.length()-1){
        if(line.charAt(0) =='+') { line = line.substring(1); continue;}//Removes the unnecessary pluses at the beginning.
        if(line.charAt(0) =='-'){
            if(!line.contains("+")) {line = line.replace("-", "+"); isnegative=true; continue;}//Changes every minus with a plus when every number is negative since handling positive numbers are easier
            line = line.substring(finder(line.substring(1))+1)+line.substring(0, finder(line.substring(1))+1); //Moves the first negative number to the end
            continue;
        }
        if(!line.contains("+")&&!line.contains("-")) break;// No sign means there are no calculations left
            char current_char = line.charAt(current_index);
            if(current_char == '+' || current_char =='-'){// Found a sign to calculate
                String first_string = reverser(line.substring(0,current_index)); // reversed the input to be able to iterate forward
                if (first_string !="")  first_string = reverser(first_string.substring(0,finder(first_string)));//Took from 0 until first sign.
                else first_string = line.substring(0,current_index);//Empty means it is at the very beginning.
                
                String second_string = line.substring(current_index+1);//From the current sign until the next sign which is found by finder method 
                second_string = line.substring(current_index+1,current_index+finder(second_string)+1);
                //Used boolean variables to store if two numbers are integer or double
                boolean is_double_first = first_string.contains(".");
                boolean is_second_double = second_string.contains(".");
                boolean isdouble = is_double_first || is_second_double; //Even one of them is double -> answer will be double
                // Two scenarios are possible, I used same lines twice because we cannot change the type of a variable which has already declared.
                if(isdouble){
                    double number1 = Double.parseDouble(first_string);//Derived numbers from strings
                    double number2 = Double.parseDouble(second_string);
                    double result;
                    if(current_char=='+') result = number1+number2;//Again two scenarios are possible.
                    else                  result = number1-number2;
                    //Next line modifies the given line. It replaces the calculation with the result. It uses an if statement to decide on the sign of the answer.
                    line = line.substring(0, current_index-first_string.length()) + (result>=0?"+":"") + result + line.substring(current_index+1+finder(second_string));
                }
                // This block is almost same as above. Only the data type is different.
                else{
                    int number1 = Integer.parseInt(first_string);
                    int number2 = Integer.parseInt(second_string);
                    int  result;
                    if(current_char=='+')     result = number1+number2;
                    else     result = number1-number2;
                    line = line.substring(0, current_index-first_string.length())+ (result>=0?"+":"")  + result + line.substring(current_index+1+finder(second_string));

                }
                current_index=-1;// Moved to index to the 0 after every calculation. (-1 +1 =0)
            }
            current_index++;// Every while loop moves the cursor to one right.
        }   
    return isnegative?"-"+line:line;//Returns the answer as a string.
}
//This method works very similar to the math_level1 method.Since almost all the variables are the same, I will comment only the different ones.
public static String math_level2(String line){
    int current_index = 0;
    line = "+"+line; //Every element must have a sign before so I add a plus as a sign of the first element.
    while(current_index<line.length() && (line.contains("*") || line.contains("/"))){
        char current_char = line.charAt(current_index);
        if(current_char == '*' || current_char =='/'){
            String first_string = reverser(line.substring(0,current_index));
            if (first_string !="")  first_string = reverser(first_string.substring(0,finder(first_string)));
            else first_string = line.substring(0,current_index);
            
            char before_sign = line.charAt(current_index-first_string.length()-1); // Sign before an element has an effect on the result so I'm storing it.
            
            String second_string = line.substring(current_index+1);
            second_string = line.substring(current_index+1,current_index+finder(second_string.substring(1))+2);
    
            boolean is_double_first = first_string.contains(".");
            boolean is_second_double = second_string.contains(".");
            boolean isdouble = is_double_first || is_second_double;

            if(isdouble){
                double number1 = Double.parseDouble(first_string);
                double number2 = Double.parseDouble(second_string);
                double result;
                if(current_char=='*') result = number1*number2;
                else                  result = number1/number2;
                // I'm using two ternary operations to decide on the sign of the result. If both before sign is a minus and the result is negative, I'm replacing it with result*-1
                line = line.substring(0, current_index-first_string.length()-1)+  ((result<0)?((before_sign=='-')?("+"+(result*-1)):(result+"")):(before_sign+""+result)) + line.substring(current_index+second_string.length()+1);
            }

            else{
                int number1 = Integer.parseInt(first_string);
                int number2 = Integer.parseInt(second_string);
                int  result;
                if(current_char=='*')     result = number1*number2;
                else     result = number1/number2;
                // Same operation as I do in line 155
                line = line.substring(0, current_index-first_string.length()-1)+  ((result<0)?((before_sign=='-')?("+"+(result*-1)):(result+"")):(before_sign+""+result)) + line.substring(current_index+second_string.length()+1);

            }
            current_index= current_index-first_string.length();// I'm using index as a cursor and moving it a little left instead of making it zero because this is much faster
        }
        current_index++;
        if(!(line.contains("*") || line.contains("/"))) break;// Breaks the while loop if there are no multiplication or division left.
    }
    return line;
}
public static String parenthesis(String line) {
    while(line.contains("(")){// Loops until there are no parentheses left
        int index_of_closer = line.indexOf(")"); // I iterated backwards by finding the closer firts instead of looking for a '(' sign
        int index_of_opener = index_of_closer; //I created a new index which is equal to the index of the '(' in order to store the index of a '(' when I find it. 
        while(line.charAt(index_of_opener) !='(') index_of_opener--;// I decreased the index left until find a opener
        String result = math_level1(math_level2(line.substring(index_of_opener+1,index_of_closer))); // I took the string between '(' and ')' and send it to math_level2 and subsequently math_level1 method.
        char before_sign = line.charAt(index_of_opener-1); // I stored the sign before the parenthesis since it affects the sign of the result.
        if(result.charAt(0)=='-'){ // If answer is negative
            if(before_sign == '-'){ // Two minuses means +, replacement will be positive
                result = result.substring(1);// I removed the minus sign
                line = line.substring(0,index_of_opener-1) +"+"+ result + line.substring(index_of_closer+1);// and replaced the calculation with the result.
            }
            else 
                line = line.substring(0,index_of_opener) + result + line.substring(index_of_closer+1);// If before sign is plus, I can ignore it 
        }
        else // Answer is positive
            line = line.substring(0,index_of_opener) + result + line.substring(index_of_closer+1);//When answer is positive, I used the before sign without controlling it first
    }
    return line;
    }
}
