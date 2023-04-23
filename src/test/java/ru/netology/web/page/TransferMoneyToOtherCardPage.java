package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyToOtherCardPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement toField = $("[data-test-id=to] input");
    private SelenideElement buttonAction = $("[data-test-id=action-transfer] span");
    private SelenideElement buttonCancel = $("[data-test-id=action-cancel]");

    public void transferCard(DataHelper.CardInfo CardInfo, int amountToTransfer) {
        amountField.setValue(String.valueOf(amountToTransfer));
        fromField.setValue(CardInfo.getCardNumber());
        buttonAction.click();
    }

    public void getNotificationAboutLimit() {
        $(withText("Ошибка! На Вашем счету недостаточно средст для осуществления перевода")).shouldBe(Condition.visible);
    }
}
