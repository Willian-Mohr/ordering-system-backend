package br.com.wohr.orderingsystembackend.domain;

import br.com.wohr.orderingsystembackend.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class PagamentoComBoleto extends Pagamento {

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataVecimento;
    private Date dataPagamento;

    public PagamentoComBoleto() {
    }

    public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVecimento, Date dataPagamento) {
        super(id, estado, pedido);
        this.dataVecimento = dataVecimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVecimento() {
        return dataVecimento;
    }

    public void setDataVecimento(Date dataVecimento) {
        this.dataVecimento = dataVecimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
