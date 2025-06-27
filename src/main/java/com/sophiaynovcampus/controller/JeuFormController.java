package com.sophiaynovcampus.controller;

import com.sophiaynovcampus.dao.JeuVideoDAO;
import com.sophiaynovcampus.dao.SupportDAO;
import com.sophiaynovcampus.model.JeuVideo;
import com.sophiaynovcampus.model.Support;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class JeuFormController {
    @FXML private TextField titreField;
    @FXML private TextField editeurField;
    @FXML private TextField developpeurField;
    @FXML private TextField anneeField;
    @FXML private ComboBox<Support> supportCombo;
    @FXML private TextField noteField;
    @FXML private TextField jaquetteField;
    @FXML private ImageView jaquettePreview;

    private JeuVideo jeuVideo;
    private final JeuVideoDAO jeuVideoDAO = new JeuVideoDAO();
    private final SupportDAO supportDAO = new SupportDAO();

    @FXML
    public void initialize() {
        supportCombo.setItems(FXCollections.observableArrayList(supportDAO.findAll()));
        // Ajout du listener pour la prévisualisation
        jaquetteField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateJaquettePreview(newVal);
        });
    }

    public void setJeuVideo(JeuVideo jeu) {
        this.jeuVideo = jeu;
        if (jeu != null) {
            titreField.setText(jeu.getTitre());
            editeurField.setText(jeu.getEditeur());
            developpeurField.setText(jeu.getDeveloppeur());
            anneeField.setText(jeu.getAnneeSortie() != null ? jeu.getAnneeSortie().toString() : "");
            supportCombo.setValue(jeu.getSupport());
            noteField.setText(jeu.getNoteMetacritic() != null ? jeu.getNoteMetacritic().toString() : "");
            jaquetteField.setText(jeu.getJaquette());
        }
    }

    @FXML
    private void onValider() {
        // Validation simple
        String titre = titreField.getText().trim();
        if (titre.isEmpty()) {
            showAlert("Le titre est obligatoire.");
            return;
        }
        Support support = supportCombo.getValue();
        if (support == null) {
            showAlert("Le support est obligatoire.");
            return;
        }
        Integer annee = null;
        try {
            if (!anneeField.getText().trim().isEmpty())
                annee = Integer.parseInt(anneeField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("L'année doit être un nombre entier.");
            return;
        }
        Integer note = null;
        try {
            if (!noteField.getText().trim().isEmpty())
                note = Integer.parseInt(noteField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("La note doit être un nombre entier.");
            return;
        }
        // Création et sauvegarde
        JeuVideo jeu = new JeuVideo();
        jeu.setTitre(titre);
        jeu.setEditeur(editeurField.getText().trim());
        jeu.setDeveloppeur(developpeurField.getText().trim());
        jeu.setAnneeSortie(annee);
        jeu.setSupport(support);
        jeu.setNoteMetacritic(note);
        jeu.setJaquette(jaquetteField.getText().trim());
        jeuVideoDAO.save(jeu);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Jeu ajouté avec succès !", ButtonType.OK);
        alert.showAndWait();
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void onAnnuler() {
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onAjouterSupport() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter un support");
        dialog.setHeaderText(null);
        dialog.setContentText("Nom du support :");
        dialog.getEditor().setPromptText("Ex : PlayStation 5");
        dialog.showAndWait().ifPresent(nom -> {
            nom = nom.trim();
            if (!nom.isEmpty()) {
                Support support = new Support(nom);
                supportDAO.save(support);
                // Rafraîchir la liste
                supportCombo.setItems(FXCollections.observableArrayList(supportDAO.findAll()));
                supportCombo.setValue(support);
            } else {
                showAlert("Le nom du support ne peut pas être vide.");
            }
        });
    }

    private void updateJaquettePreview(String path) {
        if (path == null || path.trim().isEmpty()) {
            jaquettePreview.setImage(null);
            return;
        }
        try {
            Image img;
            if (path.startsWith("http")) {
                img = new Image(path, true);
            } else {
                img = new Image("file:" + path, true);
            }
            jaquettePreview.setImage(img);
        } catch (Exception e) {
            jaquettePreview.setImage(null); // ou une image par défaut
        }
    }
} 