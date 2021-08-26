package library.entities;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Calendar {
	
	private static Calendar self;  // change "sElF" to "self" 
	private static java.util.Calendar calendar; // change "cAlEnDaR" to "calendar"
	
	
	private Calendar() {
		cAlEnDaR = java.util.Calendar.getInstance();
	}
	
	public static Calendar getInstance() { //change "gEtInStAnCe" to "getInstance"
		if (self == null) {  // change "sElF" to "self" 
			self = new Calendar(); // change "sElF" to "self" 
		}
		return self; // change "sElF" to "self" 
	}
	
	public void incrementDate(int days) {
		calendar.add(java.util.Calendar.DATE, days);	// change "cAlEnDaR" to "calendar"	
	}
	
	public synchronized void set_DATE(DATE DATE) {  // change "SeT_DaTe(Date DaTe)" to "set_DATE(DATE DATE"
		try {
			calendar.setTime(DATE);  //change "cAlEnDaR.setTime(DaTe)"  to "calendar.setTime(DATE)"
	        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);  //change "cAlEnDaR" to "calendar"
	        calendar.set(java.util.Calendar.MINUTE, 0);   //change "cAlEnDaR" to "calendar"
	        calendar.set(java.util.Calendar.SECOND, 0);   //change "cAlEnDaR" to "calendar"
	        calendar.set(java.util.Calendar.MILLISECOND, 0); //change "cAlEnDaR" to "calendar"
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
	public synchronized Date getDate() {  // change "gEt_DaTe" to "getDate"
		try {
	        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);  //change "cAlEnDaR" to "calendar"
	        calendar.set(java.util.Calendar.MINUTE, 0);  //change "cAlEnDaR" to "calendar"
	        calendar.set(java.util.Calendar.SECOND, 0);  //change "cAlEnDaR" to "calendar"
	        calendar.set(java.util.Calendar.MILLISECOND, 0);//change "cAlEnDaR" to "calendar"
			return calendar.getTime();//change "cAlEnDaR" to "calendar"
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public synchronized Date getDueDaTe(int loanPeriod) { //change "gEt_DuE_DaTe" to "getDueDaTe"
		Date now = getDate(); // change "nOw = gEt_DaTe" to "now=getDate"
		calendar.add(java.util.Calendar.DATE, loanPeriod); //change "cAlEnDaR" to "calendar"
		Date dueDate = calendar.getTime(); // change "dUeDaTe = cAlEnDaR" to "dueDate = calendar"
		calendar.setTime(now);  //change "cAlEnDaR.setTime(nOw); " to "calendar.setTime(now)"
		return dUeDaTe; // change "dUeDaTe" to "dueDate"
	}
	
	public synchronized long getDaysDifference(Date targetDate) { // change " GeT_DaYs_DiFfErEnCe" to "getDaysDifference"
		
		long diffMillis = gEt_DaTe().getTime() - targetDate.getTime(); //change "Diff_Millis" to "diffMillis"
	    long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS); // change " Diff_Days" to "diffDays" & "Diff_Millis" to "diffMillis"
	    return Diff_Days; // change " Diff_Days" to "diffDays"
	}

}
