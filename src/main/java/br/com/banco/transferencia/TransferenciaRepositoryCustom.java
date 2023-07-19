package br.com.banco.transferencia;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.banco.conta.Conta;

@Repository
public class TransferenciaRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public TransferenciaRepositoryCustom(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transferencia> getTransferenciasComFiltro(Transferencia filtro, LocalDateTime dataInicio,
            LocalDateTime dataFim) {
        List<Object> args = new ArrayList<Object>();
        args.add(filtro.getConta().getIdConta());
        boolean hasNome = false, hasInicio = false, hasFim = false, hasBetween = false;
        if (null != filtro.getNomeOperadorTransacao() && !"".equals(filtro.getNomeOperadorTransacao())) {
            args.add(filtro.getNomeOperadorTransacao());
            hasNome = true;
        }
        if (null != dataInicio) {
            args.add(dataInicio);
            hasInicio = true;
        }
        if (null != dataFim) {
            args.add(dataFim);
            hasFim = true;
        }
        if (hasInicio && hasFim) {
            hasBetween = true;
        }
        List<Transferencia> listaRetorno = new ArrayList<Transferencia>();
        String sql = " SELECT * FROM transferencia tf "
                + " INNER JOIN conta ct ON tf.conta_id = ct.id_conta"
                + " WHERE conta_id = ? "
                + (hasNome ? " AND nome_operador_transacao ilike ? " : "")
                + (hasBetween ? " AND data_transferencia BETWEEN ? AND ? "
                        : (hasInicio ? " AND data_transferencia >= ? " : "")
                                + (hasFim ? " AND data_transferencia <= ? " : ""));
        List<Map<String, Object>> resultMapList = jdbcTemplate.queryForList(sql,
                args.toArray());
        resultMapList.forEach(entry -> {
            listaRetorno.add(
                    new Transferencia(((Long) entry.get("id")).intValue(),
                            LocalDateTime.parse(entry.get("data_transferencia").toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX")),
                            (((BigDecimal) entry.get("valor")).setScale(2)),
                            (String) entry.get("tipo"),
                            (String) entry.get("nome_operador_transacao"),
                            new Conta((Integer) entry.get("conta_id"), (String) entry.get("nome_responsavel"))));
        });
        return listaRetorno;
    }

}
