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

}
