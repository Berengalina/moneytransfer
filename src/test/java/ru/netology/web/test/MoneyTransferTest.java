package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Selenide.*;


class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {  //проверяем пополнение с первой карты на вторую
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards(); //проверили видимость заголовка "Ваши карты"
        val initialBalanceToCard = dashboardPage.getFirstCardBalance(); //получили баланс карты, которую будем пополнять
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance(); //получили баланс карты, с которой будем пополнять
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        transferPage.setPayCardNumber("5559 0000 0000 0002"); //ввели сумму перевода и карту для перевода
        val dashboardPage1 = transferPage.validPayCard(); //вернулись на страницу с балансами и картами после успешного пополнения
        val actual = dashboardPage1.getFirstCardBalance(); //получили новый баланс первой карты
        val expected = initialBalanceToCard + transferPage.setAmount(); //посчитали каким должен быть баланс первой карты
        val actual2 = dashboardPage1.getSecondCardBalance(); //получили новый баланс второй карты
        val expected2 = initialBalanceFromCard - transferPage.setAmount(); //посчитали каким должен быть баланс второй карты
        assertEquals(expected, actual); //сравнили балансы первой карты
        assertEquals(expected2, actual2); //сравнили балансы второй карты
    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {  //проверяем пополнение со второй карты на первую
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber("5559 0000 0000 0001");
        val dashboardPage1 = transferPage.validPayCard();
        val actual1 = dashboardPage1.getSecondCardBalance();
        val expected1 = initialBalanceToCard + transferPage.setAmount();
        val actual2 = dashboardPage1.getFirstCardBalance();
        val expected2 = initialBalanceFromCard - transferPage.setAmount();
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV3() {  //проверяем невозможность пополнения первой карты на саму себя
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        transferPage.setPayCardNumber("5559 0000 0000 0001"); //ввели сумму перевода и ту же первую карту в качестве карты-донора
        transferPage.validPaySameCard(); //ожидаем предупреждение на странице
    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsV4() {  //проверяем невозможность пополнения первой карты с несуществующей карты
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        transferPage.setPayCardNumber("1111 1111 1111 1111"); //ввели сумму и данные несуществующей карты
        transferPage.invalidPayCard(); //ожидаем предупреждение на странице
    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsV5() {  //проверяем невозможность пополнения на сумму, большую чем на карте-доноре
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance(); //получили баланс карты, с которой будем пополнять
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        transferPage.setExtendAmount(initialBalanceFromCard + transferPage.setAmount()); //вводим сумму большую, чем есть на балансе второй карты
        transferPage.validPayExtendAmount("5559 0000 0000 0002"); //ожидаем предупреждение на странице
    }

}