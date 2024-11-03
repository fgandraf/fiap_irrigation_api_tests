# language: pt

Funcionalidade: Excluir uma area
  Como usuário da API
  Quero excluir uma area
  Para que o registro seja excluído do sistema
  Contexto: Cadastro bem-sucedido da area
    Dado que eu tenha os seguintes dados da area:
      | campo       | valor        |
      | description | Asa Norte    |
      | location    | Parque Norte |
      | size        | 15           |
    Quando eu enviar a requisição para o endpoint "/api/areas" de cadastro de area
    Então o status code da resposta da area deve ser 201

  @regressivo
  Cenário: Deve ser possível excluir uma area
    Dado que eu recupere o ID da area criada no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/areas" de deleção de areas
    Então o status code da resposta da area deve ser 204

  @regressivo
  Cenário: Não deve ser possível excluir uma area com ID inexistente
    Dado que eu recupere o ID da area criada no contexto e some um
    Quando eu enviar a requisição com o ID para o endpoint "/api/areas" de deleção de areas
    Então o status code da resposta da area deve ser 404