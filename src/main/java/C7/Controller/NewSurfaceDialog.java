package C7.Controller;

import C7.Util.Tuple2;
import com.sun.javafx.scene.control.skin.resources.ControlResources;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class NewSurfaceDialog extends Dialog<Tuple2<Integer, Integer>> {
    private final GridPane grid;
    private final Label labelx;
    private final TextField textFieldx;
    private final Label labely;
    private final TextField textFieldy;
    private final Tuple2 defaultValue;

    public NewSurfaceDialog(int defx, int defy) {
        DialogPane dialogPane = this.getDialogPane();
        this.textFieldx = new TextField(""+defx);
        this.textFieldy = new TextField(""+defy);
        this.textFieldx.setMaxWidth(1.7976931348623157E308D);
        this.textFieldy.setMaxWidth(1.7976931348623157E308D);
        GridPane.setHgrow(this.textFieldx, Priority.ALWAYS);
        GridPane.setFillWidth(this.textFieldx, true);
        GridPane.setHgrow(this.textFieldy, Priority.ALWAYS);
        GridPane.setFillWidth(this.textFieldy, true);
        this.labelx = new Label("Width");
        this.labelx.setPrefWidth(-1.0D);
        this.labely = new Label("Height");
        this.labely.setPrefWidth(-1.0D);
        this.defaultValue = new Tuple2(defx, defy);
        this.grid = new GridPane();
        this.grid.setHgap(10.0D);
        this.grid.setMaxWidth(1.7976931348623157E308D);
        this.grid.setAlignment(Pos.CENTER_LEFT);
        dialogPane.contentTextProperty().addListener((var1x) -> {
            this.updateGrid();
        });
        //this.setTitle(ControlResources.getString("Dialog.confirm.title"));
        //dialogPane.setHeaderText(ControlResources.getString("Dialog.confirm.header"));
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(new ButtonType[]{ButtonType.OK, ButtonType.CANCEL});
        this.updateGrid();
        this.setResultConverter((var1x) -> {
            ButtonData var2 = var1x == null ? null : var1x.getButtonData();
            return var2 == ButtonData.OK_DONE ? new Tuple2<>(Integer.parseInt(this.textFieldx.getText()), Integer.parseInt(this.textFieldy.getText())) : null;
        });
    }

    public final TextField getEditor() {
        return this.textFieldx;
    }

    public final Tuple2 getDefaultValue() {
        return this.defaultValue;
    }

    private void updateGrid() {
        this.grid.getChildren().clear();
        this.grid.add(this.labelx, 0, 0);
        this.grid.add(this.textFieldx, 1, 0);
        this.grid.add(this.labely, 0, 1);
        this.grid.add(this.textFieldy, 1, 1);
        this.getDialogPane().setContent(this.grid);
        Platform.runLater(() -> {
            this.textFieldx.requestFocus();
        });
    }
}
