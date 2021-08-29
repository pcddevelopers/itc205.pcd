package library.borrowbook;
import java.util.ArrayList;
import java.util.List;

import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;
import library.entities.Member;

public class borrowBookCONTROL { //change "bORROW_bOOK_cONTROL" to "borrowBookcONTROL"
	
	private BorrowBookUI uI;
	
	private Library library; //change "lIbRaRy" to "library"
	private Member member;//change "mEmBeR" to "member"
	private enum CONTROLSTATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED }; //change "CONTROL_STATE" to "CONTROLSTATE"
	private CONTROLSTATE state; //change "CONTROL_STATE sTaTe" to "CONTROLSTATE state"  
	
	private List<Book> pendingList; // change "pEnDiNg_LiSt" to "pendingList" 
	private List<Loan> completedList; // change "cOmPlEtEd_LiSt" to "completedList"
	private Book book; //change "bOoK" to "book"
	
	
	public borrowBookcONTROL() {//change "bORROW_bOOK_cONTROL" to "borrowBookcONTROL"
		this.library = Library.getInstance(); //change ".lIbRaRy = Library.GeTiNsTaNcE" to "library = Library.getInstance"
		state = CONTROLSTATE.INITIALISED; //change "sTaTe" to "state"
	}
	

	public void setUi(BorrowBookUI Ui) {  //change "SeT_Ui" to "setUi"
		if (!state.equals(CONTROLSTATE.INITIALISED))//change "sTaTe" to "state"
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.Ui = Ui; //change "uI" to "Ui"
		Ui.setState(BorrowBookUI.UiState.READY); //change"SeT_StAtE" to "setState" & "uI_STaTe" to "UiState" 
		state = CONTROLSTATE.READY; //change "sTaTe = CONTROL_STATE.READY" to "state=CONTROLSTATE" 	
	}

		
	public void swiped(int memberId) {//change "SwIpEd(int mEmBeR_Id)" to "swiped(int memberId)"
		if (!state.equals(CONTROLSTATE.READY)) //change " (!sTaTe.equals(CONTROL_STATE.READY))" to "(!state.equals(CONTROLSTATE.READY))"
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId);//change "mEmBeR = lIbRaRy.gEt_MeMbEr(mEmBeR_Id)" to "member = library.getMember(memberId)"
		if (member == null) {//change "mEmBeR" to "member"
			Ui.display("Invalid memberId"); //change "uI.DiSpLaY" to "Ui.Display"
			return;
		}
		if (library.canMemberBorrow(member)) { //change "lIbRaRy.cAn_MeMbEr_BoRrOw(mEmBeR)" to "library.canMemberBorrow(member)"
			pendingList = new ArrayList<>(); // change "pEnDiNg_LiSt" to "pendingList"
			Ui.setState(BorrowBookUI.UiState.SCANNING);//change "uI.SeT_StAtE(BorrowBookUI.uI_STaTe.SCANNING)" to "Ui.setState(BorrowBookUI.UiState.SCANNING)"
			state = CONTROLSTATE.SCANNING;   //change "sTaTe = CONTROL_STATE.READY" to "state=CONTROLSTATE"  
		}
		else {
			Ui.Display("Member cannot borrow at this time"); //change "uI.DiSpLaY" to "Ui.Display"
			Ui.setState(BorrowBookUI.UiState.RESTRICTED); //change "uI.SeT_StAtE(BorrowBookUI.uI_STaTe.RESTRICTED)" to "Ui.setState(BorrowBookUI.UiState.RESTRICTED)"
		}
	}
	
	
	public void scanned(int bookId) { //change "ScAnNeD(int bOoKiD)" to "scanned(int bookId)"
		book = null; //change "bOoK" to "book"
		if (!state.equals(CONTROLSTATE.SCANNING))  //change "sTaTe.equals(CONTROL_STATE.SCANNING)" to "state.equals(CONTROLSTATE.SCANNING)"
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state"); 
			
		book = library.getBook(bookID); // change "bOoK = lIbRaRy.gEt_BoOk(bOoKiD)" to "book = library.getBook(bookID)"
		if (book == null) { //change "bOoK" to "book"
			Ui.DISPLAY("Invalid bookId"); //change "uI.DiSpLaY" to "Ui.DISPLAY"
			return;
		}
		if (!book.isAvailable()) { //change "bOoK.iS_AvAiLaBlE()" to "book.isAvailable()"
			Ui.DISPLAY("Book cannot be borrowed");//change "uI.DiSpLaY" to "Ui.DISPLAY"
			return;
		}
		pendingList.add(book); //change "pEnDiNg_LiSt.add(bOoK)" to "pendingList.add(book)"
		for (Book B : pendingList)  //change "pEnDiNg_LiSt)" to "pendingList"
			Ui.DISPLAY(B.toString());//change "uI.DiSpLaY" to "Ui.DISPLAY"
		
		if (library.getNumberOfLoansRemainingForMember(member) - pendingList.size() == 0) { //(lIbRaRy.gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr(mEmBeR) - pEnDiNg_LiSt.size() == 0) 
			                                                                                   //to "(library.getNumberOfLoansRemainingForMember(member) - pendingList.size() == 0)"
			Ui.DISPLAY("Loan limit reached"); //change "uI.DiSpLaY" to "Ui.DISPLAY"
			COMPLETE(); //change "CoMpLeTe" to "COMPLETE"
		}
	}
	
	
	public void complete() { //change "CoMpLeTe" to "complete"
		if (pendingList.size() == 0) //change "pEnDiNg_LiSt.size" to "pendingList.size"
			CANCEL();//Change "CaNcEl" to "CANCEL"
		
		else {
			Ui.DISPLAY("\nFinal Borrowing List"); //change "uI.DiSpLaY" to "Ui.DISPLAY" 
			for (Book book : pendingList)  //change "bOoK : pEnDiNg_LiSt" to "book : pendingList"
				Ui.DISPLAY(book.toString()); //change "uI.DiSpLaY(bOoK.toString())" to "Ui.DISPLAY(book.toString())"
			
			completedList = new ArrayList<Loan>(); //change "cOmPlEtEd_LiSt" to "completedList"
			Ui.setState(BorrowBookUI.UiState.FINALISING);//change "uI.SeT_StAtE(BorrowBookUI.uI_STaTe.FINALISING)" to "Ui.setState(BorrowBookUI.UiState.FINALISING)"
			state = CONTROLSTATE.FINALISING; //change "sTaTe = CONTROL_STATE.FINALISING" to "state = CONTROLSTATE.FINALISING;"
		}
	}


	public void commitLoans() { //change "CoMmIt_LoAnS" to "commitLoans"
		if (!state.equals(CONTROLSTATE.FINALISING))  //change "sTaTe.equals(CONTROL_STATE.FINALISING)" to "state.equals(CONTROLSTATE.FINALISING)"
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
			
		for (Book B : pendingList) { //change "pEnDiNg_LiSt" to "pendingList"
			Loan loan = library.issueLoan(B, member); // change "lOaN = lIbRaRy.iSsUe_LoAn(B, mEmBeR)" to "loan = library.issueLoan(B, member)"
			completedList.add(loan); //change "cOmPlEtEd_LiSt.add(lOaN)" to "completedList.add(loan)"			
		}
		Ui.DISPLAY("Completed Loan Slip"); //change "uI.DiSpLaY" to "Ui.DISPLAY" 
		for (Loan LOAN : completedList)  //change "cOmPlEtEd_LiSt" to "completedList"
			Ui.DISPLAY(LOAN.toString()); //change "uI.DiSpLaY" to "Ui.DISPLAY" 
		
		Ui.setState(BorrowBookUI.UiState.COMPLETED); //change "uI.SeT_StAtE(BorrowBookUI.uI_STaTe.COMPLETED)" to "Ui.setState(BorrowBookUI.UiState.COMPLETED)"
		state = CONTROLSTATE.COMPLETED; //change "sTaTe = CONTROL_STATE.COMPLETED" to "state = CONTROLSTATE.COMPLETED;"
	}

	
	public void CaNcEl() {
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.CANCELLED);
		sTaTe = CONTROL_STATE.CANCELLED;
	}
	
	
}
