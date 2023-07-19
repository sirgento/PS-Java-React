package br.com.banco.conta;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.banco.transferencia.Transferencia;

@Entity
@Table(name = "conta")
public class Conta implements Serializable {

    @Id
    @Column(nullable = false, unique = true, name = "id_conta")
    private Integer idConta;

    @Column(nullable = false, unique = false, name = "nome_responsavel", length = 50)
    private String nomeResponsavel;

    @OneToMany(mappedBy = "conta")
    @JsonIgnore
    private List<Transferencia> transferencias;

    public Conta() {
    }

    public Conta(Integer idConta) {
        this.idConta = idConta;
    }

    public Conta(Integer idConta, String nomeResponsavel) {
        this.idConta = idConta;
        this.nomeResponsavel = nomeResponsavel;
    }

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

}
