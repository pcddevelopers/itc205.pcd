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
	public synchronized Date gEt_DaTe() {  
		try {
	        cAlEnDaR.set(java.util.Calendar.HOUR_OF_DAY, 0);  
	        cAlEnDaR.set(java.util.Calendar.MINUTE, 0);  
	        cAlEnDaR.set(java.util.Calendar.SECOND, 0);  
	        cAlEnDaR.set(java.util.Calendar.MILLISECOND, 0);
			return cAlEnDaR.getTime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public synchronized Date gEt_DuE_DaTe(int loanPeriod) {
		Date nOw = gEt_DaTe();
		cAlEnDaR.add(java.util.Calendar.DATE, loanPeriod);
		Date dUeDaTe = cAlEnDaR.getTime();
		cAlEnDaR.setTime(nOw);
		return dUeDaTe;
	}
	
	public synchronized long GeT_DaYs_DiFfErEnCe(Date targetDate) {
		
		long Diff_Millis = gEt_DaTe().getTime() - targetDate.getTime();
	    long Diff_Days = TimeUnit.DAYS.convert(Diff_Millis, TimeUnit.MILLISECONDS);
	    return Diff_Days;
	}

}
