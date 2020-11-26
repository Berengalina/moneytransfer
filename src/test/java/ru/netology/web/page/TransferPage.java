package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromCard = $("[data-test-id=from] input");
    //private SelenideElement toCard = $("[data-test-id=to]");
    private SelenideElement buttonPay = $("[data-test-id=action-transfer]");
    private SelenideElement heading2 = $(byText("Пополнение карты"));

    public void checkHeadingPaymentCards() {   //проверяем видимость заголовка "Пополнение карты"
        heading2.shouldBe(Condition.visible);
    }

    public void validPayAmount(String sum) {   //метод для ввода суммы пополнения
        amount.setValue(sum);
    }

    public DashboardPage validPayFirstCard() {   // ввести карту с которой осуществляется оплата и нажать "пополнить"
        fromCard.setValue(DataHelper.getFirstCard());
        buttonPay.click();
        return new DashboardPage();
    }

    public DashboardPage validPaySecondCard() {   // ввести карту с которой осуществляется оплата и нажать "пополнить"
        fromCard.setValue(DataHelper.getSecondCard());
        buttonPay.click();
        return new DashboardPage();
    }

    public TransferPage invalidPayCard() {   // метод для ввода несуществующей карты
        fromCard.setValue(DataHelper.getInvalidCard());
        buttonPay.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Произошла ошибка"));
        return new TransferPage();
    }

    public TransferPage validPayExtendAmount(int payment) {   //метод для пополнения на сумму большую, чем есть на карте
        amount.setValue(String.valueOf(payment));
        fromCard.setValue(DataHelper.getSecondCard());
        buttonPay.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Вы не можете перевести средств больше, чем есть на карте"));
        return new TransferPage();
    }

    public TransferPage validPaySameCard(String sum) {   //  метод для проверки перевода на ну же карту
        amount.setValue(sum);
        fromCard.setValue(DataHelper.getFirstCard());
        buttonPay.click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Невозможно осуществить перевод на ту же самую карту"));
        return new TransferPage();
    }
}
