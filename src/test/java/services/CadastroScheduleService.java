package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.ScheduleModel;

import static io.restassured.RestAssured.given;

public class CadastroScheduleService {

    final ScheduleModel scheduleModel = new ScheduleModel();
    public final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private String token;
    public Response response;
    String baseUrl = "http://localhost:8080";

    public void authenticate() {
        // Obtenha o token de autenticação chamando o endpoint de login
        this.token = given()
                .contentType("application/json")
                .body(""" 
                          {"email": "admin@admin.com", "password": "1234abcd"}
                        """)
                .post(baseUrl + "/api/users/login")
                .then()
                .extract()
                .path("token");
    }

    public void setFieldsDelivery(String field, String value) {
        switch (field) {
            case "numSchedule" -> scheduleModel.setNumSchedule(Integer.parseInt(value));
            case "startTime" -> scheduleModel.setStartTime(value);
            case "endTime" -> scheduleModel.setEndTime(value);
            default -> throw new IllegalStateException("Unexpected field" + field);
        }
    }

    public void createDelivery(String endPoint) {
        this.authenticate();
        String url = baseUrl + endPoint;
        //Convertendo o objeto para JSON
        String bodyToSend = gson.toJson(scheduleModel);
        response = given()
                //Especificando corpo da requisição como JSON
                .contentType(ContentType.JSON)
                //Especificando retorno aceito como JSON
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

}
