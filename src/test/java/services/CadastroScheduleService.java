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
    private String idDelivery;
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
            case "id" -> scheduleModel.setId(Integer.parseInt(value));
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
        System.out.println(response.jsonPath().prettify());
    }

    public void deleteDelivery(String endpoint){
        this.authenticate();
        String url = String.format("%s%s/%s", baseUrl, endpoint, idDelivery);
        response = given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    public void retrieveIdDelivery(boolean valid){
        if(valid) idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), ScheduleModel.class).getId());
        else idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), ScheduleModel.class).getId() + 1);
    }

}
