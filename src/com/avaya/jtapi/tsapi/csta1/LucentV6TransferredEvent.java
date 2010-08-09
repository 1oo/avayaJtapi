 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentV6TransferredEvent extends LucentV5TransferredEvent
   implements LucentTrunkConnectionMapping
 {
   CSTATrunkInfo[] lucentTrunkInfo;
   static final int PDU = 106;
 
   public static LucentTransferredEvent decode(InputStream in)
   {
     LucentV6TransferredEvent _this = new LucentV6TransferredEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream) {
     super.encodeMembers(memberStream);
     LucentTrunkInfoList.encode(this.lucentTrunkInfo, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     super.decodeMembers(memberStream);
     this.lucentTrunkInfo = LucentTrunkInfoList.decode(memberStream);
   }
 
   public CSTATrunkInfo[] getLucentTrunkInfo()
   {
     return this.lucentTrunkInfo;
   }
 
   public void setLucentTrunkInfo(CSTATrunkInfo[] _trunkList) {
     this.lucentTrunkInfo = _trunkList;
   }
 
   public Collection<String> print() {
     Collection lines = new ArrayList();
     lines.add("LucentV6TransferredEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(LucentV5OriginalCallInfo.print(this.originalCallInfo, "originalCallInfo", indent));
 
     lines.addAll(CSTAExtendedDeviceID.print(this.distributingDevice_asn, "distributingDevice", indent));
 
     lines.addAll(UCID.print(this.ucid, "ucid", indent));
     lines.addAll(LucentTrunkInfoList.print(this.lucentTrunkInfo, "lucentTrunkInfo", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 106;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentV6TransferredEvent
 * JD-Core Version:    0.5.4
 */