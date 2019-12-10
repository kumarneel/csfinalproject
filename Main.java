import java.util.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import data.*;

public class Main extends Hashmaps{
    public HashMap<String, Integer> OPTAB = new HashMap<String,Integer>();
    public HashMap<String, String> FMTAB = new HashMap<String, String>();

    public static void main(String[] args) throws FileNotFoundException{
        Hashmaps import = new Hashmaps();
        import.init();

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
      // --------  Start OpCode Calculation --------
      int B = 0;
      LinkedList<Integer> mrecSt = new LinkedList<Integer>();
      LinkedList<String> plz = new LinkedList<String>();
      LinkedList<String> opcode = new LinkedList<String>();
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
        String nixbpeString = "";
        if(SYMTAB.containsKey(operand)){
            TA = SYMTAB.get(operand);
        }else if(operand.contains(",")){
          String[] t = operand.split(",");
          TA = SYMTAB.get(t[0]);
        }
        if((!SYMTAB.containsKey("BASE")) && check.equals("BASE")){
            SYMTAB.put("BASE",SYMTAB.get(operand));
            B = SYMTAB.get(operand);
        }
        if(check.equals("BYTE") ||check.equals("WORD") || check.equals("RESB")||check.equals("RESW")){
            break;
        }

        if(check.charAt(0) ==  '+'){
            format = 4;
            if(operand.charAt(0) == '@'){
                nixbpeString = "100001";
            }
            else if(operand.charAt(0) == '#'){
                nixbpeString = "010001";
            }
            else if(operand.contains(",")){
                nixbpeString = "111001";
            }else{
                nixbpeString = "110001";
            }
        }else{
          if(operand.charAt(0) == '@'){
              // String temp = operand.subsubstring(1, operand.length());
              operand = operand.substring(1, operand.length());
              if (Character.isDigit(operand.charAt(0))){
                nixbpeString = "100000";
              }else{
                int A = LOCTAB.get(i);
                int Base = SYMTAB.get(operand);
                if(A > Base){
                  if(A - Base < 0xFFF){
                      nixbpeString = "100010";
                  }else{
                      nixbpeString = "100100";
                  }

                }else if(Base > A){
                  if(Base - A < 0xFFF){
                      nixbpeString = "100010";
                  }else{
                      nixbpeString = "100100";
                  }
                }
              }
          }else if(operand.charAt(0) == '#'){
              // String temp = operand.subsubstring(1, operand.length());
              operand = operand.substring(1, operand.length());
              if (Character.isDigit(operand.charAt(0))){
                nixbpeString = "010000";
              }else{
                int A = LOCTAB.get(i);
                int Base = SYMTAB.get(operand);
                if(A > Base){
                  if(A - Base < 0xFFF){
                      nixbpeString = "010010";
                  }else{
                      nixbpeString = "010100";
                  }

                }else if(Base > A){
                  if(Base - A < 0xFFF){
                      nixbpeString = "010010";
                  }else{
                      nixbpeString = "010100";
                  }
                }
              }
          }else if(operand.contains(",")){
            if (Character.isDigit(operand.charAt(0))){
                nixbpeString = "111000";
            }else{
              String[] split = operand.split(",");
              int A = LOCTAB.get(i);
              int Base = SYMTAB.get(split[0]);
              if(A > Base){
                if(A - Base < 0xFFF){
                    nixbpeString = "111010";
                }else{
                    nixbpeString = "111100";
                }
              }else if(Base > A){
                if(Base - A < 0xFFF){
                    nixbpeString = "111010";
                }else{
                    nixbpeString = "111100";
                }
              }
            }
          }else{
            if (Character.isDigit(operand.charAt(0))){
                nixbpeString = "110000";
            }else{
              int Base = 0;
              int A = LOCTAB.get(i);
              if(operand.equals("s")){
                  Base = 0;
              }else{
                Base = SYMTAB.get(operand);

              }
              if(A > Base){
                if(A - Base < 0xFFF){
                    nixbpeString = "110010";
                }else{
                    nixbpeString = "110100";
                }

              }else if(Base > A){
                if(Base - A < 0xFFF){
                    nixbpeString = "110010";
                }else{
                    nixbpeString = "110100";
                }
              }
            }
          }
        }

        if(check.equals("BASE")){
          continue;
        }


      //LinkedList<Integer> mrec = new LinkedList<Integer>();
        if(FMTAB.containsKey(check)){
          format = Integer.parseInt(FMTAB.get(check));
          System.out.println("------------------");
          System.out.println(check);
        }else{
          System.out.println("+++++++++++++++++");
          System.out.println(check);
          System.out.println(LOCTAB.get(i));
          mrecSt.add(LOCTAB.get(i));
        int   mrecSz = LOCTAB.get(i+1)-LOCTAB.get(i);
        System.out.println(mrecSz);
        plz.add(check);



      //    mrec.add(stAd).length();
      //    System.out.println(mrec);


        }
        //switch(nixbpeString){
          //start simple calc of displacement
          if  (nixbpeString.equals("110000")) {
              DISP = TA;
          }
          else if (nixbpeString.equals("110001")) {
              if(SYMTAB.containsKey(operand.substring(1))){
                DISP = SYMTAB.get(operand.substring(1));
              }else{

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
              if(SYMTAB.containsKey(operand.substring(1))){
                DISP = SYMTAB.get(operand.substring(1));
              }else{

              }
          }else if (nixbpeString.equals("111010")) {
              DISP = TA - PC;
          }
          else if (nixbpeString.equals("111100")) {
              DISP = TA - B;
          }
          //start indrect
          else if (nixbpeString.equals("100000")) {
              DISP = TA;
          }
          else if(nixbpeString.equals("100001")){
                if(SYMTAB.containsKey(operand.substring(1))){
                  DISP = SYMTAB.get(operand.substring(1));
                }else{

                }
          }else if(nixbpeString.equals("100010")){
              DISP = TA - PC;
          }else if(nixbpeString.equals("100100")){
              DISP = TA - B;
          }else if(nixbpeString.equals("010000")){
              DISP = TA;
          }else if(nixbpeString.equals("010001")){
              if(SYMTAB.containsKey(operand.substring(1))){
                DISP = SYMTAB.get(operand.substring(1));
              }else{

              }
          }else if(nixbpeString.equals("010010")){
              DISP = TA - PC;
          }else if(nixbpeString.equals("010100")){
              DISP = TA - B;
          }
          if (check.equals("RSUB")){
              nixbpeString = "110000";
              DISP = 0;
          }
          if(format == 1){
            String temp = String.format("%X",OPTAB.get(check));
            if(temp.length() == 1){
              temp = "0" + temp;
            }
            System.out.println(temp);
          }
          //format
          if(format == 2){
            String temp = String.format("%X",OPTAB.get(check));
            if(temp.length() == 1){
              temp = "0" + temp;
            }
            System.out.println(temp);
          }
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
                  disp = disp.substring(disp.length()-12, disp.length());
              }else{
                  length = disp.length();
                  for(int p = 0; p < (12-length);p++){
                    disp = "0" + disp;
                  }
              }

              op += disp;
              objectCode = Integer.parseInt(op, 2);
              String finalObjectCode = String.format("%X",objectCode);
              length = finalObjectCode.length();
              for(int k = 0; k < (6-length);k++){
                  finalObjectCode = "0" + finalObjectCode;
              }
              opcode.add(finalObjectCode);
          }
          if (format == 4){
              String op = Integer.toBinaryString(OPTAB.get(check.substring(1, check.length())));
              int length = op.length();
              for(int k = 0; k < (8-length);k++){
                  op = "0" + op;
              }
              op = op.substring(0, 6);
              op  = op + nixbpeString;
              if(!Character.isLetter(operand.charAt(0))){
                  operand = operand.substring(1, operand.length());
              }
              int address = SYMTAB.get(operand);
              String add = String.format("%X",address);
              length = add.length();
              for(int k = 0; k < (5-length);k++){
                  add = "0" + add;
              }
              objectCode = Integer.parseInt(op, 2);
              String finalObjectCode = String.format("%X",objectCode);
              finalObjectCode += add;
              for(int k = 0; k < (8-finalObjectCode.length());k++){
                  finalObjectCode = "0" + finalObjectCode;
              }
              opcode.add(finalObjectCode);
          }

      }

      // For Testing

      // System.out.println();

      // --------  End Opcode Calc --------

      //PASS 2
      //if opcode = 'start' then
      String passOneStart[] = assemblyMap.get("0");
      if(passOneStart[1].equals("START")){
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

      // -------  Begin Text Record  ---------
      System.out.print("T^");
      // Starting Address
      len = String.format("%X",stAd).length();
      for(int i = 0; i < (6-len);i++){
        System.out.print("0");
      }
      System.out.print(String.format("%X",stAd) + "^");
      System.out.print(String.format("%X",opcode.size()*3) + "^");
      int i = 0;
      for(String op: opcode){
        System.out.print(op);
        if(opcode.size() != ++i) {
          System.out.print("^");
        }
      }

      // ------- Begin Modficiation Record ----------
      //If()
      //addresses are stored within LOCTABLE
      //
      System.out.println("");
    //  System.out.print("M");
      for(int i = 0; i < plz.size(); i++){
          System.out.print("M");
        //  System.out.print(String.format("%X",opcode.size()*4) + "^");
          System.out.println(plz.get(i));

      }


      //---------- Begin End Record ------------

      System.out.print("\n"+ "E^");
      len = String.format("%X",stAd).length();
      for(int i = 0; i < (6-len);i++){
        System.out.print("0");
      }
      System.out.print(String.format("%X",stAd));





    }



}
