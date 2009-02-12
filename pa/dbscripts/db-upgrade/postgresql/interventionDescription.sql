-- move description from intervention to planned_activity
update planned_activity pa
set text_description =
 ( select description_text
   from intervention i
   where pa.intervention_identifier = i.identifier )
where pa.text_description is null
   or trim(pa.text_description) = '';
