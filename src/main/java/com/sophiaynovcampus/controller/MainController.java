package com.sophiaynovcampus.controller;

import com.sophiaynovcampus.dao.JeuVideoDAO;
import com.sophiaynovcampus.model.JeuVideo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;

public class MainController {
    @FXML private TableView<JeuVideo> tableJeux;
    @FXML private TableColumn<JeuVideo, String> colTitre;
    @FXML private TableColumn<JeuVideo, String> colEditeur;
    @FXML private TableColumn<JeuVideo, String> colDeveloppeur;
    @FXML private TableColumn<JeuVideo, Integer> colAnnee;
    @FXML private TableColumn<JeuVideo, String> colSupport;
    @FXML private TableColumn<JeuVideo, Integer> colNote;
    @FXML private TableColumn<JeuVideo, String> colJaquette;
    @FXML private TextField searchField;
    @FXML private FlowPane cardsPane;

    private final JeuVideoDAO jeuVideoDAO = new JeuVideoDAO();
    private ObservableList<JeuVideo> jeuxList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTitre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitre()));
        colEditeur.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEditeur()));
        colDeveloppeur.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDeveloppeur()));
        colAnnee.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getAnneeSortie()));
        colSupport.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            data.getValue().getSupport() != null ? data.getValue().getSupport().getNom() : ""));
        colNote.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getNoteMetacritic()));
        colJaquette.setCellFactory(column -> new TableCell<JeuVideo, String>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitHeight(60);
                imageView.setFitWidth(45);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.trim().isEmpty()) {
                    setGraphic(null);
                } else {
                    Image img;
                    if (item.startsWith("http")) {
                        img = new Image(item, true);
                    } else {
                        img = new Image("file:" + item, true);
                    }
                    imageView.setImage(img);
                    setGraphic(imageView);
                }
            }
        });
        colJaquette.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getJaquette()));
        chargerJeux();
    }

    private void chargerJeux() {
        List<JeuVideo> jeux = jeuVideoDAO.findAll();
        jeux.removeIf(Objects::isNull);
        jeuxList.setAll(jeux);
        tableJeux.setItems(jeuxList);
        tableJeux.refresh();
    }

    @FXML
    private void onAjouter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/JeuForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un jeu vidéo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            chargerJeux();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ouverture du formulaire d'ajout : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onModifier() {
        JeuVideo jeuSelectionne = tableJeux.getSelectionModel().getSelectedItem();
        if (jeuSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un jeu à modifier.");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/JeuForm.fxml"));
            Parent root = loader.load();
            JeuFormController controller = loader.getController();
            controller.setJeuVideo(jeuSelectionne);
            Stage stage = new Stage();
            stage.setTitle("Modifier un jeu vidéo");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            chargerJeux();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ouverture du formulaire de modification : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onSupprimer() {
        JeuVideo jeuSelectionne = tableJeux.getSelectionModel().getSelectedItem();
        if (jeuSelectionne == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un jeu à supprimer.");
            alert.showAndWait();
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce jeu ?", ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            jeuVideoDAO.delete(jeuSelectionne);
            chargerJeux();
        }
    }

    @FXML
    private void onSearch() {
        String recherche = searchField.getText().trim().toLowerCase();
        if (recherche.isEmpty()) {
            tableJeux.setItems(jeuxList);
        } else {
            ObservableList<JeuVideo> jeuxFiltres = FXCollections.observableArrayList();
            for (JeuVideo jeu : jeuxList) {
                if (jeu.getTitre() != null && jeu.getTitre().toLowerCase().contains(recherche)) {
                    jeuxFiltres.add(jeu);
                }
            }
            tableJeux.setItems(jeuxFiltres);
        }
        tableJeux.refresh();
    }

    @FXML
    private void onExporterCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la collection en CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));
        Stage stage = (Stage) tableJeux.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                // En-tête CSV
                writer.write("Titre;Editeur;Développeur;Année;Support;Note;Jaquette\n");
                for (JeuVideo jeu : tableJeux.getItems()) {
                    writer.write(String.format("%s;%s;%s;%s;%s;%s;%s\n",
                        jeu.getTitre(),
                        jeu.getEditeur(),
                        jeu.getDeveloppeur(),
                        jeu.getAnneeSortie() != null ? jeu.getAnneeSortie() : "",
                        jeu.getSupport() != null ? jeu.getSupport().getNom() : "",
                        jeu.getNoteMetacritic() != null ? jeu.getNoteMetacritic() : "",
                        jeu.getJaquette() != null ? jeu.getJaquette() : ""
                    ));
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Export CSV réussi !", ButtonType.OK);
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'export : " + e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
} 