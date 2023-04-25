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
    private SelenideElement firstCard = $("[data-test-id=\"92df3f1c-a033-48e6-8390-206f6b1f56c0\"]");
    private SelenideElement secondCard = $("[data-test-id=\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\"]");
    private SelenideElement cashInFirst = $$("[data-test-id=action-deposit]").first();
    private SelenideElement cashInSecond = $$("[data-test-id=action-deposit]").last();
    private SelenideElement reload = $("[data-test-id=action-reload]");

    public TransferMoneyToOtherCardPage cashIn(String cashInType) {
        if (cashInType.equals("first")) {
            cashInFirst.click();
        } else if (cashInType.equals("second")) {
            cashInSecond.click();
        } else {
            throw new IllegalArgumentException("Неверный номер карты " + cashInType);
        }
        return new TransferMoneyToOtherCardPage();
    }


    public int getCurrentBalanceOfCard(SelenideElement card) {
        String selectedValue = card.getText();
        String balance = selectedValue.substring(selectedValue.indexOf(balanceStart) + balanceStart.length(),
                selectedValue.indexOf(balanceFinish));
        return Integer.parseInt(balance);
    }

}