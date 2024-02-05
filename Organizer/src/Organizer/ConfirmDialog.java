package Organizer;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;



public class ConfirmDialog extends Dialog<String> {
	
	public String content = "";
	
	
	public ConfirmDialog(String content) {
		getDialogPane().setHeaderText("Do you confirm");
		getDialogPane().setContentText(content);
		getDialogPane().getButtonTypes().addAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		
		this.content = content;
		};
	}
