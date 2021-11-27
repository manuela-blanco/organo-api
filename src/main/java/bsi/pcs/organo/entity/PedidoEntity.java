package bsi.pcs.organo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import bsi.pcs.organo.util.MetodoPagamento;
import bsi.pcs.organo.util.Status;

@Entity
@Table(name = "pedido")
public class PedidoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pedido_id")
	private Long id;
	private float valor;
	@ManyToOne
	@JoinColumn(name = "comprador_id")
	private CompradorEntity compradorAssociado;
	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private FornecedorEntity fornecedorAssociado;
	private Date dataEntrega;
	private Status status;
	private MetodoPagamento metodoPagamento;
	@OneToMany
	@JoinColumn(name = "item_id")
	private List<ItemEntity> itens;
	@OneToOne
	@JoinColumn(name = "endereco_id")
	private EnderecoEntity endereco;
	
	public PedidoEntity() {}
	
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public MetodoPagamento getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public CompradorEntity getCompradorAssociado() {
		return compradorAssociado;
	}

	public void setCompradorAssociado(CompradorEntity compradorAssociado) {
		this.compradorAssociado = compradorAssociado;
	}

	public FornecedorEntity getFornecedorAssociado() {
		return fornecedorAssociado;
	}

	public void setFornecedorAssociado(FornecedorEntity fornecedorAssociado) {
		this.fornecedorAssociado = fornecedorAssociado;
	}	
	
}