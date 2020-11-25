# Sistema de processamento automático de arquivo de retaguarda do Sicredi.

|Dependências |
| ----------- |
|Apache Maven 3.6.3 |
|Java version: 15.0.1|

O projeto tem como objetivos:  

0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma nova coluna.


Formato CSV:  
>agencia;conta;saldo;status  
>0101;12225-6;100,00;A  
>0101;12226-8;3200,50;A  
>3202;40011-1;-35,12;I  
>3202;54001-2;0,00;P  
>3202;00321-2;34500,00;B"  
    
    
    
