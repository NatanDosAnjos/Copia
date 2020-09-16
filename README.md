# Copia
Testado em Linux e Windows, pode ser que funcione em MAC e de mais Sistemas Operacionais.  

Se o script estiver executando toda partição conectada ao computador será copiada.  

A pasta principal chamada "The Dir" será criada no diretório principal do usuário que estará executando o script.  

Cada mídia copiada terá uma pasta com seu nome criada dentro do diretório principal, o diretório **"The Dir".**

##### O script aceita dois tipos de argumentos:

- "nonVerbose" para executar sem mostrar os arquivos copiados no prompt (qualquer outro argumento que não seja "nonVerbose" na primeira posição tornará o script verboso)
 
- [nome-do-arquivo.extensão ou nome da pasta a ser copiada] | Vários nomes podem ser passados como parâmetros.  
 
 
 **ATENÇÃO: Se parâmetros forem ser passados o primeiro, OBRIGATÓRIAMENTE, deve ser o parâmetro de verbosidade.**  
 *Ex:*
 
 - java -jar import.jar nonVerbose Musicas trabalho.doc
 - java -jar import.jar verbose trabalho.doc Pictures
 - java -jar import.jar
  
 Note que é possível copiar apenas alguns arquivos ou diretórios expecíficos da mídia que será conectada.  
 Caso algum argumento passado não coincidir com o nome de algum arquivo ou pasta no dispositivo a ser copiado ele será ignorado e a cópia prosseguirá.
  
 
