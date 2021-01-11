# REST API usando Spring Boot, Hibernate, JPA e H2 Data Base 

### Este artigo tem como objetivo montar uma REST API usando Spring Boot, Hibernate, JPA e H2 Data Base. Para simular o cadastro de clientes.

## Antes de começar precisaremos dos seguintes itens instalados:
### •	Java JDK(v11)
### •	Maven 
### •	Spring Tools 4 (IDE baseada no Eclipse)
### •	Postman 

## Criando a aplicação

### New> Spring Starter Project

## Adicionar as seguintes dependências 

### •	Spring Boot DevTools (Ajuda na configuração automática)
### •	Spring Data Jpa (Java persistence API)
### •	Spring Web (Spring padrão MVC)
### •	H2 Data Base (Banco de dados em memória)
### As dependências foram adicionadas no arquivo pom.xml

''' <dependencies>
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
 '''

 https://github.com/sabinabernardes/cadastro_cliente/blob/main/pom.xml
