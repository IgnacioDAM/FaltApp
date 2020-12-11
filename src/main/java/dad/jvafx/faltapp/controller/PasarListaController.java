package dad.jvafx.faltapp.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.javafx.faltapp.model.Alumno;
import dad.javafx.faltapp.model.Asistente;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.BorderPane;

public class PasarListaController implements Initializable {

	// model
	private ListProperty<Asistente> asistentes = new SimpleListProperty<>(FXCollections.observableArrayList());

	// view
	@FXML
	private BorderPane view;

	@FXML
	private DatePicker fechaPicker;

	@FXML
	private CheckBox todasCheck;

	@FXML
	private CheckBox primeraCheck;

	@FXML
	private CheckBox segundaCheck;

	@FXML
	private CheckBox terceraCheck;

	@FXML
	private CheckBox cuartaCheck;

	@FXML
	private CheckBox quintaCheck;

	@FXML
	private CheckBox sextaCheck;

	@FXML
	private Button btnPonerFaltas;

	@FXML
	private TableView<Asistente> faltasTable;

	@FXML
	private TableColumn<Asistente, Alumno> nombreCompletoColumn;

	@FXML
	private TableColumn<Asistente, Boolean> faltaColumn;

	@FXML
	private TableColumn<Asistente, Boolean> retrasoColumn;
	
	public PasarListaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nombreCompletoColumn.setCellValueFactory(v -> v.getValue().alumnoProperty());
		faltaColumn.setCellValueFactory(v -> v.getValue().faltaProperty());
		retrasoColumn.setCellValueFactory(v -> v.getValue().retrasoProperty());

		faltaColumn.setCellFactory(CheckBoxTableCell.forTableColumn(faltaColumn));
		retrasoColumn.setCellFactory(CheckBoxTableCell.forTableColumn(retrasoColumn));
		
		this.asistentes.addListener((o, ov, nv) -> onAsistentesChanged(o, ov, nv));
	}

	private void onAsistentesChanged(ObservableValue<? extends ObservableList<Asistente>> o,
			ObservableList<Asistente> ov, ObservableList<Asistente> nv) {
		if (ov != null) {
			faltasTable.setItems(null);
			btnPonerFaltas.disableProperty().unbind();
		}

		if (nv != null) {
			//faltasTable.setItems((((Object) nv).getAsistente();
			btnPonerFaltas.disableProperty().bind(Bindings.isEmpty(faltasTable.getItems()));
		}
	}

	@FXML
	void onClickPonerFaltas(ActionEvent event) {

	}
	
	public BorderPane getView() {
		return this.view;
	}
	
	public final ListProperty<Asistente> asistentesProperty() {
		return this.asistentes;
	}

	public final ObservableList<Asistente> getAsistentes() {
		return this.asistentesProperty().get();
	}

	public final void setAsistentes(final ObservableList<Asistente> asistentes) {
		this.asistentesProperty().set(asistentes);
	}

}
