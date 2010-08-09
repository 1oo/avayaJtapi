 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentV7NetworkReachedEvent extends LucentPrivateData
 {
   private LucentDeviceHistoryEntry[] deviceHistory;
   static final int PDU = 136;
 
   public static LucentV7NetworkReachedEvent decode(InputStream in)
   {
     LucentV7NetworkReachedEvent _this = new LucentV7NetworkReachedEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.deviceHistory = CSTADeviceHistoryData.decode(memberStream);
   }
 
   public void encodeMembers(OutputStream memberStream) {
     CSTADeviceHistoryData.encode(this.deviceHistory, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentV7NetworkReachedEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTADeviceHistoryData.print(this.deviceHistory, "deviceHistory", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 136;
   }
 
   LucentDeviceHistoryEntry[] getDeviceHistory()
   {
     return this.deviceHistory;
   }
 
   public void setDeviceHistory(LucentDeviceHistoryEntry[] deviceHistory) {
     this.deviceHistory = deviceHistory;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV7NetworkReachedEvent
 * JD-Core Version:    0.5.4
 */