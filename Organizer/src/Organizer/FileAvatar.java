package Organizer;

import java.nio.file.attribute.FileTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.image.Image;

public class FileAvatar {
	
	public boolean isVideo = false;
	public boolean isText = false;
	public boolean isPicture = false;
	public boolean isAudio = false;
	public Image icon;
	public String name = "";
	public FileTime fileTime;
	public Date creation;
	public Date published;
	public Date added;
	public String creator;
	 
	public DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/uuuu");
	public ArrayList<ArrayList<String>> informationString = new ArrayList<ArrayList<String>>();
	
	
	public FileAvatar(boolean isVideo,boolean isText,boolean isPicture,Image icon, 
			String name, Date creation, Date published, Date added, String creator, 
			ArrayList<ArrayList<String>> informationString, boolean isAudio) {
		
	 this.isVideo = isVideo;
	 this.isText = isText;
	 this.isPicture = isPicture;
	 this.icon = icon;
	 this.name = name;
	 this.creation = creation;
	 this.published = published;
	 this.added = added;
	 this.creator = creator;
	 this.informationString = informationString;
	 this.isAudio = isAudio;

	}

}
