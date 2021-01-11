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

### Apos adicionar as variaveis usar as anotações abaixo 
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

#### O Código abaixo mostra a classe model completa 

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
https://github.com/sabinabernardes/cadastro_cliente/blob/803f65e6cae1fea3786cb4d08f1b5209bf125ca6/src/main/java/com/sabinabernardes/crm/model/Cliente.java

## Classe Controller

##### Responsável por gerenciar as requisições 

#### Anotações utilizadas nesta classe 

##### @RestController (retorna o objeto e os dados do objeto para gravar  diretamente na resposta HTTP como JSON ou XML.)
##### @RequestMapping("/clientes")
##### @Autowired
##### @GetMapping
##### @PostMapping
##### @ResponseStatus(HttpStatus.CREATED)



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

##### Responsável por isolar os objetos ou entidades do domínio do código que acessa o banco de dadoss


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






 
 
