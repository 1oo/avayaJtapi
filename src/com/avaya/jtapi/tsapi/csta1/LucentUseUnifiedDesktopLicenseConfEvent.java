 package com.avaya.jtapi.tsapi.csta1;
 
 import java.io.InputStream;
 import java.util.ArrayList;
 import java.util.Collection;
 
 public final class LucentUseUnifiedDesktopLicenseConfEvent extends LucentPrivateData
 {
   static final int PDU = 140;
 
   public static LucentUseUnifiedDesktopLicenseConfEvent decode(InputStream in)
   {
     LucentUseUnifiedDesktopLicenseConfEvent _this = new LucentUseUnifiedDesktopLicenseConfEvent();
     _this.doDecode(in);
 
     return _this;
   }
 
   public void decodeMembers(InputStream memberStream) {
   }
 
   public Collection<String> print() {
     Collection lines = new ArrayList();
     lines.add("LucentUseUnifiedDesktopLicenseConfEvent ::=");
     lines.add("{");
     lines.add("}");
     return lines;
   }
 
   public int getPDU()
   {
     return 140;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.csta1.LucentUseUnifiedDesktopLicenseConfEvent
 * JD-Core Version:    0.5.4
 */