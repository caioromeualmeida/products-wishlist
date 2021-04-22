# Products-Wishlist

1. **Java**    - Linguagem utilizada no desenvolvimento
2. **Spring**  - Framework escolhido para facilitar o desenvolvimento e escalabilidade
3. **JWT**     - Modo de autenticação 
4. **MongoDB** - Base de dados NoSql
5. **Redis**   - Cache de requisição


### Observações sobre a arquitetura
A arquitetura do projeto procurou ser o mais clean possível. Por ser um unico serviço, optei por não separar por módulos e nem serviços diferentes;
Foi utilizado o MongoDB pois, como foi dito no teste, a API seria bastante requisitada. Por esse motivo também foi escolhido o Redis para cache. 
  - Na base é inserido apenas o id do produto, fazendo com que todos os outros dados sejam buscados conforme solicitados;
  - Em um cenário real, seria necessário ajustar o tempo do cache conforme o número de requisições, servidor etc.

Os endpoints de cadastro de cliente, cadastro de produto e remoção de produto recebem uma lista de string como parâmetro para o produto. O motivo dessa escolha foi visando diminuir o número de acesso à base.

**Observações gerais**
1. Os produtos podem ser enviados no momento de cadastrar um novo cliente ou em um momento separado.
2. Não foi gerado um endpoint localhost:8080/api/customers/ID/products pois as informações seriam identicas à localhost:8080/api/customers/ID/, com exceção do nome e e-mail do cliente. Caso necessário, é de fácil implementação.


**TOKEN DE ACESSO - JTW** <br>
USER: admin <br>
PASSWORD: password <br>

**Modo de usar**
1. Para facilitar o desenvolvimento e não precisar instalar o MongoDB e o Redis, basta entrar na pasta .docker e executar o comando docker-compose up
2. Após receber a mensagem de que ambos os ambientes estão no ar, basta rodar o ./mvn clean install

* Obs: foi utilizado o mapstruct no projeto para facilitar a criação dos DAOs. As classes de implementação são geradas na fase install do maven.

3. Para consumir a API, primeiro é necessário fazer uma requisição POST ao /login passando o token de acesso, conforme documentação da collection. Será retornado um token que deverá ser passado no header das próximas requisições.


**Documentação do POSTMAN com as collections inclusas.**  <br>
https://documenter.getpostman.com/view/4512599/TzJvexBz    <br>
https://www.getpostman.com/collections/f428cf431738376fc7e8

**PONTOS DE MELHORIA**
1. Transformar o products_wishlist em microsserviço utilizando Spring Cloud.
2. Por ser uma aplicação simples, foi optado por subir a parte de segurança no mesmo projeto. Isso essencialmente deveria ser um novo microserviço.

Bons testes e quaquer dúvida pode enviar para caioromeualmeida@gmail.com
