package library.entities;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable {
	
	public static enum loanState { CURRENT, OVER_DUE, DISCHARGED }; //change loanState to loanState
	
	private int loanId; // change LoAn_Id to loanId
	private Book book; // change BoOk to book
	private Member member;// change MeMbEr to member
	private Date date; // change DaTe to date
	private loanState state; //change StAtE to state, 

	
	public Loan(int loanId, Book book, Member member, Date dueDate) {
		this.loanId = loanId;
		this.book = book;
		this.member = member;
		this.date = dueDate; // change DuE_dAtE to dueDate
		this.state = loanState.CURRENT;
	}

	
	public void checkOverdue() { // change cHeCk_OvEr_DuE to checkOverdue
		if (state == loanState.CURRENT && //change StAtE to state
			Calendar.getInstance().getDate().after(date)) //change gEtInStAnCe to getInstance, gEt_DaTe to getDate
			this.state = loanState.OVER_DUE;			
		
	}

	
	public boolean isOverDue() { //change Is_OvEr_DuE to isOverDue
		return state == loanState.OVER_DUE;
	}

	
	public Integer getId() { // change GeT_Id to getId
		return loanId;
	}


	public Date getDuedate() { // change GeT_DuE_DaTe to getDuedate
		return date;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(loanId).append("\n")
		  .append("  Borrower ").append(member.GeT_ID()).append(" : ")
		  .append(member.getLastname()).append(", ").append(member.getFirstname()).append("\n") // change GeT_FiRsT_NaMe to getFirstname, GeT_LaSt_NaMe to getLastname
		  .append("  Book ").append(book.gEtId()).append(" : " )
		  .append(book.getTitle()).append("\n") // change gEtTiTlE to getTitle
		  .append("  DueDate: ").append(sdf.format(date)).append("\n")
		  .append("  State: ").append(state);		
		return sb.toString();
	}


	public Member getMember() { //change GeT_MeMbEr to getMember
		return member;
	}


	public Book getBook() { // change GeT_BoOk to getBook
		return book;
	}


	public void discharge() {
		state = loanState.DISCHARGED;		
	}

}
