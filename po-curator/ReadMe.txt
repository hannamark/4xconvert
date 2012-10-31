Generating Organization ChangeRequest Report (Developed as part-I of PO-5475 & PO-5495)
-------------------------------------------------------------------------------------
1. Copy build.properties to local.properties,
2. Provide PO database connection details in local.properties file,
3. Execute "ant generate:orgcr:report" command,
4. A ZIP file with pattern "Organization-CR-Report-yyyy-MM-dd.zip" will be generated in "archives" folder. 
    This ZIP file will contain a file with pattern "Organization-CR-Report-yyyy-MM-dd.xlsx". 
    The xlsx file will contain 3 sheets "Valid CRs-To be Reviewed", "Duplicate CRs" & "Invalid Or Phantom CRs"
    
    
Process Duplicate & Invalid/Phantom Organization ChangeRequests (Developed as Part-II of PO-5475)
--------------------------------------------------------------------------------------------------
1. Copy build.properties to local.properties,
2. Provide PO database connection details in local.properties file,
3. Execute "ant process:duplicate:phantom:orgcr" command,
4. All the Duplicate & Invalid/Phantom Organization ChangeRequests will be marked as processed.
5: A table named "organizationcr_process_log" will be created in PO database and the CRs processed is logged in there with date and comments.


Generating Duplicate Organization Report (Developed as part of PO-5587)
-------------------------------------------------------------------------------------
1. Copy build.properties to local.properties,
2. Provide PO database connection details in local.properties file,
3. Execute "ant generate:duplicate:org:report" command,
4. A ZIP file with pattern "Duplicate-Organization-Report-yyyy-MM-dd.zip" will be generated in "archives" folder. 
    This ZIP file will contain a file with pattern "Duplicate-Organization-Report-yyyy-MM-dd.xlsx". 
    
Generating Organization Role Report (Developed as part of PO-5497)
-------------------------------------------------------------------------------------
1. Copy build.properties to local.properties,
2. Provide PO database connection details in local.properties file,
3. Execute "ant generate:duplicate:org:role:report" command,
4. A ZIP file with pattern "Organization-Role-Report-yyyy-MM-dd.zip" will be generated in "archives" folder. 
    This ZIP file will contain a file with pattern "Organization-Role-Report-yyyy-MM-dd.xlsx".     