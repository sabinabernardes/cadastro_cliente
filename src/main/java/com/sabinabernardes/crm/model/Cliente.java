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
