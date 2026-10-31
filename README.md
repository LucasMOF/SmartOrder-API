# SmartOrder Parser API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2+-brightgreen?style=for-the-badge&logo=spring)
![Google Gemini](https://img.shields.io/badge/Google_Gemini-AI-blue?style=for-the-badge&logo=google)

Uma API RESTful desenvolvida em **Java 21** e **Spring Boot** que utiliza a inteligência artificial do **Google Gemini** para transformar textos desestruturados e informais de clientes (como mensagens de WhatsApp) em dados estruturados (JSON) prontos para processamento em sistemas de PDV e Delivery.

## O Problema que Resolvemos

Aplicativos de delivery e restaurantes frequentemente recebem pedidos por texto livre, o que exige intervenção humana para digitar os itens no sistema. Esta API automatiza esse processo, interpretando variações de linguagem, gírias e observações complexas, garantindo a extração limpa e padronizada dos itens, quantidades e restrições.

## Tecnologias e Arquitetura

Este projeto foi desenhado focando em escalabilidade, manutenibilidade e clean code, utilizando os recursos mais modernos da stack Java:

* **Java 21:** Uso intensivo de `Records` para DTOs imutáveis e `Text Blocks` para Engenharia de Prompt limpa.
* **Spring Boot 3:** Framework base para o ecossistema REST.
* **Integração de IA:** Cliente HTTP construído para consumo nativo da API do Google Gemini (`gemini-3.8-flash`).
* **Design Pattern em Camadas:**
    * `Controller:` Exposição clara do contrato REST.
    * `Service:` Orquestração de negócio e Engenharia de Prompt.
    * `Integration:` Isolamento da infraestrutura de comunicação com APIs externas.
* **Resiliência:** Tratamento global de exceções via `@ControllerAdvice` para padronização de respostas de erro (Timeouts, Bad Gateway, etc.).

## Como Executar Localmente

**Pré-requisitos:** Java 21+, Maven e uma API Key do Google AI Studio.

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/LucasMOF/SmartOrder-API.git
   cd SmartOrder-API
   ```

2. **Configure a sua Chave de API:**
   Abra o arquivo `src/main/resources/application.properties` e insira sua API Key e URL do Gemini:
   ```properties
   gemini.api.key=SUA_CHAVE_AQUI
   gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-3.8-flash:generateContent
   ```

3. **Rode a aplicação via Maven:**
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints

### `POST /api/v1/orders/parse`
Processa o texto bruto de um pedido e retorna uma lista estruturada de itens.

**Request Body (JSON):**
```json
{
  "rawText": "Boa noite, chefe! Manda pra mim 2 x-bacon caprichados, mas tira a cebola de um deles por favor. Quero também uma batata frita média e duas cocas lata bem geladas."
}
```

**Response (200 OK):**
```json
{
  "itens": [
    {
      "produto": "x-bacon",
      "quantidade": 2,
      "observacoes": "tirar cebola de um deles"
    },
    {
      "produto": "batata frita",
      "quantidade": 1,
      "observacoes": "média"
    },
    {
      "produto": "coca lata",
      "quantidade": 2,
      "observacoes": "bem geladas"
    }
  ]
}
```

**Response de Erro Padronizado (Exemplo 502 Bad Gateway):**
```json
{
  "timestamp": "2026-10-31T18:30:00",
  "status": 502,
  "error": "Bad Gateway - Falha de Integração com IA",
  "message": "O serviço de IA está temporariamente indisponível."
}
```