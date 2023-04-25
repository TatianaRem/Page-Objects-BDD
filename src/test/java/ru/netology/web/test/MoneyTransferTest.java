package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.$;
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
    void shouldTransferMoneyFromFirstToSecond() {
        int amount = 1001;
        var dashboardPage = new DashboardPage();
        var transferMoneyToOtherCardPage = dashboardPage.cashIn("second");
        var cardInfo = DataHelper.getFirstCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        int amount = 5000;
        var dashboardPage = new DashboardPage();
        var transferMoneyToOtherCardPage = dashboardPage.cashIn("first");
        var cardInfo = DataHelper.getSecondCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);
    }

    @Test
    void shouldTransferMoneyFromFirstCardAndCheckBalance() {
        int amount = 1000;
        var dashboardPage = new DashboardPage();
        int balanceStartFirst = dashboardPage.getCurrentBalanceOfCard($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]"));
        int balanceStartSecond = dashboardPage.getCurrentBalanceOfCard($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]"));
        var transferMoneyToOtherCardPage = dashboardPage.cashIn("second");
        var cardInfo = DataHelper.getFirstCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);

        int balanceEndTransferFirstCard = DataHelper.balanceOfFirstCardAfterTransferMoney(balanceStartFirst, amount);
        int balanceEndTransferSecondCard = DataHelper.balanceOfSecondCardAfterTransfer(balanceStartSecond, amount);
        int balanceOfFirstCardEnd = dashboardPage.getCurrentBalanceOfCard($("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]"));
        int balanceOfSecondCardEnd = dashboardPage.getCurrentBalanceOfCard($("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]"));

        assertEquals(balanceEndTransferFirstCard, balanceOfFirstCardEnd);
        assertEquals(balanceEndTransferSecondCard, balanceOfSecondCardEnd);

    }

    @Test
    void shouldTransferMoneyAboveBalance() {
        int amount = 13000;
        var dashboardPage = new DashboardPage();
        var transferMoneyToOtherCardPage = dashboardPage.cashIn("second");
        var cardInfo = DataHelper.getFirstCardInfo();

        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);
        transferMoneyToOtherCardPage.getNotificationAboutLimit();

    }

}