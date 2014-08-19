package TzukEitan.utils;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import TzukEitan.utils.Utils;


public class WarFormater extends Formatter {

	public String format(LogRecord rec) {
		StringBuffer str = new StringBuffer(100);
		str.append(Utils.getCurrentTime());
		str.append(" ");
		str.append(rec.getLevel());
		str.append("\t");
		str.append(formatMessage(rec));
		
		return str.toString();
	}

}
