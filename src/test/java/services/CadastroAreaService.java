package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.AreaModel;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONTokener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroAreaService {

    final AreaModel areaModel = new AreaModel();
    public final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private String token;
    private String idDelivery;
    public Response response;
    String baseUrl = "http://localhost:8080";
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper objectMapper = new ObjectMapper();


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
            case "id" -> areaModel.setId(Integer.parseInt(value));
            case "description" -> areaModel.setDescription(value);
            case "location" -> areaModel.setLocation(value);
            case "size" -> areaModel.setSize(value);
            default -> throw new IllegalStateException("Unexpected field" + field);
        }
    }

    public void createDelivery(String endPoint) {
        this.authenticate();
        String url = baseUrl + endPoint;
        //Convertendo o objeto para JSON
        String bodyToSend = gson.toJson(areaModel);
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
        if(valid) idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), AreaModel.class).getId());
        else idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), AreaModel.class).getId() + 1);
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro Area bem-sucedido" -> jsonSchema = loadJsonFromFile(schemasPath + "cadastro-area-bem-sucedido.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }


    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = objectMapper.readTree(jsonResponse.toString());
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);
        return schemaValidationErrors;
    }

}