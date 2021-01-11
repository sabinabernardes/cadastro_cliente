# REST API usando Spring Boot, Hibernate, JPA e H2 Database 

### Este artigo tem como objetivo montar uma REST API usando Spring Boot, Hibernate, JPA e H2 Data Base. Para simular o cadastro de clientes.

## Antes de começar precisaremos dos seguintes itens instalados:
1. 	Java JDK(v11)
2. 	Maven 
3. 	Spring Tools 4 (IDE baseada no Eclipse)
4. 	Postman 

## Criando a aplicação

#### File>New>Spring Starter Project
#### Preencher os campos abaixo
1.Name
2.Group
3.Artifact
4.Package

![iniciando o projeto](https://github.com/sabinabernardes/cadastro_cliente/blob/main/Spring%20starter.PNG)


#### Adicionar as seguintes dependências 

1.	Spring Boot DevTools (Ajuda na configuração automática)
2.	Spring Data Jpa (Java persistence API)
3.	Spring Web (Spring padrão MVC)
4.	H2 Database (Banco de dados em memória)


### As dependências foram adicionadas no arquivo pom.xml conforme mostradas abaixo 

```
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.hibernate.validator</groupId>
			<artifactId>hibernate-validator-cdi</artifactId>
			<version>7.0.0.Final</version>
		</dependency>

	</dependencies>
	
```	


 https://github.com/sabinabernardes/cadastro_cliente/blob/main/pom.xml
 
 ## Estrutura de projeto
 
#### Neste projeto usaremos a estrutura MVC ( Model, View, Controller)

## Classe Cliente Model

#### Classe reponsavel pela parte dos dados do projeto 

### Para o cadastro de clientes usaremos  as seguintes variaveis  

##### public Integer id ; 
##### public String nome;
##### public String email;
##### public String cpf;
##### public Date dataNasc;

### Apos adicionar as variaveis usaremos as anotações abaixo 
##### @Entity (informa que uma classe também é uma entidade)
##### @Id (campo/atributo relacionado à chave primária)
##### @GeneratedValue(strategy = GenerationType.IDENTITY) (identificador único da entidade será gerenciada pelo provedor de persistência, usada abaixo do @id)
##### @Column(nullable = false)	(Mantem o nome da variavel como o da coluna no banco de dados)
##### @JsonFormat(pattern = "dd/MM/yyyy") (Formata o Json ex: 01/01/1998)

#### Botão Direito > Source adicionar os itens abaixo

1. •	Generated Construtor using  Fields   (Selecionar todos os campos)
2. •	Generated Getters and Setters	     (Selecionar todos os campos)
3. •	Generated hashCode() and equals()    (id)
4. •	Generated toString()...              (Selecionar todos os campos)

#### O Código abaixo mostra a classe Cliente completa 

```

package com.sabinabernardes.crm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Integer id ;
	
	@Column(nullable = false)
	public String nome;
	

	@Column(nullable = false,unique = true)
	public String email;
	

	@Column(nullable = false,unique=true)
	public String cpf;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")	
	@Column(nullable = false)
	public Date dataNasc;
	
	public Cliente () {
		
	}

	public Cliente(Integer id, String nome, String email, String cpf, Date dataNasc) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return nome;
	}

	public void setName(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", name=" + nome + ", email=" + email + ", cpf=" + cpf + ", dataNasc=" + dataNasc
				+ "]";
	}

}

```


## Classe Controller

##### Responsável por gerenciar as requisições 

#### Anotações utilizadas nesta classe 

##### @RestController (retorna o objeto e os dados do objeto para gravar  diretamente na resposta HTTP como JSON ou XML.)
##### @RequestMapping("/clientes") (indica que o controlador, que neste caso é a classe Cliente, receberá as requisições feitas à URL)
##### @Autowired (injeção de classes/dependências no Spring)
##### @GetMapping (é uma anotação composta que atua como um atalho para @RequestMapping(method = RequestMethod. GET) )
##### @PostMapping (é uma anotação composta que atua como um atalho para @RequestMapping(method = RequestMethod. PUT))
##### @ResponseStatus(HttpStatus.CREATED) ( declara o código de status da resposta HTTP)



```
package com.sabinabernardes.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sabinabernardes.crm.model.Cliente;
import com.sabinabernardes.crm.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")


public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public List<Cliente> listar() {
		
		return clienteRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@RequestBody Cliente cliente) {
	return clienteRepository.save(cliente);	
	}
	}
```

## Classe Cliente Repository

##### Responsável por isolar os objetos ou entidades do domínio do código que acessa o banco de dados
##### Criar Interface


```
package com.sabinabernardes.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sabinabernardes.crm.model.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente,Integer>{
	

}
```
## Teste de cadastro utilizando o Postman 

### Comando Put

##### Utilizando o Postman podemos fazer o teste de cadastro do primeiro cliente no Json.
A partir da porta 8080 e o path /clientes 
Conforme mostrado abaixo após o cadastro a resposta do sistema foi 201 indicando que o cadastro foi realizado .

![](https://github.com/sabinabernardes/cadastro_cliente/blob/main/postman_put.PNG)

### Comando Get

##### Com o comando Get podemos receber os dados do Json com as informações do cliente

![](https://github.com/sabinabernardes/cadastro_cliente/blob/main/postman_get.PNG)

## Ajustando as exceções e erros no cadastro

### Criando a classe ApiErrors

```
package com.sabinabernardes.crm.api_errors;

import java.util.Arrays;
import java.util.List;



public class ApiErrors {

	
	public ApiErrors(List<String> errors) {}
	public ApiErrors(String message) {Arrays.asList(message);}
}

```

### Criando a classe ApplicationControllerAdvice

```
package com.sabinabernardes.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sabinabernardes.crm.api_errors.ApiErrors;

@RestControllerAdvice

public class ApplicationControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidantionErrors(MethodArgumentNotValidException ex) 
	{
		BindingResult bindingResult = ex.getBindingResult();
		List<String> messages = bindingResult.getAllErrors().stream()
			.map(objectError -> objectError.getDefaultMessage())
			.collect(Collectors.toList());
		return new ApiErrors(messages);
	}
	
	

}

```
## Adicionando as exceções na classe Cliente 

### Anotações utilizadas para cada variavel

##### @NotEmpty(message = "{campo.nome.obrigatorio}")
##### @Length(max = 100,message="{campo.nome.caracteres}")
##### @NotNull(message="{campo.nome.nulo}")

#### Código atualizado da Classe Cliente

```
package com.sabinabernardes.crm.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Integer id ;
	
	@Column(nullable = false)
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	@Length(max = 100,message="{campo.nome.caracteres}")
	@NotNull(message="{campo.nome.nulo}")
	public String nome;
	
	@Email(message ="{campo.email.invalido}")
	@NotNull(message="{campo.email.nulo}")
	@Length(max = 100,message="{campo.email.caracteres}")
	@NotEmpty(message = "{campo.email.obrigatorio}")	
	@Column(nullable = false,unique = true)
	public String email;
	
	@CPF(message = "{campo.cpf.invalido}")
	@NotNull(message="{campo.cpf.nulo}")
	@NotEmpty(message = "{campo.cpf.obrigatorio}")
	@Column(nullable = false,unique=true)
	public String cpf;
	
	
	@JsonFormat(pattern = "dd/MM/yyyy")	
	@NotNull(message="{campo.dataNasc.nulo}")
	@Column(nullable = false)
	public Date dataNasc;
	
	public Cliente () {
		
	}

	public Cliente(Integer id, String nome, String email, String cpf, Date dataNasc) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return nome;
	}

	public void setName(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", name=" + nome + ", email=" + email + ", cpf=" + cpf + ", dataNasc=" + dataNasc
				+ "]";
	}

}


```

## Criar arquivo com as mensagem de erros 

###### campo.nome.obrigatorio=O campo nome é obrigatório!
###### campo.nome.caracteres=O campo nome passou de 100 caracteres
###### campo.nome.nulo=O campo nome esta nulo!
###### campo.cpf.obrigatorio=O campo CPF é obrigatório!
###### campo.cpf.invalido=O CPF inválido!
###### campo.cpf.nulo=O CPF está nulo!
###### campo.cpf.unico=O CPF já existente!
###### campo.email.obrigatorio= O email é obrigatório!
###### campo.email.invalido= O email esta inválido!
###### campo.email.nulo=O Email está  nulo!
###### campo.email.caracteres=O email passou de 255 caracteres!
###### campo.email.unico=O email já existe!
###### campo.dataNasc.obrigatorio= A data de nascimento é obrigatório!
###### campo.dataNasc.nulo= A data de nascimento está nula!

## Gerador de CPF válido para teste 

## Teste de Duplicidade CPF

## Teste de Duplicidade e-mail 









 
 
