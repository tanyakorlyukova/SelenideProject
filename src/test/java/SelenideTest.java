import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SelenideTest {

    /*http://open-eshop.stqa.ru/oc-panel/auth/login/
    Сценарий:
    1. Вход в панель администрирования (логин: demo@open-eshop.com, пароль: demo)
    2. Создание нового купона в разделе eShop -> Coupons
    (достаточно заполнить только обязательные поля -- название, количество и дату,
    причём постарайтесь название выбрать так, чтобы не конфликтовать с другими,
    которые могут в то же самое время выполнять аналогичные действия)
    3. Поиск созданного купона по названию (поле для поиска находится над таблицей со списком купонов)
    и удаление найденного купона
    4. Выход (logout)*/

    @Test
    public void selenideTestScenario() {
        String couponName = "Coupon777666";

        Configuration.startMaximized=true;
        open("http://open-eshop.stqa.ru/oc-panel/auth/login/");

        //login
        $("#page input[name='email']").setValue("demo@open-eshop.com");
        $("#page input[name='password']").setValue("demo").pressEnter();
        $(By.tagName("h1")).shouldHave(text("Welcome Admin"));

        //open Coupons page
        $$(".panel-heading").findBy(text("eShop")).click();
        $$("#collapseOne a").findBy(text("Coupons")).click();
        $(By.tagName("h1")).shouldHave(text("Coupon"));

        //add new coupon
        while ($("a[class='btn btn-primary pull-right']").exists()) {
            $("a[class='btn btn-primary pull-right']").click();
        }
        $("#name").setValue(couponName);
        $("#discount_amount").setValue("10");
        $("input[placeholder='yyyy-mm-dd']").setValue("2021-09-09").pressEnter();
        $("div.alert-success").shouldHave(text("Coupon " + couponName + " created"));

        //search coupon
        $("input[placeholder='Coupon name']").setValue(couponName).pressEnter();
        $(By.tagName("tr")).should(exist);

        //delete coupon
        $("a[class='btn btn-danger index-delete']").click();
        $(".confirm").click();

        //logout
        $("a.index-delete").click();
        $(By.xpath("//i[@class='glyphicon glyphglyphicon glyphicon-off']/ancestor::a")).click();
        $("a[class='btn btn-primary-white']").shouldBe(visible);
    }


}
