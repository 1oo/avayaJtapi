 package com.avaya.jtapi.tsapi;
 
 public final class CallClassifierInfo
 {
   public int numAvailPorts;
   public int numInUsePorts;
 
   public CallClassifierInfo(int _numAvailPorts, int _numInUsePorts)
   {
     this.numAvailPorts = _numAvailPorts;
     this.numInUsePorts = _numInUsePorts;
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.CallClassifierInfo
 * JD-Core Version:    0.5.4
 */