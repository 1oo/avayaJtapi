 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentMonitorStopOnCall extends LucentPrivateData
 {
   int monitorCrossRefID;
   CSTAConnectionID call;
   static final int PDU = 31;
 
   public LucentMonitorStopOnCall(int _monitorCrossRefID, CSTAConnectionID _call)
   {
     this.monitorCrossRefID = _monitorCrossRefID;
     this.call = _call;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAMonitorCrossRefID.encode(this.monitorCrossRefID, memberStream);
     CSTAConnectionID.encode(this.call, memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentMonitorStopOnCall ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAMonitorCrossRefID.print(this.monitorCrossRefID, "monitorCrossRefID", indent));
     lines.addAll(CSTAConnectionID.print(this.call, "call", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 31;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentMonitorStopOnCall
 * JD-Core Version:    0.5.4
 */