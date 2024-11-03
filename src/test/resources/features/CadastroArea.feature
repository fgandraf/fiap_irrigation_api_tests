# language: pt

Funcionalidade: Cadastro de nova area
  Como usuário da API
  Quero cadastrar uma nova area
  Para que o registro seja salvo corretamente no sistema

  @regressivo
  Cenário: Cadastro bem-sucedido da area
    Dado que eu tenha os seguintes dados da area:
      | campo       | valor        |
      | description | Asa Norte    |
      | location    | Parque Norte |
      | size        | 15           |
    Quando eu enviar a requisição para o endpoint "/api/areas" de cadastro de areas
    Então o status code da resposta da area deve ser 201
    E que o arquivo de contrato da area esperado é o "Cadastro Area bem-sucedido"
    Então a resposta da requisição deve estar em conformidade com o contrato da area selecionado