package C7.Controller;

import C7.Model.Layer.ILayer;
import C7.Model.Layer.Layer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;

public class LayersController extends AnchorPane {
    @FXML
    TableView<ILayer> tableView;

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    public LayersController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LayersView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        TableColumn showhide = tableView.getColumns().get(0);

        showhide.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Layer, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Layer, CheckBox> arg0) {
                Layer layer = arg0.getValue();

                CheckBox checkBox = new CheckBox();

                ///TODO: checkBox.selectedProperty().setValue() true/false depending on whether layer is selected



                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {

                        ///TODO: Set layer as selected
                        ///uncheck the other layers' boxes somehow

                    }
                });

                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });

        TableColumn thumbnail = tableView.getColumns().get(1); //Definitely change this, give them an fxid or something //Actually maybe it doesn't matter idk

        thumbnail.setCellFactory(param -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(50);

            //Set up the Table
            TableCell<Layer, Image> cell = new TableCell<Layer, Image>() {
                public void updateItem(Image item, boolean empty) {
                    if (item != null) {
                        imageview.setImage(item);
                    }
                }
            };

            // Attach the imageview to the cell
            cell.setGraphic(imageview);

            return cell;
        });
/*
        thumbnail.setCellValueFactory(new Callback< TableColumn.CellDataFeatures<Layer, Image>, ObservableValue<Layer> >() {
            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Layer, Image> arg0) {
        });

        Layer testLayer = new Layer(600, 400, new C7.Model.Color(0, 0, 0, 0));
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                testLayer.setPixel(i, j, new Color(0,1,0,1));
            }
        }

        tableView.getItems().addAll(
                testLayer,
                new Layer(600, 400, new C7.Model.Color(0, 0, 0, 0)),
                new Layer(600, 400, new C7.Model.Color(0, 0, 0, 0))
                );

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
*/

    }
}
