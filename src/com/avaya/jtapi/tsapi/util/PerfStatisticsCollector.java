 package com.avaya.jtapi.tsapi.util;
 
 import java.util.Timer;
 import java.util.TimerTask;
 import java.util.Vector;
 import java.util.concurrent.atomic.AtomicInteger;
 import org.apache.log4j.Logger;
 
 public class PerfStatisticsCollector
 {
   private static final String PROP_PERF_LOGGER_NAME = "jtapi.performanceLogger";
   private static Timer perfTimer;
   private static boolean perfWindowChanged = true;
 
   private static Logger logger = Logger.getLogger("jtapi.performanceLogger");
 
   private static int performanceWindow = 60;
 
   private static long unsolicitedHandlingTimeThreshold = 100L;
   private static long serviceRequestTurnaroundTimeThreshold = 100L;
   private static long queueLengthThreshold = 10L;
   private static long messageLatencyThreshold = 20L;
   private static long numWindowsRun = 0L;
   private static AtomicInteger callCount;
   private static float callCountHistorySum = 0.0F;
   private static float minCallHistory;
   private static float maxCallHistory;
   private static boolean callHistoryInitialized = false;
   private static AtomicInteger eventCount;
   private static float eventCountHistorySum = 0.0F;
   private static float minEventHistory;
   private static float maxEventHistory;
   private static boolean eventHistoryInitialized = false;
   private static Vector<Long> unsolicitedHandlingTime;
   private static PerfHistoryBean unsolHandlingTimeHistory;
   private static AtomicInteger unsolicitedHandlingTimeCounter;
   private static Vector<Long> serviceRequestTurnaroundTime;
   private static PerfHistoryBean serviceRequestTurnaroundTimeHistory;
   private static AtomicInteger serviceRequestTurnaroundTimeCounter;
   private static Vector<Long> queueLength;
   private static long queueOverallMax;
   private static long queueMaxWindowSum;
   private static AtomicInteger queueLengthCounter;
   private static Vector<Long> messageLatency;
   private static PerfHistoryBean messageLatencyHistory;
   private static AtomicInteger messageLatencyCounter;
   private static boolean isInitialized = false;
 
   public static void initPerfStatisticsCollector()
   {
     if (!isInitialized) {
       initializeDataStructures();
       if (perfTimer != null) {
         perfTimer.cancel();
         perfTimer = null;
       }
       perfTimer = new Timer(true);
       perfTimer.schedule(new PerfTimerTask(), performanceWindow * 1000, performanceWindow * 1000);
       logger.info("##########Performance statistics monitoring start##########");
       isInitialized = true;
       perfWindowChanged = false;
     }
   }
 
   public static void shutdown() {
     if (isInitialized) {
       resetDataStructures();
       logger.info("##########Performance statistics monitoring stop##########");
     }
   }
 
   public static void updatePerfStatisticsCollectorConfig() {
     if (perfWindowChanged) {
       resetDataStructures();
       initializeDataStructures();
       perfTimer = new Timer(true);
       perfTimer.schedule(new PerfTimerTask(), performanceWindow * 1000, performanceWindow * 1000);
       perfWindowChanged = false;
     }
   }
 
   public static void updateMessageLatency(long newValue) {
     if (isInitialized) {
       messageLatency.add(Long.valueOf(newValue));
       messageLatencyCounter.incrementAndGet();
       if (newValue > messageLatencyThreshold)
         logger.info("Message latency threshold value crossed. New value is : " + newValue);
     }
   }
 
   public static void updateCallCount()
   {
     if (isInitialized)
       callCount.incrementAndGet();
   }
 
   public static void updateEventCount()
   {
     if (isInitialized)
       eventCount.incrementAndGet();
   }
 
   public static void updateQueueLength(long newValue) {
     if (isInitialized) {
       queueLength.add(new Long(newValue));
       queueLengthCounter.incrementAndGet();
       if (newValue > queueLengthThreshold)
         logger.info("Queue length threshold value crossed. New value is : " + newValue);
     }
   }
 
   public static void updateServiceRequestTurnaroundTime(long newValue)
   {
     if (isInitialized) {
       serviceRequestTurnaroundTime.add(new Long(newValue));
       serviceRequestTurnaroundTimeCounter.incrementAndGet();
       if (newValue > serviceRequestTurnaroundTimeThreshold)
         logger.info("Service request turnaround time threshold value crossed. New value is : " + newValue);
     }
   }
 
   public static void updateUnsolicitedHandlingTime(long newValue)
   {
     if (isInitialized) {
       unsolicitedHandlingTime.add(new Long(newValue));
       unsolicitedHandlingTimeCounter.incrementAndGet();
       if (newValue > unsolicitedHandlingTimeThreshold)
         logger.info("Unsolicited handling time threshold value crossed. New value is : " + newValue);
     }
   }
 
   private static void initializeDataStructures()
   {
     callCount = new AtomicInteger();
     callCountHistorySum = 0.0F;
 
     eventCount = new AtomicInteger();
     eventCountHistorySum = 0.0F;
 
     unsolHandlingTimeHistory = new PerfHistoryBean("UNSOLICITED HANDLING TIME");
     unsolicitedHandlingTime = new Vector();
     unsolicitedHandlingTimeCounter = new AtomicInteger();
 
     serviceRequestTurnaroundTimeHistory = new PerfHistoryBean("SERVICE REQUEST TURNAROUND TIME");
     serviceRequestTurnaroundTime = new Vector();
     serviceRequestTurnaroundTimeCounter = new AtomicInteger();
 
     queueLength = new Vector();
     queueMaxWindowSum = 0L;
     queueOverallMax = 0L;
     queueLengthCounter = new AtomicInteger();
 
     messageLatencyHistory = new PerfHistoryBean("MESSAGE LATENCY");
     messageLatency = new Vector();
     messageLatencyCounter = new AtomicInteger();
   }
 
   private static void resetDataStructures() {
     unsolicitedHandlingTime.clear();
     unsolicitedHandlingTime = null;
     unsolicitedHandlingTimeCounter = null;
     unsolHandlingTimeHistory = null;
     numWindowsRun = 0L;
 
     serviceRequestTurnaroundTime.clear();
     serviceRequestTurnaroundTime = null;
     serviceRequestTurnaroundTimeCounter = null;
     serviceRequestTurnaroundTimeHistory = null;
 
     queueLength.clear();
     queueOverallMax = 0L;
     queueLength = null;
     queueLengthCounter = null;
 
     messageLatency.clear();
     messageLatency = null;
     messageLatencyCounter = null;
     messageLatencyHistory = null;
 
     callCount = null;
     minCallHistory = PerfStatisticsCollector.maxCallHistory = PerfStatisticsCollector.callCountHistorySum = 0.0F;
     callHistoryInitialized = false;
 
     eventCount = null;
     eventCountHistorySum = PerfStatisticsCollector.minEventHistory = PerfStatisticsCollector.maxEventHistory = 0.0F;
     eventHistoryInitialized = false;
 
     perfTimer.cancel();
     perfTimer = null;
 
     isInitialized = false;
   }
 
   public static void setUnsolicitedHandlingTimeThreshold(long unsolicitedHandlingTimeThreshold)
   {
     unsolicitedHandlingTimeThreshold = unsolicitedHandlingTimeThreshold;
   }
 
   public static void setServiceRequestTurnaroundTimeThreshold(long serviceRequestTurnaroundTimeThreshold)
   {
     serviceRequestTurnaroundTimeThreshold = serviceRequestTurnaroundTimeThreshold;
   }
 
   public static void setQueueLengthThreshold(long queueLengthThreshold)
   {
     queueLengthThreshold = queueLengthThreshold;
   }
 
   public static void setMessageLatencyThreshold(long messageLatencyThreshold)
   {
     messageLatencyThreshold = messageLatencyThreshold;
   }
 
   public static int getPerformanceWindow()
   {
     return performanceWindow;
   }
 
   public static void setPerformanceWindow(int performanceWindow)
   {
     if (performanceWindow != performanceWindow)
       perfWindowChanged = true;
     performanceWindow = performanceWindow;
   }
 
   static class PerfHistoryBean
   {
     private long historyMin;
     private long historyMax;
     private long windowMin;
     private long windowMax;
     private long windowSum;
     private long windowCount;
     private long historySum;
     private int historyCount;
     private String name;
     private boolean historyInitialized = false;
 
     public PerfHistoryBean(String name) {
       this.historySum = 0L;
       this.historyCount = 0;
       this.name = name;
     }
 
     public void printWindowStats(Logger logger) {
       if (this.windowSum != 0L)
         logger.info(this.name + ": Min = " + this.windowMin + "\tMax = " + this.windowMax + "\tAverage = " + (float)this.windowSum / (float)this.windowCount);
       else
         logger.info(this.name + ": Min = " + this.windowMin + "\tMax = " + this.windowMax + "\tAverage = " + 0);
     }
 
     public void printHistoryStats(Logger logger) {
       if (this.historyCount != 0)
         logger.info(this.name + ": Min = " + this.historyMin + "\tMax = " + this.historyMax + "\tAverage = " + (float)this.historySum / this.historyCount);
       else
         logger.info(this.name + ": Min = " + this.historyMin + "\tMax = " + this.historyMax + "\tAverage = " + 0);
     }
 
     public void copyToHistory(Vector<Long> windowDetails, int count) {
       if (windowDetails.size() == 0)
         return;
       long sum = 0L;
       this.windowMax = (this.windowMin = ((Long)windowDetails.firstElement()).longValue());
 
       for (Long value : windowDetails) {
         sum += value.longValue();
         if (value.longValue() < this.windowMin)
           this.windowMin = value.longValue();
         if (value.longValue() > this.windowMax)
           this.windowMax = value.longValue();
       }
       this.windowSum = sum;
       this.windowCount = count;
       if (sum != 0L) {
         this.historySum += sum;
         this.historyCount += count;
         if (!this.historyInitialized) {
           this.historyMin = this.windowMin;
           this.historyInitialized = true;
         } else if (this.windowMin < this.historyMin) {
           this.historyMin = this.windowMin;
         }if (this.windowMax > this.historyMax)
           this.historyMax = this.windowMax;
       }
     }
   }
 
   static class PerfStatisticsCalculator
   {
     private static Logger logger = Logger.getLogger("jtapi.performanceLogger");
     private Vector<Long> data;
     private long min;
     private long max;
     private float average;
     private long historyMin;
     private long historMax;
     private float historyAverage;
     private long window;
 
     public PerfStatisticsCalculator()
     {
     }
 
     public PerfStatisticsCalculator(Vector<Long> theData, long window)
     {
       this.data = theData;
       this.window = window;
     }
 
     public void reset() {
       this.min = (this.max = this.historMax = this.historyMin = this.window = 0L);
       this.average = (this.historyAverage = 0.0F);
       this.data.clear();
       this.data = null;
     }
 
     public void computeMinMaxAverage() {
       computeHistoryMinMaxAvg();
       computeMinMaxAvgWindow();
     }
 
     private void computeMinMaxAvgWindow() {
       if (this.data.size() == 0)
         return;
       this.min = ((Long)this.data.get(0)).longValue();
       this.max = 0L;
       this.average = 0.0F;
       if (this.window > 0L) {
         for (int i = (int)(this.data.size() - this.window); i < this.data.size(); ++i) {
           long dataValue = ((Long)this.data.get(i)).longValue();
           if (dataValue < this.min)
             this.min = dataValue;
           if (dataValue > this.max)
             this.max = dataValue;
           this.average += (float)dataValue;
         }
         this.average /= (float)this.window;
       }
     }
 
     private void computeHistoryMinMaxAvg() {
       if (this.data.size() == 0)
         return;
       this.historyMin = ((Long)this.data.get(0)).longValue();
       this.historMax = 0L;
       this.historyAverage = 0.0F;
       if (this.data.size() > 0) {
         for (Long tempData : this.data) {
           long dataValue = tempData.longValue();
           if (dataValue < this.historyMin)
             this.historyMin = dataValue;
           if (dataValue > this.historMax)
             this.historMax = dataValue;
           this.historyAverage += (float)dataValue;
         }
         this.historyAverage /= this.data.size();
       }
     }
 
     public static void printMaxAverage(String name, Vector<Long> data) {
       long max = 0L;
       float average = 0.0F;
       if (data.size() > 0) {
         for (Long dataValue : data) {
           if (dataValue.longValue() > max)
             max = dataValue.longValue();
           average += (float)dataValue.longValue();
         }
         average /= data.size();
       }
       logger.info(name + ": Max = " + max + "\tAverage = " + average);
     }
 
     public static void printMinMaxAverageData(Vector<Float> data, String name) {
       if (data.size() == 0)
         return;
       float min = ((Float)data.get(0)).floatValue();
       float max = 0.0F; float average = 0.0F;
       if (data.size() > 0) {
         for (Float dataValue : data) {
           if (dataValue.floatValue() < min)
             min = dataValue.floatValue();
           else if (dataValue.floatValue() > max)
             max = dataValue.floatValue();
           average += dataValue.floatValue();
         }
         average /= data.size();
       }
       logger.info(name + " Min = " + min + "\tMax = " + max + "\tAverage = " + average);
     }
 
     public Vector<Long> getData()
     {
       return this.data;
     }
 
     public long getMin()
     {
       return this.min;
     }
 
     public long getMax()
     {
       return this.max;
     }
 
     public float getAverage()
     {
       return this.average;
     }
 
     public long getHistoryMin()
     {
       return this.historyMin;
     }
 
     public long getHistorMax()
     {
       return this.historMax;
     }
 
     public float getHistoryAverage()
     {
       return this.historyAverage;
     }
 
     public long getWindow()
     {
       return this.window;
     }
 
     public void setWindow(long window)
     {
       this.window = window;
     }
 
     public void setData(Vector<Long> data)
     {
       this.data = data;
     }
   }
 
   private static class PerfTimerTask extends TimerTask
   {
     public void run()
     {
//       PerfStatisticsCollector.access$108();
       Vector queueLengthCloneVector;
       int queueLengthCloneCounter;
       int callCountClone;
       int eventCountClone;
       synchronized (PerfStatisticsCollector.class) {
         Vector unsolCloneVector = (Vector)PerfStatisticsCollector.unsolicitedHandlingTime.clone();
         int unsolCloneCounter = PerfStatisticsCollector.unsolicitedHandlingTimeCounter.getAndSet(0);
         PerfStatisticsCollector.unsolicitedHandlingTime.clear();
         PerfStatisticsCollector.unsolHandlingTimeHistory.copyToHistory(unsolCloneVector, unsolCloneCounter);
 
         Vector serviceTurnaroundCloneVector = (Vector)PerfStatisticsCollector.serviceRequestTurnaroundTime.clone();
         int serviceTurnaroundCloneCounter = PerfStatisticsCollector.serviceRequestTurnaroundTimeCounter.getAndSet(0);
         PerfStatisticsCollector.serviceRequestTurnaroundTime.clear();
         PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory.copyToHistory(serviceTurnaroundCloneVector, serviceTurnaroundCloneCounter);
 
         queueLengthCloneVector = (Vector)PerfStatisticsCollector.queueLength.clone();
         PerfStatisticsCollector.queueLength.clear();
         queueLengthCloneCounter = PerfStatisticsCollector.queueLengthCounter.getAndSet(0);
 
         Vector messageLatencyCloneVector = (Vector)PerfStatisticsCollector.messageLatency.clone();
         int messageLatencyCloneCounter = PerfStatisticsCollector.messageLatencyCounter.getAndSet(0);
         PerfStatisticsCollector.messageLatency.clear();
         PerfStatisticsCollector.messageLatencyHistory.copyToHistory(messageLatencyCloneVector, messageLatencyCloneCounter);
 
         callCountClone = PerfStatisticsCollector.callCount.getAndSet(0);
 
         eventCountClone = PerfStatisticsCollector.eventCount.getAndSet(0);
       }
       PerfStatisticsCollector.PerfStatisticsCalculator qPerfCalc = new PerfStatisticsCollector.PerfStatisticsCalculator();
       compute(queueLengthCloneVector, queueLengthCloneCounter, qPerfCalc);
 
       PerfStatisticsCollector.logger.info("Statistics for current window : ");
       PerfStatisticsCollector.unsolHandlingTimeHistory.printWindowStats(PerfStatisticsCollector.logger);
       PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory.printWindowStats(PerfStatisticsCollector.logger);
       PerfStatisticsCollector.messageLatencyHistory.printWindowStats(PerfStatisticsCollector.logger);
 
       if (queueLengthCloneVector.size() > 0) {
         PerfStatisticsCollector.logger.info("QUEUE LENGTH: Current = " + queueLengthCloneVector.lastElement() + "\tMax = " + qPerfCalc.getMax());
       }
       if (callCountClone != 0) {
         float currentCallsPerSecond = callCountClone / PerfStatisticsCollector.performanceWindow;
         PerfStatisticsCollector.logger.info("CALLS PER SECOND: " + currentCallsPerSecond);
//         PerfStatisticsCollector.access$1716(currentCallsPerSecond);
         if (!PerfStatisticsCollector.callHistoryInitialized) {
//           PerfStatisticsCollector.access$1802(true);
//           PerfStatisticsCollector.access$1902(PerfStatisticsCollector.access$2002(currentCallsPerSecond));
         }
         if (currentCallsPerSecond < PerfStatisticsCollector.minCallHistory)
//           PerfStatisticsCollector.access$1902(currentCallsPerSecond);
         if (currentCallsPerSecond > PerfStatisticsCollector.maxCallHistory) {
//           PerfStatisticsCollector.access$2002(currentCallsPerSecond);
         }
       }
       if (eventCountClone != 0) {
         float currentEventsPerSecond = eventCountClone / PerfStatisticsCollector.performanceWindow;
         PerfStatisticsCollector.logger.info("EVENTS PER SECOND:" + currentEventsPerSecond);
//         PerfStatisticsCollector.access$2116(currentEventsPerSecond);
         if (!PerfStatisticsCollector.eventHistoryInitialized) {
//           PerfStatisticsCollector.access$2202(true);
//           PerfStatisticsCollector.access$2302(PerfStatisticsCollector.access$2402(currentEventsPerSecond));
         }
         if (currentEventsPerSecond < PerfStatisticsCollector.minEventHistory)
//           PerfStatisticsCollector.access$2302(currentEventsPerSecond);
         if (currentEventsPerSecond > PerfStatisticsCollector.maxEventHistory) {
//           PerfStatisticsCollector.access$2402(currentEventsPerSecond);
         }
       }
 
       PerfStatisticsCollector.logger.info("Cumulative Statistics Since Performance Monitoring Started :");
       PerfStatisticsCollector.unsolHandlingTimeHistory.printHistoryStats(PerfStatisticsCollector.logger);
       PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory.printHistoryStats(PerfStatisticsCollector.logger);
       PerfStatisticsCollector.messageLatencyHistory.printHistoryStats(PerfStatisticsCollector.logger);
       long currentQMax = qPerfCalc.getMax();
//       PerfStatisticsCollector.access$2514(qPerfCalc.getMax());
       if (currentQMax > PerfStatisticsCollector.queueOverallMax) {
//         PerfStatisticsCollector.access$2602(currentQMax);
       }
       PerfStatisticsCollector.logger.info("QUEUE LENGTH: Max = " + PerfStatisticsCollector.queueOverallMax + "\tAverage = " + (float)PerfStatisticsCollector.queueMaxWindowSum / (float)PerfStatisticsCollector.numWindowsRun);
       if (callCountClone != 0) {
         PerfStatisticsCollector.logger.info("CALLS PER SECOND: Min = " + PerfStatisticsCollector.minCallHistory + "\tMax = " + PerfStatisticsCollector.maxCallHistory + "\tAverage = " + PerfStatisticsCollector.callCountHistorySum / (float)PerfStatisticsCollector.numWindowsRun);
       }
       if (eventCountClone != 0)
         PerfStatisticsCollector.logger.info("EVENTS PER SECOND: Min = " + PerfStatisticsCollector.minEventHistory + "\tMax = " + PerfStatisticsCollector.maxEventHistory + "\tAverage = " + PerfStatisticsCollector.eventCountHistorySum / (float)PerfStatisticsCollector.numWindowsRun);
     }
 
     private void compute(Vector<Long> statData, int counter, PerfStatisticsCollector.PerfStatisticsCalculator perfBean)
     {
       perfBean.setData(statData);
       perfBean.setWindow(counter);
       perfBean.computeMinMaxAverage();
     }
   }
 }

/* Location:           C:\Documents and Settings\Daniel Jurado\Meus documentos\My Dropbox\install\Avaya\jtapi-sdk-5.2.2.483\lib\ecsjtapia.jar
 * Qualified Name:     com.avaya.jtapi.tsapi.util.PerfStatisticsCollector
 * JD-Core Version:    0.5.4
 */