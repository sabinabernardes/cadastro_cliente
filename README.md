# REST API usando Spring Boot, Hibernate, JPA e H2 Data Base 

### Este artigo tem como objetivo montar uma REST API usando Spring Boot, Hibernate, JPA e H2 Data Base. Para simular o cadastro de clientes.

## Antes de começar precisaremos dos seguintes itens instalados:
#### •	Java JDK(v11)
#### •	Maven 
#### •	Spring Tools 4 (IDE baseada no Eclipse)
#### •	Postman 

## Criando a aplicação

#### File>New> Spring Starter Project

![iniciando o projeto](https://github.com/sabinabernardes/cadastro_cliente/commit/21efc9610e09fd18b9b42a1a551091a62544c70c#diff-06f290ca62e4784f66c38bc037d790d4e4fbcb160ea8bc2dfe89ee9c13f6aadd)

## Adicionar as seguintes dependências 

#### •	Spring Boot DevTools (Ajuda na configuração automática)
#### •	Spring Data Jpa (Java persistence API)
#### •	Spring Web (Spring padrão MVC)
#### •	H2 Data Base (Banco de dados em memória)


### As dependências foram adicionadas no arquivo pom.xml conforme mostradas abaixo 

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
	
	


 https://github.com/sabinabernardes/cadastro_cliente/blob/main/pom.xml
 
 ## Estrutura de projeto
 
#### Neste projeto usaremos a estrutura MVC ( Model, View, Controller)

## Classe Cliente Model

### Classe reponsavel pela parte dos dados do projeto 

#### Para o cadastro de clientes usaremos  as seguintes variaveis  

##### public Integer id ; 
##### public String nome;
##### public String email;
##### public String cpf;
##### public Date dataNasc;

### Apos adicionar as variaveis criar as estidades abaixo 
### Botão Direito > Source adicionar os itens abaixo

#### •	Generated Construtor using  Fields   (Selecionar todos os campos)
#### •	Generated Getters and Setters	     (Selecionar todos os campos)
#### •	Generated hashCode() and equals()    (id)
#### •	Generated toString()...              (Selecionar todos os campos)

### Utilizar tambem essas anotações 

##### @Entity 
##### @Id
##### @GeneratedValue(strategy = GenerationType.IDENTITY)
##### @Column(nullable = false)	
##### @NotEmpty(message = "{campo.nome.obrigatorio}")
##### @Length(max = 100,message="{campo.nome.caracteres}")
##### @NotNull(message="{campo.nome.nulo}")
##### @JsonFormat(pattern = "dd/MM/yyyy")

### O Código abaixo mostra a classe model completa 

''' package com.sabinabernardes.crm.model;
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


https://github.com/sabinabernardes/cadastro_cliente/blob/803f65e6cae1fea3786cb4d08f1b5209bf125ca6/src/main/java/com/sabinabernardes/crm/model/Cliente.java



 
 
