# Teste Petz

Repositório com CRUD do teste de desenvolver backend Petz

### Sobre o projeto ###

Aplicação Java Web. Além de código fonte e configurações, segue também a documentação descrevendo como o projeto foi
desenvolvido, e o que foi adotado para implementar teste solicitado.

Essa aplicação foi desenvolvida seguindo os modelos de arquitetura MVC e REST. Na solução também tem um banco de dados e
ambiente de execução embutidos.

### Tecnologias Utilizadas ###

* Java versão 13.
* Lombok: Utilizada gerar os getters e setter das classes de domínio
* JPA / Hibernate: mapeamento de entidades persistentes em pojos de domínio.
* Bean Validations: framework para definição de regras de validação em entidades JPA via anotações.
* Logback: geração de logs.
* Spring Data JPA: Tecnologia utilizada gerar parte do código relacionado a camada de persistência. Na aplicação foi
  escrito os contratos de persistência, que realizam a criação dos comandos de manipulação (CRUD), consultas simples e
  complexas.
* Spring Web MVC: framework web usado como solução MVC para a definição de componentes seguindo o modelo de arquitetura
  REST
* Estrutura de pacotes e classes baseada no modelo Clean
  Architecture (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

### Camadas e pacotes ###

* br.com.dca.configs: Pacote com as configurações necessárias para o swagger.
* br.com.dca.domains: Pacote contendo as entidades persistentes, mapeadas com anotações JPA.
* br.com.dca.exceptions: Pacote contendo as exceções customizadas.
* br.com.dca.gateways.repositories: Pacote contendo as interfaces de persistência.
* br.com.dca.gateways.impl: Pacote contendo as implementações para orquestrar os componentes de acesso a dados.
* br.com.dca.gateways.http: Pacote contendo os componentes Controller e serviços REST.
* br.com.dca.usecases: Pacote contendo os componentes de negócio, que são responsáveis por realizar todas as validações
  de negócio, orquestrar os componentes de acesso a dados, transação com banco de dados e eventuais validações.

### Tecnologias Adicionais ###

* Banco de dados: H2 embutido na aplicação. O banco é criado durante o startup da aplicação. No fim da execução o banco
  é destruído.
* Testes: os testes são definidos como Use Case do JUnit. Os testes dos serviços REST contam com: Spring Web MVC para
  mock da infra-estrutura web; JsonPath e hamcrest para acesso e assertions no conteúdo Json. Os testes foram
  disponibilizados na estrutura src/test/java.
* Spring Boot: tecnologia utilizada para criar um ambiente embutido de execução, utilizei essa tecnologia para
  simplificar o uso do Spring e para controlar o escopo do banco. No arquivo src/main/resources/application.yaml constam
  algumas propriedades do Spring Boot para o projeto.
* Tomcat embutido: disponibilizado pelo Spring Boot.
* Maven: gestão de ciclo de vida e build do projeto.
* Swagger: documentação e execução de apis
* Spock Framework: framework para criação dos testes BDD dos usecases de negócio. Documentação de referência
  em: http://spockframework.org/spock/docs/1.3/all_in_one.html

### Pré-requisitos ###

* JDK - versão 13.0.2 do Java;
* Qualquer IDE Java com suporte ao Maven;
* Maven - para build e dependências.

Após baixar os fontes, para executar a aplicação execute o comando maven:

$ mvn clean package spring-boot:run

Para visualizar a apis abra o browser de sua preferência e digite:

http://localhost:8080/swagger-ui.html
