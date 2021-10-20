package C7.Controller;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;
import C7.View.IView;
import C7.View.ViewFactory;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Objects;

public class LayersController extends AnchorPane {
    @FXML
    TableView<Integer> tableView;

    @FXML TableColumn columnShowHide;
    @FXML TableColumn columnPreview;
    @FXML TableColumn columnName;


    private IProject project;

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    private static final int THUMBNAIL_WIDTH = 100;
    private static final int THUMBNAIL_HEIGHT = 70;

    public LayersController(AnchorPane parent, IProject project) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/LayersView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        parent.getChildren().add(this);

        this.project = project;

        columnShowHide.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Integer, CheckBox>, ObservableValue<CheckBox>>) arg0 -> {
            ILayer layer = project.getLayer(arg0.getValue());

            CheckBox checkBox = new CheckBox();

            ///TODO: checkBox.selectedProperty().setValue() true/false depending on whether layer is visible


            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean old_val, Boolean new_val) {

                    ///TODO: Set layer as visible


                }
            });

            return new SimpleObjectProperty<CheckBox>(checkBox);

        });

        columnPreview.setPrefWidth(THUMBNAIL_WIDTH);

        columnPreview.setCellFactory(v -> new TableCell<Integer, Canvas>() {

            @Override
            protected void updateItem(Canvas item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setGraphic(item);
                }
            }

        });

        columnPreview.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Integer, Canvas>, ObservableValue<Canvas>>) arg0 -> {
            Canvas canvas = new Canvas(project.getWidth(), project.getHeight());

            double xscale = (double)THUMBNAIL_WIDTH/project.getWidth();
            double yscale = (double)THUMBNAIL_HEIGHT/project.getHeight();
            double scale;

            if (xscale > yscale) {
                scale = yscale;
            } else {
                scale = xscale;
            }


            canvas.setScaleX(scale);
            canvas.setScaleY(scale);

            canvas.setTranslateX(canvas.getWidth()*(scale/2-1d/2));
            canvas.setTranslateY(canvas.getHeight()*(scale/2-1d/2));

            ILayer layer = project.getLayer(arg0.getValue());
            IView view = ViewFactory.createView(layer);
            view.setGraphicsContext(canvas.getGraphicsContext2D());
            view.setBounds(canvas.widthProperty(), canvas.heightProperty());
            view.render();
            return new SimpleObjectProperty<Canvas>(canvas);
        });

        //columnName.setCellValueFactory(new SimpleStringProperty());

        updateLayers();

        tableView.setFixedCellSize(THUMBNAIL_HEIGHT);

        tableView.setRowFactory(tv -> {
            TableRow<Integer> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                project.setActiveLayer(row.getItem());
            });

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
                    int draggedLayer = tableView.getItems().remove(draggedIndex);

                    int dropIndex ;

                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableView.getItems().add(dropIndex, draggedLayer);
                    System.out.println("Dropped at " + (tableView.getItems().size()-dropIndex-1));
                    project.setLayerIndex(draggedLayer, tableView.getItems().size()-dropIndex-1);



                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row ;
        });

    }

    public void setIProject(IProject project){
        Objects.requireNonNull(project);
        this.project = project;
    }

    public void updateLayers() {
        tableView.getItems().clear();

        for (int id:
             project.getAllLayerIds()) {
            tableView.getItems().add(0, id);
        }
    }
}
