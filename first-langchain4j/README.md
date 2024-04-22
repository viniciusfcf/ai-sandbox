# first-langchain4j

Aplicação simples utilizando openchat:7b-v3.5

Disponibiliza dois endpoints:
- /poem
  - Retorna um poema sobre Flores com 2 linhas
- /who
  - Retorna quem é a pessoa que manda em minha casa
    - Nesse exemplo estou utilizando RAG, com a extensão easy rag.
- /review
  - Analiza uma review como POSITIVA ou NEGATIVA e gera uma resposta adequada para ser postada como resposta do review
- /request-1
  - Exemplo de pedido sobre um buraco que apareceu na rua depois de uma chuva
- /request-2
  - Exemplo de pedido sobre a religação da energia em um apartamento alugado
- /request-3
  - Exemplo de pedido sobre o barulho em um bar (não existe empresa mapeada para isso)

# Pré requisitos
- Ollama
- Java 21
- Quarkus CLI

# Contras
- Utilizando easy rag ele incluiu o documento em todas as requisições. Não tenho conhecimento profundo nisso, mas atrapalhou em algums respostas.