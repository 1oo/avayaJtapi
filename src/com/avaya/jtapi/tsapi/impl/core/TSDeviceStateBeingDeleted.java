 package com.avaya.jtapi.tsapi.impl.core;
 
 class TSDeviceStateBeingDeleted extends TSDeviceState
 {
   void recreate(TSDevice _tsDevice)
   {
   }
 
   public String toString()
   {
     return "BeingDeleted";
   }
   boolean wasDeleteDone() {
     return false;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.core.TSDeviceStateBeingDeleted
 * JD-Core Version:    0.5.4
 */