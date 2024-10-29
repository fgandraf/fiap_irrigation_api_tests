package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import services.CadastroScheduleService;

import java.util.List;
import java.util.Map;

public class CadastroScheduleSteps {

    CadastroScheduleService cadastroScheduleService = new CadastroScheduleService();

    @Dado("que eu tenha os seguintes dados da schedule:")
    public void queEuTenhaOsSeguintesDadosDaSchedule(List<Map<String, String>> linhas) {
        for(Map<String, String> coluna : linhas) {
            cadastroScheduleService.setFieldsDelivery(coluna.get("campo"), coluna.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de schedules")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeSchedules(String endpoint) {
        cadastroScheduleService.createDelivery(endpoint);
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroScheduleService.response.getStatusCode());
    }

    @Dado("que eu recupere o ID da schedule criada no contexto")
    public void queEuRecupereOIDDaScheduleCriadaNoContexto() {
        cadastroScheduleService.retrieveIdDelivery(true);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de schedules")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeSchedules(String endpoint) {
        cadastroScheduleService.deleteDelivery(endpoint);
    }

    @Dado("que eu recupere o ID da schedule criada no contexto e some um")
    public void queEuRecupereOIDDaScheduleCriadaNoContextoESomeUm() {
        cadastroScheduleService.retrieveIdDelivery(false);
    }
}
