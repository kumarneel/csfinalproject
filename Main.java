import java.util.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException{
        //OPTAB - HashMap of commands and their corresponding operation codes
        HashMap<String, String> opcodes = new HashMap<String,String>();
        opcodes.put("ADD",   "00011000");//18
        opcodes.put("ADDF",  "01011000");//58
        opcodes.put("ADDR",  "10010000");//90
        opcodes.put("AND",   "01000000");//40
        opcodes.put("CLEAR", "10110100");//B4
        opcodes.put("COMP",  "00101000");//28
        opcodes.put("COMPF", "10001000");//88
        opcodes.put("COMPR", "10100000");//A0
        opcodes.put("DIV",   "00100100");//24
        opcodes.put("DIVF",  "01100100");//64
        opcodes.put("DIVR",  "10011100");//9C
        opcodes.put("FIX",   "11000100");//C4
        opcodes.put("FLOAT", "11000000");//C0
        opcodes.put("HIO",   "11110100");//F4
        opcodes.put("J",     "00111100");//3C
        opcodes.put("JEQ",   "00110000");//30
        opcodes.put("JGT",   "00110100");//34
        opcodes.put("JLT",   "00111000");//38
        opcodes.put("JSUB",  "01001000");//48
        opcodes.put("LDA",   "00000000");//00
        opcodes.put("LDB",   "01101000");//68
        opcodes.put("LDCH",  "01010000");//50
        opcodes.put("LDF",   "01110000");//70
        opcodes.put("LDL",   "00001000");//08
        opcodes.put("LDS",   "01101100");//6C
        opcodes.put("LDT",   "01110100");//74
        opcodes.put("LDX",   "00000100");//04
        opcodes.put("LPS",   "11010000");//D0
        opcodes.put("MULF",  "0110000");//60
        opcodes.put("MULR",  "10011000");//98
        opcodes.put("NORM",  "11001000");//C8
        opcodes.put("OR",    "01000100");//44
        opcodes.put("RD",    "11011000");//D8
        opcodes.put("RMO",   "10101100");//AC
        opcodes.put("RSUB",  "01001100");//4C
        opcodes.put("SHIFTL","10100100");//A4
        opcodes.put("SHIFTR","10101000");//A8
        opcodes.put("SIO",   "11110000");//F0
        opcodes.put("SSK",   "11101100");//EC
        opcodes.put("STA",   "00001100");//0C
        opcodes.put("STB",   "01111000");//78
        opcodes.put("STCH",  "01010100");//54
        opcodes.put("STF",   "10000000");//80
        opcodes.put("STI",   "11010100");//D4
        opcodes.put("STL",   "00010100");//14
        opcodes.put("STS",   "01111100");//7C
        opcodes.put("STSW",  "11101000");//E8
        opcodes.put("STT",   "10000100");//84
        opcodes.put("STX",   "10000000");//10
        opcodes.put("SUB",   "00011100");//1C
        opcodes.put("SUBF",  "01101100");//5C
        opcodes.put("SUBR",  "10010100");//94
        opcodes.put("SVC",   "10110000");//B0
        opcodes.put("TD",    "11100000");//E0
        opcodes.put("TIO",   "11111000");//F8
        opcodes.put("TIX",   "00101100");//2C
        opcodes.put("TIXR",  "10111000");//B8
        opcodes.put("WD",    "11011100");//DC


        //open and read input file and store into map...
        File file = new File("/Users/neelkumar 1/Desktop/csfinalproject/assembly.asm");

        HashMap<String, String[]> assemblyMap = new LinkedHashMap<String, String[]>();
        Scanner sc = new Scanner(file);
        int counter = 0;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String words[] = line.split("\\.");
            assemblyMap.put(Integer.toString(counter),words);
            counter++;
        }
        System.out.print(assemblyMap.keySet());
        int LOCCTR;
        int stAd;
        //Pass 1

        //if opcode = 'start' then...
        String firstLine[] = assemblyMap.get("0");
        if (firstLine[1].equals("START")){
          //save #[OPERAND] as starting address
          stAd = Integer.parseInt(firstLine[2]);
          //initialize LOCCTR to starting address
          LOCCTR = stAd;
          //write line to intermediate File
          //read next input line
        }
        else{
          //initialize LOCCTR to 0
          LOCCTR = 0;
        }
          //begin
          //write line to intermediate File
          //read next input line
        //end {if START}
        //else

          //save starting address




            //while(counter != words.size){

        //  }
          //while OPCODE != END
            // if this != comment then
              // for sym in SYMTAB
                // if sym == label then
                  // Set error flag
                // else
                  // Insert (LABEL,LOCCTR) into SYMTAB
              // endfor
              // if found then
                // add 3 {instruction length} to LOCCTR
              // else if OPCODE = 'WORD' then
                // add 3 to LOCCTR
              // else if OPCODE = 'RESW' then
                // add 3 * #[OPERAND] to LOCCTR
              // else if OPCODE = 'RESB'
                // add #[OPERAND] to LOCCTR
              // else if OPCODE = 'BYTE' then
                // find length of constant in bytes
                // add length to LOCCTR
              // else
                // set error flag (invalid operation code)
            // end if not comment
          // write line to intermediate file
          // read next input line
        // end {while not end}
      // write last line to intermediate file
      // save (LOCCTR - starting address)as program length
    // end {pass 1}



      //PASS 2
      //if opcode = 'start' then
          //begin
          //write listing line
          //read next input Line
          //end if START


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
