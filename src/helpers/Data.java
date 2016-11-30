/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Orlando
 */
public class Data {

    public static Date dataAtual() throws ParseException {
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        Date hoje = formatador.parse(formatador.format(data));
        return hoje;
    }

    /**
     *
     * @return
     */
    public static String converterParaMysql(Date data) {
        String pattern = "yyyy/MM/dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDateString = formatter.format(data);
        return mysqlDateString;
    }

    public static Date adicionarMes(Date data, int quantidade) {
        System.out.println(data);
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + quantidade));
        data = cal.getTime();
        return data;
    }

    public static String diaDaSemana() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        String retorno = "";

        switch (day_of_week) {
            case 1:
                retorno = "Dom";
                break;

            case 2:
                retorno = "Seg";
                break;

            case 3:
                retorno = "Ter";
                break;

            case 4:
                retorno = "Qua";
                break;

            case 5:
                retorno = "Qui";
                break;

            case 6:
                retorno = "Sex";
                break;

            case 7:
                retorno = "Sab";
                break;
        }

        return retorno;
    }

}
