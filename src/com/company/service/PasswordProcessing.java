package com.company.service;


import com.company.Main;
import com.company.domain.Password;
import com.company.exception.InvalidPasswordException;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static com.company.utils.Constants.*;
import static com.company.utils.PasswordHelper.checkSymbol;
import static com.company.utils.PasswordHelper.encrypt;

public class PasswordProcessing implements Processable {

    private Password password;
    private int countOfTry = 2;

    public PasswordProcessing(Password password) {
        this.password = password;
    }

    private String validatePasswordLength() {
        if (this.password.getLength() < 5) {
            return "\nPassword too short";
        } else if (this.password.getLength() > 50) {
            return "\nPassword too long";
        }
        return "\n";
    }

    private Console getConsole() {
        Console console = System.console();
        if (console == null)
            throw new NullPointerException("Console object is null. Please, launch application through the terminal.");
        return console;
    }

    private void appendToPassword(String symbol) {
        this.password.setPassword(this.password.getPassword() + symbol);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    private void maskPassword(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(SYMBOL);
        }
    }

    private void displayTitle() throws IOException, InterruptedException {
        Main.lineInputMode();
        System.out.println("\nEnter your password:");
    }

    private void showPassword() {
        this.clearScreen();
        try {
            this.displayTitle();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.maskPassword(this.password.getLength());
    }

    private String input() throws IOException, InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        Main.characterInputMode();
        char[] password = getConsole().readPassword();
        Main.lineInputMode();
        stringBuilder.append(password);
        return stringBuilder.toString();
    }

    @Override
    public void process() throws IOException, InterruptedException {
        password = new Password();
        this.displayTitle();
        boolean isValid = false;
        while (!isValid) {
            String symbol = input();
            if (checkSymbol(symbol)) {
                appendToPassword(symbol);
                showPassword();
            } else if (symbol.equals(EMPTY_STRING)) {
                if(countOfTry == 0){
                    System.out.println("\nSorry, you have no more tries");
                    break;
                }
                else{
                    onEnterButtonPressed();
                    isValid = true;
                }
            }
        }
    }

    private void onEnterButtonPressed() throws IOException, InterruptedException {
            if (isPasswordLengthValid()) {
                System.out.println("\n");
                appendToPassword(this.password.getPassword());
                savePassword(this.password);
            } else {
                countOfTry--;
                System.out.println("One more try");
                process();
            }
    }

    private boolean isPasswordLengthValid() {
        String validationMessage = validatePasswordLength();
        System.out.println(validationMessage);
        return this.password.getLength() >= 5 && this.password.getLength() <= 50;
    }

    private void writeToFile(String password) throws IOException {
        try{
            File file = new File(FILE_NAME);
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.write(password);
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void savePassword(Password password) {
        try {
            String choice;
            boolean isSaved = false;
                while (!isSaved) {
                    System.out.print(FILE_NAME + " file will be overridden. Y/N\n");
                    Scanner scanner = new Scanner(System.in);
                    choice = scanner.next();
                    if (YES_ANSWER.equalsIgnoreCase(choice)) {
                        writeToFile(encrypt(password.getPassword()));
                        isSaved = true;
                    } else if (NO_ANSWER.equalsIgnoreCase(choice)) {
                        isSaved = true;
                    } else {
                        System.out.println("Wrong symbol! Please try again...");
                    }
                }
        } catch (IOException e) {
            throw new InvalidPasswordException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


}


