# Sobre o projeto
Trata-se de um efetivador para transações no Caju benefícios.

# Informações de execução
Com o ambiente previamente configurado para rodar uma aplicação Java, Rodar a classe TesteCajuEfetivadorCartaoApplication.java como Java Application, a aplicação irá subir na porta 8080, chamar a rota através do CURL abaixo usando Insomnia, Postman ou similares... 

curl --request POST \
  --url http://localhost:8080/efetivadorcartao/efetivartransacao \
  --header 'Content-Type: application/json' \
  --data '{
	"account": "123",
	"totalAmount": 3000.00,
	"mcc": "5811",
	"merchant": "PADARIA DO ZE               SAO PAULO BR"
}'

# Regras de negócio
O efetivador retornará três tipos de retorno:
* 00 se a transação for aprovada.
* 51 se a transação for rejeitada.
* 07 se acontecer algum outro tipo de erro durante a tentativa de efetivar a compra.

Se a compra for efetivada, o saldo será gasto na categoria correspondente, caso a conta não possua saldo suficiente na categoria correspondente, a parte faltante será descontada no valor do crédito livre (caso exista valor suficiente para a compra).

O efetivador sempre retornará o status HTTP 200, em caso de erro, será tratado com o código 07.

Caso o comerciante esteja na lista para validação de MCC, o MCC utilizado será o cadastrado para o estabelicimento, e será ignorado o MCC do request.

# L4 - Resposta
Em transações online teriamos a opção de postar a mensagem em uma fila em vez de chamar diretamente o endpoint de processamento síncrono. Assim as transações no cartão físico seriam sempre priorizadas, e as de lojas online poderiam ser processadas um pouco depois, mas ainda mantendo o tempo de retorno da rota HTTP.

# Considerações
* No fim, acabei me repetindo nos testes, por algum motivo não estava reconhecendo os mocks que eu colocava dentro do beforeEach... precisava analisar um pouco mais mas não daria tempo.
* As informações do banco de dados estão todas mockadas, não consegui configurar uma opção melhor a tempo. Os MCCs mencionados foram implementados e funcionarão normalmente. Qualquer conta que for chamada, irá retornar, mas os valores estão fixos, cada categoria (food, meal e cash) vai retornar 1.000,00. O estabelecimento "Estabelecimento Teste MC" irá retornar o MCC 5811 em qualquer situação.
* Em alguns lugares é convencionado nomear as variáveis e métodos em inglês, por hábito (convenção de onde trabalho), acabei fazendo em português e não me atentei ao fato do nome das variáveis do payload estarem em inglês, acabou ficando "torto", mas não terei tempo de trocar tudo.
