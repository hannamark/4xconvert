import static org.apache.commons.lang.StringUtils.*

import javax.mail.*
import javax.mail.internet.*

import java.sql.Timestamp


/**
 * @author dkrylov
 *
 */

def props = new Properties()
new File("resolved.build.properties").withInputStream { stream ->
	props.load(stream)
}
println "Starting PO-8009: looking for organization roles that need to be set to ACTIVE and have their CR accepted"
println "Using " + props['po.jdbc.url'] + " to connect to PO database"

def poSourceConnection = ReportGenUtils.getPoSourceConnection()

def grouperURL = poSourceConnection.firstRow("select grid_grouper_url as url from csm_remote_group limit 1").url;
def tier = grouperURL.contains("training.cagrid.org")?"DEV":(grouperURL.contains("prod.nci.nih.gov")?"PROD":"QA1/2/STAGE")
println "We are in ${tier} tier"

StringBuilder log = new StringBuilder()

poSourceConnection.eachRow(Queries.activeUnprocessedHcfCrs) { roleCR ->
	println "\rOK, found an active & unprocessed HCF CR; id=${roleCR.id}"
	handleCR(roleCR, poSourceConnection, 'healthcarefacility', 'hcf', log)
}

poSourceConnection.eachRow(Queries.activeUnprocessedRoCrs) { roleCR ->
	println "\rOK, found an active & unprocessed RO CR; id=${roleCR.id}"
	handleCR(roleCR, poSourceConnection, 'researchorganization', 'ro', log)
}


// send email
if (log.length()>0) {
	try {
		println "Sending mail..."
		props.put("mail.smtp.host", "mailfwd.nih.gov");
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("ctrp-team@semanticbits.com"));
		InternetAddress toAddress = new InternetAddress("ctrp-team@semanticbits.com");
		message.addRecipient(Message.RecipientType.TO, toAddress);
		message.setSubject("PO-8009 Nightly Job Report in ${tier}");
		message.setText("This is a report of running PO-8009 Nightly Job in ${tier} on ${new Date()}\r\n\r\n"+log);

		Transport transport = session.getTransport("smtp");
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

def handleCR(roleCR, poSourceConnection, String roleName, String roleAbbr, log) {
	def role = poSourceConnection.firstRow("select * from "+roleName+" where id=${roleCR.target}")
	println "This CR is for a "+roleName+" role named ${role.name}; id=${role.id}"

	def org = poSourceConnection.firstRow("select * from organization where id=${roleCR.player_id}")
	println "The role's organization is ${org.name}; id=${org.id}"

	def ctepecm =  poSourceConnection.firstRow("select * from csm_user where login_name like '%CN=ctepecm' limit 1")

	poSourceConnection.withTransaction {
		println "Activating the role..."
		poSourceConnection.executeUpdate("update "+roleName+" set status = 'ACTIVE' where id=${role.id}")
		println "Removing Override flags..."
		poSourceConnection.executeUpdate("update "+roleName+" set overridden_by_id = null where id=${role.id}")
		poSourceConnection.executeUpdate("update organization set overridden_by_id = null where id=${org.id}")

		if (ctepecm) {
			println "Changing the org/role owner to CTEP..."
			poSourceConnection.executeUpdate("update "+roleName+" set created_by_id = ${ctepecm.user_id} where id=${role.id}")
			poSourceConnection.executeUpdate("update organization set created_by_id = ${ctepecm.user_id} where id=${org.id}")
		}

		def newName = roleCR.name
		println "Changing the role's name to ${newName}..."
		poSourceConnection.executeUpdate("update "+roleName+" set name = ? where id=${role.id}", [newName])
		if (isNotBlank(newName)) {
			println "Changing the org's name to ${newName} as well..."
			poSourceConnection.executeUpdate("update organization set name = ? where id=${org.id}", [newName])
		} else {
			println "Since the name in CR is blank, we can't propagate it to the Org level!"
		}

		def crAddressRows =
				poSourceConnection.rows("select * from address where id in (select address_id from "+roleAbbr+"cr_address where "+roleAbbr+"cr_id=${roleCR.id})")
		println "This CR brings ${crAddressRows.size()} addresses"
		poSourceConnection.executeUpdate("delete from  "+roleAbbr+"_address where "+roleAbbr+"_id=${role.id}")
		if (crAddressRows.size()>0) {
			println "We now need to update the role's and the org's address with the one specified by this CR..."
			poSourceConnection.executeUpdate("update organization set postal_address_id = ? where id=${org.id}", [crAddressRows[0].id])
			crAddressRows.each { addr ->
				poSourceConnection.executeInsert("insert into "+roleAbbr+"_address("+roleAbbr+"_id, address_id) values(${role.id},${addr.id})")
			}
		}

		def phones =
				poSourceConnection.rows("select * from phonenumber where id in (select phone_id from "+roleAbbr+"cr_phone where "+roleAbbr+"cr_id=${roleCR.id})")
		println "This CR brings ${phones.size()} phone numbers"
		poSourceConnection.executeUpdate("delete from  "+roleAbbr+"_phone where "+roleAbbr+"_id=${role.id}")
		if (phones.size()>0) {
			println "We now need to update the role's and the org's phone number(s) with the one specified by this CR..."
			poSourceConnection.executeUpdate("delete from organization_phone where organization_id=${org.id}")
			phones.eachWithIndex { phone, idx ->
				poSourceConnection.executeInsert("insert into "+roleAbbr+"_phone ("+roleAbbr+"_id, phone_id, idx) values(${role.id}, ${phone.id}, ${idx})")
				poSourceConnection.executeInsert("insert into organization_phone (organization_id, phone_id, idx) values(${org.id}, ${phone.id}, ${idx})")
			}
		}

		def emails =
				poSourceConnection.rows("select * from email where id in (select email_id from "+roleAbbr+"cr_email where "+roleAbbr+"cr_id=${roleCR.id})")
		println "This CR brings ${emails.size()} emails"
		poSourceConnection.executeUpdate("delete from  "+roleAbbr+"_email where "+roleAbbr+"_id=${role.id}")
		if (emails.size()>0) {
			println "We now need to update the role's and the org's email(s) with the one specified by this CR..."
			poSourceConnection.executeUpdate("delete from organization_email where organization_id=${org.id}")
			emails.eachWithIndex { email, idx ->
				poSourceConnection.executeInsert("insert into "+roleAbbr+"_email ("+roleAbbr+"_id, email_id, idx) values(${role.id}, ${email.id}, ${idx})")
				poSourceConnection.executeInsert("insert into organization_email (organization_id, email_id, idx) values(${org.id}, ${email.id}, ${idx})")
			}
		}

		def commentText = """\
Organization: ${org.name}; ID=${org.id}
We found an active and unprocessed ${roleName} change request (id=${roleCR.id}) for this organization.
Therefore, according to the requirements specified in PO-8009, we automatically performed the following:		     
 - Apply all changes from the CR to both the role and the organization, including blanking out fields that are blank in the CR
 - Remove any existing overrides flag on the role and organization
 - Change the owner on the role and organization from the curator to the ECM user
 - Mark the CR as resolved

Additional helpful information about the changes made:
New Org & Role Name: ${newName}. ${isBlank(newName)?'Since the name in CR is blank, we cannot propagate it to the Org level!':''}
New Address: ${crAddressRows}.
New Phone(s): ${phones}.
New Email(s): ${emails}.	

Please see https://tracker.nci.nih.gov/browse/PO-8009 for more information.		

			""";

		def commentID = poSourceConnection.firstRow("SELECT NEXTVAL('HIBERNATE_SEQUENCE') as nextid")
		poSourceConnection.executeInsert("insert into comment (id, value, create_date, user_id) values (?,?,?,?)",
				[
					commentID.nextid,
					commentText.toString(),
					new Timestamp(System.currentTimeMillis()),
					ctepecm!=null?ctepecm.user_id:null
				])

		poSourceConnection.executeInsert("insert into organization_comment (organization_id, comment_id, idx) values (${org.id}, ${commentID.nextid}, (select COALESCE(max (idx), -1)+1 from organization_comment where organization_id=${org.id}))")
		log.append(commentText)

		println "Marking the CR as processed."
		poSourceConnection.executeUpdate("update "+roleName+"cr set processed = true where id=${roleCR.id}")
	}
}
