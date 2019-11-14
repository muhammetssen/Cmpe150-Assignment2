import java.io.*;
import java.util.Scanner;

public class Assignment21{
    public static void main(String[] args) throws FileNotFoundException {
    
    File input_file = new File("input.txt");
		Scanner lineScanner = new Scanner(input_file);
		String line1 = remover(lineScanner.nextLine(), ' ').toLowerCase();
		String line2 = remover(lineScanner.nextLine(), ' ').toLowerCase();
		String line3 = remover(lineScanner.nextLine(), ' ').toLowerCase();
		String calculation_line = remover(lineScanner.nextLine(), ' ').toLowerCase();
		calculation_line ="+"+calculation_line.substring(0,calculation_line.length()-1);
		int index_of_sign_1 = line1.indexOf("=");
		int index_of_sign_2 = line2.indexOf("=");
		int index_of_sign_3 = line3.indexOf("=");
		String var1_name,var2_name,var3_name;
		//double var1_value,var2_value,var3_value;
		
		if(line1.charAt(0)=='i'){
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
        
        System.out.println((math_level1(math_level2(parant(calculation_line)))));
}

public static String remover(String line,char virus) {
    String result = "";
    for (int i = 0; i < line.length(); i++) {
        result += (line.charAt(i) != virus ?line.charAt(i) :"" );
    }
    return result;
}
public static int counter(String line, char character) {
    int result = 0;
    for (int i = 0; i < line.length(); i++) {
        if(line.length()==character){
            result++;
        }
    }
    return result;
}
public static int finder(String line){
    for (int i = 0; i < line.length(); i++) {
        char current_char = line.charAt(i);
        if(current_char == '+' || current_char == '-' || current_char == '*' || current_char == '/' ){
            return i;
        }

    }
    return line.length();
}
public static String reverser(String line) {
    String result="";

    for (int i = line.length()-1; i >=0 ; i--) {
        result += line.charAt(i);
        
    }
    return result;
}
public static boolean chechker(String line,char character) {
    return counter(line, character)==0?true:false;
    
}

public static String math_level1(String line) {
    boolean isnegative = false;
    int current_index = 0;
    while(current_index<line.length()-1){
        if(line.charAt(0) =='+') { line = line.substring(1); continue;}
        if(line.charAt(0) =='-'){
            if(!line.contains("+")) {line = line.replace("-", "+"); isnegative=true; continue;}
            String temp = line;
            line = temp.substring(finder(temp.substring(1))+1)+temp.substring(0, finder(temp.substring(1))+1); 
            if(!chechker(line.substring(1),'+') && !chechker(line.substring(1),'+')) break;
            
            continue;
        }
            char current_char = line.charAt(current_index);
            if(current_char == '+' || current_char =='-'){

            String first_string = reverser(line.substring(0,current_index));
            if (first_string !="")  first_string = reverser(first_string.substring(0,finder(first_string)));
            else first_string = line.substring(0,current_index);
            
            String second_string = line.substring(current_index+1);
            second_string = line.substring(current_index+1,current_index+finder(second_string)+1);
    
            boolean is_double_first = first_string.contains(".");
            boolean is_second_double = second_string.contains(".");
            boolean isdouble = is_double_first || is_second_double;

            if(isdouble){
                double number1 = Double.parseDouble(first_string);
                double number2 = Double.parseDouble(second_string);
                double result;
                if(current_char=='+') result = number1+number2;
                else                  result = number1-number2;
                line = line.substring(0, current_index-first_string.length()) + (result>=0?"+":"") + result + line.substring(current_index+1+finder(second_string));
            }

            else{
                int number1 = Integer.parseInt(first_string);
                int number2 = Integer.parseInt(second_string);
                int  result;
                if(current_char=='+')     result = number1+number2;
                else     result = number1-number2;
                line = line.substring(0, current_index-first_string.length())+ (result>=0?"+":"")  + result + line.substring(current_index+1+finder(second_string));

            }
            current_index=-1;
        }
        current_index++;
}
    return isnegative?"-"+line:line;
    
}




public static String math_level2(String line){
    int current_index = 0;
    while(current_index<line.length() && (chechker(line,'*') || chechker(line,'/'))){
        char current_char = line.charAt(current_index);
        if(current_char == '*' || current_char =='/'){
            
            String first_string = reverser(line.substring(0,current_index));
            if (first_string !="")  first_string = reverser(first_string.substring(0,finder(first_string)));
            else first_string = line.substring(0,current_index);
            
            String second_string = line.substring(current_index+1);
            boolean isnegative = second_string.charAt(0) =='-';
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
                line = line.substring(0, current_index-first_string.length()) + result + line.substring(current_index+1+finder(second_string));
            }

            else{
                int number1 = Integer.parseInt(first_string);
                int number2 = Integer.parseInt(second_string);
                int  result;
                if(current_char=='*')     result = number1*number2;
                else     result = number1/number2;
                line = line.substring(0, current_index-first_string.length())+ result + line.substring(current_index+1+finder(second_string));

            }
            current_index=-1;
        }
        current_index++;
}
return line;






}


public static String parant(String line) {

    int current_index = 0;
    while(line.contains("(")){
        char current_char = line.charAt(current_index);
        if(current_char == '('){
            for (int i = current_index+1; i <line.length(); i++) {
                if(line.charAt(i)=='('){
                    break;
                }
            else if (line.charAt(i) == ')'){ // Got the pair
                String result = math_level1(math_level2(line.substring(current_index+1,i)));
                char before_sign = line.charAt(current_index-1);
                if(result.charAt(0)=='-'){ // Answer is negative
                    if(before_sign == '-'){
                        result = result.substring(1);
                        line = line.substring(0,current_index-1) +"+"+ result + line.substring(i+1);
                    }
                    else if(before_sign =='+'){
                        line = line.substring(0,current_index-1) + result + line.substring(i+1);
                    }
                }
                else{ // Answer is positive
                    line = line.substring(0,current_index) + result + line.substring(i+1);

                }



                current_index = 0;
                break;
            
            }
            
        }  
        }
        current_index++;
    }
    return line;
}
}
