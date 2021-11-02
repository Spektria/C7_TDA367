package C7.Controller;

import C7.Model.IProject;
import C7.Model.Layer.ILayer;
import C7.Model.ProjectFactory;
import C7.Util.Vector2D;
import C7.View.IView;
import C7.View.ViewFactory;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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

/**
 * Controls a panel displaying a list of the layers in the current project, with controls to create/delete and show/hide layers.
 * @author Elias Ersson
*/
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

        parent.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                tableView.setPrefHeight(number.doubleValue()-25);
            }
        });

        this.project = project;

        columnShowHide.setCellFactory( new Callback< TableColumn<Integer, CheckBox>, TableCell<Integer, CheckBox>> ()
        {

            @Override
            public TableCell<Integer, CheckBox> call(TableColumn<Integer, CheckBox> p) {
                TableCell<Integer, CheckBox> tc =
                        new TableCell<Integer, CheckBox>() {
                            @Override
                            protected void updateItem(CheckBox item, boolean empty) {
                                super.updateItem(item, empty);
                                if (!empty) {
                                    setGraphic(item);
                                } else {
                                    setGraphic(null);
                                }
                            }
                        };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }

        });

        columnShowHide.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Integer, CheckBox>, ObservableValue<CheckBox>>) arg0 -> {
            int layer = arg0.getValue();

            CheckBox checkBox = new CheckBox();

            checkBox.setSelected(project.getLayerVisibility(layer));


            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
                    project.setLayerVisibility(layer, new_val);
                }
            });

            return new SimpleObjectProperty<CheckBox>(checkBox);

        });

        columnPreview.setPrefWidth(THUMBNAIL_WIDTH);

        columnPreview.setCellFactory(v -> new TableCell<Integer, Canvas>() {

            @Override
            protected void updateItem(Canvas item, boolean empty) {
                if (!empty) {
                    setGraphic(item);
                } else {
                    setGraphic(null);
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

        columnName.setCellFactory( new Callback< TableColumn<Integer, String>, TableCell<Integer, String>> ()
        {

            @Override
            public TableCell<Integer, String> call(TableColumn<Integer, String> p) {
                TableCell<Integer, String> tc =
                        new TableCell<Integer, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {
                                if (!empty) {
                                    setText(item);
                                } else {
                                    setText(null);
                                }
                            }
                        };
                tc.setAlignment(Pos.CENTER_LEFT);
                return tc;
            }

        });

        columnName.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Integer, String>, ObservableValue<String>>) arg0 -> {
            return new SimpleStringProperty("Layer " + arg0.getValue());
        });

        updateLayers();

        tableView.setFixedCellSize(THUMBNAIL_HEIGHT);

        tableView.setRowFactory(tv -> {
            TableRow<Integer> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (row.getItem() != null) {
                    project.setActiveLayer(row.getItem());
                }
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
            if (id == project.getActiveLayerID()) {
                tableView.getSelectionModel().select(0);
            }
        }
    }

    @FXML
    private void newLayer() {
        NewSurfaceDialog dialog = new NewSurfaceDialog(project.getWidth(), project.getHeight());
        dialog.setTitle("New layer");
        dialog.setHeaderText("A new layer with the specified dimensions will be created");

        dialog.showAndWait()
                .ifPresent(result -> project.setActiveLayer(project.createLayer(result.getVal1(), result.getVal2(), Vector2D.ZERO)));

        updateLayers();
    }

    @FXML
    private void deleteLayer() {
        project.removeLayer(project.getActiveLayerID());
        updateLayers();
    }
}
