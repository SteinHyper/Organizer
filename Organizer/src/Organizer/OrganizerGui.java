package Organizer;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class OrganizerGui extends Application{

	private Font choosenFont;

	private Stage primaryStage;

	private Rectangle buttonBackground;
	private final double BUTTON_HEIGHT = 30;

	private Rectangle filterBackground;

	private File currentFile;

	private Labeled lblStatus;

	private Button btnSaveCollection;

	private final ExtensionFilter videoFilter = new  ExtensionFilter("Video File","*.mp4","*.M4P","*.M4V","*.mov","*.QT",
		"*.ogv","*.WEBM","*.MPG",
		"*.MP2","*.MPEG","*.MPE","*.MPV","*.AVI"," *.WMV","*.FLV", "*.SWF","*.AVCHD");
private final ExtensionFilter textFilter = new  ExtensionFilter("Text File","*.txt","*.irf","*.irx","*.chm","*.djvu",
		"*.DOC","*.DOCX","*.EPUB",
		"*.pdb","*.fb2","*.xeb","*.ceb","*.htm"," *.ibooks","*.inf", "*.azw","*.azw3","*.kf8","*.kfx","*.lit",
		"*.pkg","*.opf","*.pdf","*.pdb","*.rtf","*pdg","*.xml","*.oxps","*.xps");
private final ExtensionFilter pictureFilter = new  ExtensionFilter("Picture File","*.apng","*.avif","*.gif","*.jp",
		"*.jpg", "*.jpeg", "*.jfif", "*.pjpeg", "*.pjp","*.png","*.svg","*.webp");
private final ExtensionFilter audioFilter = new  ExtensionFilter("Audio File","*.cda","*.8svx","*.wv","*.wma","*.wav",
		"*.vox","*.voc","*.tta","*.rf64","*.raw","*.rm","*.ra","*.opus","*.oga","*.nmf","*.msv","*.mpc","*.mp3",
		"*.movpkg","*.mmf","*.m4b","*.m4a","*.3gp","*.aa","*aac.","*.aax ","*.act","*.aiff","*.aiff","*.alac",
		"*.amr ","*.ape","*.au","*.awb","*.dss","*.dvf ","*.flac","*.gsm ","*.iklax","*.ivs");

private Image iconVideo = new Image("png/Video_Icon.png");
private Image iconNull = new Image("png/Null_Icon.png");
private Image iconAudio = new Image("png/Audio_Icon.png");
private Image iconPicture = new Image("png/Picture_Icon.png");
private Image iconText = new Image("png/Text_Icon.png");

private ArrayList<VBox> vboxFileList = new ArrayList<VBox>();
private FileChooser chooserFile;

private Image imageFile;

private FlowPane flowPane;


private ArrayList<FileAvatar> fileAvatars = new ArrayList<FileAvatar>();

private VBox focusedFile;
private String focusedFileName;








@Override
public void start(Stage primaryStage) throws Exception {
	this.primaryStage = primaryStage;

	BorderPane mainPane = new BorderPane();
	//defineChoosenFont(selectedFont);

	//mainPane.setTop(buildTopPane());
	mainPane.setCenter(buildCenterPane());
	mainPane.setLeft(buildLeftPane());
	//mainPane.setBottom(buildBottomPane());

	Scene scene = new Scene(mainPane);
	primaryStage.setScene(scene);
	primaryStage.setTitle("Organizer");
	primaryStage.sizeToScene();
	primaryStage.show();
}


private void defineChoosenFont(Font selectedFont) {
	if(selectedFont == null)
		choosenFont = setDefaultFont();
	else
		choosenFont = selectedFont;
}
private Font setDefaultFont() {
	Font defaultFont = Font.getDefault();
	return defaultFont;
}


private Node buildBottomPane() {
	VBox vbox = new VBox();
	lblStatus = new Label("Running unnamed Collection");
	vbox.getChildren().addAll(lblStatus);
	return vbox;
}


private Node buildCenterPane() {
	buttonBackground = (Rectangle)buildBackround();
	filterBackground = (Rectangle)buildBackround();

	VBox centerPiece = new VBox();

	StackPane fileButtonSpace = new StackPane();
	HBox fileButtons = new HBox();
	fileButtons.setAlignment(Pos.CENTER_LEFT);
	fileButtons.setSpacing(1);
	Rectangle fileButtonBackground = buttonBackground;
	fileButtonSpace.getChildren().addAll(fileButtonBackground,fileButtons);


	Button btnLoadCollection = new Button("Load");
	btnSaveCollection = new Button("Save");
	Button btnAddFile = new Button("Add File");

	Button btnRemoveFile = new Button("Remove File");
	btnRemoveFile.setOnAction(e_-> doRemovFile());
	fileButtons.getChildren().addAll(btnLoadCollection,btnSaveCollection,btnAddFile,btnRemoveFile);


	VBox searchBarSpace = new VBox();
	TextField searchBar = new TextField();
	searchBarSpace.getChildren().addAll(searchBar);

	HBox inExClude = new HBox();
	VBox includeSide = new VBox();
	VBox excludeSide = new VBox();
	Label lblInclude = new Label("Include");
	Label lblExclude = new Label("Exclude");
	ScrollPane includePane = buildAttributeScrollPane();
	includePane.setPrefViewportHeight(100);
	includePane.setPrefViewportWidth(200);
	ScrollPane excludePane = buildAttributeScrollPane();
	excludePane.setPrefViewportHeight(100);
	excludePane.setPrefViewportWidth(200);
	includeSide.getChildren().addAll(lblInclude,includePane);
	excludeSide.getChildren().addAll(lblExclude,excludePane);
	inExClude.getChildren().addAll(includeSide,excludeSide);

	HBox attributeBar = new HBox();
	TextField searchAttribute = new TextField();
	Button btnAZ = new Button("A-Z");
	Button btnType = new Button("Type");
	Button btnFilter = new Button("Filter");
	Rectangle filterButtonBackground =  filterBackground;
	StackPane filterPane = buildStackPane();
	filterPane.setAlignment(Pos.CENTER_LEFT);
	attributeBar.getChildren().addAll(searchAttribute,btnAZ,btnType,btnFilter);
	filterPane.getChildren().addAll(filterButtonBackground,attributeBar);

	VBox attributeSpace = new VBox();
	ScrollPane attributeScroll = buildAttributeScrollPane();
	attributeScroll.setPrefViewportHeight(350);
	attributeSpace.getChildren().addAll(attributeScroll);

	centerPiece.getChildren().addAll(fileButtonSpace,searchBarSpace,inExClude,filterPane,attributeSpace);
	btnAddFile.setOnAction(ev ->doLoadFileItem());
	listenStageSize();
	return centerPiece;
}

private Node buildLeftPane() {
	VBox vBoxFileBox= new VBox();
	ScrollPane scrollPane = buildFileScrollPane();
	Label lblFiles = new Label("File Items");
	
    vBoxFileBox.getChildren().addAll(lblFiles,scrollPane);
	return  vBoxFileBox;		
}

// Creates the A Button for the Pane that displays the Files
private VBox buildAddButton() {
	VBox add = new VBox();
	add.setAlignment(Pos.CENTER);
	Label addMore = new Label("Add More");
	Button btnAdd = new Button();
	StackPane addFile = new StackPane();
	Rectangle addBackround = new Rectangle();
	addBackround.setFill(Color.SEASHELL);
	
	Rectangle vPlus = new Rectangle();
	vPlus.setFill(Color.GREENYELLOW);
	vPlus.setHeight(40);
	vPlus.setWidth(10);
	Rectangle hPlus = new Rectangle();
	hPlus.setFill(Color.GREENYELLOW);
	hPlus.setHeight(10);
	hPlus.setWidth(40);
	addFile.getChildren().addAll(addBackround,vPlus,hPlus);
    btnAdd.setGraphic(addFile);
    btnAdd.setMaxSize(50, 50);
   
    btnAdd.setOnAction(ev ->doLoadFileItem());
    btnAdd.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, new CornerRadii(10), new Insets(0))));
	add.getChildren().addAll(btnAdd,addMore);      
	return add;
}


private Node buildTopPane() {
	// TODO Auto-generated method stub
	return null;
}

private Node buildBackround() {
	Rectangle backround = new Rectangle();
	backround.setHeight(BUTTON_HEIGHT);
	backround.autosize();
	backround.setFill(Color.CYAN);
	return backround;
}

private StackPane buildStackPane() {
	StackPane nodeStack = new StackPane();
	return nodeStack;
}

private void resizeBackground() {
	buttonBackground.setWidth(primaryStage.getWidth()*0.9);
	filterBackground.setWidth(primaryStage.getWidth()*0.9);

}

private void listenStageSize() {
	primaryStage.widthProperty().addListener((obs, oldVal,newVal)->{
		resizeBackground();
	});
	primaryStage.heightProperty().addListener((obs, oldVal,newVal)->{
		resizeBackground();
	});

}

/*private int countHBoxContant(HBox buttonContainer) {
	int count = 0;
	for (Node childe : buttonContainer.getChildren() ) {
		count++;
	}
	return count;
	}*/
// Creates the Scroll and FlowPane of the FileCollection.
private ScrollPane buildFileScrollPane() {
	flowPane = new FlowPane();
	flowPane.setOrientation(Orientation.HORIZONTAL);
    
	flowPane.setVgap(8);
	flowPane.setHgap(4);
	vboxFileList.add(buildAddButton());
		for (VBox vBox : vboxFileList) {
			flowPane.getChildren().add(vBox);
		}
		ScrollPane scrollPane = new ScrollPane(flowPane);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
  
  private ScrollPane buildAttributeScrollPane() {
	 FlowPane flowPaneAttribute = new FlowPane();
	 flowPaneAttribute.setOrientation(Orientation.HORIZONTAL);
		flowPaneAttribute.setVgap(8);
		flowPaneAttribute.setHgap(4);
		//flowPaneAttribute.setPrefWrapLength(300); //preferred width = 300
	 Button btnAttribute[] = {};
			for (Button button : btnAttribute) {
				flowPaneAttribute.getChildren().add(button);
			}
			ScrollPane scrollPane = new ScrollPane(flowPaneAttribute);
			scrollPane.setFitToWidth(true);
			return scrollPane;
  }

	private void addFileToWindow(FileAvatar fileAvatar) {
		VBox fileItem = new VBox();
		ImageView fileIcon = new ImageView();
		Image icon = imageFile;
		fileIcon.setImage(icon);
		fileIcon.setFitHeight(50);
		fileIcon.setFitWidth(50);
		
		Button btnFile = new Button();
		btnFile.setGraphic(fileIcon);
		
		Label fileName = new Label(fileAvatar.name);
		fileItem.getChildren().addAll(btnFile,fileName);
		
		vboxFileList.add(fileItem);
		fileName.setMaxSize(50, BUTTON_HEIGHT);
		fileName.setEllipsisString("");
		fileItem.setAlignment(Pos.CENTER);
		btnFile.setOnMouseClicked(e-> doLastFocus(fileItem, fileName.getText()));
		flowPane.getChildren().add(fileItem);
	}



	private void doLastFocus(VBox fileItem, String FilenName) {
		focusedFileName = FilenName;
		focusedFile = fileItem;
	}


	private void doExecuteFile() {

	}


	private void switchDisableStateForSaveButtons(boolean state) {
		btnSaveCollection.setDisable(state);
	}

	private void doFileNew() {
		//taEditor.clear();
	currentFile = null;
	switchDisableStateForSaveButtons(true);
}

private void doSaveFileAs() {
	FileChooser chooserCollection = new FileChooser();
	chooserCollection.setTitle("Save your Collection");
	chooserCollection.getExtensionFilters().addAll(
			new ExtensionFilter("Collection",".csv"));
}



private void doLoadFileItem() {
	if(iconNull != null && iconVideo != null) {
		imageFile = iconNull;
		chooserFile = new FileChooser();
		chooserFile.setTitle("Choose a File to add");
		chooserFile.getExtensionFilters().addAll(videoFilter,pictureFilter,audioFilter,textFilter);		

		File addedFile = chooserFile.showOpenDialog(primaryStage);
		pickIcon();
		String fileName = addedFile.getName();
		LocalDateTime added = LocalDateTime.now();
		Image icon = imageFile;
		
		FileAvatar fileAvatar = new FileAvatar(icon, fileName, addedFile.lastModified(), null, added, "", null, addedFile);
		addFileToWindow(fileAvatar);
	}

	if(iconNull==null) {
		Alert alertNullIcon = new Alert(AlertType.ERROR,"Warning Null_Icon.png is missing");
		if(alertNullIcon.getResult() == ButtonType.OK) 
			return;


		if(iconVideo==null) {
			Alert alertVideoIcon = new Alert(AlertType.ERROR,"Warning Video_Icon.png is missing");
			if(alertVideoIcon.getResult() == ButtonType.OK) 
				return;
			if(iconAudio==null) {
				Alert alertAudioIcon = new Alert(AlertType.ERROR,"Warning Audio_Icon.png is missing");
				if(alertAudioIcon.getResult() == ButtonType.OK) 
					return;
			}
			if(iconPicture==null) {
				Alert alertPictureIcon = new Alert(AlertType.ERROR,"Warning Picture_Icon.png is missing");
				if(alertPictureIcon.getResult() == ButtonType.OK) 
					return;
			}
			if(iconText==null) {
				Alert alertTextIcon = new Alert(AlertType.ERROR,"Warning Text_Icon.png is missing");
				if(alertTextIcon.getResult() == ButtonType.OK) 
					return;
			}
		}
	}
}

private void doRemovFile() {
	ConfirmDialog confirmDialog = new ConfirmDialog("Are you sure you want to remove "+"\n"+focusedFileName);
	flowPane.getChildren().remove(focusedFile);

}

private void pickIcon() {
	if(chooserFile.getSelectedExtensionFilter() == videoFilter) {
		imageFile = iconVideo;
	}
	if(chooserFile.getSelectedExtensionFilter() == audioFilter) {
		imageFile = iconAudio;
	}
	if(chooserFile.getSelectedExtensionFilter() == textFilter) {
		imageFile = iconText;
	}
	if(chooserFile.getSelectedExtensionFilter() == pictureFilter) {
		imageFile = iconPicture;
	}
	
}

private String getFileExtension(File file) {
	String fileName = file.getName();
	int lastIndexOf = fileName.lastIndexOf(".");
	if (lastIndexOf == -1) {
		return ""; //empty extension
	}
	return fileName.substring(lastIndexOf);

}

private String checkExtensionTyp(String extension) {
	String[] videoArray = new String[] {".mp4",".M4P",".M4V",".mov",".QT",".ogv",".WEBM",".MPG",
			".MP2",".MPEG",".MPE",".MPV",".AVI"," .WMV",".FLV", ".SWF",".AVCHD"};	
	String[] textArray = new  String[]{".txt",".irf",".irx",".chm",".djvu",
			".DOC",".DOCX",".EPUB",
			".pdb",".fb2",".xeb",".ceb",".htm",".ibooks",".inf", ".azw",".azw3",".kf8","kfx","*.lit",
			".pkg",".opf",".pdf",".pdb",".rtf","pdg",".xml",".oxps",".xps"};
	String[] pictureArry = new String[]{".apng",".avif",".gif",".jp",
			".jpg", ".jpeg", ".jfif", ".pjpeg", ".pjp",".png",".svg",".webp"};
	String[] audioArray = new  String[]{"*.cda","*.8svx","*.wv","*.wma","*.wav",
			".vox",".voc",".tta",".rf64",".raw",".rm",".ra",".opus",".oga",".nmf",".msv",".mpc",".mp3",
			".movpkg","*.mmf",".m4b",".m4a",".3gp",".aa","*aac.",".aax ",".act",".aiff",".aiff",".alac",
			".amr ",".ape",".au",".awb",".dss",".dvf ",".flac",".gsm ",".iklax",".ivs"};
	//The File is a Video
	if (containsStringArray(videoArray, extension)) {
		String video = "video";
		return video;
	}
	else if (containsStringArray(textArray, extension)) {
		String text = "text";
		return text;
	}

	else if (containsStringArray(pictureArry, extension)) {
		String picture ="picture";
		return picture;
	}

	else if (containsStringArray(audioArray, extension)) {
		String audio = "audio";
		return audio;
	}
	String none = "none";
		return none;
	}

	private boolean containsStringArray(String[] array,String string) {
		boolean contains = false;
		for (String s : array) {
			if( s== string) {
				contains= true;
				return contains;
			}	
		}
		return contains;

	}

}
