package library.borrowbook;
import java.util.ArrayList;
import java.util.List;

import library.entities.Book;
import library.entities.Library;
import library.entities.Loan;
import library.entities.Member;

public class borrowBookcONTROL { //change "bORROW_bOOK_cONTROL" to "borrowBookcONTROL"
	
	private BorrowBookUI uI;
	
	private Library library; //change "lIbRaRy" to "library"
	private Member member;//change "mEmBeR" to "member"
	private enum CONTROLSTATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED }; //change "CONTROL_STATE" to "CONTROLSTATE"
	private CONTROLSTATE state; //change "CONTROL_STATE sTaTe" to "CONTROLSTATE state"  
	
	private List<Book> pendingList; // change "pEnDiNg_LiSt" to "pendingList" 
	private List<Loan> completedList; // change "cOmPlEtEd_LiSt" to "completedList"
	private Book bOoK; //change "bOoK" to "book"
	
	
	public borrowBookcONTROL() {//change "bORROW_bOOK_cONTROL" to "borrowBookcONTROL"
		this.library = Library.getInstance(); //change ".lIbRaRy = Library.GeTiNsTaNcE" to "library = Library.getInstance"
		sTaTe = CONTROL_STATE.INITIALISED; //change "sTaTe" to "state"
	}
	

	public void SeT_Ui(BorrowBookUI Ui) { 
		if (!sTaTe.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.uI = Ui;
		Ui.SeT_StAtE(BorrowBookUI.uI_STaTe.READY);
		sTaTe = CONTROL_STATE.READY;		
	}

		
	public void SwIpEd(int mEmBeR_Id) {
		if (!sTaTe.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		mEmBeR = lIbRaRy.gEt_MeMbEr(mEmBeR_Id);
		if (mEmBeR == null) {
			uI.DiSpLaY("Invalid memberId");
			return;
		}
		if (lIbRaRy.cAn_MeMbEr_BoRrOw(mEmBeR)) {
			pEnDiNg_LiSt = new ArrayList<>();
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.SCANNING);
			sTaTe = CONTROL_STATE.SCANNING; 
		}
		else {
			uI.DiSpLaY("Member cannot borrow at this time");
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.RESTRICTED); 
		}
	}
	
	
	public void ScAnNeD(int bOoKiD) {
		bOoK = null;
		if (!sTaTe.equals(CONTROL_STATE.SCANNING)) 
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
			
		bOoK = lIbRaRy.gEt_BoOk(bOoKiD);
		if (bOoK == null) {
			uI.DiSpLaY("Invalid bookId");
			return;
		}
		if (!bOoK.iS_AvAiLaBlE()) {
			uI.DiSpLaY("Book cannot be borrowed");
			return;
		}
		pEnDiNg_LiSt.add(bOoK);
		for (Book B : pEnDiNg_LiSt) 
			uI.DiSpLaY(B.toString());
		
		if (lIbRaRy.gEt_NuMbEr_Of_LoAnS_ReMaInInG_FoR_MeMbEr(mEmBeR) - pEnDiNg_LiSt.size() == 0) {
			uI.DiSpLaY("Loan limit reached");
			CoMpLeTe();
		}
	}
	
	
	public void CoMpLeTe() {
		if (pEnDiNg_LiSt.size() == 0) 
			CaNcEl();
		
		else {
			uI.DiSpLaY("\nFinal Borrowing List");
			for (Book bOoK : pEnDiNg_LiSt) 
				uI.DiSpLaY(bOoK.toString());
			
			cOmPlEtEd_LiSt = new ArrayList<Loan>();
			uI.SeT_StAtE(BorrowBookUI.uI_STaTe.FINALISING);
			sTaTe = CONTROL_STATE.FINALISING;
		}
	}


	public void CoMmIt_LoAnS() {
		if (!sTaTe.equals(CONTROL_STATE.FINALISING)) 
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
			
		for (Book B : pEnDiNg_LiSt) {
			Loan lOaN = lIbRaRy.iSsUe_LoAn(B, mEmBeR);
			cOmPlEtEd_LiSt.add(lOaN);			
		}
		uI.DiSpLaY("Completed Loan Slip");
		for (Loan LOAN : cOmPlEtEd_LiSt) 
			uI.DiSpLaY(LOAN.toString());
		
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.COMPLETED);
		sTaTe = CONTROL_STATE.COMPLETED;
	}

	
	public void CaNcEl() {
		uI.SeT_StAtE(BorrowBookUI.uI_STaTe.CANCELLED);
		sTaTe = CONTROL_STATE.CANCELLED;
	}
	
	
}
