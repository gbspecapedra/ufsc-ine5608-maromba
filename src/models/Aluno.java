/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.Alerta;
import helpers.Validador;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import libs.Dao;

/**
 *
 * @author Orlando
 */
public class Aluno extends Pessoa {
    
    private String plano;
    
    public Aluno() throws SQLException {
    }
    
    public int salvar() throws SQLException {
        
        String sql;
        ResultSet linhas;
        int retorno = 0;
        
        // Verifica se já existe um aluno com esse CPF
        if (verificarCpfCadastrado()) {
            return -1;
        }
                        
        if (this.getMatricula() > 0) {
            // Atualiza um registro
            sql = "UPDATE pessoas SET nome = '" + this.getNome() + "', cpf = '" + this.getCpf() + "', plano = '" + this.getPlano()+ "' WHERE id = '" + this.getMatricula()+ "'";
            Dao.execute(sql);
            retorno = this.getMatricula();
            
        } else {                           
            // Insere novo registro
            sql = "INSERT into pessoas (nome, cpf, plano, tipo) values ('" + this.getNome() + "', '" + this.getCpf() + "','" + this.getPlano()+ "', 'Aluno')";
            Dao.execute(sql);
            
            // Retorna o id do novo aluno
            sql = "SELECT id FROM pessoas WHERE cpf = '" + this.getCpf() + "'";
            linhas = Dao.select(sql);
            if (linhas.next()) {
                retorno = linhas.getInt("id");
            }
        }
        return retorno;
    }
    

    public int deletar() throws SQLException {
        
        // VERIFICAR SE NÃO HÁ PAGAMENTOS PENDENTES
                
        String sql = "DELETE FROM pessoas WHERE id = " + this.getMatricula()+ "";
        Dao.execute(sql);
        Alerta.informar("Dados excluídos com sucesso.");
        return 0;
    }
        
    public ObservableList<Aluno> listarAlunos() throws SQLException {
        ObservableList<Aluno> alunos = FXCollections.observableArrayList();
        ResultSet linhas = Dao.select("select * from pessoas where tipo = 'Aluno'");
        while (linhas.next()) {
                    
            // Inicializa um objeto
            Aluno aluno = new Aluno();
            aluno.setNome(linhas.getString("nome"));
            aluno.setMatricula(linhas.getInt("id"));
            aluno.setPlano(linhas.getString("plano"));
            aluno.setCpf(linhas.getString("cpf"));
            // Adiciona o objeto ao retorno
            alunos.add(aluno);
        }
        return alunos;
    }
    
    
    public String validarModelo(){      
        
        if(this.getPlano().isEmpty()){
           return "Os campos em vermelho são de preenchimehto obrigatório.";
        }
        
        if(this.getNome().isEmpty()){
           return "Os campos em vermelho são de preenchimehto obrigatório.";
        }
        
        if(this.getCpf().isEmpty()){
            return "Os campos em vermelho são de preenchimehto obrigatório.";
        } else {
            if(!Validador.validarCPF(this.getCpf())){
                return "O CPF informado não é válido.";
            }
        }    
        
        return "0";
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
    
    
    
    
    
}
