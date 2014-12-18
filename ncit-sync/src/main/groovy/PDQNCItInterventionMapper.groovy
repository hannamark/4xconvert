import groovy.sql.Sql;
import groovyx.net.http.RESTClient


public class PDQNCItInterventionMapper{

  def restClient = new RESTClient()
  def lexEVSRestClient = new RESTClient()
  
  StringBuffer fileContents = new StringBuffer("BEGIN;");

  def getInvSynonyms(String ncitCode , url) {
      String inverVentionSyncUrl = url+"?query=Entity[@_entityCode=${ncitCode}]";
      def response =null
      try{
      response = lexEVSRestClient.get(uri: inverVentionSyncUrl)
      }
      catch(Exception e) {
          return
      } 
    if (! response.success || response.status != 200) {
      
      throw new RuntimeException("Failure from LexEVS: " + response.data.text)
    }

    def doc = response.data;
    def entity = doc.queryResponse.'class'.find{ it.@name.text() == "org.LexGrid.concepts.Entity" };

    // get name
    String prefName;
    def synonyms = ["Chemical structure name":[],"Lexical variant":[],"IND code":[],"Subtype":[],"Broader":[],"Code name":[],"Foreign brand name":[],"Common usage":[],"Spanish":[],"Obsolete name":[],"Abbreviation":[],"US brand name":[],"Acronym":[],"NSC number":[],"Synonym" :[],"CAS Registry name":[]]

    def presentations = entity.field.find{ it.@name.text() == "_presentationList" }.'class';

    for (def presentation : presentations) {
      boolean isPreferred = presentation.field.find{ it.@name.text() == "_isPreferred" }.text() == "true";
      def name =  presentation.field.find{ it.@name.text() == "_value" }.'class'.field.find{ it.@name.text() == "_content" && it.text() }.text();
      if (isPreferred){
        prefName = name
      }else {
        def repForm = presentation.field.find{ it.@name.text() == "_representationalForm" }.text()
        
        switch (repForm){
           
          case 'SY' :   synonyms["Synonym"] += name; break;
          case 'AB' :   synonyms["Abbreviation"] += name; break;
          case 'BR' :   synonyms["US brand name"] += name; break;
          case 'FB' :   synonyms["Foreign brand name"] += name; break;
          case 'NY' :   synonyms["Chemical structure name"] += name; break;
          case 'CN' :   synonyms["Code name"] += name; break;
        }
      }
    }

    def properties = entity.field.find{ it.@name.text() == "_propertyList" }.'class';

    for (def property : properties) {
      def value =  property.field.find{ it.@name.text() == "_value" }.'class'.field.find{ it.@name.text() == "_content" && it.text() }.text();
      def propName = property.field.find{ it.@name.text() == "_propertyName" }.text()
      switch (propName) {
        case 'NSC_Code' : synonyms["NSC number"] += value; break
        case 'CAS_Registry' : synonyms["CAS Registry name"] += value; break
      }
    }
    return [prefName, synonyms]
  }

  def getPreferredName(def ncitCode,String url){
      
    String preferredNameUrl = url +"/${ncitCode}?format=xml"
    def response = null
    try{
        response = restClient.get(uri: preferredNameUrl)
    } catch(Exception e) {
          return
      }    

    if (! response.success || response.status != 200) {
      //            println "fetchChildren - failed to fetch children for ${ncitCode} from ${url} with error ${response.data.text}"
      throw new RuntimeException("Failure from LexEVS: " + response.data.text)
    }
    def doc = response.data;
    def prefName =  doc.EntityDescription.namedEntity.designation.find{ it.@designationRole == 'PREFERRED' }?.text()
    return prefName;
  }

  def generateInvUpdateSQL( def ncitCode, def prefName){
   fileContents.append(" update intervention  set name='${prefName.replaceAll('\'','\'\'')}' where nt_term_identifier='${ncitCode}';");
  }

  def generateInvSynUpdateSQL(def ncitCode, def synonyms){
    synonyms.each (){ code, vals ->
       
      if(vals){
        fileContents.append(" delete from intervention_alternate_name where intervention_identifier = (select min(identifier) from intervention where nt_term_identifier='${ncitCode}') and name_type_code='${code}';");
        vals.each { val -> fileContents.append(" insert into intervention_alternate_name (intervention_identifier, name,status_code, status_date_range_low, ");
          fileContents.append(" date_last_created, name_type_code) values ((select min(identifier) from intervention where nt_term_identifier='${ncitCode}'), ");
          fileContents.append(" '${val.replaceAll('\'','\'\'')}','ACTIVE',now(),now(),'${code}' );")
          }
      }
    }
  }

  def generateInvRemoveDupSQL(def remove, def replace){
    println "update planned_activity set intervention_identifier = ${replace} where intervention_identifier=${remove};"
    println "update performed_activity set intervention_identifier = ${replace} where intervention_identifier=${remove};"
    println "delete from intervention_alternate_name where intervention_identifier=${remove};"
    println "delete from intervention where identifier=${remove};"
  }

  public void syncIntervention(String outputDir, String preferredNameUrl, String interventionUrl ,
         String paJdbcUrl,String dbuser,String dbpassword){
         
         String ncitCode =null;
         def  sql = Sql.newInstance(paJdbcUrl, dbuser, dbpassword, "org.postgresql.Driver")
         def ctrpTerms = sql.rows("select distinct(nt_term_identifier) from intervention");
         
        ctrpTerms.each (){
             
         ncitCode= it.nt_term_identifier;
        
         
         if(ncitCode!=null) {
            println "Syncing intervention term "+ncitCode
            String  prefName = getPreferredName(ncitCode,preferredNameUrl)
            if(prefName!=null) {
                generateInvUpdateSQL(ncitCode,prefName)
            }
           
            // Get Intervention synonyms
            def syns
            syns  = getInvSynonyms(ncitCode ,interventionUrl)
            generateInvSynUpdateSQL(ncitCode,syns[1])
             }
         }
         def outputFile = new File(outputDir)
         File sqlFile = new File(outputFile, "interventionQueries.sql")
         if(sqlFile.exists()) {
             sqlFile.delete();
         }
         def out = new FileOutputStream(sqlFile)
         def writer = new OutputStreamWriter( out , "UTF-8")
         fileContents.append("COMMIT;")
         writer.write fileContents.toString()
         writer.flush();
         writer.close();
         
         sql.executeUpdate(fileContents.toString());
    
  }
}