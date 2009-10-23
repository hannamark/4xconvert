update researchorganization ro set name = (select o.name from organization o join researchorganization ro2 on o.id = ro2.player_id where ro2.id = ro.id) where name is null;
update healthcarefacility hcf set name = (select o.name from organization o join healthcarefacility hcf2 on o.id = hcf2.player_id where hcf2.id = hcf.id) where name is null;
