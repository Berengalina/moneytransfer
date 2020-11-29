package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromCard = $("[data-test-id=from] input");
    private SelenideElement buttonPay = $("[data-test-id=action-transfer]");
    private SelenideElement heading2 = $(byText("Пополнение карты"));

    public void checkHeadingPaymentCards() {   //проверяем видимость заголовка "Пополнение карты"
        heading2.shouldBe(Condition.visible);
    }

    public int setAmount() { //метод для установки суммы платежа
        int payment = 5;
        return payment;
    }

    public void setPayCardNumber(String card) {   // метод для ввода суммы, карты-донора и нажатия "пополнить"
        amount.setValue(String.valueOf(setAmount()));
        fromCard.setValue(card);
        buttonPay.click();
    }

    public DashboardPage validPayCard() {   // метод для возврата в DashboardPage после успешного перевода
        return new DashboardPage();
    }

    public void invalidPayCard() {   // метод для получения ошибки при переводе на несуществующую карты
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Произошла ошибка"));
    }

    public void validPaySameCard() {   //  метод для получения ошибки при переводе на ту же карту
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Невозможно осуществить перевод на ту же самую карту"));
    }

    public void setExtendAmount(int extAmount) {   // метод для ввода суммы, превышающей баланс карты-донора
        amount.setValue(String.valueOf(extAmount));
    }

    public void validPayExtendAmount(String card) {   //метод для получения ошибки при переводе суммы большей, чем есть на карте-доноре
        fromCard.setValue(card);
        buttonPay.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Вы не можете перевести средств больше, чем есть на карте"));
    }

}
