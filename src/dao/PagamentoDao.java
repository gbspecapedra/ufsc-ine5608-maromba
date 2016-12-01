/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helpers.Alerta;
import helpers.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import libs.Dao;

/**
 *
 * @author orlando
 */
public class PagamentoDao extends Dao {

    public ResultSet listarPagamentosPorPeriodoDao(String dtIni, String dtFim) throws SQLException, ParseException {
        dtIni = Data.formatoSql(dtIni);
        dtFim = Data.formatoSql(dtFim);
        
        String sql = "SELECT * from pagamento where dtPagamento is not null and dtPagamento between '"+dtIni+"' and '"+dtFim+"'";
        ResultSet itens = this.select(sql);
        Alerta.log(sql);
        return itens;
    }

}
