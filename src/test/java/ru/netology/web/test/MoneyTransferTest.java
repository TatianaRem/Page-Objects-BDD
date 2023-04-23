package ru.netology.web.test;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        int amount = 1001;
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferMoneyToOtherCardPage = dashboardPage.cashInSecond();
        var cardInfo = DataHelper.getFirstCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        int amount = 5000;
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferMoneyToOtherCardPage = dashboardPage.cashInFirst();
        var cardInfo = DataHelper.getSecondCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);
    }

    @Test
    void shouldTransferMoneyFromFirstCardAndCheckBalance() {
        int amount = 1000;
        var loginPage = open("http://localhost:9999", LoginPage.class);

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        int balanceStartFirst = dashboardPage.getCurrentBalanceOfFirstCard();
        int balanceStartSecond = dashboardPage.getCurrentBalanceOfSecondCard();
        var transferMoneyToOtherCardPage = dashboardPage.cashInSecond();
        var cardInfo = DataHelper.getFirstCardInfo();
        transferMoneyToOtherCardPage.transferCard(cardInfo, amount);

        int balanceEndTransferFirstCard = DataHelper.balanceOfFirstCardAfterTransferMoney(balanceStartFirst, amount);
        int balanceEndTransferSecondCard = DataHelper.balanceOfSecondCardAfterTransfer(balanceStartSecond, amount);
        int balanceOfFirstCardEnd = dashboardPage.getCurrentBalanceOfFirstCard();
        int balanceOfSecondCardEnd = dashboardPage.getCurrentBalanceOfSecondCard();

        assertEquals(balanceEndTransferFirstCard, balanceOfFirstCardEnd);
        assertEquals(balanceEndTransferSecondCard, balanceOfSecondCardEnd);

    }

}