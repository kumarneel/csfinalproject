package data;

import java.util.*;

public class Hashmaps {
  public Hashmaps();

  public void init() {
    //OPTAB - HashMap of commands and their corresponding operation codes
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
  }
}
