package org.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Utils {

    static public String readLineFromConsole(String strPrompt) {
        try {
            System.out.print(strPrompt);
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int readIntFromConsole(String strPrompt) {
        do {
            try {
                String strInt = readLineFromConsole(strPrompt);
                int iInt = Integer.parseInt(strInt);
                return iInt;
            } catch (NumberFormatException ex) {
                //Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    public static double readDoubleFromConsole(String strPrompt) {
        do {
            try {
                String strDouble = readLineFromConsole(strPrompt);
                double iDouble = Double.parseDouble(strDouble);
                return iDouble;
            } catch (NumberFormatException ex) {
                //Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Data readDateFromConsole(String strPrompt) {
        do {
            try {
                String strData = readLineFromConsole(strPrompt + "(formato: dd-MM-yyyy)");
                // dd-MM-yyyy
                String[] arr = strData.split("-");
                int dia = Integer.parseInt(arr[0]);
                int mes = Integer.parseInt(arr[1]);
                int ano = Integer.parseInt(arr[2]);
                return new Data(ano, mes, dia);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                System.out.println("--> " + ex.toString());
            }
        } while (true);
    }

    static public boolean confirma(String sMessage) {
        String strConfirma;
        do {
            strConfirma = Utils.readLineFromConsole(sMessage);
        } while (!strConfirma.equalsIgnoreCase("s") && !strConfirma.equalsIgnoreCase("n"));

        return strConfirma.equalsIgnoreCase("s");
    }


    /**
     * Lê uma password sem mostrar os caracteres digitados.
     * Em terminal real usa System.console().readPassword() que esconde os caracteres.
     * Dentro do IntelliJ (onde System.console() é null), abre uma janela popup
     * com campo de password mascarado (JPasswordField).
     *
     * @param strPrompt mensagem a apresentar ao utilizador
     * @return password introduzida
     */
    static public String readPasswordFromConsole(String strPrompt) {
        if (System.console() != null) {
            char[] password = System.console().readPassword(strPrompt);
            return new String(password);
        } else {
            // Dentro do IDE — popup com campo de password mascarado
            javax.swing.JPasswordField jpf = new javax.swing.JPasswordField();
            int resultado = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    jpf,
                    strPrompt,
                    javax.swing.JOptionPane.OK_CANCEL_OPTION,
                    javax.swing.JOptionPane.PLAIN_MESSAGE
            );
            if (resultado == javax.swing.JOptionPane.OK_OPTION) {
                return new String(jpf.getPassword());
            }
            return "";
        }
    }
    public static String readPasswordWithMask(String prompt) {
        System.out.print(prompt);

        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                char c = (char) System.in.read();

                // ENTER (13 no Windows, 10 no Linux/Mac)
                if (c == '\n' || c == '\r') {
                    System.out.println();
                    break;
                }

                // BACKSPACE
                if (c == 8 || c == 127) {
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b"); // apaga o último *
                    }
                    continue;
                }

                // Caracter normal
                password.append(c);
                System.out.print("*");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return password.toString();
    }

}