package C7.Controller;

import C7.Util.Color;
import C7.Model.IObserver;
import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import C7.Model.Project;
import C7.Util.Tuple2;
import C7.Util.Vector2D;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;

public class LayersController extends AnchorPane {
    @FXML
    TableView<ILayer> tableView;

    private Project project;

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    public LayersController(Project project) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LayersView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.project = project;

        TableColumn showhide = tableView.getColumns().get(0);

        showhide.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Layer, CheckBox>, ObservableValue<CheckBox>>) arg0 -> {
            Layer layer = arg0.getValue();

            CheckBox checkBox = new CheckBox();

            ///TODO: checkBox.selectedProperty().setValue() true/false depending on whether layer is selected
            ///Later note: Did I mean visible?
            ///I have to have meant visible



            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean old_val, Boolean new_val) {

                    ///TODO: Set layer as visible


                }
            });

            return new SimpleObjectProperty<CheckBox>(checkBox);

        });

        TableColumn thumbnail = tableView.getColumns().get(1); //Definitely change this, give them an fxid or something //Actually maybe it doesn't matter idk

        /*thumbnail.setCellFactory(param -> {
            final Canvas canvas = new Canvas();

            //Set up the Table
            TableCell<Layer, Canvas> cell = new TableCell<Layer, Canvas>() {
                public void updateItem(Layer layer) {
                    PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
                    for (int y = 0; y < layer.getHeight(); y++) {
                        for (int x = 0; x < layer.getWidth(); x++) {
                            // Note, we need to change the color type from C7 color to JavaFX color.
                            Color color = layer.getLocalPixel(x, y);
                            pw.setColor(x, y, new javafx.scene.paint.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                        }
                    }
                }
            };

            // Attach the canvas to the cell
            cell.setGraphic(canvas);

            return cell;
        });*/

        thumbnail.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Layer, Canvas>, ObservableValue<Canvas>>) arg0 -> {
            Canvas canvas = new Canvas();

            ILayer layer = arg0.getValue();
            layer.addObserver(new IObserver<Tuple2<Vector2D, Vector2D>>() {
                @Override
                public void notify(Tuple2<Vector2D, Vector2D> data) {
                    PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
                    for (int y = 0; y < layer.getHeight(); y++) {
                        for (int x = 0; x < layer.getWidth(); x++) {
                            // Note, we need to change the color type from C7 color to JavaFX color.
                            Color color = layer.getLocalPixel(x, y);
                            pw.setColor(x, y, new javafx.scene.paint.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                        }
                    }
                }
            });

            return new SimpleObjectProperty<Canvas>(canvas);
        });

        Layer testLayer = new Layer(600, 400, new Color(0, 0, 0, 0));
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                testLayer.setLocalPixel(i, j, new Color(0,1,0,1));
            }
        }

        updateLayers();

        tableView.setRowFactory(tv -> {
            TableRow<ILayer> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != (Integer) db.getContent(SERIALIZED_MIME_TYPE)) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    ILayer draggedLayer = tableView.getItems().remove(draggedIndex);

                    int dropIndex ;

                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableView.getItems().add(dropIndex, draggedLayer);

                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row ;
        });

    }

    private void updateLayers() {
        tableView.getItems().clear();

        for (int id:
             project.getAllLayerIds()) {
            tableView.getItems().add(project.getLayer(id));
        }
    }
}
