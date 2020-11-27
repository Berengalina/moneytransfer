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
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance(); //получили баланс карты, которую будем пополнять
        val initialBalanceToCard = dashboardPage.getSecondCardBalance(); //получили баланс карты, с которой будем пополнять
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        val dashboardPage1 = transferPage.validPaySecondCard("5"); //ввели сумму перевода и карту для перевода и вернулись на страницу с балансами и картами
        val actual = dashboardPage1.getFirstCardBalance(); //получили новый баланс первой карты
        val expected = initialBalanceFromCard + 5; //посчитали каким должен быть баланс первой карты
        val actual2 = dashboardPage1.getSecondCardBalance(); //получили новый баланс второй карты
        val expected2 = initialBalanceToCard - 5; //посчитали каким должен быть баланс второй карты
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
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val initialBalanceToCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        val dashboardPage1 = transferPage.validPayFirstCard("999");
        val actual1 = dashboardPage1.getSecondCardBalance();
        val expected1 = initialBalanceFromCard + 999;
        val actual2 = dashboardPage1.getFirstCardBalance(); //получили новый баланс второй карты
        val expected2 = initialBalanceToCard - 999;
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
        //val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        //val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        transferPage.validPaySameCard("500"); //выбрали ту же первую карту, ожидаем предупреждение на странице
    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsV4() {  //проверяем невозможность пополнения первой карты с несуществующей карты
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        //val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards();
        transferPage.invalidPayCard("1000"); //ввели сумму и данные несуществующей карты, ожидаем предупреждение на странице
    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsV5() {  //проверяем невозможность пополнения первой карты на сумму, большую чем на карте
        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        //val initialBalanceFromCard = dashboardPage.getFirstCardBalance(); //получили баланс карты, которую будем пополнять
        val initialBalanceToCard = dashboardPage.getSecondCardBalance(); //получили баланс карты, с которой будем пополнять
        val transferPage = dashboardPage.validChoosePay1(); //выбрали карту c которой будет оплата
        transferPage.checkHeadingPaymentCards(); // проверили видимость заголовка "Пополнение карты"
        transferPage.validPayExtendAmount(initialBalanceToCard + 1);//вводим сумму, большую на 1 руб., чем имеется на счету второй карты
    }

}