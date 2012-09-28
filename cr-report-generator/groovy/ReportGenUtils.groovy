import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Map;

import groovy.sql.Sql;

/**
 * @author Monish
 *
 */
public final class ReportGenUtils {

    public static Sql getPoSourceConnection() {
        def props = new Properties()
        new File("resolved.build.properties").withInputStream { stream ->
            props.load(stream)
        }
        def poSourceConnection = Sql.newInstance(props['po.jdbc.url'], props['po.db.username'],
                props['po.db.password'], props['po.jdbc.driver'])
    }

    public static Sql getPaSourceConnection() {
        def props = new Properties()
        new File("resolved.build.properties").withInputStream { stream ->
            props.load(stream)
        }
        def poSourceConnection = Sql.newInstance(props['pa.jdbc.url'], props['pa.db.username'],
                props['pa.db.password'], props['pa.jdbc.driver'])
    }

    public static Map getCountriesMap() {
        def countriesSQL = """select id,alpha2,alpha3,name,numeric from country"""
       
        def result = [:]
        getPoSourceConnection().eachRow(countriesSQL) { row ->
            result.put(row.id, row.toRowResult())
        }
        return result
    }
    
    public static String getFormatedCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat(Constants.DATE_PATTERN, Locale.getDefault());
        return format.format(date);
    }
}
