SUM.START.4000
FIRST.LDX.ZERO
 .LDA.ZERO
LOOP.ADD.TABLE,X
 .TIX.COUNT
 .JLT.LOOP
 .STA.TOTAL
 .RSUB.s
TABLE.RESW.2000
COUNT.RESW.1
ZERO.WORD.0
TOTAL.RESW.1
 .END.first
