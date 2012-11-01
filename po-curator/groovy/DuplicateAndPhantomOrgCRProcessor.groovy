import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.*

/**
 * @author Monish
 *
 */

def props = new Properties()
new File("resolved.build.properties").withInputStream { stream ->
    props.load(stream)
}
println "Using " + props['po.jdbc.url'] + " to connect to PO database"

def poSourceConnection = ReportGenUtils.getPoSourceConnection();
def orginalList
def filteredList

createLogTable(poSourceConnection)

poSourceConnection.eachRow(Queries.orgsWithCRSQL) { orgsWithCR ->

    poSourceConnection.eachRow(Queries.currentOrgDetailsSQL + orgsWithCR.target) { currOrg ->

        currentOrganization = new Organization(currOrg.poid,currOrg.name,
                currOrg.city,currOrg.suite,
                currOrg.zip,currOrg.state,
                currOrg.street,currOrg.country,
                currOrg.email,null);

        orginalList = []
        
        poSourceConnection.eachRow(Queries.crIDsSQL + orgsWithCR.target + """ order by id asc""") { orgCrs ->

            poSourceConnection.eachRow(Queries.crOrgDetailsSQL + orgCrs.id) { crOfcurrOrg ->
                orginalList << new Organization(null,crOfcurrOrg.name,
                        crOfcurrOrg.city,crOfcurrOrg.suite,
                        crOfcurrOrg.zip,crOfcurrOrg.state,
                        crOfcurrOrg.street,crOfcurrOrg.country,
                        crOfcurrOrg.email,crOfcurrOrg.crid)
            }
        }
        
        duplicateCRs = []
        phantomList = []
        filteredList = []
        
        orginalList.each {
            if (currentOrganization.equals(it) ){
                duplicateCRs.add(it);
            }
            if (!filteredList.contains(it)) {
                filteredList.add(it)
            }else {
                if(!duplicateCRs.contains(it)){
                    phantomList.add(it)
                }
            }
        }
        
//        filteredList.each {
//            if(!currentOrganization.equals(it)){
//            }
//        }
        
        duplicateCRs.each {
            poSourceConnection.executeUpdate('update organizationcr set processed = true where id= ?', [it.getCrId()])
            poSourceConnection.execute('insert into organizationcr_process_log (organizationcr_id, comments, date_processed) values (?, ? ,CURRENT_DATE)', [it.getCrId(),'Phantom/Invalid Organization ChangeRequest'])
        }
        
        phantomList.each {
            poSourceConnection.executeUpdate('update organizationcr set processed = true where id= ?', [it.getCrId()])
            poSourceConnection.execute('insert into organizationcr_process_log (organizationcr_id, comments, date_processed) values (?,?,CURRENT_DATE)', [it.getCrId(),'Duplicate Organization ChangeRequest'])
        }
    }
}

void createLogTable(sql){
    try {
        sql.execute('select count(*) from organizationcr_process_log')
    } catch (Exception e) {
        if (StringUtils.containsIgnoreCase(e.getMessage(), "relation \"organizationcr_process_log\" does not exist")) {
            sql.execute(""" CREATE TABLE organizationcr_process_log
                                            (
                                              organizationcr_id bigint NOT NULL,
                                              comments character varying(1000),
                                              date_processed timestamp without time zone
                                            )
                                       """)
        }
    }
}
