# language: pt

Funcionalidade: Excluir uma schedule
  Como usuário da API
  Quero excluir uma schedule
  Para que o registro seja excluído do sistema
  Contexto: Cadastro bem-sucedido de schedule
    Dado que eu tenha os seguintes dados da schedule:
      | campo     | valor               |
      | startTime | 2024-11-27T10:34:56 |
      | endTime   | 2024-05-27T12:34:56 |
    Quando eu enviar a requisição para o endpoint "/api/schedules" de cadastro de schedules
    Então o status code da resposta da schedule deve ser 201

  @regressivo
  Cenário: Deve ser possível excluir uma schedule
    Dado que eu recupere o ID da schedule criada no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/schedules" de deleção de schedules
    Então o status code da resposta da schedule deve ser 204

  @regressivo
  Cenário: Não deve ser possível excluir uma schedule com ID inexistente
    Dado que eu recupere o ID da schedule criada no contexto e some um
    Quando eu enviar a requisição com o ID para o endpoint "/api/schedules" de deleção de schedules
    Então o status code da resposta da schedule deve ser 404