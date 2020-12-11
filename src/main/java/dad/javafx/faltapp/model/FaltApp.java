package dad.javafx.faltapp.model;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FaltApp {
	private ListProperty<Grupo> grupos = new SimpleListProperty<Grupo>(FXCollections.observableArrayList());
	private ListProperty<Asistente> asistente = new SimpleListProperty<Asistente>(FXCollections.observableArrayList());

	public static void main(String[] args) {

		FaltApp faltApp = new FaltApp();
		Gson gson = FxGson.fullBuilder().setPrettyPrinting().create();

		String json = gson.toJson(faltApp);

		System.out.println(json);

	}

	public final ListProperty<Grupo> gruposProperty() {
		return this.grupos;
	}

	public final ObservableList<Grupo> getGrupos() {
		return this.gruposProperty().get();
	}

	public final void setGrupos(final ObservableList<Grupo> grupos) {
		this.gruposProperty().set(grupos);
	}

	public final ListProperty<Asistente> asistenteProperty() {
		return this.asistente;
	}

	public final ObservableList<Asistente> getAsistente() {
		return this.asistenteProperty().get();
	}

	public final void setAsistente(final ObservableList<Asistente> asistente) {
		this.asistenteProperty().set(asistente);
	}

}
