# Sobre o projeto
O projeto foi proposto pelo professor Fábio Kon na disciplina de MAC 0321 do curso de Engenharia de Computação da Escola Politécnica da Universidade de São Paulo. A ideia consiste na criação de um feed de notícias e blogs nomeado de **Super Agregador de Notícias**. 

## Como faz para executá-lo?
A execução do programa é bem simples, podendo facilmente ser executado em sistemas Linux e Windows:

>#### Inicialização do servidor em sistemas Linux

``` 
$ ./mvnw spring-boot:run
```

>#### Inicialização do servidor em sistemas Windows

```
> ./mvnw spring-boot:run 
```

## Requisitos do Projeto
O projeto tinha 6 requisitos obrigatórios e 2 opcionais que estão aparecendo na mesma ordem que foram propostos na descrição do exercício:

- [x] Interface por onde o cliente pode requisitar para adicionar fontes de notícias.
- [x] Usuário pode visualizar as fontes existentes, além de adicionar e remover 
- [x] O site mostra ao usuário as notícias presentes nos links que ele adicionou.
- [x] Ao se clicar no título de uma notícia, ou na sua imagem, o usuário é redirecionado para a mesma.
- [x] Possibilidade de filtrar notícias por data e hora.
- [x] Sistema de pesquisas entre as notícias do site com uso de palavras-chave.
- [x] Possibilidade de uso de expressões regulares para pesquisar
- [x] O usuário pode visualizar as palavras que mais ocorrem entre suas notícias, excluindo preposições.

Requisitos extra que nós adicionamos para nos adequar às boas práticas de um projeto.
- [x] Readme bem organizada e que explica para um usuário o que ele precisa saber.
- [ ] Testes automatizados para garantir o bom funcionamento do programa
- [x] Buscar seguir os principios de criação de bons códigos, como SOLID e Design Patterns.

># Sprint 1:
_O primeiro sprint foi o pontapé inicial para o projeto, criando o design do site e o recurso de adicionar fontes de notícias._

* Optamos por usar maven como gerenciador de dependências.
* Escolhemos o arcabouço Spring-boot para criar o projeto.
* O frontend foi feito usando o Bootstrap do Twitter.

### Problemas da versão:
1. Quando o servidor era reiniciado, as fontes cadastradas eram perdidas. 
2. Caso dois usuários diferentes se conectassem ao mesmo tempo, os dados que o servidor tinha na memória (fontes cadastradas de qualquer um), eram mostradas para ambos.

># Sprint 2:
_Nesta fase o site parou de apenas cadastrar fontes e passou a exibir notícias._

* Tornou possível visualizar as notícias de fontes cadastradas
* Para corrigir os problemas da versão 1 do projeto, decidimos salvar cookies nos navegadores dos usuários

### Problemas da versão:
1. Os cookies só salvavam no máximo uma fonte de notícias. 
2. Caso dois usuários diferentes se conectassem ao mesmo tempo, os dados que o servidor tinha na memória (fontes cadastradas de qualquer um), eram mostradas para ambos.
3. As notícias não mostravam de qual fonte veio.
4. Poderia ser um pouco problemático usar o site, porque ao clicar numa notícia, não era aberta em uma nova aba.

># Sprint 3:
_Fase final que implementou sistemas de busca e buscou procurar e corrigir erros de outras versões_

* Criação de sistemas de busca por expressões no frontend javascript
* Criação de um sistema de busca usando algoritmo de AhoCorasick para criar a nuvem de palavras mais comuns
* Procura por erros no nosso projeto e a correção destes
* Busca de acordo com a data inputada pelo usuário
* Organização de uma readme mais bonita e mais agradável
