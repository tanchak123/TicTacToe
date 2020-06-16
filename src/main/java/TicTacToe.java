import javax.swing.*;
import java.util.Scanner;

public class TicTacToe {
    private static Scanner sc = new Scanner(System.in);
    private static char[][] field;

    public static void main(String[] args) {

        boolean flag = true;
        field = new char[][]{
                {'*', '-', '-', '-', '-', '-', '*'},
                {'|', ' ', '|', ' ', '|', ' ', '|'},
                {'|', '-', '+', '-', '+', '-', '|'},
                {'|', ' ', '|', ' ', '|', ' ', '|'},
                {'|', '-', '+', '-', '+', '-', '|'},
                {'|', ' ', '|', ' ', '|', ' ', '|'},
                {'*', '-', '-', '-', '-', '-', '*'}
        };
        print();
        String character = null;
        String numbers = "123456789";
        while (true) {
            char symbol = 'X';
            if (flag) {
                symbol = '0';
            }
            String string = "";
            for (char num : numbers.toCharArray()) {
                string = string + num + "|";
            }
            if (!flag) {
                System.out.println(string.length());
                System.out.println("Введите символ: " + string);
                character = sc.nextLine();
                if (character.equals("")
                        || character.length() > 2
                        || !character.matches(string)) {
                    System.out.println("Нужно ввести цифру " + string);
                    continue;
                }
            } else {
                if (string.length() == 16) {
                    if (character.matches("1|2|3|4|9|6|7|8") || character == null) {
                        character = "5";
                    } else if (character.equals("5")) {
                        character = "9";
                    }
                } else {
                    character = machineWinAvailable();
                }
            }
            switch (Integer.parseInt(character)) {
                case 1:
                    field[1][1] = symbol;
                    break;
                case 2:
                    field[1][3] = symbol;
                    break;
                case 3:
                    field[1][5] = symbol;
                    break;
                case 4:
                    field[3][1] = symbol;
                    break;
                case 5:
                    field[3][3] = symbol;
                    break;
                case 6:
                    field[3][5] = symbol;
                    break;
                case 7:
                    field[5][1] = symbol;
                    break;
                case 8:
                    field[5][3] = symbol;
                    break;
                default:
                    field[5][5] = symbol;
                    break;
            }
            numbers = numbers.replace(character, "");
            print();
            flag = !flag;
            System.out.println(numbers.length());
            if (numbers.length() == 0) {
                System.out.println("Ничья");
                break;
            }
            if (checkWin())
                break;
        }
    }

    private static void print() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    private static boolean checkWin() {
        for (int i = 1; i <= 5; i += 2) {
            if (((field[i][1] == 'X')
                    && (field[i][3] == 'X')
                    && (field[i][5] == 'X'))
                    || ((field[1][i] == 'X')
                            && (field[3][i] == 'X')
                            && (field[5][i] == 'X'))) {
                System.out.println("Поздравляем вы победили");
                return true;
            }
            if (((field[i][1] == '0')
                    && (field[i][3] == '0')
                    && (field[i][5] == '0'))
                    || ((field[1][i] == '0')
                            && (field[3][i] == '0')
                            && (field[5][i] == '0'))) {
                System.out.println("Виртуальный интеллект победил");
                return true;
            }
        }
        if (((field[1][1] == 'X') && (field[3][3] == 'X') && (field[5][5] == 'X'))
                    || (((field[1][5] == 'X') && (field[3][3] == 'X') && (field[5][1] == 'X')))) {
            System.out.println("Поздравляем вы победили");
            return true;
        } else if (((field[1][1] == '0') && (field[3][3] == '0') && (field[5][5] == '0'))
                    || ((field[1][5] == '0') && (field[3][3] == '0') && (field[5][1] == '0'))) {
            System.out.println("Виртуальный интеллект победил");
            return true;
        }
        return false;
    }

    private static String humanWinAvailable() {
        int gorizonCount = 0;
        int verticalCount = 0;
        for (int i = 1; i <= 5; i += 2) {
            for (int j = 1; j <= 5; j += 2) {
                if (field[i][j] == 'X') {
                    gorizonCount++;
                } else if (field[i][j] == '0') {
                    gorizonCount--;
                }
                if (gorizonCount == 2) {
                    int one = 1;
                    while (one <= 5) {
                        if (field[i][one] == ' ') {
                            return switcher(i, one);
                        }
                        one += 2;
                    }
                }
                if (field[j][i] == 'X') {
                    verticalCount++;
                } else if (field[j][i] == '0') {
                    verticalCount--;
                }
                if (verticalCount == 2) {
                    int one = 1;
                    while (one <= 5) {
                        if (field[one][i] == ' ') {
                            return switcher(one, i);
                        }
                        one += 2;
                    }
                }
            }
            gorizonCount = 0;
            verticalCount = 0;
        }
        if (field[3][3] == 'X') {
            if (field[1][1] == 'X') {
                if (field[5][5] =='0' && field[1][5] == ' ') {
                    return "3";
                }
                return "8";
            } else if (field[1][5] == 'X' && field[5][1] == ' ') {
                return "7";
            } else if (field[5][1] == 'X' && field[1][5] == ' ') {
                return "3";
            } else if (field[5][5] == 'X' && field[5][1] == ' ') {
                return "1";
            }
        }
        for (int i = 1; i <= 5; i += 2) {
            for (int j = 1; j <= 5; j += 2) {
                if (field[i][j] == ' ') {
                    return switcher(i, j);
                }
            }
        }
        return null;
    }

    private static String machineWinAvailable() {
        int gorizonCount = 0;
        int verticalCount = 0;
        for (int i = 1; i <= 5; i += 2) {
            for (int j = 1; j <= 5; j += 2) {
                if (field[i][j] == '0') {
                    gorizonCount++;
                } else if (field[i][j] == 'X') {
                    gorizonCount--;
                }
                if (gorizonCount == 2) {
                    int one = 1;
                    while (one <= 5) {
                        if (field[i][one] == ' ') {
                            return switcher(i, one);
                        }
                        one += 2;
                    }
                }
                if (field[j][i] == '0') {
                    verticalCount++;
                } else if (field[j][i] == 'X') {
                    verticalCount--;
                }
                if (verticalCount == 2) {
                    int one = 1;
                    while (one <= 5) {
                        if (field[one][i] == ' ') {
                            return switcher(one, i);
                        }
                        one += 2;
                    }
                }
            }
            gorizonCount = 0;
            verticalCount = 0;
        }
        if (field[3][3] == '0') {
            if (field[1][1] == '0' && field[5][5] == ' ') {
                return "9";
            } else if (field[1][5] == '0'&& field[5][1] == ' ') {
                return "7";
            } else if (field[5][1] == '0' && field[1][5] == ' ') {
                return "3";
            } else if (field[5][5] == '0' && field[1][1] == ' ') {
                return "1";
            }
        }
        return humanWinAvailable();
    }

    private static String switcher(int i, int j) {
        switch (i) {
            case 1:
                switch (j) {
                    case 1:
                        return "1";
                    case 3:
                        return "2";
                    default:
                        return "3";
                }
            case 3:
                switch (j) {
                    case 1:
                        return "4";
                    case 3:
                        return "5";
                    default:
                        return "6";
                }

            default:
                switch (j) {
                    case 1:
                        return "7";
                    case 3:
                        return "8";
                    default:
                        return "9";
                }
        }
    }

}
