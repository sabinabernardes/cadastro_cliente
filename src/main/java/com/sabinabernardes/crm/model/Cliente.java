package com.sabinabernardes.crm.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	public Integer id ;
	
	//@Column(nullable = false)
	//@NotEmpty(message = "{campo.clienteNome.obrigatorio}")
	//@Length(max = 100,message="{campo.clienteNome.caracteres}")
	//@NotNull(message="{campo.name.nulo}")
	public String name;
	
	//@Email(message ="{campo.clienteEmail.invalido}")
	//@NotNull(message="{campo.clienteEmail.nulo}")
	//@Length(max = 100,message="{campo.clienteEmail.caracteres}")
	//@NotEmpty(message = "{campo.clienteEmail.obrigatorio}")	
	//@Column(nullable = false,unique = true)
	public String email;
	
	//@CPF(message = "{campo.clienteCpf.invalido}")
	//@NotNull(message="{campo.clienteCpf.nulo}")
	//@NotEmpty(message = "{campo.clienteCpf.obrigatorio}")
	//@Column(nullable = false,unique=true)
	public String cpf;
	
	
	//@JsonFormat(pattern = "dd/MM/yyyy")	
	//@NotNull(message="{campo.clienteNascimento.nulo}")
	//@Column(nullable = false)
	public Date dataNasc;
	
	public Cliente () {
		
	}

	public Cliente(Integer id, String name, String email, String cpf, Date dataNasc) {
		super();
		this.id = id;
		this.name = name;
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "Cliente [id=" + id + ", name=" + name + ", email=" + email + ", cpf=" + cpf + ", dataNasc=" + dataNasc
				+ "]";
	}

}
