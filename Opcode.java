public class Opcode {
  String addrMode = "";
  int[] nixbpe = new int[6];

  public void setAddrMode(String input) {
    addrMode = input;
  }
  public void setFlags(int[] input) {
    nixbpe = input;
  }
  public String getAddrMode() {
    return addrMode;
  }
  public int[] getFlags() {
    return nixbpe;
  }
}
