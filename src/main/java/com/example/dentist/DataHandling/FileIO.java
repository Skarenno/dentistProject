package com.example.dentist.DataHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import com.example.dentist.Casi.Caso;

public class FileIO {

    // INSTANCE
    private static FileIO instance = null;


    // FILES
    private File storedAnswers;

    private File Casi;


    // STRUTTURE DATI
    private String[] Questions;
    private HashMap<Integer, String>[] Answers;

    private List<Caso> casi = new ArrayList<Caso>();

    // ALTRO
    public int numOfQuestions;


    // GETTER FILES //

    public String[] getQuestions(){
        return this.Questions;
    }

    public HashMap<Integer, String>[] getAnswers(){
        return this.Answers;
    }

    public List<Caso> getCasi() {
        return casi;
    }

    // COSTRUTTORE //
    public FileIO() throws FileNotFoundException {
        this.storedAnswers = new File("src/files/Risposte.txt");
        this.Casi = new File("src/files/Casi.txt");
        this.Questions = new String[64];
        this.Answers = new HashMap[64];
        this.numOfQuestions = 0;
        this.checkFiles();
        this.writeArrays();
    }


    public static FileIO getInstance() throws FileNotFoundException {
        // Crea l'oggetto solo se NON esiste:
        if (instance == null) {
            instance = new FileIO();
        }
        return instance;
    }

    public void checkFiles(){
        try {
            if (!storedAnswers.isFile()) {
                throw new IOException();
            }
        }
        catch(IOException FileError) {
            System.out.println("COULDN'T LOAD FILE " + FileError.getMessage());
        }
    }

    public void writeArrays() throws FileNotFoundException {
        Scanner aReader = null;
        Scanner cReader = null;

        try {
            cReader = new Scanner(this.Casi);
            aReader = new Scanner(this.storedAnswers);
        } catch (FileNotFoundException e) {
            System.out.println("File reading ERROR " + e.toString());
            System.exit(e.hashCode());
        }

        String whole;
        while (aReader.hasNextLine()) {
            Integer key;
            String[] HashString = new String[2];
            whole = aReader.nextLine();
            if (!whole.contains(":")) {
                Questions[numOfQuestions] = whole;
                this.Answers[numOfQuestions] = new HashMap<>();
                this.numOfQuestions += 1;
            } else {
                HashString = whole.split(":");
                this.Answers[numOfQuestions - 1].put(Integer.valueOf(HashString[0]), HashString[1]);
            }
        }

        while (cReader.hasNextLine()) {
            whole = cReader.nextLine();
            Scanner wholeScan = new Scanner(whole);
            String nome =  wholeScan.next();
            while(!wholeScan.hasNextInt()){
                nome += " ";
                nome += wholeScan.next();
            }
            int[] arr = new int[64];
            int i = 0;

            while(wholeScan.hasNextInt()){
                arr[i] = wholeScan.nextInt();
                i++;
            }

            String descrizione = wholeScan.nextLine();

            casi.add(new Caso(nome, descrizione, arr));
        }
        for (int i = 0; i < numOfQuestions; i++)
            System.out.println(Answers[i].toString());

        cReader.close();
        aReader.close();
        casi.forEach(caso1 -> {
            System.out.println(caso1.toString());
        });
    }
}
