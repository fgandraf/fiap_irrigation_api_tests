# language: pt

Funcionalidade: Cadastro de nova schedule
  Como usuário da API
  Quero cadastrar uma nova schedule
  Para que o registro seja salvo corretamente no sistema

  @regressivo
  Cenário: Cadastro bem-sucedido de schedule
    Dado que eu tenha os seguintes dados da schedule:
      | campo     | valor               |
      | startTime | 2024-11-27T10:34:56 |
      | endTime   | 2024-05-27T12:34:56 |
    Quando eu enviar a requisição para o endpoint "/api/schedules" de cadastro de schedules
    Então o status code da resposta do schedule deve ser 201
    E que o arquivo de contrato da schedule esperado é o "Cadastro Schedule bem-sucedido"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado da schedule