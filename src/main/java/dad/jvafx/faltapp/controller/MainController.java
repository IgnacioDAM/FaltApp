package dad.jvafx.faltapp.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonSyntaxException;

import dad.javafx.faltapp.model.FaltApp;
import dad.javafx.faltapp.model.Grupo;
import dad.javafx.faltapp.App;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	//controllers
	private GrupoController grupoController = new GrupoController();
	private PasarListaController pasarListaController = new PasarListaController();
	
	// model
	private ObjectProperty<FaltApp> main = new SimpleObjectProperty<>();

	// view
	@FXML
	private BorderPane view;

	@FXML
	private Button btnNuevo;

	@FXML
	private ImageView imgNuevo;

	@FXML
	private Button btnAbrir;

	@FXML
	private ImageView imgAbrir;

	@FXML
	private Button btnGuardar;

	@FXML
	private ImageView imgGuardar;

	@FXML
	private Button btnSalir;

	@FXML
	private ImageView imgSalir;

	@FXML
	private Tab grupoTab;

	@FXML
	private Tab pasarListaTab;

	public MainController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		grupoTab.setContent(grupoController.getView());
		pasarListaTab.setContent(pasarListaController.getView());

		main.addListener((o, ov, nv) -> onMainChanged(o, ov, nv));

		main.set(new FaltApp());
	}

	private void onMainChanged(ObservableValue<? extends FaltApp> o, FaltApp ov, FaltApp nv) {
		if (ov != null) {
			grupoController.grupoProperty().unbind();
			pasarListaController.asistentesProperty().unbind();
		}

		if (nv != null) {
			grupoController.grupoProperty().bind(nv.gruposProperty());
			pasarListaController.asistentesProperty().bind(nv.asistenteProperty());
		}
	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onClickAbrir(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Abrir faltas");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Faltas (*.faltas)", "*.faltas"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
		File faltaFile = fileChooser.showOpenDialog(App.getPrimaryStage());
		if (faltaFile != null) {

			try {
				main.set(JSONUtils.fromJson(faltaFile, FaltApp.class));
				App.info("Se ha abierto el fichero " + faltaFile.getName() + " correctamente.", "Abierto");
			} catch (JsonSyntaxException | IOException e) {
				App.error("Ha ocurrido un error al abrir " + faltaFile, e.getMessage());
			}
		}
	}

	@FXML
	void onClickGuardar(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar un currículum");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Faltas (*.faltas)", "*.faltas"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos (*.*)", "*.*"));
		File faltaFile = fileChooser.showSaveDialog(App.getPrimaryStage());
		if (faltaFile != null) {

			try {
				JSONUtils.toJson(faltaFile, main.get());
			} catch (JsonSyntaxException | IOException e) {
				App.error("Ha ocurrido un error al guardar " + faltaFile, e.getMessage());
			}
		}
	}

	@FXML
	void onClickNuevo(ActionEvent event) {
		System.out.println("Nuevo");
		main.set(new FaltApp());
	}

	@FXML
	void onClickSalir(ActionEvent event) {
		Platform.exit();
	}
}
