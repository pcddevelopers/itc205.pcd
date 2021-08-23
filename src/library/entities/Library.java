package library.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	private static final String libraryFile = "library.obj";// change  libraryFile to libraryFile
	private static final int loanLimit = 2; // change loanLimit to loanLimit
	private static final int loanPeriod = 2; 
	private static final double finePerDay = 1.0;//change finePerDay to finePerDay
	private static final double maxFinesOwed = 1.0;
	private static final double damageFee = 2.0;
	
	private static Library SeLf;
	private int bookId; // change bookId to bookId
	private int memberId; // change memberId to memberId
	private int loanId; // change loanId to loanId
	private Date loanDate; // change loanDate to loanDate
	
	private Map<Integer, Book> catalog; // change catalog to catalog
	private Map<Integer, Member> members;//change members to members
	private Map<Integer, Loan> loans;//change loans to loans
	private Map<Integer, Loan> currentLoans;//change currentLoans to currentLoans
	private Map<Integer, Book> damagedBooks;//change damagedBooks to damagedBooks
	

	private Library() {
		catalog = new HashMap<>();
		members = new HashMap<>();
		loans = new HashMap<>();
		currentLoans = new HashMap<>();
		damagedBooks = new HashMap<>();
		bookId = 1;
		memberId = 1;		
		loanId = 1;		
	}

	
	public static synchronized Library getInstance() {		//change getInstance to getInstance
		if (SeLf == null) {
			Path PATH = Paths.get(libraryFile);			
			if (Files.exists(PATH)) {	
				try (ObjectInputStream fileLibrary = new ObjectInputStream(new FileInputStream(libraryFile));) { // change fileLibrary to fileLibrary
			    
					SeLf = (Library) fileLibrary.readObject();
					calendar.getInstance().setDate(SeLf.loanDate); //change Calender to calender, change setDate to setDate
					fileLibrary.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else SeLf = new Library();
		}
		return SeLf;
	}

	
	public static synchronized void SaVe() {
		if (SeLf != null) {
			SeLf.loanDate = calendar.getInstance().getDate(); //change getDate to getDate
			try (ObjectOutputStream fileLibrary = new ObjectOutputStream(new FileOutputStream(libraryFile));) {
				fileLibrary.writeObject(SeLf);
				fileLibrary.flush();
				fileLibrary.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int getbookId() { //change gEt_BoOkId to getbookId
		return bookId;
	}
	
	
	public int getmemberId() { //change gEt_memberId to getmemberId
		return memberId;
	}
	
	
	private int getNextbookId() { //change gEt_NeXt_bookId to getNextbookId
		return bookId++;
	}

	
	private int getNextmemberId() { //change gEt_NeXt_memberId to getNextmemberId
		return memberId++;
	}

	
	private int getNextloanId() { //change gEt_NeXt_loanId to getNextloanId
		return loanId++;
	}

	
	public List<Member> listMembers() {		// change lIsT_members to listMembers
		return new ArrayList<Member>(members.values()); 
	}


	public List<Book> listBooks() {		//change lIsT_BoOkS to listBooks
		return new ArrayList<Book>(catalog.values()); 
	}


	public List<Loan> listCurrentLoans() { // change lISt_currentLoans to listCurrentLoans
		return new ArrayList<Loan>(currentLoans.values());
	}


	public Member aDd_MeMbEr(String lastName, String firstName, String email, int phoneNo) {		
		Member member = new Member(lastName, firstName, email, phoneNo, getNextmemberId());
		members.put(member.getId(), member);		//change GeT_ID to getId
		return member;
	}

	
	public Book aDd_BoOk(String a, String t, String c) {		
		Book b = new Book(a, t, c, getNextbookId());
		catalog.put(b.getId(), b);		
		return b;
	}

	
	public Member gEt_MeMbEr(int memberId) {
		if (members.containsKey(memberId)) 
			return members.get(memberId);
		return null;
	}

	
	public Book getbook(int bookId) { //change gEt_BoOk to getbook
		if (catalog.containsKey(bookId)) 
			return catalog.get(bookId);		
		return null;
	}

	
	public int getLoanLimit() { //change gEt_LoAn_LiMiT to getLoanLimit
		return loanLimit;
	}

	
	public boolean canMemberBorrow(Member member) {		//change cAn_MeMbEr_BoRrOw to canMemberBorrow
		if (member.getNumberofCurrentLoans() == loanLimit ) //change gEt_nUmBeR_Of_currentLoans to getNumberofCurrentLoans
			return false;
				
		if (member.finesOwed() >= maxFinesOwed) //change FiNeS_OwEd to finesOwed
			return false;
				
		for (Loan loan : member.getLoans()) //change GeT_loans to getLoans
			if (loan.IsOverDue()) //change Is_OvEr_DuE to IsOverDue
				return false;
			
		return true;
	}

	
	public int getnLoan(Member MeMbEr) {		//change gEt_NuMbEr_Of_loans_ReMaInInG_FoR_MeMbEr to getnLoan
		return loanLimit - MeMbEr.getnCurrent(); // change gEt_nUmBeR_Of_currentLoans to getnCurrent
	}

	
	public Loan iSsUe_LoAn(Book book, Member member) { // change iSsUe_LoAn to issueLoan
		Date dueDate = calendar.getInstance().getDdate(loanPeriod); // change gEt_DuE_DaTe to getDdate
		Loan loan = new Loan(getnloanId(), book, member, dueDate); // change gEt_NeXt_loanId to getnloanId
		member.takeoutLoan(loan); // change TaKe_OuT_LoAn to takeoutLoan
		book.borrow(); // change BoRrOw to borrow
		loans.put(loan.getId(), loan);
		currentLoans.put(book.gEtId(), loan);
		return loan;
	}
	
	
	public Loan getloanBookId(int bookId) { // change GeT_LoAn_By_BoOkId to getloanBookId
		if (currentLoans.containsKey(bookId)) 
			return currentLoans.get(bookId);
		
		return null;
	}

	
	public double calculateFine(Loan LoAn) { // change CaLcUlAtE_OvEr_DuE_FiNe to calculateFine
		if (LoAn.IsOverDue()) {
			long daysOdue = calendar.getInstance().getDifference(LoAn.getDdate()); // change to getDifference // change DaYs_OvEr_DuE to daysOdue
			double fInE = daysOdue * finePerDay;
			return fine; //change fiNe to fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(Loan currentLoan, boolean isDamaged) { //DiScHaRgE_LoAn to dischargeLoan
		Member member = currentLoan.getMember();
		Book book  = currentLoan.getBook();
		
		double overDueFine = calculateFine(currentLoan); // change oVeR_DuE_FiNe to overDueFine
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan); // change mEmBeR.dIsChArGeLoAn to member.dischargeLoan
		book.return(isDamaged); //change ReTuRn to return
		if (isDamaged) { //change iS_dAmAgEd to isDamaged
			memberaddFine(damageFee); // change mEmBeR.AdD_FiN to memberaddFine
			damagedBooks.put(book.getId(), book);
		}
		currentloan.discharge(); //change DiScHaRgE to discharge
		currentLoans.remove(book.getIdb());
	}


	public void checkCurrentLoans() { change cHeCk_currentLoans to checkCurrentLoans
		for (Loan loan : currentLoans.values()) 
			loan.checkOverDue(); //change lOaN.cHeCk_OvEr_DuE to loan.checkOverDue
				
	}


	public void repairBook(Book currentBook) { // change RePaIr_BoOk to repairBook
		if (damagedBooks.containsKey(currentBook.getId())) {
			currentBook.Repair(); // change RePaIr to Repair
			damagedBooks.remove(currentBook.getId()); // change cUrReNt_BoOk to currentBook
		}
		else 
			throw new RuntimeException("Library: repairBook: book is not damaged");
		
		
	}
	
	
}
