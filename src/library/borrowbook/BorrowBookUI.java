package library.borrowbook;
import java.util.Scanner;


public class BorrowBookUI {
	
	public static enum UIstate { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };  //change "uI_STaTe" to "UIstate"

	private borrowbookCONTROL control; // change " bORROW_bOOK_cONTROL CoNtRoL" to "borrowbookCONTROL control" 
	private Scanner input; // change "InPuT" to "input"
	private UIstate state; //change "uI_STaTe STaTe" to "UIstate state"

	
	public BorrowBookUI(borrowbookCONTROL control) {  // change " bORROW_bOOK_cONTROL CoNtRoL" to "borrowbookCONTROL control"
		this.control = control; //change "CoNtRoL" to "control"
		input = new Scanner(System.in); // change "InPuT" to "input"
		state = UIstate.INITIALISED; //change "StaTe = uI_STaTe.INITIALISED" to "state = UIstate.INITIALISED"
		control.setUI(this); // change "SeT_Ui" to "setUI"
	}

	
	private String input(String prompt) { // change "InPuT" to "input" and "PrOmPt" to "prompt"
		System.out.print(prompt); //change "PrOmPt" to "prompt"
		return input.nextLine();// change "InPuT" to "input"
	}	
		
		
	private void  output(Object OBJECT) { //change " OuTpUt(Object ObJeCt)" to " output(Object OBJECT) "
		System.out.println(object); //change "ObJeCt" to "object"
	}
	
			
	public void setState(UIState STAtE) { // change "SeT_StAtE(uI_STaTe StAtE)" to " setState(UIState STAtE)"
		this.state = state; // Change "this.StaTe = StAtE;" to "this.state = state"
	}

	
	public void run() { //change "RuN" to "run"
		output("Borrow Book Use Case UI\n"); //change "OuTpUt" to "output"
		
		while (true) {
			
			switch (state) { //change "StaTe" to "state" 			
			
			case CANCELLED:
				output("Borrowing Cancelled");  //change "OuTpUt" to "output"
				return;

				
			case READY:
				String memStr = iNpUT("Swipe member card (press <enter> to cancel): "); //change "MEM_STR" to "memStr"
				if (memStr.length() == 0) {  //change "MEM_STR" to "memStr"
					control.CANCEL(); //change "CoNtRoL.CaNcEl" to "control.CANCEL"
					break;
				}
				try {
					int memberID = Integer.valueOf(memStr).intValue(); //change "MeMbEr_Id" to "memberID" & "MEM_STR" to "memStr"
					control.swiped(memberID); //change "CoNtRoL.SwIpEd(MeMbEr_Id)" to "control.swiped(memberID)"
				}
				catch (NumberFormatException e) {
					output("Invalid Member Id");//change "OuTpUt" to "output"
				}
				break;

				
			case RESTRICTED:
				input("Press <any key> to cancel"); //change "iNpUT" to "input"
				control.CANCEL(); //change "CoNtRoL.CaNcEl" to "control.CANCEL"
				break;
			
				
			case SCANNING:
				String bookStringInput = input("Scan Book (<enter> completes): "); //change " BoOk_StRiNg_InPuT = iNpUT" to "bookStringInput = input"
				if (bookStringInput.length() == 0) { // change "BoOk_StRiNg_InPuT" to "bookStringInput"
					control.COMPLETE(); //change "CoNtRoL.CoMpLeTe" to "control.COMPLETE"
					break;
				}
				try {
					int BiD = Integer.valueOf(bookStringInput).intValue(); //change "BiD" to "bid" and "BoOk_StRiNg_InPuT" to "bookStringInput"
					control.SCANNED(bid); //change "CoNtRoL.ScAnNeD(BiD)" to "control.SCANNED(bid)"
					
				} catch (NumberFormatException e) { 
					output("Invalid Book Id");//change "OuTpUt" to "output"
				} 
				break;
					
				
			case FINALISING:
				String AnS = iNpUT("Commit loans? (Y/N): ");
				if (AnS.toUpperCase().equals("N")) {
					CoNtRoL.CaNcEl();
					
				} else {
					CoNtRoL.CoMmIt_LoAnS();
					iNpUT("Press <any key> to complete ");
				}
				break;
				
				
			case COMPLETED:
				OuTpUt("Borrowing Completed");
				return;
	
				
			default:
				OuTpUt("Unhandled state");
				throw new RuntimeException("BorrowBookUI : unhandled state :" + StaTe);			
			}
		}		
	}


	public void DiSpLaY(Object object) {
		OuTpUt(object);		
	}


}
