package br.com.banco.transferencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.banco.conta.Conta;

@Entity
@Table(name = "transferencia")
public class Transferencia implements Serializable {

    @Id
    @Column(nullable = false, unique = true, name = "id")
    private Integer id;

    @Column(nullable = false, unique = false, name = "data_transferencia")
    private LocalDateTime dataTransferencia;

    @Column(nullable = false, unique = false, precision = 20, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, unique = false, length = 15)
    private String tipo;

    @Column(nullable = true, unique = false, name = "nome_operador_transacao", length = 50)
    private String nomeOperadorTransacao;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Transferencia() {
    }
    
    public Transferencia(Conta conta) {
        this.conta = conta;
    }

    public Transferencia(String nomeOperadorTransicao, Conta conta) {
        this.nomeOperadorTransacao = nomeOperadorTransicao;
        this.conta = conta;
    }

    public Transferencia(Integer id, LocalDateTime dataTransferencia, BigDecimal valor, String tipo,
            String nomeOperadorTransacao, Conta conta) {
        this.id = id;
        this.dataTransferencia = dataTransferencia;
        this.valor = valor.setScale(2);
        this.tipo = tipo;
        this.nomeOperadorTransacao = nomeOperadorTransacao;
        this.conta = conta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDateTime data_transferencia) {
        this.dataTransferencia = data_transferencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeOperadorTransacao() {
        return nomeOperadorTransacao;
    }

    public void setNomeOperadorTransacao(String nome_operador_transacao) {
        this.nomeOperadorTransacao = nome_operador_transacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dataTransferencia == null) ? 0 : dataTransferencia.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        result = prime * result + ((nomeOperadorTransacao == null) ? 0 : nomeOperadorTransacao.hashCode());
        result = prime * result + ((conta == null) ? 0 : conta.hashCode());
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
        Transferencia other = (Transferencia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dataTransferencia == null) {
            if (other.dataTransferencia != null)
                return false;
        } else if (!dataTransferencia.equals(other.dataTransferencia))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        if (tipo == null) {
            if (other.tipo != null)
                return false;
        } else if (!tipo.equals(other.tipo))
            return false;
        if (nomeOperadorTransacao == null) {
            if (other.nomeOperadorTransacao != null)
                return false;
        } else if (!nomeOperadorTransacao.equals(other.nomeOperadorTransacao))
            return false;
        if (conta == null) {
            if (other.conta != null)
                return false;
        } else if (!conta.equals(other.conta))
            return false;
        return true;
    }

    
}
