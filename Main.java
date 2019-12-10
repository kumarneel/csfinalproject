import java.util.*;
import java.io.File;

public class Main extends Hashmaps{
    public static HashMap<String, Integer> OPTAB = new HashMap<String,Integer>();
    public static HashMap<String, String> FMTAB = new HashMap<String, String>();
    public static HashMap<String, Integer> SYMTAB  = new LinkedHashMap<String,Integer>();
    public static HashMap<Integer,Integer> LOCTAB = new LinkedHashMap<Integer,Integer>();
    public static HashMap<String, String[]> assemblyMap = new LinkedHashMap<String, String[]>();


    public static LinkedList<Integer> mrecSt = new LinkedList<Integer>();
    public static LinkedList<String> mrecCmd = new LinkedList<String>();
    public static LinkedList<Integer> mrecAd = new LinkedList<Integer>();
    public static LinkedList<String> opcode = new LinkedList<String>();

    public static String PROGNAME;
    public static int PROGLEN;
    public static int LOCCTR = 0;
    public static int stAd = 0;

    public static void init() {
    	Hashmaps init = new Hashmaps();
    	init.fmtab(FMTAB);
    	init.optab(OPTAB);
    }

    public static void readFile() {
        //open and read input file and store into map...
    	Scanner userInput = new Scanner(System.in);
    	System.out.println("Input the name of your SIC/XE file.");
    	String fileLoc = userInput.nextLine();
    	userInput.close();
        try {
        	File file = new File(fileLoc);
            Scanner sc = new Scanner(file);
            int counter = 0;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String words[] = line.split("\\.");
                assemblyMap.put(Integer.toString(counter),words);
                counter++;
            }
            sc.close();
        }
        catch(Exception e) {
        	System.out.println("Sorry, file not found.");
        	System.out.println("Exiting...");
        	System.exit(0);
        }
    }

    public static void passOne() {
    	// INIT GLOBAL VARIABLES
		PROGNAME = "";
	    LOCCTR = 0;
	    stAd = 0;
	    PROGLEN = 0;

	    //if opcode = 'start' then...
	    String firstLine[] = assemblyMap.get("0");
	    if (firstLine[1].equals("START")){
	      PROGNAME = firstLine[0];
	      //save #[OPERAND] as starting address
	      stAd = Integer.parseInt(String.valueOf(firstLine[2]),16);
	      //initialize LOCCTR to starting address
	      LOCCTR = stAd;
	    }
	    else{
	      //initialize LOCCTR to 0
	      LOCCTR = 0x00;
	    }

	    for(int i = 1; i < assemblyMap.size();i++){
	    	// Local Variables
		    String currentLine[] = assemblyMap.get(String.valueOf(i));
		    String label = currentLine[0];
		    String check = currentLine[1];
		    String operand = currentLine[2];

		    LOCTAB.put(i,LOCCTR);

		    // END OF FILE
		    if(check.equals("END")){
		    	break;
		    }
		    //check if there is a symbol in the label field
		    if (!label.equals(" ")) {
		        //check if the label already exists in the SYMTAB
		        if ((SYMTAB.containsKey(label))){
		          System.out.print("DUPLICATE SYMBOL");
		          return;
		        }else{
		            //insert into SYMTAB
		            SYMTAB.put(label,LOCCTR);
		        }
		    }
		    //add to LOCCTR
		    if(check.charAt(0) == '+'){
		      LOCCTR += 0x04;
		    }else if(OPTAB.containsKey(check)){
		      LOCCTR += 0x03;
		    }else if(check.equals("WORD")){
		      LOCCTR += 0x03;
		    }else if(check.equals("RESW")){
		      LOCCTR += 0x03 * Integer.parseInt(operand);
		    }else if(check.equals("RESB")){
		      LOCCTR += Integer.parseInt(operand);
		    }else if(check.equals("BYTE")){
		        LOCCTR += operand.length()-2;
		    } else if(check.equals("BASE")){
		        //comment line do nothing...
		    } else {
		        System.out.println("INVALID OPERATION CODE");
		            return;
		        }
	    }

      Scanner scan = new Scanner(System.in);
      String answ;
      String sym[] = new String[3];
      int val;

      System.out.println("Would you like to define a new symbol? Y/N");
      answ = scan.nextLine();
      if(answ == "Y" || answ == "y"){
        System.out.println("Define new symbol");
        System.out.println("Format: SYMBOL + EQU + FLAGS ");
        for(int i = 0; i<2; i++){
          sym[i] = scan.nextLine();
        }
        val = Integer.parseInt(sym[2]);
        OPTAB.put(sym[0],val);
	    }



		// CALCULATE PROGRAM LENGTH
		PROGLEN = LOCCTR - stAd;

		// -------- START OPCODE CALCULATION --------
		int B = 0;
    LinkedList<Integer> mrecSt = new LinkedList<Integer>();
    LinkedList<String> mrecCmd = new LinkedList<String>();
    LinkedList<Integer> mrecAd = new LinkedList<Integer>();
		for (int i = 1; i < assemblyMap.size(); i++) {
			// Local Variables
			String line[] = assemblyMap.get(String.valueOf(i));
			int objectCode = 0;
			int format = 0;
			int DISP = 0;
			int TA = 0;
			int PC = LOCTAB.get(i+1);

			String operand = line[2];
			String check = line[1];
			String nixbpeString = "";

			if(SYMTAB.containsKey(operand)) {
				TA = SYMTAB.get(operand);
			}
			else if(operand.contains(",")) {
				String[] t = operand.split(",");
				TA = SYMTAB.get(t[0]);
			}

			if((!SYMTAB.containsKey("BASE")) && check.equals("BASE")) {
				SYMTAB.put("BASE",SYMTAB.get(operand));
				B = SYMTAB.get(operand);
			}

			if(check.equals("BYTE") ||check.equals("WORD") || check.equals("RESB")||check.equals("RESW")) {
				break;
			}

			if(check.charAt(0) =='+') {
				format = 4;
				if(operand.charAt(0) == '@') {
					nixbpeString = "100001";
				}
				else if(operand.charAt(0) == '#') {
					nixbpeString = "010001";
				}
				else if(operand.contains(",")) {
					nixbpeString = "111001";
				}
				else {
					nixbpeString = "110001";
				}
			}
			else {
				if(operand.charAt(0) == '@') {
					operand = operand.substring(1, operand.length());
					if (Character.isDigit(operand.charAt(0))) {
						nixbpeString = "100000";
					}
					else {
						int A = LOCTAB.get(i);
						int Base = SYMTAB.get(operand);
						if(A > Base) {
							if(A - Base < 0xFFF) {
								nixbpeString = "100010";
							}
							else {
								nixbpeString = "100100";
							}
						}
						else if(Base > A){
							if(Base - A < 0xFFF) {
								nixbpeString = "100010";
							}
							else {
								nixbpeString = "100100";
							}
						}
					}
				}
				else if(operand.charAt(0) == '#') {
					operand = operand.substring(1, operand.length());
					if (Character.isDigit(operand.charAt(0))) {
						nixbpeString = "010000";
					}
					else {
						int A = LOCTAB.get(i);
						int Base = SYMTAB.get(operand);
						if(A > Base){
							if(A - Base < 0xFFF) {
								nixbpeString = "010010";
							}
							else {
								nixbpeString = "010100";
							}
						}
						else if(Base > A){
							if(Base - A < 0xFFF) {
								nixbpeString = "010010";
							}
							else {
								nixbpeString = "010100";
							}
						}
					}
				}
				else if(operand.contains(",")) {
					if (Character.isDigit(operand.charAt(0))) {
						nixbpeString = "111000";
					}
					else {
						String[] split = operand.split(",");
						int A = LOCTAB.get(i);
						int Base = SYMTAB.get(split[0]);

						if(A > Base){
							if(A - Base < 0xFFF) {
								nixbpeString = "111010";
							}
							else {
								nixbpeString = "111100";
							}
						}
						else if(Base > A) {
							if(Base - A < 0xFFF) {
								nixbpeString = "111010";
							}
							else {
								nixbpeString = "111100";
							}
						}
					}
				}
				else {
					if (Character.isDigit(operand.charAt(0))) {
						nixbpeString = "110000";
					}
					else {
						int Base = 0;
						int A = LOCTAB.get(i);

						if(operand.equals("s")) {
							Base = 0;
						}
						else {
							Base = SYMTAB.get(operand);
						}
						if(A > Base) {
							if(A - Base < 0xFFF) {
								nixbpeString = "110010";
							}
							else {
								nixbpeString = "110100";
							}
						}
						else if(Base > A) {
							if(Base - A < 0xFFF) {
								nixbpeString = "110010";
							}
							else {
								nixbpeString = "110100";
							}
						}
					}
				}
			}

			if(check.equals("BASE")) {
				continue;
			}


      if(FMTAB.containsKey(check)){
        format = Integer.parseInt(FMTAB.get(check));
      }else{
        mrecSt.add(LOCTAB.get(i));
        mrecCmd.add(check);
        mrecAd.add(LOCTAB.get(i));
      }
			//start simple calculation of displacement
			if(nixbpeString.equals("110000")) {
				DISP = TA;
			}
			else if (nixbpeString.equals("110001")) {
				if(SYMTAB.containsKey(operand.substring(1))) {
					DISP = SYMTAB.get(operand.substring(1));
				}
				else {
					// Do nothing
				}
			}
			else if (nixbpeString.equals("110010")) {
				DISP = TA - PC;
			}
			else if (nixbpeString.equals("110100")) {
				DISP = TA - B;
			}
			else if (nixbpeString.equals("111000")) {
				DISP = TA;
			}
			else if (nixbpeString.equals("111001")) {
				if(SYMTAB.containsKey(operand.substring(1))) {
					DISP = SYMTAB.get(operand.substring(1));
				}
				else {
					// Do nothing
				}
			}
			else if (nixbpeString.equals("111010")) {
				DISP = TA - PC;
			}
			else if (nixbpeString.equals("111100")) {
				DISP = TA - B;
			}
			// Indirect Addressing Mode
			else if (nixbpeString.equals("100000")) {
				DISP = TA;
			}
			else if(nixbpeString.equals("100001")) {
				if(SYMTAB.containsKey(operand.substring(1))) {
					DISP = SYMTAB.get(operand.substring(1));
				}else{
					// Do nothing
				}
			}
			else if(nixbpeString.equals("100010")) {
				DISP = TA - PC;
			}
			else if(nixbpeString.equals("100100")) {
				DISP = TA - B;
			}
			else if(nixbpeString.equals("010000")) {
				DISP = TA;
			}
			else if(nixbpeString.equals("010001")) {
				if(SYMTAB.containsKey(operand.substring(1))) {
					DISP = SYMTAB.get(operand.substring(1));
				}
				else {
					// Do nothing
				}
			}
			else if(nixbpeString.equals("010010")) {
				DISP = TA - PC;
			}
			else if(nixbpeString.equals("010100")) {
				DISP = TA - B;
			}
			if (check.equals("RSUB")) {
				nixbpeString = "110000";
				DISP = 0;
			}

			// FORMAT 1 ADDRESSING
			if(format == 1) {
				String temp = String.format("%X",OPTAB.get(check));
				if(temp.length() == 1){
					temp = "0" + temp;
				}
				System.out.println(temp);
			}

			// FORMAT 2 ADDRESSING
			if(format == 2) {
				String temp = String.format("%X",OPTAB.get(check));
				if(temp.length() == 1) {
					temp = "0" + temp;
				}
				System.out.println(temp);
			}

			// FORMAT 3 ADDRESSING
			if(format == 3){
				String op = Integer.toBinaryString(OPTAB.get(check));
				int length = op.length();
				for(int k = 0; k < (8-length);k++) {
					op = "0" + op;
				}

				op = op.substring(0, 6);
				op= op + nixbpeString;
				//check if longer than 12 bits...
				String disp = Integer.toBinaryString(DISP);
				if(disp.length() > 12) {
					disp = disp.substring(disp.length()-12, disp.length());
				}
				else {
					length = disp.length();
					for(int p = 0; p < (12-length);p++) {
						disp = "0" + disp;
					}
				}
				op += disp;
				objectCode = Integer.parseInt(op, 2);
				String finalObjectCode = String.format("%X",objectCode);
				length = finalObjectCode.length();
				for(int k = 0; k < (6-length);k++) {
					finalObjectCode = "0" + finalObjectCode;
				}
				opcode.add(finalObjectCode);
			}

			// FORMAT 4 ADDRESSING
			if (format == 4) {
				String op = Integer.toBinaryString(OPTAB.get(check.substring(1, check.length())));
				int length = op.length();
				for(int k = 0; k < (8-length);k++) {
					op = "0" + op;
				}
				op = op.substring(0, 6);
				op= op + nixbpeString;
				if(!Character.isLetter(operand.charAt(0))) {
					operand = operand.substring(1, operand.length());
				}
				int address = SYMTAB.get(operand);
				String add = String.format("%X",address);
				length = add.length();
				for(int k = 0; k < (5-length);k++) {
					add = "0" + add;
				}
				objectCode = Integer.parseInt(op, 2);
				String finalObjectCode = String.format("%X",objectCode);
				finalObjectCode += add;
				for(int k = 0; k < (8-finalObjectCode.length());k++) {
					finalObjectCode = "0" + finalObjectCode;
				}
				opcode.add(finalObjectCode);
			}

		}
		// --------End Opcode Calculation --------
    }

    public static void passTwo() {
        //if opcode = 'start' then
        String passOneStart[] = assemblyMap.get("0");
        if(passOneStart[1].equals("START")) {
          // Do Nothing
        }
        else {
          return;
        }

        // ------  Begin Header Record  -------
        System.out.print("H^" + PROGNAME);
        for(int i = 0; i < 6-PROGNAME.length();i++) {
          System.out.print(" ");
        }
        int len = String.format("%X",stAd).length();
        System.out.print("^");
        for(int i = 0; i < (6-len);i++) {
          System.out.print("0");
        }
        System.out.print(String.format("%X",stAd) + "^");


        len = String.format("%X",PROGLEN).length();
        for(int i = 0; i < (6-len);i++) {
          System.out.print("0");
        }
        System.out.println(String.format("%X",PROGLEN));
        // End Header Record

        // -------  Begin Text Record  ---------
        System.out.print("T^");
        // Starting Address
        len = String.format("%X",stAd).length();
        for(int i = 0; i < (6-len);i++) {
          System.out.print("0");
        }
        System.out.print(String.format("%X",stAd) + "^");
        System.out.print(String.format("%X",opcode.size()*3) + "^");
        int i = 0;
        for(String op: opcode) {
          System.out.print(op);
          if(opcode.size() != ++i) {
            System.out.print("^");

          }
        }

        // ------- Begin Modficiation Record ----------
        System.out.println("");
        for(int k = 0; k < mrecCmd.size(); k++){
            System.out.print("M");
            len = String.format("%X",mrecAd.get(k)).length();
            for(int j = 0; j < (6-len);j++){
              System.out.print("0");
            }
            System.out.print(mrecAd.get(i)+"^");
            System.out.print("05^");
            System.out.println(mrecSt.get(i));

        }

        //---------- Begin End Record ------------
        System.out.print("\n"+ "E^");
        len = String.format("%X",stAd).length();
        for(i = 0; i < (6-len);i++){
          System.out.print("0");
        }
        System.out.print(String.format("%X",stAd));
    }

    public static void main(String[] args){
    	init();
    	readFile();

    	try {
    		passOne();
    	}
    	catch (Exception e) {
    		System.out.println("Pass one failed.");
    	}

    	try {
        	passTwo();
    	}
    	catch (Exception e) {
    		System.out.println("Pass two failed.");
    	}
    }
}
