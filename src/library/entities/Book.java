package library.entities;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Book implements Serializable {
	
	private String title;  //change "tItLe" to "title" 
	private String authOr;  //change "AuThOr" to "author"
	private String callNo;  //change "CALLNO" to "callNo"
	private int id;  //change "iD" to "id" 
	
	private enum sTaTe { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private state state;  //change "sTate StAtE" to "state state"
	
	
	public Book(String author, String title, String callNo, int id) {
		this.author = author; //change "AuThOr" to "author"
		this.title = title;  //change "tItLe" to "title"
		this.CALLNO = callNo; //change "CALLNO" to "callNo"
		this.iD = id; //change "iD" to "id"
		this.state = state.AVAILABLE; //change "StAtE = sTaTe.AVAILABLE" to "state = state.AVAILABLE"
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(id).append("\n") //change "iD" to "id"
		  .append("  Title:  ").append(title).append("\n")  //change "tItLe" to "title"
		  .append("  Author: ").append(author).append("\n") //change "AuThOr" to "author"
		  .append("  CallNo: ").append(callNo).append("\n") //change "CALLNO" to "callNo"
		  .append("  State:  ").append(state); //change "StAtE" to "state"
		
		return sb.toString();
	}

	public Integer getId() { // change "gEtId" to "getId"
		return id;  //change "iD" to "id"
	}

	public String getTitle() { //change "gEtTiTlE" to "getTitle"
		return title;   //change "tItLe" to "title"
	}


	
	public boolean isAvailable() {  // change " iS_AvAiLaBlE" to "isAvailable" 
		return state == state.AVAILABLE; //change "StAtE == sTaTe.AVAILABLE" to "state = state.AVAILABLE"
	}

	
	public boolean iS_On_LoAn() {
		return StAtE == sTaTe.ON_LOAN;
	}

	
	public boolean isDamaged() { //Change " iS_DaMaGeD" to "isDamaged"
		return state == state.DAMAGED; // change "StAtE == sTaTe.DAMAGED" to "state == state.DAMAGED"
	}

	
	public void BoRrOw() {
		if (StAtE.equals(sTaTe.AVAILABLE)) 
			StAtE = sTaTe.ON_LOAN;
		
		else 
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", StAtE));
		
		
	}


	public void ReTuRn(boolean DaMaGeD) {
		if (StAtE.equals(sTaTe.ON_LOAN)) 
			if (DaMaGeD) 
				StAtE = sTaTe.DAMAGED;
			
			else 
				StAtE = sTaTe.AVAILABLE;
			
		
		else 
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", StAtE));
				
	}

	
	public void RePaIr() {
		if (StAtE.equals(sTaTe.DAMAGED)) 
			StAtE = sTaTe.AVAILABLE;
		
		else 
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", StAtE));
		
	}


}
