package handlers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {

    @Step("Создание базового запроса")
    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    @Step("Создание базового запроса с типом контента: {contentType}")
    private RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    @Step("Авторизация пользователя с email: {email} и паролем: {password}")
    private Response loginUser(String email, String password) {
        return given(this.baseRequest("application/json"))
                .body(new User(email, password))
                .when()
                .post(Parameters.API_LOGIN);
    }

    @Step("Удаление пользователя с токеном: {token}")
    private Response deleteUser(String token) {
        return given(this.baseRequest())
                .auth().oauth2(token)
                .delete(Parameters.API_DELETE_USER);
    }

    @Step("Удаление тестового пользователя с email: {email} и паролем: {password}")
    public void deleteTestUser(String email, String password) {
        Response response = loginUser(email, password);

        if (response.getStatusCode() != 200) {
            return;
        }

        String token = response.body().as(UserResponsed.class).getAccessToken();
        if (token == null || token.isEmpty()) {
            return;
        }

        // Удаляем пользователя
        deleteUser(token);
    }

    @Step("Создание пользователя с именем: {name}, email: {email} и паролем: {password}")
    public void createUser(String name, String email, String password) {
        Response response = given(this.baseRequest("application/json"))
                .body(new User(email, password, name))
                .when()
                .post(Parameters.API_CREATE_USER);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Не удалось создать пользователя");
        }
    }
}