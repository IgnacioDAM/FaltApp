package dad.jvafx.faltapp.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import dad.javafx.faltapp.model.Alumno;
import dad.javafx.faltapp.model.Falta;
import dad.javafx.faltapp.model.Grupo;
import dad.javafx.faltapp.App;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.converter.LocalDateStringConverter;

public class GrupoController implements Initializable {

	// model
	private ListProperty<Grupo> grupo = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Alumno> seleccionado = new SimpleObjectProperty<>();
	private ObjectProperty<Falta> seleccionadas = new SimpleObjectProperty<>();

	// view
	@FXML
	private GridPane view;

	@FXML
	private Button btnAñadir;

	@FXML
	private Button btnEliminar;

	@FXML
	private TextField tituloText;

	@FXML
	private Spinner<Integer> cursoSpinner;

	@FXML
	private TextField denominacionText;
	
	@FXML
	private TableView<Alumno> alumnosTable;

	@FXML
	private TableColumn<Alumno, String> cialColumn;

	@FXML
	private TableColumn<Alumno, String> nombreColumn;

	@FXML
	private TableColumn<Alumno, String> apellidosColumn;

	@FXML
	private TableColumn<Alumno, LocalDate> fechaNacColumn;

	@FXML
	private TableView<Falta> faltasTable;

	@FXML
	private TableColumn<Falta, LocalDate> diaColumn;

	@FXML
	private TableColumn<Falta, Number> horaColumn;

	@FXML
	private TableColumn<Falta, Boolean> retrasoColumn;

	public GrupoController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GrupoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cursoSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2));

		cialColumn.setCellValueFactory(v -> v.getValue().cialProperty());
		nombreColumn.setCellValueFactory(v -> v.getValue().nombreProperty());
		apellidosColumn.setCellValueFactory(v -> v.getValue().apellidosProperty());
		fechaNacColumn.setCellValueFactory(v -> v.getValue().fechaNacimientoProperty());

		diaColumn.setCellValueFactory(v -> v.getValue().fechaProperty());
		horaColumn.setCellValueFactory(v -> v.getValue().horaProperty());
		retrasoColumn.setCellValueFactory(v -> v.getValue().retrasoProperty());

		cialColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		apellidosColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		fechaNacColumn
				.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(FormatStyle.SHORT)));

		diaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		retrasoColumn.setCellFactory(CheckBoxTableCell.forTableColumn(retrasoColumn));

		this.grupo.addListener((o, ov, nv) -> onGrupoChanged(o, ov, nv));
	}

	private void onGrupoChanged(ObservableValue<? extends ObservableList<Grupo>> o, ObservableList<Grupo> ov,
			ObservableList<Grupo> nv) {
		if (ov != null) {
			alumnosTable.setItems(null);
			faltasTable.setItems(null);
			seleccionado.unbind();
			btnEliminar.disableProperty().unbind();
		}

		if (nv != null) {
			alumnosTable.setItems(((Grupo) nv).getAlumnos());
			seleccionado.bind(alumnosTable.getSelectionModel().selectedItemProperty());
			faltasTable.setItems(((Alumno) nv).getFaltas());
			btnEliminar.disableProperty().bind(Bindings.isEmpty(alumnosTable.getItems()));
		}
	}

	@FXML
	void onClickAñadirAlumno(ActionEvent event) {
		
	}

	@FXML
	void onClickEliminarAlumno(ActionEvent event) {
		String title = "Eliminar Alumno";
		String header = "Confirmar eliminacion";
		String content = "¿Está seguro de borrar el alumno?";
		Alumno alumnoSelec = seleccionado.get();

		if (grupo != null && App.confirm(title, header, content)) {
			grupo.get().remove(alumnoSelec);
		}
	}

	@FXML
	void onFaltasTableKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.DELETE)) {
			Falta faltasSelec = seleccionadas.get();
			grupo.get().remove(faltasSelec);
		}
	}

	public GridPane getView() {
		return this.view;
	}

	public final ListProperty<Grupo> grupoProperty() {
		return this.grupo;
	}

	public final ObservableList<Grupo> getGrupo() {
		return this.grupoProperty().get();
	}

	public final void setGrupo(final ObservableList<Grupo> grupo) {
		this.grupoProperty().set(grupo);
	}

}
