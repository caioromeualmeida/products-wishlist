# Products-Wishlist

1. **Java**    - Linguagem utilizada no desenvolvimento
2. **Spring**  - Framework escolhido para facilitar o desenvolvimento e escalabilidade
3. **JWT**     - Modo de autenticação 
4. **MongoDB** - Base de dados NoSql
5. **Redis**   - Cache de requisição


### Observações
A arquitetura do projeto procurou ser o mais clean possível. Por ser um unico serviço, optei por não separar por módulos e nem serviços diferentes;
Foi utilizado o MongoDB pois, como foi dito no teste, a API seria bastante requisitada. Por esse motivo também foi escolhido o Redis para cache. 
  - Na base é inserido apenas o id do produto, fazendo com que todos os outros dados sejam buscados conforme solicitados;
  - Em um cenário real, seria necessário ajustar o tempo do cache conforme o número de requisições, servidor etc.

Os endpoints de cadastro de cliente, cadastro de produto e remoção de produto recebem uma lista de string como parâmetro para o produto. O motivo dessa escolha foi visando diminuir o número de acesso à base.

**TOKEN DE ACESSO - JTW** <br>
USER: admin <br>
PASSWORD: password <br>
