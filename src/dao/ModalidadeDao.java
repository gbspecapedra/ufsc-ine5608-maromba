/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import libs.Dao;
import models.Modalidade;

/**
 *
 * @author orlando
 */
public class ModalidadeDao extends Dao {

    public int persistir(Modalidade modalidade) throws SQLException {
        String sql;
        ResultSet linhas;
        int retorno = 0;

        if (modalidade.getId() > 0) {
            // Atualiza um registro
            sql = "UPDATE modalidades SET nome = '" + modalidade.getNome() + "', diasSemana = '" + modalidade.getDiasSemana()+ "', valor = '" + Double.toString(modalidade.getValor())+ "' WHERE id = '" + modalidade.getId() + "'";
            System.out.println(sql);
            this.execute(sql);
            retorno = modalidade.getId();

        } else {
            // Insere novo registro
            sql = "INSERT into modalidades (nome, diasSemana, valor) values ('" + modalidade.getNome() + "', '" + modalidade.getDiasSemana()+ "', " + modalidade.getValor() + ")";
            System.out.println(sql);
            this.execute(sql);

            // Retorna o id do novo modalidade
            sql = "SELECT id FROM modalidades ORDER by id DESC LIMIT 1";
            linhas = this.select(sql);
            if (linhas.next()) {
                retorno = linhas.getInt("id");
            }
        }
        return retorno;
    }

    public int deletar(int matricula) throws SQLException {

        // VERIFICAR SE NÃO HÁ PAGAMENTOS PENDENTES        
        String sql = "DELETE FROM modalidades WHERE id = " + matricula;
        try {
            this.execute(sql);
            return matricula;
        } catch (SQLException e) {

        }
        return 0;
    }

    public ResultSet listar() throws SQLException {
        ResultSet itens = this.select("select * from modalidades");
        return itens;
    }

}
