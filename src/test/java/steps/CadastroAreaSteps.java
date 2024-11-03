package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import services.CadastroAreaService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroAreaSteps {

    CadastroAreaService cadastroAreaService = new CadastroAreaService();

    @Dado("que eu tenha os seguintes dados da area:")
    public void queEuTenhaOsSeguintesDadosDaArea(List<Map<String, String>> linhas) {
        for(Map<String, String> coluna : linhas) {
            cadastroAreaService.setFieldsDelivery(coluna.get("campo"), coluna.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de area")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeArea(String arg0) {
        cadastroAreaService.createDelivery(arg0);
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de areas")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeAreas(String arg0) {
        cadastroAreaService.createDelivery(arg0);
    }

    @Então("o status code da resposta da area deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroAreaService.response.getStatusCode());
    }


    @Dado("que eu recupere o ID da area criada no contexto")
    public void queEuRecupereOIDDaAreaCriadaNoContexto() {
        cadastroAreaService.retrieveIdDelivery(true);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de areas")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeAreas(String endpoint) {
        cadastroAreaService.deleteDelivery(endpoint);
    }

    @Dado("que eu recupere o ID da area criada no contexto e some um")
    public void queEuRecupereOIDDaAreaCriadaNoContextoESomeUm() {
        cadastroAreaService.retrieveIdDelivery(false);
    }

    @E("que o arquivo de contrato da area esperado é o {string}")
    public void queOArquivoDeContratoEsperadoÉO(String contrato) throws IOException {
        cadastroAreaService.setContract(contrato);
    }

    @Então("a resposta da requisição deve estar em conformidade com o contrato da area selecionado")
    public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validateResponse = cadastroAreaService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }



}