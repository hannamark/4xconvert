<%@ taglib uri="/struts-tags" prefix="s" %>

<ul>
<s:iterator value="autoCompleteResult">
  <li><s:property/></li>
</s:iterator>
</ul>
