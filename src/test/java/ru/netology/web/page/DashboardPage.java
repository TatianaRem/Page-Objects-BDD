package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private String cardDepositButton = "[data-test-id=action-deposit]";
    private SelenideElement reload = $("[data-test-id=action-reload]");

    public TransferMoneyToOtherCardPage cashIn(int id) {

        int index = id - 1;
        cards.get(index).find(cardDepositButton).click();
        return new TransferMoneyToOtherCardPage();
    }


    public int getCurrentBalanceOfCard(int id) {
       int index = id - 1;
       var text = cards.get(index).text();
       return extractBalance(text);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}