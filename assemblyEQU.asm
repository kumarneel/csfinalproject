SUM.START.0
ZERO.EQU.0
FIRST.LDX.#ZERO
 .LDA.#ZERO
 .+LDB.#TABLE2
 .BASE.TABLE2
LOOP.ADD.TABLE,X
 .ADD.TABLE2,X
 .TIX.COUNT
 .JLT.LOOP
 .+STA.TOTAL
 .RSUB.s
COUNT.RESW.1
TABLE.RESW.2000
TABLE2.RESW.2000
TOTAL.RESW.1
 .BYTE.'Hello World'
 .END.first
