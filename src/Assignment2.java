import java.io.*;
import java.util.Scanner;
public class Assignment2 {

	public static String remover(String line,char virus) {
		String result = "";
		for (int i = 0; i < line.length(); i++) {
			result += (line.charAt(i) != virus ?line.charAt(i) :"" );
		}
		return result;
	}


	public static int counter(String line, char character){
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == character){
				count++;
			}
		}
		return count;
	}


	public static double basic_math(String line) { // Just for addition and subtraction
		//String operators = "+-";
		String seperatedString = "";
		int index = 0; 
		while(index<line.length()){
			if(line.charAt(index) == '+'){
				seperatedString += "+";
				index ++;
			}
			else if(line.charAt(index) == '-'){
				seperatedString += "-";
				index ++;
			} 
			else{
			String temp = "";
			int te = 0;
			for (int i = index; i < line.length(); i++) {
				char char_now = line.charAt(i);
				if (char_now !='+' &&char_now !='-'){//Number or dot
					temp += ( "" + char_now);
					te++;	
				}
				else{
					break;
				}
			}
			index+=te;	
			seperatedString += (temp+",");
			}
		}
		seperatedString = seperatedString.substring(0,seperatedString.length());
		System.out.println(seperatedString);
		int count = counter(seperatedString, ',');
		double sum = 0;
		for (int i = 0; i <count; i++) {
			String partial = seperatedString.substring(0,seperatedString.indexOf(','));
			System.out.println("q="+partial);
			seperatedString = seperatedString.substring(seperatedString.indexOf(',')+1,seperatedString.length());
			sum+= Double.parseDouble(partial);	
		}
	return sum;
	}		
	

	public static String intermediate_math(String line) {
		



		return line;
	}
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
		System.out.println("result = "+basic_math(calculation_line));

		
	
	}
}
