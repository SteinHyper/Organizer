package Organizer;

import java.io.File;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;

public class FileAvatar {
	
	public Image icon;
	public String name = "";
	public FileTime fileTime;
	public long modified;
	public Date published;
	public LocalDateTime added;
	public String creator;
	public File file;
	 
	public DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
	public ArrayList<ArrayList<String>> listAtrributs = new ArrayList<ArrayList<String>>();
	
	
	public FileAvatar(Image icon,String name, long modified, Date published, LocalDateTime added, String creator, 
			ArrayList<ArrayList<String>> listAtrributs, File file) {
		

	 this.name = name;
	 this.modified = modified;
	 this.published = published;
	 this.added = added;
	 this.creator = creator;
	 this.listAtrributs = listAtrributs;
	 this.file = file;
	
	}

}
