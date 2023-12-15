package Organizer;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
	
	private final ExtensionFilter vidoFilter = new  ExtensionFilter("Video File","*.mp4","*.M4P","*.M4V","*.mov","*.QT","*.ogg","*.WEBM","*.MPG",
			"*.MP2","*.MPEG","*.MPE","*.MPV","*.AVI"," *.WMV","*.FLV", "*.SWF","*.AVCHD");
	private Image videoIcon = new Image("C:\\Users\\ToureRa\\Pictures\\Video_Icon.png");
	private Image nullIcon = new Image("C:\\Users\\ToureRa\\Pictures\\Null_Icon.png");

	private ArrayList<HBox> hboxFileList = new ArrayList<HBox>();

	private VBox vboxFiles;

	



	
	
	
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
		fileButtons.getChildren().addAll(btnLoadCollection,btnSaveCollection,btnAddFile,btnRemoveFile);
	
		
		VBox searchBarSpace = new VBox();
		TextField searchBar = new TextField();
		searchBarSpace.getChildren().addAll(searchBar);
		
		HBox inExClude = new HBox();
		VBox includeSide = new VBox();
		VBox excludeSide = new VBox();
		Label lblInclude = new Label("Include");
		Label lblExclude = new Label("Exclude");
		ScrollPane includePane = buildScrollPane();
		includePane.setPrefViewportHeight(100);
		includePane.setPrefViewportWidth(200);
		ScrollPane excludePane = buildScrollPane();
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
		ScrollPane attributeScroll = buildScrollPane();
		attributeScroll.setPrefViewportHeight(350);
		attributeSpace.getChildren().addAll(attributeScroll);
		
		centerPiece.getChildren().addAll(fileButtonSpace,searchBarSpace,inExClude,filterPane,attributeSpace);
		btnAddFile.setOnAction(ev ->doLoadFileItem());
		listenStageSize();
		return centerPiece;
	}


	private Node buildLeftPane() {
		StackPane fileSpace = new StackPane();
		fileSpace.setAlignment(Pos.TOP_LEFT);
	    vboxFiles = new VBox();
		HBox hboxFiles = new HBox();
		hboxFileList.add(hboxFiles);
		hboxFiles.setSpacing(3);
		Label lblFiles = new Label("File Items");
		ScrollPane fileScrollArea = new ScrollPane(vboxFiles);
		//fileScrollArea.setPrefViewportHeight(500);
		//fileScrollArea.setPrefViewportWidth(500);
		
		
		Rectangle fileBackground = new Rectangle();
		fileBackground.setWidth(300);;
		fileBackground.setFill(Color.AQUA);
		
		VBox add = new VBox();
		Label addMore = new Label("Add More");
		StackPane addFile = new StackPane();
		Rectangle addBackround = new Rectangle();
		addBackround.setFill(Color.SEASHELL);
		addBackround.setHeight(50);
		addBackround.setWidth(50);
		Rectangle vPlus = new Rectangle();
		vPlus.setFill(Color.GREENYELLOW);
		vPlus.setHeight(40);
		vPlus.setWidth(10);
		Rectangle hPlus = new Rectangle();
		hPlus.setFill(Color.GREENYELLOW);
		hPlus.setHeight(10);
		hPlus.setWidth(40);
		addFile.getChildren().addAll(addBackround,vPlus,hPlus);
		
		fileSpace.getChildren().addAll(fileBackground,fileScrollArea);
		vboxFiles.getChildren().addAll(lblFiles, hboxFiles);
		add.getChildren().addAll(addFile,addMore);
		hboxFiles.getChildren().addAll(add);
		return fileSpace;
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
	
	private int countHBoxContant(HBox buttonContainer) {
		int count = 0;
		for (Node childe : buttonContainer.getChildren() ) {
			count++;
		}
		return count;

	}
	
	private ScrollPane buildScrollPane() {
		HBox hBox = new HBox();
		VBox vBox = new VBox(hBox);
		
		ScrollPane scrollPane = new ScrollPane(vBox);
		vBox.setFocusTraversable(true);
		scrollPane.setFitToWidth(true);
		return scrollPane;
	}
	
	private int switchSrollRow() {
		int index;
	if (vboxFiles.getChildren().size()== 0) {
		index = 0;
	}
	else index =
		
		if(hboxFileList.get(index).getChildren().size()== 6) {
				index++;
				hboxFileList.add(new HBox());
				vboxFiles.getChildren().addAll(hboxFileList.get(index));
				return index;
			}
		else return index;
		}
		
	
	private void addFileToWindow(Image icon,String name) {
		VBox fileItem = new VBox();
		ImageView fileIcon = new ImageView();
		fileIcon.setImage(icon);
		fileIcon.setFitHeight(50);
		fileIcon.setFitWidth(50);
		Label fileName = new Label("name");
		fileItem.getChildren().addAll(fileIcon,fileName);
		fileItem.setAlignment(Pos.CENTER);
		hboxFileList.get(switchSrollRow()).getChildren().add(fileItem);
		
		
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
		if(nullIcon != null && videoIcon != null) {
			Image imageFile = nullIcon;
			FileChooser chooserFile = new FileChooser();
			chooserFile.setTitle("Save your Collection");
		chooserFile.getExtensionFilters().addAll(vidoFilter);
			if(chooserFile.getSelectedExtensionFilter()==vidoFilter) {
				 imageFile = videoIcon;
			};
			File addedFile = chooserFile.showOpenDialog(primaryStage);
			String fileName = addedFile.getName();
			addFileToWindow(imageFile,fileName);	
		}
		
		if(nullIcon==null) {
			Alert alertNullIcon = new Alert(AlertType.ERROR,"Warning Null_Icon.png is missing");
			if(alertNullIcon.getResult() == ButtonType.OK) 
				return;
			
			
	if(videoIcon==null) {
			Alert alertVideoIcon = new Alert(AlertType.ERROR,"Warning Null_Icon.png is missing");
				if(alertVideoIcon.getResult() == ButtonType.OK) 
				return;;
			
	}
	}
		}

}
