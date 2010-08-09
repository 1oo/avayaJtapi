 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class CSTANotReadyEvent extends CSTAUnsolicited
 {
   CSTAExtendedDeviceID agentDevice;
   String agentID;
   public static final int PDU = 74;
 
   public static CSTANotReadyEvent decode(InputStream in)
   {
     CSTANotReadyEvent _this = new CSTANotReadyEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.agentDevice = CSTAExtendedDeviceID.decode(memberStream);
     this.agentID = AgentID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
     lines.add("CSTANotReadyEvent ::=");
     lines.add("{");
 
     String indent = "  ";
     lines.add(indent + "monitorCrossRefID " + this.monitorCrossRefID);
     lines.addAll(CSTAExtendedDeviceID.print(this.agentDevice, "agentDevice", indent));
     lines.addAll(AgentID.print(this.agentID, "agentID", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 74;
   }
 
   public CSTAExtendedDeviceID getAgentDevice()
   {
     return this.agentDevice;
   }
 
   public String getAgentID()
   {
     return this.agentID;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.CSTANotReadyEvent
 * JD-Core Version:    0.5.4
 */