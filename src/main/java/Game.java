import database.Attempt;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

/**
 * Класс игры.
 */
class Game {
    /**
     * Разрядность числа
     */
    private static final int RANK_NUMBER = 4;

    /**
     * Количество используемых цифр
     */
    private static final int DIGITS_NUMBER = 10;

    /**
     * Условие победы
     */
    private static final String WIN_CONDITION = "4Б0К";

    /**
     * Ссылка на единственный экземпляр класса
     */
    private static Game instance;

    /**
     * Загаданное число. В массиве значение, отличное от нуля указывает на какой позиции цифра, соответствующая индексу.
     * Например число 7934:
     * { 0, 0, 0, 3, 4, 0, 0, 1, 0, 2}
     */
    private static int[] number;

    /**
     * Счетчик вариантов пользователя в текущей попытке
     */
    private static int attemptsCount = 0;

    /**
     * Генератор случайных чисел
     */
    private static Random random = new Random();

    /**
     * Конструктор гененрирует число
     */
    private Game() {
        generateNumber();
    }

    /**
     * Возвращает единственный экземпляр класса
     * @return экземпляр класса
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Возвращает результат обработки предложеннго пользователем варианта в формате JSON
     * { "attempt": "<количество вариантов, включая данный>", "userNumber": "<вариант пользователя>",
     * "check": "<результат проверки в формате "*Б*К">", "msg": "<сообщение>" }
     * @param number предложенный вариант
     * @param logIn логин
     * @return результат
     */
    public JSONObject check(String number, String logIn) {
        JSONObject result = new JSONObject();

        result.put("attempt", ++attemptsCount);
        result.put("userNumber", number);

        String check = checkNumber(number);
        result.put("check", check);

        if (check.equals(WIN_CONDITION)) {
            result.put("msg", "Вы угадали!");

            save(logIn, attemptsCount);
        }
        else {
            result.put("msg", "Неверно.");
        }

        return result;
    }

    /**
     * Сбрасывает игру
     */
    public void reset() {
        attemptsCount = 0;
        generateNumber();
    }

    /**
     * Проверяет предложенный пользователем вариант
     * @param number вариант пользователя
     * @return результат
     */
    private String checkNumber(String number) {
        int[] numberAsArray = stringToArray(number);
        int bullCount = 0;
        int cowCount = 0;

        for (int i = 0; i < DIGITS_NUMBER; i++){
            if (numberAsArray[i] != 0 && this.number[i] != 0){
                if (numberAsArray[i] == this.number[i]){
                    bullCount++;
                    continue;
                }
                cowCount++;
            }
        }

        return bullCount + "Б" + cowCount + "К";
    }

    /**
     * Преобразует вариант числа пользователя из строки в массив, аналогичный {@link Game#number}
     * @param number вариант пользователя
     * @return число в виде массива
     */
    private int[] stringToArray(String number){
        int[] result = new int[DIGITS_NUMBER];
        for (int i = 0; i < RANK_NUMBER; i++){
            result[Character.digit(number.charAt(i), 10)] = i + 1;
        }
        return result;
    }

    /**
     * Генерирует число. На каждой итерации определяет цифру и ее положение и удаляет использованную цифру из диапазона
     * во избежание повтора цифр
     */
    private void generateNumber(){
        number = new int[DIGITS_NUMBER];
        LinkedList<Integer> digits = getFillingList();

        int temp;
        for (int i = 0; i < RANK_NUMBER; i++){
            temp = random.nextInt(DIGITS_NUMBER - i);
            number[digits.get(temp)] = i + 1;
            digits.remove(temp);
        }
    }

    /**
     * Возвращает исходный диапазон цифр
     * @return список цифр
     */
    private LinkedList<Integer> getFillingList(){
        LinkedList<Integer> result = new LinkedList<>();

        for (int i = 0; i < 10; i++){
            result.add(i);
        }

        return result;
    }

    /**
     * Сохраняет (сохранение поптыки) и сбрасывает игру.
     * @param logIn логин
     * @param value количество вариантов, потребоваашееся пользователю для успешного угадывания
     */
    private void save(String logIn, int value) {
        reset();

        try {
            Attempt.AttemptDao.save(
                    new Attempt(logIn, value));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
