package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromFirstCardAndCheckBalance() {
        int amount = 1000;
        var dashboardPage = new DashboardPage();
        int balanceStartFirst = dashboardPage.getCurrentBalanceOfCard(1);
        int balanceStartSecond = dashboardPage.getCurrentBalanceOfCard(2);
        var transferMoneyToOtherCardPage = dashboardPage.cashIn(2);

        var cardInfo = DataHelper.getFirstCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);

        int balanceEndTransferFirstCard = DataHelper.balanceOfFirstCardAfterTransferMoney(balanceStartFirst, amount);
        int balanceEndTransferSecondCard = DataHelper.balanceOfSecondCardAfterTransfer(balanceStartSecond, amount);
        int balanceOfFirstCardEnd = dashboardPage.getCurrentBalanceOfCard(1);
        int balanceOfSecondCardEnd = dashboardPage.getCurrentBalanceOfCard(2);

        assertEquals(balanceEndTransferFirstCard, balanceOfFirstCardEnd);
        assertEquals(balanceEndTransferSecondCard, balanceOfSecondCardEnd);

    }

    @Test
    void shouldTransferMoneyAboveBalance() {
        int amount = 13000;
        var dashboardPage = new DashboardPage();
        var transferMoneyToOtherCardPage = dashboardPage.cashIn(2);
        var cardInfo = DataHelper.getFirstCardInfo();

        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);
        transferMoneyToOtherCardPage.getNotificationAboutLimit();

    }

}