This directory contains the various IVY MODULE DEFINITIONS so that our build may 
publish the correct versions of our dependencies within the CBIIT 
IVY repository (http://gforge.nci.nih.gov/svnroot/commonlibrary/trunk/ivy-repo/). 

USAGE:
Each of the ivy module definition files contain a @revision@ token so that definition's revision is configurable. 
1. Use Apache Ant's  /copy + filter/ technique to replace the token with your desired revision number.
2. Also, ensure the file is renamed to "ivy.xml" and placed in the appropriate directory (organisation/module/revision) 
   within any IVY repository -- CBIIT's Enterprise or your local.    
