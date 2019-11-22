import java.util.*;
public class ObjectCode {

    private int nixbpe[] = new int[6];
    private String addrMode = "";
    private int objectCode = 0;

    public int[] getFlags(){
      return nixbpe;
    }
    public void setFlags(int[] flags){
        this.nixbpe = flags;
        //calculate objectCode for current object
        calculateObjectCode();
        return;
    }
    public void setAddrMode(String addrMode){
        this.addrMode = addrMode;
    }
    public String getAddrMode(){
      return addrMode;
    }
    public int getObjectCode(){
        return objectCode;
    }
    public void calculateObjectCode(){

    }


}
