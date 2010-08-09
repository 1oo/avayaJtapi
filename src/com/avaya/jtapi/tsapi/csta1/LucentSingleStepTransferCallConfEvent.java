 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public class LucentSingleStepTransferCallConfEvent extends LucentPrivateData
 {
   CSTAConnectionID transferredCall;
   static final int PDU = 143;
 
   public LucentSingleStepTransferCallConfEvent(CSTAConnectionID _transferredCall)
   {
     this.transferredCall = _transferredCall;
   }
 
   public LucentSingleStepTransferCallConfEvent()
   {
   }
 
   public static LucentSingleStepTransferCallConfEvent decode(InputStream in)
   {
     LucentSingleStepTransferCallConfEvent _this = new LucentSingleStepTransferCallConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void encodeMembers(OutputStream memberStream)
   {
     CSTAConnectionID.encode(this.transferredCall, memberStream);
   }
 
   public void decodeMembers(InputStream memberStream)
   {
     this.transferredCall = CSTAConnectionID.decode(memberStream);
   }
 
   public Collection<String> print()
   {
     Collection lines = new ArrayList();
 
     lines.add("LucentSingleStepTransferCallConfEvent ::=");
     lines.add("{");
 
     String indent = "  ";
 
     lines.addAll(CSTAConnectionID.print(this.transferredCall, "transferredCall", indent));
 
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 143;
   }
 
   public CSTAConnectionID getTransferredCall()
   {
     return this.transferredCall;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentSingleStepTransferCallConfEvent
 * JD-Core Version:    0.5.4
 */