## API REST para o Consulta e Geolocalização de Cidades do Brasil
> A API REST consiste de uma ferramenta de consulta utilizando técnica de paginação para performance e realizando cálculos de geolocalização.

[![Spring Badge](https://img.shields.io/badge/-Spring-brightgreen?style=flat-square&logo=Spring&logoColor=white&link=https://spring.io/)](https://spring.io/)
[![Maven Badge](https://img.shields.io/badge/-Maven-000?style=flat-square&logo=Apache-Maven&logoColor=white&link=https://maven.apache.org/)](https://maven.apache.org/)
[![Docker Badge](https://img.shields.io/badge/-Docker-information?style=flat-square&logo=Docker&logoColor=white&link=https://www.docker.com/products/docker-hub/)](https://www.docker.com/products/docker-hub/)
[![PostgreSQL Badge](https://img.shields.io/badge/-PostgreSQL-blue?style=flat-square&logo=PostgreSQL&logoColor=white&link=https://www.postgresql.org/)](https://www.postgresql.org/)
[![Heroku Badge](https://img.shields.io/badge/-Heroku-blueviolet?style=flat-square&logo=Heroku&logoColor=white&link=https://id.heroku.com/)](https://id.heroku.com/)


<img align="right" width="400" height="300" src="https://github.com/willdkdevj/assets/blob/main/Spring/spring-framework.png">

## Descrição da Aplicação
A aplicação consiste em uma API (*Application Programming Interface*) REST (*Representational State Transfer*), sendo aplicado o modelo cliente/servidor na qual tem a função de enviar e receber dados através do protocolo HTTP, sendo o seu principal objetivo permitir a interoperabilidade entre aplicações distintas. Mas nesta aplicação, o intuito é emula um serviço web que interage com um serviço de banco de dados a fim de consultar registro sobre países, estados, e cidades. Quanto a consulta a estados e a cidades, estão disponíveis apenas os registros do Brasil. Além disso, é possível realizar cálculos de distância entre dois parâmetros de localização, neste caso, entre duas cidades para obter a distância entre ambos.

Referente a consulta, como ela pode retornar um volume considerável de registros é utilizado o recurso de paginação a fim de obter um ganho de performance ao obter e na apresentação dos dados. Já os cálculos estão disponíveis por meio de dois modelos, o primeiro é habilitado através de extensões disponíves através do SGBD PostgreSQL, que fornecem funções que automatizam o cálculo, já o segundo, foi a partir do desenvolvimento de métodos que recebem o diâmetro do planeta Terra e realiza o cálculo através da passagem de Pontos de Localização, possível ao implementar a classe Point do springframework.

No decorrer deste documento é apresentado com mais detalhes sua implementação, descrevendo como foi desenvolvida a estrutura da API, suas dependências e como foi colocado em prática a implementação dos cálculos e listagem por paginação. Além disso, como foi implementado o Spring Boot, para agilizar a construção do código e sua configuração, conforme os *starters* e as suas dependências. Bem como, o Spring Data JPA, que nos dá diversas funcionalidades permitindo uma melhor dinâmica nas operações com bancos de dados e sua manutenção. Até o seu deploy na plataforma Heroku para disponibilizá-la pela nuvem ao cliente.

## Principais Frameworks
Os frameworks são pacotes de códigos prontos que facilita o desenvolvimento de aplicações, desta forma, utilizamos estes para obter funcionalidades para agilizar a construção da aplicação. Abaixo segue os frameworks utilizados para o desenvolvimento este projeto:

**Pré-Requisito**: Java 11 (11.0.10 2021-01-19 LTS) / Maven 3.6.3 / Docker 20 (20.10.6 build 370c289)

| Framework       | Versão | Função                                                                                            |
|-----------------|:------:|---------------------------------------------------------------------------------------------------|
| Spring Boot     | 2.4.4  | Permite agilizar o processo de configuração e publicação de aplicações do ecossistema Spring      |
| Spring Actuator | 2.4.4  | Fornece endpoints que permite verificar o estado da aplicação através de métricas                 |
| Spring Data JPA | 2.4.4  | Facilita na interação com database permitindo uma fluídez na persistência dos dados de modo geral |
| Hibernate       | 6.1.7  | Permite automatizar as tarefas com o banco de dados facilitando o código da aplicação             |
| Lombok          | 1.18.18| Permite reduzir a verbosidade do código através de anotações                                      |
| MapStruct       | 1.4.1  | Permite o mapeamento entre bean Java com base de uma abordagem de conversão sobre configuração    |
| JUnit 	      | 5.7.1  | Permite a realização de testes unitários de componentes da aplicação Java                         |
| Mockito         | 3.6.28 | Permite criar objetos dublês a fim de realizar testes de unidade em aplicações Java               |
| Swagger         | 2.9.2  | Possibilita a definição e a criação de modo estruturado a documentação de API REST                | 

### Utilizando Docker para Disponibilizar o PostgreSQL
Pode ser um Sistema de Gerenciamento de Banco de Dados (SGBD) PostgreSQL instalado na máquina, mas para o projeto foi construído um container Docker personalizado (através de um *Dockerfile*) com o sistema encapsulado, na qual ao executá-lo, é criado uma database já com todas as tabelas necessárias para utilizar na API. Mas caso exista um SGBD PostgreSQL instalado no host, no diretório ``docker/scriptSQL`` estão scripts SQL para serem executados no sistema.

Para utilizar o SGBD através do Docker digite o *snippet* abaixo para configurar o container e deixá-lo apto para uso da API.
```sh
docker run --name postgres-cities -d -p 5432:5432 -e POSTGRES_USER=postgres_supernova_user  -e POSTGRES_PASSWORD=supernova_pass -e POSTGRES_DB=citiesBrazil willdkdev/postgres-cities:latest
```

O comando run tentará encontrar a imagem no repositório local, onde não encontrará, desta forma, ele acessará o Docker Hub para encontrar e baixar a imagem **postgres-cities**, fornendo uma *alias* para invocá-lo ao invés de utilizar o container ID. Também é exposta a porta 5432 atrelando-a a porta de mesmo número no host, além disso, são fornecidas variáveis (enviroment (-e)) para passar os parãmetros (usuário, senha, database) para configuração do PostgreSQL. Enquanto o parâmetro willdkdev/postgres-cities:latest referente ao proprietário, imagem e versão.

## Utilizando o Pageable para Paginação de Grandes Volumes de Dados
A paginação é utilizida ao realizar uma requisição de consulta a um grande volume de dados, ela possibilita filtrar a quantidade de registros que serão retornados informando mais parâmetros que funcionam como filtros especificos para a *Query*. Desta forma, é possível restringir a quantidade de registros que serão apresentados por intervalo de páginas. É possível realizar estes "filtros" na própria query, mas a paginação permite que seja passado como parâmetro da própria **URI** utilizando os recursos do Spring Data, através da dependência ``spring-boot-starter-data-jpa``.

Agora, se faz necessário que a interface *Repository* extenda a também interface JpaRepository. Pois a interface JpaRepository também extende a **PagingAndSortingRepository**, sendo ela que possibilita interpretar o **Pageable** recebido por parâmetro através de um *Resource* utilizando o tipo de requisição GET, desta forma, utilizando as vantagens do fator de multi-herança.
```java
@GetMapping("/users")
public ResponseEntity execute(Pageable page){
    return ResponseEntity.ok(service.execute(page));
}
```

Também é possível passar parâmetros padrões (*default*) a fim de serem aplicados assim que o *resouce* é invocado, ao utilizar a anotação **@PageableDefault**. Para isso, se faz necessário passar alguns atributos para sua validação, são eles:
* **Page** - identifica qual a página a ser retornada de uma lista;
* **Size** - identifica a quantidade de registros a serem apresentados na página;
* **Sort** - define a ordenação dos registros através do nome do campo;
* **Direction** - define o tipo de ordenação a ser aplicada a paginação (Crescente (ASC) / Decrescente (DESC)).
```java
@GetMapping("/users")
  public ResponseEntity execute(
         @PageableDefault(sort = "name",
                 direction = Sort.Direction.ASC,
                 page = 0,
                 size = 10) Pageable page){

      return ResponseEntity.ok(service.execute(page));
  }
```

Assim como, direto na **URI** conforme é apresentado na imagem, através da ferramenta Insomnia que apresenta uma requisição do tipo GET.

![Request GET - ListAll](https://github.com/willdkdevj/assets/blob/main/Heroku/deploy_heroku_person.png)

## Utilizando Cálculos para Retornar a Distância entre Dois Pontos
Umas das funções da API é retornar a distância entre dois pontos para obter seu valor em **Milhas** e **Metros**, o cálculo da distância entre dois pontos no espaço é um assunto discutido na *Geometria Analítica* e tem suas bases no teorema de Pitágoras. A distância entre dois pontos no espaço é o comprimento do menor segmento de reta que liga esses dois pontos, para isso, é necessário calcular antes a distância entre dois pontos no plano. 

### Habilitando Extensões para Uso de Funções do PostgreSQL para Geolocalização
Para demonstrar como foi realizado o uso do conceito TDD com o framework abaixo vou apresentar o que foi realizado para construção do método registerPerson() na classe de ``Service`` MVC. Mas conforme foi explanado anteriormente, foi necessário criar objetos para simular as classes em entidades com dados estáticos para emular entradas de informações aos objetos com classes construtoras (*builders*), que se trata de classes com valores estáticos para seus atributos, seguindo os conceitos do DTO. E para realizar esta conversão de uma classe DTO em uma entidade, foi utilizado o framework ``MapStruct``. Ele simplifica o mapeamento de objetos DTO para objetos de Entidade permitindo gerar código com base em uma abordagem de conversão utilizando uma interface.

Para realizar esta abordagem é utilizada a anotação @Mapper na interface que mapeia quais são os objetos a serem convertidos atraves da sobreescritas de seus métodos.

```java
@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}
```

Neste caso especifico, foi necessário informar ao MapStruct que tipo de dado o objeto DTO está passando para o objeto Bean, que neste caso, é um atríbuto LocalDate, enquanto o DTO é uma String. Desta forma, a anotação @Mapping atrela estes campos distintos informando o formato do dado.

Agora que está esclarecido como os dados dos objetos serão utilizados pelos testes e seus objetos mocados, vamos para a clase de teste de Serviço (**Service**). Antes de mais nada, foi anotada a classe de teste com a anotação @ExtendWith(MockitoExtension.class) que injeta nesta classe a biblioteca do ``Mockito`` a fim de permitir *mocar* objetos em nossa classe utilizando mais duas anotações @Mock e @InjectMocks. O objetivo de mocar objetos é criar objetos dublês que simulam o comportamento de objetos reais de forma controlada.

```java
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonMapper mapper = PersonMapper.INSTANCE;

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

```

Observe que "mocamos" objetos PersonRepository e PersonService para inserirmos no contexto da classe de teste Mockito, além disso, criamos uma instância constante de Mapper através do PersonMapper, para realizarmos a conversão de objetos a serem utilizados nos testes utilizando de suas facilidades de já instanciar os objetos e atribuir valores aos seus atríbutos.

Agora, depois de todos estes passos, vamos ao método registerPerson() para realizar o teste inicial. O conceito do TDD preconiza que o teste tem que falhar antes de realizar a construção lógica para tentar validá-lo. Desta forma, foi criado o método testPersonDTOProvidedThenReturnSavedMessage() na qual é anotado com @Test a fim de informar que é um método de teste ao JUnit e executamos sem implementação alguma, só para ocorrer a falha. Depois foi implementado o código com a lógica necessária para fazê-lo passar. E finalmente, o mesmo é refatorado a fim de torná-lo mais "limpo", deixando-o mais legível.

```java
	@Test
    void testPersonDTOProvidedThenReturnSavedMessage() {
    	// given
        PersonDTO personDTO = PersonDTO.builder().build().toPersonDTO();
        Person convertedPerson = mapper.toModel(personDTO);

        // When
        when(repository.save(any(Person.class))).thenReturn(convertedPerson);

        // Then
        MessageResponseDTO expectedSuccessMessage = service.registerPerson(personDTO);
        MessageResponseDTO expectedSuccessMessageID = createInspectMessageResponse(convertedPerson.getId());

        assertEquals(expectedSuccessMessageID, expectedSuccessMessage);
    }
```
Desta forma, em ``given`` é o que parâmetro fornecido ao método, que recebe um objeto do tipo PersonDTO, e o que é retornado após a invocação do método da JPA para salvar o conteúdo presente no objeto em ``when``, que no caso o método save() recebe um Bean convertido do DTO e retorna novamente um Bean do objeto passado. Na qual em ``then`` é realizado a contraprova ao checar os objetos retornados ao invocar o método através do objeto mocado de Service (service.registerPerson()), em comparação com uma Classe de Construção para testes (MessageBuilder) a fim confirmar se a resposta serão iguais, confirmadas através do método do JUnit **assertEquals**.

Este processo de verificação é realizado para testar os returnos esperados pela aplicação, assim como, as eventuais exceções a serem tratadas para devolutiva ao usuário.  

### Desenvolvimento de Lógica para Cálculo de Distância


## A Hospedagem na Plataforma Heroku
Para hospedar nosso código na plataforma **Heroḱu** é necessário criar uma conta e atrelá-la a conta no **GitHub**, desta forma, ao logar no *Dashboard* do Heroku é criado um novo aplicativo apontando a conta do GitHub informando o nome do repositório em que está o projeto. Além disso, é habilitado a opção de *deploy* automático, para que todas as vezes que for realizado um *PUSH* para o repositório seja realizado o deploy da aplicação.

Como a aplicação está com a versão 11 do Java é necessário passar um parâmetro de configuração ao Heroku, pois por padrão, o Heroku suporta aplicações com a versão 8. Desta forma, no diretório raiz do projeto é criado o arquivo de configuração ``system.properties`` com o seguinte snippet
```sh
java.runtime.version=11
```

Este processo de criação foi realizado antes de "subir" a aplicação para a plataforma, desta maneira, ela reconhece a aplicação com a versão informada.

![Deploy Heroku](https://github.com/willdkdevj/assets/blob/main/Heroku/deploy_heroku_person.png)

Para acessar a aplicação diponibilizada em *cloud*, acesse o seguinte link <https://apipeople-dio.herokuapp.com/api/v1/people>. Desta forma, é possível realizar as interações com a ferramenta das requisições HTTP.

## Como Está Documentado o Projeto
O framework ``Swagger UI`` auxilia na criação da documentação do projeto, por possuir uma variedade de ferramentas que permite modelar a descrição, consumo e visualização de serviços da API REST. No projeto foi incluída suas dependências (Swagger-UI, Swagger-Core) para habilitá-lo para uso na aplicação, desta forma, no *snippet* abaixo é apresentado o Bean principal para sua configuração, presente na classe SwaggerConfig.

```java
@Bean
public Docket api() {
return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(apis())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(constructorApiInfo());
}
```

A especificação da API consiste na determinação de parâmetros de identificação e os modelos de dados que serão aplicados pela API, além de suas funcionalidades. Entretanto, o Swagger por padrão lista todos os endpoints retornando os códigos 200, 201, 204,401,403 e 404, mas é possível especificar quais são os códigos do protocolo HTTP que sua aplicação utiliza ao utilizar a anotação @ApiResponses.

![Framework Project - Test](https://github.com/willdkdevj/assets/blob/main/Spring/swagger_panel_person.png)

O método apis() permite utilizar a classe **RequestHandlerSelectors** para filtrar qual é o pacote base (*basePackage*) a fim de ser listado apenas os seus endpoints. Já o método apiInfo() possibilita inserir parâmetros para descrever dados de informação sobre a aplicação e seu desenvolvedor. Desta forma, o framework Swagger possibilita documentar a API REST de um modo ágil de eficiente as suas funcionalidades. Sua exposição é feita através do link <http://localhost:8080/swagger-ui.html>

## Como Executar o Projeto

```bash
# Para clonar o repositório do projeto, através do terminal digite o comando abaixo
git clone https://github.com/willdkdevj/RESTAPI_PERSONS.git

# Para acessar o diretório do projeto digite o comando a seguir
cd /RESTAPI_PERSONS

# Executar projeto via terminal, digite o seguinte comando
./mvnw spring-boot:run

# Para Executar a suíte de testes desenvolvidas, basta executar o seguinte comando
./mvnw clean test
```

Para testar a API, como a aplicação consome e produz JSON, é necessário utilizar uma ferramenta que permite realizar requisições HTTP neste formato, como o Postman, Insomnia, entre outras. Na diretório ``JSON-TEST-HTTP/``  há um arquivo que pode ser importado a qualquer uma destas ferramentas, onde já estarão formatados os tipos de requisições suportadas pela API para a realização dos testes.

## Agradecimentos
Obrigado por ter acompanhado aos meus esforços para desenvolver este Projeto utilizando a estrutura do Spring para criação de uma API REST 100% funcional, utilizando os recursos do Spring data JPA para facilitar as consultas, o padrão DTO para inclusão e atualização dos dados, além de, listar grandes quantidades de dados paginas, com ordenação e busca, utilizando os conceitos do TDD para implementar testes de integração para validar nossos endpoints com o MockMVC e gerar a documentação de forma automática através do Swagger! :octocat:

Como diria um velho mestre:
> *"Cedo ou tarde, você vai aprender, assim como eu aprendi, que existe uma diferença entre CONHECER o caminho e TRILHAR o caminho."*
>
> *Morpheus - The Matrix*