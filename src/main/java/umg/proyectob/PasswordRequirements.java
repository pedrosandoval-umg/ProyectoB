package umg.proyectob;

public class PasswordRequirements {
     public static boolean validarPassword(String password) {
        if (password.length() < 6) return false;

        boolean tieneMayus = false;
        boolean tieneMinus = false;
        boolean tieneNumero = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayus = true;
            else if (Character.isLowerCase(c)) tieneMinus = true;
            else if (Character.isDigit(c)) tieneNumero = true;
        }

        return tieneMayus && tieneMinus && tieneNumero;
    }
}
