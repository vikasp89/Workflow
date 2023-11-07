/*    */ package comm;
/*    */ 
/*    */ import java.sql.Timestamp;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CaseLock {
/*    */   private static final int List = 0;
/*    */   
/* 24 */   public static Map<String, Map<String, Timestamp>> caseLockMap = new HashMap<>();
/*    */   
/*    */   public static void setCaseLock(String caseId, String userId) {
/* 27 */     userId = userId.toUpperCase();
/* 28 */     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
/* 29 */     Map<String, Timestamp> userTimeMap = new HashMap<>();
/* 30 */     userTimeMap.put(userId, timestamp);
/* 31 */     caseLockMap.put(caseId, userTimeMap);
/* 32 */     System.out.println("you are done");
/*    */   }
/*    */   
/*    */   public static String getCaseLock(String caseId, String userId) {
/* 36 */     userId = userId.toUpperCase();
/* 37 */     String caseLock = "N";
/* 38 */     Iterator<Map.Entry<String, Map<String, Timestamp>>> itr = caseLockMap.entrySet().iterator();
/* 40 */     while (itr.hasNext()) {
/* 41 */       System.out.println("getCaseLock() invoked");
/* 42 */       Map.Entry<String, Map<String, Timestamp>> entry = itr.next();
/* 43 */       System.out.println("Key = " + (String)entry.getKey() + 
/* 44 */           ", Value = " + entry.getValue());
/* 45 */       if (((String)entry.getKey()).equals(caseId)) {
/* 46 */         Map<String, Timestamp> userTimeMap = entry.getValue();
/* 47 */         Iterator<Map.Entry<String, Timestamp>> itrins = userTimeMap.entrySet().iterator();
/* 48 */         while (itrins.hasNext()) {
/* 50 */           Map.Entry<String, Timestamp> entryins = itrins.next();
/* 51 */           System.out.println("entryins Key = " + (String)entryins.getKey() + 
/* 52 */               ", entryins Value = " + entryins.getValue());
/* 53 */           if (!((String)entryins.getKey()).equals(userId)) {
/* 55 */             Timestamp timestamp = new Timestamp(System.currentTimeMillis());
/* 56 */             System.out.println("timestamp " + timestamp);
/* 58 */             caseLock = "Y";
/* 59 */             double difference = (timestamp.getTime() - ((Timestamp)entryins.getValue()).getTime());
/* 60 */             System.out.println("difference " + difference);
/* 61 */             if (difference < 30000.0D) {
/* 62 */               caseLock = String.valueOf(userId.toLowerCase()) + "~" + (String)entryins.getKey();
/* 64 */               System.out.println("fdhjfurf   " + caseLock);
/* 65 */               System.out.println("Not Allowed to Login");
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 74 */     return caseLock;
/*    */   }
/*    */ }


/* Location:              C:\Users\DELL\Desktop\server project\Workflow\WEB-INF\classes\!\comm\CaseLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */