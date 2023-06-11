package br.ufmg.engsoft.emprestimojogos.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

public class Helper {
	
	public static String formatarDataParaString(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}
	
	public static Date getDataAtualSemHoras() {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.parse(sdf.format(new Date()));
			
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public static Date parsearData(String dataString) {
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date data = sdf.parse(dataString);
			return data;
			
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public static Date converterStringParaData(String dateString) {
    	
    	try {
    		
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dataLimite = formatter.parse(dateString);
            
            if(dataLimite == null) {
            	throw new InputMismatchException("Data inválida");
            }
            
            return dataLimite;
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    	
    	return null;
    }
}
