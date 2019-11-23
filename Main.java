import java.util.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main{
    public static void main(String[] args) throws FileNotFoundException{
        //OPTAB - HashMap of commands and their corresponding operation codes
        HashMap<String, Integer> OPTAB = new HashMap<String,Integer>();
        OPTAB.put("ADD",   0x18);//18
        OPTAB.put("ADDF",  0x58);//58
        OPTAB.put("ADDR",  0x90);//90
        OPTAB.put("AND",   0x40);//40
        OPTAB.put("CLEAR", 0xB4);//B4
        OPTAB.put("COMP",  0x28);//28
        OPTAB.put("COMPF", 0x88);//88
        OPTAB.put("COMPR", 0xA0);//A0
        OPTAB.put("DIV",   0x24);//24
        OPTAB.put("DIVF",  0x64);//64
        OPTAB.put("DIVR",  0x9C);//9C
        OPTAB.put("FIX",   0xC4);//C4
        OPTAB.put("FLOAT", 0xC0);//C0
        OPTAB.put("HIO",   0xF4);//F4
        OPTAB.put("J",     0x3C);//3C
        OPTAB.put("JEQ",   0x30);//30
        OPTAB.put("JGT",   0x34);//34
        OPTAB.put("JLT",   0x38);//38
        OPTAB.put("JSUB",  0x48);//48
        OPTAB.put("LDA",   0x00);//00
        OPTAB.put("LDB",   0x68);//68
        OPTAB.put("LDCH",  0x50);//50
        OPTAB.put("LDF",   0x70);//70
        OPTAB.put("LDL",   0x08);//08
        OPTAB.put("LDS",   0x6C);//6C
        OPTAB.put("LDT",   0x74);//74
        OPTAB.put("LDX",   0x04);//04
        OPTAB.put("LPS",   0xD0);//D0
        OPTAB.put("MUL",   0x20);//20
        OPTAB.put("MULF",  0x60);//60
        OPTAB.put("MULR",  0x98);//98
        OPTAB.put("NORM",  0xC8);//C8
        OPTAB.put("OR",    0x44);//44
        OPTAB.put("RD",    0xD8);//D8
        OPTAB.put("RMO",   0xAC);//AC
        OPTAB.put("RSUB",  0x4C);//4C
        OPTAB.put("SHIFTL",0xA4);//A4
        OPTAB.put("SHIFTR",0xA8);//A8
        OPTAB.put("SIO",   0xF0);//F0
        OPTAB.put("SSK",   0xEC);//EC
        OPTAB.put("STA",   0x0C);//0C
        OPTAB.put("STB",   0x78);//78
        OPTAB.put("STCH",  0x54);//54
        OPTAB.put("STF",   0x80);//80
        OPTAB.put("STI",   0xD4);//D4
        OPTAB.put("STL",   0x14);//14
        OPTAB.put("STS",   0x7C);//7C
        OPTAB.put("STSW",  0xE8);//E8
        OPTAB.put("STT",   0x84);//84
        OPTAB.put("STX",   0x10);//10
        OPTAB.put("SUB",   0x1C);//1C
        OPTAB.put("SUBF",  0x5C);//5C
        OPTAB.put("SUBR",  0x94);//94
        OPTAB.put("SVC",   0xB0);//B0
        OPTAB.put("TD",    0xE0);//E0
        OPTAB.put("TIO",   0xF8);//F8
        OPTAB.put("TIX",   0x2C);//2C
        OPTAB.put("TIXR",  0xB8);//B8
        OPTAB.put("WD",    0xDC);//DC

        HashMap<String, String> FMTAB = new HashMap<String, String>();
        FMTAB.put("ADD", "3");
        FMTAB.put("ADDF", "3");
        FMTAB.put("ADDR", "2");
        FMTAB.put("AND", "3");
        FMTAB.put("CLEAR", "2");
        FMTAB.put("COMP", "3");
        FMTAB.put("COMPF", "3");
        FMTAB.put("COMPR", "2");
        FMTAB.put("DIV",   "3");
        FMTAB.put("DIVF",  "3");
        FMTAB.put("DIVR",  "2");
        FMTAB.put("FIX",   "1");
        FMTAB.put("FLOAT", "1");
        FMTAB.put("HIO",   "1");
        FMTAB.put("J",     "3");
        FMTAB.put("JEQ",   "3");
        FMTAB.put("JGT",   "3");
        FMTAB.put("JLT",   "3");
        FMTAB.put("JSUB",  "3");
        FMTAB.put("LDA",   "3");
        FMTAB.put("LDB",   "3");
        FMTAB.put("LDCH",  "3");
        FMTAB.put("LDF",   "3");
        FMTAB.put("LDL",   "3");
        FMTAB.put("LDS",   "3");
        FMTAB.put("LDT",   "3");
        FMTAB.put("LDX",   "3");
        FMTAB.put("LPS",   "3");
        FMTAB.put("MUL",   "3");
        FMTAB.put("MULF",  "3");
        FMTAB.put("MULR",  "2");
        FMTAB.put("NORM",  "1");
        FMTAB.put("OR",    "3");
        FMTAB.put("RD",    "3");
        FMTAB.put("RMO",   "2");
        FMTAB.put("RSUB",  "3");
        FMTAB.put("SHIFTL","2");
        FMTAB.put("SHIFTR","2");
        FMTAB.put("SIO",   "1");
        FMTAB.put("SSK",   "3");
        FMTAB.put("STA",   "3");
        FMTAB.put("STB",   "3");
        FMTAB.put("STCH",  "3");
        FMTAB.put("STF",   "3");
        FMTAB.put("STI",   "3");
        FMTAB.put("STL",   "3");
        FMTAB.put("STS",   "3");
        FMTAB.put("STSW",  "3");
        FMTAB.put("STT",   "3");
        FMTAB.put("STX",   "3");
        FMTAB.put("SUB",   "3");
        FMTAB.put("SUBF",  "3");
        FMTAB.put("SUBR",  "2");
        FMTAB.put("SVC",   "2");
        FMTAB.put("TD",    "3");
        FMTAB.put("TIO",   "1");
        FMTAB.put("TIX",   "3");
        FMTAB.put("TIXR",  "2");
        FMTAB.put("WD",    "3");

        //open and read input file and store into map...
        File file = new File("assembly.asm");
        HashMap<String, String[]> assemblyMap = new LinkedHashMap<String, String[]>();
        Scanner sc = new Scanner(file);
        int counter = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String words[] = line.split("\\.");
            assemblyMap.put(Integer.toString(counter),words);
            counter++;
        }
        sc.close();

        String PROGNAME = "";
        int LOCCTR = 0;
        int stAd = 0;
        int PROGLEN = 0;
        //PASS 1

        //if opcode = 'start' then...
        String firstLine[] = assemblyMap.get("0");
        if (firstLine[1].equals("START")){
          PROGNAME = firstLine[0];
          //save #[OPERAND] as starting address
          // String temp = firstLine[2];
          stAd = Integer.parseInt(String.valueOf(firstLine[2]),16);
          //initialize LOCCTR to starting address
          LOCCTR = stAd;
          //write line to intermediate File
          //read next input line
        }
        else{
          //initialize LOCCTR to 0
          LOCCTR = 0x00;
        }

        //read next input line
        //store the location in a pairing array, right side of pair is index, left side is the location...

        //initialize HashMap
          HashMap<String, Integer> SYMTAB  = new LinkedHashMap<String,Integer>();
          HashMap<Integer,Integer> LOCTAB = new LinkedHashMap<Integer,Integer>(); //intermediate file

          for(int i = 1; i < assemblyMap.size();i++){
                String currentLine[] = assemblyMap.get(String.valueOf(i));

                String label = currentLine[0];
                String check = currentLine[1];
                String operand = currentLine[2];
                LOCTAB.put(i,LOCCTR);
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
          //calculate program length
          PROGLEN = LOCCTR - stAd;
          // for(String key: SYMTAB.keySet()){
          //     System.out.println("LABEL: " + key + " location -> " + String.format("%X",SYMTAB.get(key)));
          // }
          // for(Integer index: LOCTAB.keySet()){
          //   System.out.println("line index: " + index + " location -> " + String.format("%X",LOCTAB.get(index)));
          // }


      // --------  Start OpCode Calculation --------
      int B = 0;
      Map<Integer, Integer> opcode = new HashMap<Integer, Integer>();
      for (int i = 1; i < assemblyMap.size(); i++){
        String line[] = assemblyMap.get(String.valueOf(i));
        String addrMode = "";
        int objectCode = 0;
        int format = 0;
        int DISP = 0;
        int X = 0;
        int TA = 0;
        int PC = LOCTAB.get(i+1);
        int[] nixbpe = new int[]{0,0,0,0,0,0};
        // Addressing Mode
        String operand = line[2];
        String check = line[1];
        if(SYMTAB.containsKey(operand)){
            TA = SYMTAB.get(operand);
        }
        if((!SYMTAB.containsKey("BASE")) && check.equals("BASE")){
            SYMTAB.put("BASE",SYMTAB.get(operand));
            B = SYMTAB.get(operand);
        }
        if(check.equals("BYTE") ||check.equals("WORD") || check.equals("RESB")||check.equals("RESW")){
            break;
        }
        if (operand.charAt(0) == '@'){
          nixbpe[0] = 1;
          nixbpe[1] = 0;
          nixbpe[2] = 0;
        } else if (operand.charAt(0) == '#') {
          String temp = operand.substring(1);
          if(SYMTAB.containsKey(temp)){
            TA = SYMTAB.get(temp);
          }else{
            TA = Integer.parseInt(temp);
          }
          nixbpe[0] = 0;
          nixbpe[1] = 1;
          nixbpe[2] = 0;
        }
        else {
          nixbpe[0] = 1;
          nixbpe[1] = 1;
        }
        String[] delimit = operand.split(",");
        if (delimit.length > 1) {
          if (delimit[1].equals("X")){
            nixbpe[2] = 1;
              X = 1;
          }
        }
        //test printing nixpbe...
        // System.out.print(operand + ": ");
        // for(int j = 0; j < 6;j++){
        //   System.out.print(nixbpe[j] + " ");
        // }
        if(check.equals("BASE")){
          continue;
        }
        if(FMTAB.containsKey(check)){
          format = Integer.parseInt(FMTAB.get(check));
        }
        if (check.charAt(0) == '+') {
          nixbpe[5] = 1;
          format = 4;
        }
        String nixbpeString = "";
        for(int j = 0; j < 6; j++){
            nixbpeString += String.valueOf(nixbpe[j]);
        }
        switch(nixbpeString){
          //start simple calc of displacement
          case "110000":
              DISP = TA;
          case "110001":
              if(SYMTAB.containsKey(operand.substring(1))){
                DISP = SYMTAB.get(operand.substring(1));
              }else{

              }
          case "110010":
              DISP = TA - PC;
          case "110100":
              DISP = TA - B;
          case "111000":
              DISP = TA;
          case "111001":
              if(SYMTAB.containsKey(operand.substring(1))){
                DISP = SYMTAB.get(operand.substring(1));
              }else{

              }
          case "111010":
              DISP = TA - PC;
          case "1111100":
              DISP = TA - B;
          //start indrect
          case "100000":
              DISP = TA;
          case "100001":
                if(SYMTAB.containsKey(operand.substring(1))){
                  DISP = SYMTAB.get(operand.substring(1));
                }else{

                }
          case "100010":
              DISP = TA - PC;
          case "100100":
              DISP = TA - B;
          case "010000":
              DISP = TA;
          case "010001":
              if(SYMTAB.containsKey(operand.substring(1))){
                DISP = SYMTAB.get(operand.substring(1));
              }else{

              }
          case "010010":
              DISP = TA - PC;
          case "010100":
              DISP = TA - B;
          default:
              // System.out.println(String.format("%X",DISP));
          if(format == 3){
              String op = Integer.toBinaryString(OPTAB.get(check));
              int length = op.length();
              for(int k = 0; k < (8-length);k++){
                  op = "0" + op;
              }
              op = op.substring(0, 6);
              op  = op + nixbpeString;
              //check if longer than 12 bits...
              String disp = Integer.toBinaryString(DISP);
              if(disp.length() > 12){
                  disp = disp.substring(disp.length()-13, disp.length()-1);
              }else{
                  length = disp.length();
                  for(int p = 0; p < (12-length);p++){
                    disp = "0" + disp;
                  }
              }
              op += disp;
              objectCode = Integer.parseInt(op, 2);
              System.out.println(String.format("%X",objectCode));
          }

        }
      }

      // For Testing

      // System.out.println();

      // --------  End Opcode Calc --------

      //PASS 2
      //if opcode = 'start' then
      String passOneStart[] = assemblyMap.get("0");
      if (passOneStart[1].equals("START")){
        //begin
      }else{
        return;
      }

      // ------  Begin Header Record  -------
      System.out.print("H^" + PROGNAME);
      for(int i = 0; i < 6-PROGNAME.length();i++){
        System.out.print(" ");
      }
      int len = String.format("%X",stAd).length();
      System.out.print("^");
      for(int i = 0; i < (6-len);i++){
        System.out.print("0");
      }
      System.out.print(String.format("%X",stAd) + "^");

      len = String.format("%X",PROGLEN).length();
      for(int i = 0; i < (6-len);i++){
        System.out.print("0");
      }
      System.out.println(String.format("%X",PROGLEN));
      // End Header Record

      // Begin Refer Record?
      // Begin Define Record?

      // -------  Begin Text Record  ---------
      System.out.print("T^");
      // Starting Address
      len = String.format("%X",stAd).length();
      for(int i = 0; i < (6-len);i++){
        System.out.print("0");
      }
      System.out.print(String.format("%X",stAd) + "^");
      // Object Code Length
      // TBD
      System.out.println(" ");

      // Begin End Record


          //write Header record to object program
          //initialize first Text record
          //while OPCODE != 'END' do
              //begin
                //if this is not a comment line then
                  //begin search OPTAB for OPCODE
                    //if found then
                        //begin
                        //if there is a symbol in OPERAND field then
                          //begin
                              //search SYMTAB for OPERAND
                              //if found then...
                                //store symbol value as operand address
                                //else
                                  //store 0 as operand address
                                  //set error flag(undefined symbol)
                                    //end
                                    //end (if symbol)

                                //else
                                    //store 0 as operand address
                                    //assmble the object code instruction
                                  //end IF OPCODE FOUND
                              //else if OPCODE = 'BYTE' or 'WORD' then
                                //convert constant to object code
                            //if object code will not fit into current Text record then
                                //begin
                                    //write Text record to object program
                                    //initialize new Text record
                                    //end
                                //add object code to Text record
                                    //end
                                //end IF NOT COMMENT
                                //write listing line
                                //read next input line
                            //end WHILE NOT END
                              //write last Text record to object program
                              //write End record to object program
                              //write last listing Line
                              //end PASS 2

    }
}
