/**
 * Обработчик нажатия кнопки определенной цифры
 * @param e элемент кнопки
 */
function onDigitButtonClick(e) {
    let inputArea = document.getElementById('inputArea');

    if (!inputArea.innerHTML.includes(e.target.innerHTML) && inputArea.innerHTML.length < 4) {
        inputArea.innerHTML += e.target.innerHTML;
    }
}

/**
 * Обработчик нажатия кнопки 'Сброс'
 * Очищает поле ввода цифр
 */
function onResetButtonClick() {
    clean('inputArea');
}

/**
 * Обработчик нажатия кнопки 'Новая игра'
 * Сбрасывает игру и перезагружет страницу
 */
function onNewGameButtonClick() {
    if (confirm('Начать новую игру? Результат не будет сохранен')) {
        clean('inputArea');
        clean('outputArea');

        reset();
    }
}

/**
 * Обработчик нажатия кнопки 'Рейтинг'
 * Перенаправляет на страницу рейтинга
 */
function onRatingButtonClick() {
    if (confirm('Перейти к рейтингу? Результат не будет сохранен')){
        reset();

        location.href = '/bull_cow/rating';
    }
}

/**
 * Удаляет содержимое innerHTML элеменита
 * @param elementId id элемента
 */
function clean(elementId) {
    document.getElementById(elementId).innerHTML = '';
}

/**
 * Сбрасывает игру отправкой POST запроса
 */
function reset() {
    const request = new XMLHttpRequest();

    request.open('post', 'reset', false);
    request.send();
}

/**
 * Создает панель кнопок всех цифр и регистрирует обработчики событий.
 */
function init() {
    const digitPanel = document.getElementById('digitPanel');

    let digitButton;
    for (let i = 0; i < 10; i++){
        digitButton = document.createElement('div');
        digitButton.className = 'digitButton';
        digitButton.innerHTML = i;
        digitPanel.appendChild(digitButton);

        digitButton.addEventListener('click', onDigitButtonClick)
    }

    // Регистрация события кнопки 'Сброс'
    document.getElementById('resetButton').addEventListener('click', onResetButtonClick);

    // Регистрация события кнопки 'Новая игра'
    document.getElementById('newGameButton').addEventListener('click', onNewGameButtonClick);

    // Регистрация события кнопки 'Рейтинг'
    document.getElementById('ratingButton').addEventListener('click', onRatingButtonClick);
}

/**
 * Ajax запрос на получения результата попытки
 * Отправляет число, введенное пользователем и возвращает результат в формате JSON
 */
jQuery(document).ready(function () {
    jQuery('#sendButton').click(function () {
        if (document.getElementById('inputArea').innerHTML.length < 4) {
            return;
        }

        let data = {"number": $('#inputArea').val()};

        clean('inputArea');

        jQuery.ajax({
            type: "POST",
            data: data,
            url: 'game',
            success: async function (serverData) {
                let row = document.createElement('tr');

                fillTableData(serverData.attempt, row);
                fillTableData(serverData.userNumber, row);
                fillTableData(serverData.check, row);
                fillTableData(serverData.msg, row);

                document.getElementById('outputArea').appendChild(row)
            }
        })
    })
});

/**
 * Добавляет данные попытки на страницу в таблицу
 * @param data данные
 * @param row строка таблицы
 */
function fillTableData(data, row) {
    let tableData = document.createElement('td');
    tableData.innerText = data;
    row.appendChild(tableData);
}

window.onload = init;

