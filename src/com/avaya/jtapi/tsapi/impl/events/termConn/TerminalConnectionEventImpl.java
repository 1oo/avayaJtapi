 package com.avaya.jtapi.tsapi.impl.events.termConn;
 
 import com.avaya.jtapi.tsapi.impl.events.call.CallEventParams;
 import com.avaya.jtapi.tsapi.impl.events.conn.ConnectionEventImpl;
 import javax.telephony.MetaEvent;
 import javax.telephony.TerminalConnection;
 import javax.telephony.TerminalConnectionEvent;
 
 public class TerminalConnectionEventImpl extends ConnectionEventImpl
   implements TerminalConnectionEvent
 {
   public TerminalConnectionEventImpl(CallEventParams params, MetaEvent event, int eventId)
   {
     super(params, event, eventId);
   }
 
   public TerminalConnection getTerminalConnection()
   {
     TerminalConnection tc = null;
     if (this.callEventParams instanceof TermConnEventParams)
       tc = ((TermConnEventParams)this.callEventParams).getTermConn();
     return tc;
   }
 
   public Object getSource()
   {
     if (this.callEventParams instanceof TermConnEventParams) {
       return ((TermConnEventParams)this.callEventParams).getTermConn();
     }
     return super.getSource();
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.impl.events.termConn.TerminalConnectionEventImpl
 * JD-Core Version:    0.5.4
 */