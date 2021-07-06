package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDopedido) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(instanteDopedido);
        cal.add(Calendar.WEEK_OF_MONTH, 1);
        pagto.setDataVecimento(cal.getTime());
    }
}
