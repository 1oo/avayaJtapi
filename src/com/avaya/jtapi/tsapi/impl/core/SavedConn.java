 package com.avaya.jtapi.tsapi.impl.core;
 
 final class SavedConn
 {
   TSConnection conn;
   long saveTime;
 
   SavedConn(TSConnection _conn)
   {
     this.conn = _conn;
     this.saveTime = System.currentTimeMillis();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.SavedConn
 * JD-Core Version:    0.5.4
 */