package com.example.dentist;

import com.example.dentist.Casi.Caso;
import com.example.dentist.DataHandling.FileIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;


public class QuestionController implements Initializable {


//    private static final String BUTTON_CLASS = "<!-- HTML !-->\n" +
//            "<button class=\"button-1\" role=\"button\">Button 1</button>\n" +
//            "\n" +
//            "/* CSS */\n" +
//            ".button-1 {\n" +
//            "  background-color: #EA4C89;\n" +
//            "  border-radius: 8px;\n" +
//            "  border-style: none;\n" +
//            "  box-sizing: border-box;\n" +
//            "  color: #FFFFFF;\n" +
//            "  cursor: pointer;\n" +
//            "  display: inline-block;\n" +
//            "  font-family: \"Haas Grot Text R Web\", \"Helvetica Neue\", Helvetica, Arial, sans-serif;\n" +
//            "  font-size: 14px;\n" +
//            "  font-weight: 500;\n" +
//            "  height: 40px;\n" +
//            "  line-height: 20px;\n" +
//            "  list-style: none;\n" +
//            "  margin: 0;\n" +
//            "  outline: none;\n" +
//            "  padding: 10px 16px;\n" +
//            "  position: relative;\n" +
//            "  text-align: center;\n" +
//            "  text-decoration: none;\n" +
//            "  transition: color 100ms;\n" +
//            "  vertical-align: baseline;\n" +
//            "  user-select: none;\n" +
//            "  -webkit-user-select: none;\n" +
//            "  touch-action: manipulation;\n" +
//            "}\n" +
//            "\n" +
//            ".button-1:hover,\n" +
//            ".button-1:focus {\n" +
//            "  background-color: #F082AC;\n" +
//            "}";

    private static final String nome = "DOLORE DA PROTESI";
    private static final String descrizione = "Capire se il dolore è causato dalle protesi o se " +
            "si tratta di un disagio estetico e in tal caso sottolineare la sconvenienza di procedere alla rimozione. " +
            "Se la protesi non viene indossata nemmeno per poco tempo a causa del dolore dare più priorità al caso. " +
            "Trattare col dottore l'eventuale terapia pre-appuntamento in studio e specificare che il paziente dovrà indossare " +
            "la protesi almeno 2 ore prima dell'appuntamento per evidenziarne eventuali problemi";
    private static final String text= "Il seguente software è a scopo dimostrativo. L'obiettivo principale " +
            "è quello di facilitare una \"pre-diagnosi\" telefonica. Le sintomatologie non hanno una " +
            "corrispondenza 1:1 tra casi analoghi: dunque " +
            "l'utente finale NON deve prendere i risultati del quesito come assoluti e, " +
            "al contrario, interpretarli per ciò che sono, un'analisi probabilistica!";
    @FXML
    private Label QuestionLabel;

    @FXML
    private Text MenuText;

    @FXML
    private HBox insideTextHbox;
    @FXML
    private HBox myhbox;
    @FXML
    private VBox myvbox;


    @FXML
    private BorderPane borderPane;
    private ObservableList<Button> AnswerButtons = FXCollections.observableArrayList();

    int iterations = 0;
    private int questionIndex = 0;

    private Button mainMenubtn;
    private Button backButton;
    private int[] answerArray;

    private boolean hasProtesi = false;
    private Font ButtonFont;

    private Font LabelFont;

    public void QuestionController(){

    }

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonFont = new Font("Verdana", 22);
        try {
            FileIO Data = FileIO.getInstance();
            answerArray = new int[Data.numOfQuestions];
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        mainMenu();
    }

    public void mainMenu(){
        Arrays.stream(answerArray).forEach( el -> System.out.println(el));
        borderPane.setMaxHeight(Double.MAX_VALUE);
        borderPane.setMaxWidth(Double.MAX_VALUE);

        if(MenuText.getText() != text){
            MenuText.setText(text);
            borderPane.getChildren().remove(myvbox);
            borderPane.getChildren().remove(mainMenubtn);
            borderPane.setBottom(myvbox);
            borderPane.setCenter(MenuText);
        }
        Button btn = new Button("INIZIARE");
        QuestionLabel.setText("Benvenuto!");
        MenuText.setText(QuestionController.text);
        myhbox.setAlignment(Pos.CENTER);
        btn.setMinSize(300, 50);
        btn.setFont( ButtonFont);
        btn.setOnAction(event -> {
            borderPane.getChildren().remove(MenuText);
            borderPane.getChildren().remove(myvbox);
            borderPane.setCenter((Node) myvbox);
            this.populateButtons();
        });
        btn.setId("button-77");
        myvbox.getChildren().clear();
        myvbox.getChildren().add(btn);
        myvbox.setAlignment(Pos.CENTER);
        MenuText.setTextAlignment(TextAlignment.CENTER);
    }


    @FXML
    protected void populateButtons(){
        if(questionIndex == 1 && answerArray[questionIndex - 1] == 1){
            this.hasProtesi = true;
        }


        try {
            MenuText.setText("");
            FileIO Data = FileIO.getInstance();
            HashMap<Integer, String>[] AnswerHash = Data.getAnswers();
            String[] Questions = Data.getQuestions();
            QuestionLabel.setText(Questions[questionIndex]);

            // MainMenu BUTTON
            mainMenubtn = new Button("Menu Principale");
            mainMenubtn.setMinSize(300, 50);
            mainMenubtn.setOnAction( backEvent -> {
                myvbox.getChildren().clear();
                if(questionIndex > 0)
                    borderPane.getChildren().remove(backButton);
                questionIndex = 0;
                Data.getCasi().forEach( caso -> {
                    caso.setDiffFromAnswers(0);
                    this.AnswerButtons = FXCollections.observableArrayList();
                    this.answerArray = new int[answerArray.length];
                });
                this.mainMenu();
            });

            // BACK BUTTON
            backButton = new Button();
            backButton.setMinSize(100, 30);
//            backButton.setStyle(BUTTON_CLASS);
            backButton.setOnAction( btnEvent -> {
                questionIndex--;
                this.answerArray[questionIndex] = 0;
                this.AnswerButtons = FXCollections.observableArrayList();
                this.populateButtons();
            });

            // SE E' CASO DI PROTESI
            if(this.hasProtesi == true){
                Caso Protesi = new Caso(this.nome, this.descrizione, null);
                QuestionLabel.setText("Caso: "+ Protesi.getNomeCaso());
                MenuText.setText(Protesi.getDescrizione());
                myvbox.getChildren().clear();
                MenuText.setText(descrizione);
                myvbox.getChildren().add(MenuText);
                this.answerArray = new int[Data.numOfQuestions];
                questionIndex = Integer.MAX_VALUE;
                this.hasProtesi = false;
            }
            // CREO UN BOTTONE PER OGNI POSSIBILE RISPOSTA
            AnswerHash[questionIndex].forEach( (key, value) -> {
                Button btn = new Button(value);
                btn.setFont(ButtonFont);
                // SETTO LA FUNZIONALITA DEL BOTTONE
                btn.setOnAction( event -> {
                    //Prime 5 domande
                    if(questionIndex < Data.numOfQuestions - 1) {
                        answerArray[questionIndex] = key;
                        System.out.println(answerArray[questionIndex]);
                        questionIndex++;
                        AnswerButtons.clear();
                         this.populateButtons();
                    }

                    // ALL'ULTIMA DOMANDA DEVO COMPORTARMI DIVERSAMENTE
                    else {
                        borderPane.getChildren().remove(myvbox);
                        borderPane.getChildren().remove(backButton);
                        borderPane.setCenter(MenuText);
                        BorderPane.setAlignment(MenuText, Pos.TOP_CENTER);
                        answerArray[questionIndex] = key;
                        System.out.println(answerArray[questionIndex]);
                        AnswerButtons.clear();
                        myvbox.getChildren().clear();
                        List<Caso> casiWithDiff = new ArrayList<Caso>();
                        try {
                            casiWithDiff = calculateDiff();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        Collections.sort(casiWithDiff);
                        Caso casoProbabile = casiWithDiff.stream().findFirst().get();
                        Caso secondoProbabile = casiWithDiff.get(1);
                        QuestionLabel.setText("Caso: "+ casoProbabile.getNomeCaso());
                        MenuText.setText(casoProbabile.getDescrizione());
                        if(secondoProbabile.getDiffFromAnswers() == casoProbabile.getDiffFromAnswers()){
                            QuestionLabel.setText("Caso: " + casoProbabile.getNomeCaso() + " O " + secondoProbabile.getNomeCaso());
                            MenuText.setText(casoProbabile.getNomeCaso() + "\n" + casoProbabile.getDescrizione() +
                                    "\n\n" + secondoProbabile.getNomeCaso() + "\n" + secondoProbabile.getDescrizione());
                        }
                        myvbox.getChildren().addAll(AnswerButtons);
                    }
                });
                /*FINE ACTION BUTTON*/
                borderPane.setBottom(mainMenubtn);

                AnswerButtons.add(btn);
            });
            myvbox.getChildren().clear();
            myvbox.getChildren().addAll(AnswerButtons);

            borderPane.setBottom(mainMenubtn);
            BorderPane.setAlignment(mainMenubtn, Pos.TOP_CENTER);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    private List<Caso> calculateDiff() throws FileNotFoundException {
        FileIO Data = FileIO.getInstance();
        List<Caso> casi = Data.getCasi();
        casi.stream().forEach(caso -> {
            int [] casoAnswers = caso.getAnswerArray();
            for(int i = 0; i < Data.numOfQuestions ; i++){
                if(casoAnswers[i] != answerArray[i]){
                    caso.setDiffFromAnswers(caso.getDiffFromAnswers() + 1);
                }
            }
        });
        return casi;
    }
}