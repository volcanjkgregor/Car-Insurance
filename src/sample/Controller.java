package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Date;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public MenuItem save_id;
    public MenuItem quit_id;
    public MenuItem delete_id;
    public MenuItem help_id;
    public ChoiceBox gorivo_id;
    public ColorPicker barva_id;
    public Spinner sedezi_id;
    public TextField ime_id;
    public TextField priimek_id;
    public TextField znamka_id;
    public TextField model_id;
    public TextField prostornina_id;
    public TextField moc_id;
    public TextField ulica_id;
    public TextField hisnast_id;
    public TextField kraj_id;
    public TextField postnast_id;
    public ToggleGroup izkusnje;
    public ToggleGroup osnovnoZav;
    public ToggleGroup kaskoZav;
    public CheckBox zavStekel;
    public CheckBox zavPark;
    public CheckBox zavPotnik;
    public CheckBox zavTretjaOs;
    public CheckBox zavGume;
    public CheckBox zavKraja;
    public CheckBox zavToca;
    public ToggleGroup vozilo;
    public TextField reg_kraj;
    public TextField reg_ozn;
    public DatePicker datum_1reg;
    public DatePicker datum_roj;
    public Label log;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gorivo_id.getItems().addAll("Bencin", "Dizel", "Elektrika", "Hibrid");
        sedezi_id.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }
    
    public void save(ActionEvent actionEvent) {
        if(vozilo.getSelectedToggle() == null) {
            log.setText("Prosim izberite tip vozila");
            return;
        }
        if(znamka_id.getText().equals("")) {
            log.setText("Prosim vpisite znamko vozila");
            return;
        }
        if(model_id.getText().equals("")) {
            log.setText("Prosim vpisite model vozila");
            return;
        }

        try {
            if (Integer.parseInt(prostornina_id.getText()) < 0) {
                log.setText("Prosim vpisite prostornino vozila");
                return;
            }
        } catch (Exception e) {
            log.setText("Prosim vpisite prostornino vozila v obliki stevilke");
            return;
        }

        try {
            if(Integer.parseInt(moc_id.getText()) < 0) {
                log.setText("Prosim vpisite moc vozila");
                return;
            }
        } catch (Exception e) {
            log.setText("Prosim vpisite moc vozila v obliki stevilke");
            return;
        }

        if(gorivo_id.getValue() == null) {
            log.setText("Prosim vnesite tip goriva");
            return;
        }
        if(barva_id.getValue() == null) {
            log.setText("Prosim vnesite barvo vozila");
            return;
        }

        /* sedezi so original na vrednost 1
        if(sedezi_id.getValue() == null) {
            log.setText("Prosim vnesite barvo vozila");
        }*/


        /////////////////////////////////////
        /////// Podatki o zavarovancu ///////
        /////////////////////////////////////

        if(ime_id.getText().equals("")) {
            log.setText("Prosim vpisite ime zavarovanca");
            return;
        }

        if(priimek_id.getText().equals("")) {
            log.setText("Prosim vpisite priimek zavarovanca");
            return;
        }

        if(datum_roj.getValue() == null) {
            log.setText("Prosim vnesite datum rojstva zavarovanca (in pritisnite tipko enter)");
            return;
        }

        if (datum_roj.getValue().getYear() > java.time.LocalDate.now().getYear() - 14) {
            log.setText("Preden se zavarujete morate dopolniti starost, kjer vam je dovoljeno da pričnete voziti motorna vozila");
            return;
        }

        if(datum_roj.getValue().getYear() > java.time.LocalDate.now().getYear() || datum_roj.getValue().getYear() < 1900) {
            log.setText("Prosim vstavite pravilen datum rojstva (in pritisnite tipko enter)");
            return;
        }

        if(ulica_id.getText().equals("")) {
            log.setText("Prosim vpisite ulico zavarovanca");
            return;
        }

        if(hisnast_id.getText().equals("")) {
            log.setText("Prosim vpisite hisno stevilko zavarovanca");
            return;
        }

        if(kraj_id.getText().equals("")) {
            log.setText("Prosim vpisite kraj zavarovanca");
            return;
        }

        try {
            if (Integer.parseInt(postnast_id.getText()) < 0 || Integer.parseInt(postnast_id.getText()) > 10000) {
                log.setText("Prosim vpisite pravilno postno stevilko");
                return;
            }
        } catch (Exception e) {
            log.setText("Prosim vnesite postno stevilko zavarovanca v obliki stevilke");
            return;
        }

        if(izkusnje.getSelectedToggle() == null) {
            log.setText("Prosim izberite izkusnjo zavarovanca");
            return;
        }

        /////////////////////////////////////
        /////// Podatki o zavarovanju ///////
        /////////////////////////////////////

        if(osnovnoZav.getSelectedToggle() == null) {
            log.setText("Prosim izberite eno izmed osnovnih zavarovanj");
            return;
        }

        if(kaskoZav.getSelectedToggle() == null) {
            log.setText("Prosim izberite eno izmed kasko zavarovanj");
            return;
        }

        ////////////////////////////////////////
        //////// Podatki o registraciji ////////
        ////////////////////////////////////////

        if(datum_1reg.getValue() == null) {
            log.setText("Prosim vnesite datum prve registracije (in pritisnite tipko enter)");
            return;
        }
        if(datum_1reg.getValue().getYear() > java.time.LocalDate.now().getYear() || datum_1reg.getValue().getYear() < 1950) {
            log.setText("Prosim vstavite pravilen datum prve registracije (in pritisnite tipko enter)");
            return;
        }

        if(reg_kraj.getText().equals("")) {
            log.setText("Prosim vpisite kraj prve registracije");
            return;
        }

        if(reg_ozn.getText().equals("")) {
            log.setText("Prosim vpisite oznako registracije");
            return;
        }

        log.setText("OK");

        FileChooser fc = new FileChooser();
        fc.setTitle("Izberi datoteko za shranjevanje");
        File f = fc.showSaveDialog(null);

        if (f != null)  {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                bw.write("Vozilo= " + vozilo.getSelectedToggle().toString().split("]")[1] + "\n");

                bw.write("Znamka= " + znamka_id.getText() + "\n");
                bw.write("Model= " + model_id.getText() + "\n");
                bw.write("Prostornina= " +prostornina_id.getText() + "\n");
                bw.write("Moc= " + moc_id.getText() + "\n");

                bw.write("Gorivo= " + gorivo_id.getValue().toString() + "\n");
                bw.write("Barva= " + barva_id.getValue().toString() + "\n");
                bw.write("St_sedezev= " + sedezi_id.getValue() + "\n");

                // Podatki o zavarovancu
                bw.write("Ime= " + ime_id.getText() + "\n");
                bw.write("Priimek= " + priimek_id.getText() + "\n");
                bw.write("Datum_rojstva= " + datum_roj.getValue() + "\n");
                bw.write("Ulica= " + ulica_id.getText() + "\n");
                bw.write("Hisna_stevilka= " + hisnast_id.getText() + "\n");
                bw.write("Kraj= " + kraj_id.getText() + "\n");
                bw.write("Postna_stevilka= " + postnast_id.getText() + "\n");
                bw.write("Izkusnje= " + izkusnje.getSelectedToggle().toString().split("]")[1] + "\n");

                // Podatki o zavarovanju
                bw.write("Osnovno_zavarovanje= " + osnovnoZav.getSelectedToggle().toString().split("]")[1] + "\n");
                bw.write("Kasko_zavarovanje= " + kaskoZav.getSelectedToggle().toString().split("]")[1] + "\n");

                bw.write("Zavarovanje_stekel= " + zavStekel.isSelected() + "\n");
                bw.write("Zavarovanje_na_parkiriscu= " + zavPark.isSelected() + "\n");
                bw.write("Zavarovanje_potnikov= " + zavPotnik.isSelected() + "\n");
                bw.write("Zavarovanje_tretje_osebe= " + zavTretjaOs.isSelected() + "\n");
                bw.write("Zavarovanje_gum= " + zavGume.isSelected() + "\n");
                bw.write("Zavarovanje_proti_kraji= " + zavKraja.isSelected() + "\n");
                bw.write("Zavarovanje_proti_toci= " + zavToca.isSelected() + "\n");

                // Podatki o registraciji
                bw.write("Datum_prve_registracije= " + datum_1reg.getValue() + "\n");
                bw.write("Kraj_prve_registracije= " + reg_kraj.getText() + "\n");
                bw.write("Registrska_oznacba= " + reg_ozn.getText() + "\n");

                log.setText("Datoteka je bila shranjena v: " + f.getAbsolutePath());

                bw.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void deleteAll(ActionEvent actionEvent) {

        if(!log.getText().equals("Ce ste prepricani da zelite vse izbrisati, ponovno aktivirajte \"pobrisi vse\"")){
            log.setText("Ce ste prepricani da zelite vse izbrisati, ponovno aktivirajte \"pobrisi vse\"");
            return;
        }

        vozilo.selectToggle(null);

        znamka_id.setText("");
        model_id.setText("");
        prostornina_id.setText("");
        moc_id.setText("");

        gorivo_id.setValue(null);
        barva_id.setValue(null);
        sedezi_id.getValueFactory().setValue(1);

        // Podatki o zavarovancu
        ime_id.setText("");
        priimek_id.setText("");
        datum_roj.setValue(null);

        ulica_id.setText("");
        hisnast_id.setText("");
        kraj_id.setText("");
        postnast_id.setText("");

        izkusnje.selectToggle(null);

        // Podatki o zavarovanju
        osnovnoZav.selectToggle(null);
        kaskoZav.selectToggle(null);

        zavStekel.setSelected(false);
        zavPark.setSelected(false);
        zavPotnik.setSelected(false);
        zavTretjaOs.setSelected(false);
        zavGume.setSelected(false);
        zavKraja.setSelected(false);
        zavToca.setSelected(false);

        // Podatki o registraciji

        datum_1reg.setValue(null);
        reg_kraj.setText("");
        reg_ozn.setText("");

        log.setText("Log:");
    }

    public void help(ActionEvent actionEvent) {
        log.setText("Program je napisal: Jan Milovanović");
    }

    public void quit(ActionEvent actionEvent) {
        /*
        Label secondLabel = new Label("Are you sure you want to quit?");
        Button yes = new Button("Yes");
        Button no = new Button("No");

        StackPane secondLayout = new StackPane();
        secondLayout.getChildren().add(secondLabel);
        secondLayout.getChildren().add(yes);
        secondLayout.getChildren().add(no);

        Scene scene2 = new Scene(secondLayout, 200, 200);

        Stage window2 = new Stage();
        window2.setTitle("Are you sure?");
        window2.setScene(scene2);

        window2.setX(1080 / 2);
        window2.setY(1920 / 2);


        window2.show();
         */

        if(!log.getText().equals("Ponovno izvedite akcijo, ce ste prepricani, da naj se izvede")){
            log.setText("Ponovno izvedite akcijo, ce ste prepricani, da naj se izvede");
            return;
        }

        System.exit(0);
    }
}
