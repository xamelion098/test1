package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

public class test {


    public String generateDate(Integer addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    @Test
    void shouldTestThePopup() {
        String date = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+7912345678");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(text("Встреча забронирована на" + date), Duration.ofSeconds(15));

    }

    @Test
    void shouldTestTheCityField() {
        String date = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='date'] input").setValue("date");
        $("[data-test-id='name'] input").setValue("Иванова Анна");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCheckTheCorrectDate() {
        $("[data-test-id='city'] input").setValue("Чебоксары");
        $("[data-test-id='date'] input").setValue("36.02.1995");
        $("[data-test-id='name'] input").setValue("Иванова Анна");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void shouldTestTheTelephoneField() {
        String date = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestTheDateField() {
        String date = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Пермь");
        $("[data-test-id='name'] input").setValue("Иванова Анна");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Неверно введена дата"));

    }

    @Test
    void shouldTestTheConsentCheckbox() {
        String date = generateDate(4, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79123456789");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .checkbox__text").shouldBe(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
