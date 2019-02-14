package com.company;


import com.company.domain.Password;
import com.company.service.PasswordProcessing;
import com.company.service.Processable;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class Main {

    public static void main(String[] args){
        Processable processing = new PasswordProcessing(new Password());
        while(true){
            try{
                processing.process();
                showTitle();
                characterInputMode();
                checkInput(System.in.read());
                lineInputMode();
            }
            catch (NullPointerException e) {
                System.out.println(e.getMessage());
                break;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void checkInput(int key){
        if(key == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    public static void showTitle(){
        System.out.println("\nESC to exit");
    }

    public static void characterInputMode() throws IOException, InterruptedException {
        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();

    }

    public static void lineInputMode() throws IOException, InterruptedException {
        String[] cmd = {"/bin/sh", "-c", "stty cooked</dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }
}
