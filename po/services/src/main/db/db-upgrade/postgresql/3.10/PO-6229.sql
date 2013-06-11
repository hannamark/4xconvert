CREATE OR REPLACE FUNCTION update_CTEP_organizations_status()
  RETURNS VOID AS
$BODY$
  DECLARE  
	row_data RECORD;
BEGIN
    
    FOR row_data IN select 'hcf' as role, hcf.status rolestatus, org.status orgstatus, org.id orgid, hcf.id roleid
					from hcf_otheridentifier hcfoi
					join healthcarefacility hcf on (hcfoi.hcf_id = hcf.id)
					join organization org on (hcf.player_id = org.id)
					where hcfoi.root = '2.16.840.1.113883.3.26.6.2'
					union
					select 'ro' as role, ro.status rolestatus, org.status orgstatus, org.id orgid, ro.id roleid
					from ro_otheridentifier rooi
					join researchorganization ro on (rooi.ro_id = ro.id)
					join organization org on (ro.player_id = org.id)
					where rooi.root = '2.16.840.1.113883.3.26.6.2'

	LOOP

		IF row_data.orgstatus != 'ACTIVE' THEN
			update organization set status = 'ACTIVE' where id = row_data.orgid;
		END IF;

		IF  row_data.rolestatus != 'ACTIVE' and  row_data.role = 'hcf' THEN
			update healthcarefacility set status = 'ACTIVE' where id = row_data.roleid;
		END IF;

		IF  row_data.rolestatus != 'ACTIVE' and  row_data.role = 'ro' THEN
			update researchorganization set status = 'ACTIVE' where id = row_data.roleid;
		END IF;

	END LOOP;
    
END;
$BODY$ LANGUAGE 'plpgsql';

select update_CTEP_organizations_status() as output;

DROP FUNCTION update_CTEP_organizations_status();