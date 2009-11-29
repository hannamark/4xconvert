<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>    
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<SCRIPT LANGUAGE="JavaScript">
function handleMailAction(){
        var jsFromEmail = document.getElementById("fromEmail").value;
        var jsemailMsg = document.getElementById("emailMessage").value;
        var url = '/pa/protected/popupCadsrsendCDERequestEmail.action?fromEmail='+jsFromEmail+'&emailMsg=' + jsemailMsg;
        var div = document.getElementById('cdeDetailsDiv');
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div,url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
}
</script>
<div id="cdeDetailsDiv">
<div>
            <pa:failureMessage/>
            <pa:sucessMessage /> 
            </div>
            <h2>Email: Request to create CDE</h2>
            <table class="form">
                 <tr>
                    <td scope="row" class="label"><s:label for="toEmail">To Email:</s:label>                  
                    <td class="value" colspan="2">
                        
                        <s:textfield id="toEmail" name="webDTO.ToEmail"  cssStyle="height: 25px"/>                                        
                    </td>                   
                </tr>
                <tr>
                    <td scope="row" class="label"><s:label for="fromEmail">Sender's Email:</s:label><span class="required">*</span></td>
                  
                    <td class="value" colspan="2">
                        
                        <s:textfield id="fromEmail" name="webDTO.fromEmail" maxlength="200" size="200"                      
                        cssStyle="width: 200px" />
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>webDTO.fromEmail</s:param>
                              </s:fielderror>                            
                        </span>                     
                    </td>                   
                </tr>                
                 <tr>
                    <td scope="row" class="label"><s:label for="subject">Subject:</s:label>                  
                    <td class="value" colspan="2">
                        
                        <s:textfield id="subject" name="webDTO.subject" readonly="true" cssClass="readonly"  cssStyle="height: 25px"/>                                        
                    </td>                   
                </tr>
                <tr>
                    <td scope="row" class="label"><s:label for="message">Message:</s:label><span class="required">*</span></td>
                 
                    <td class="value" colspan="2">
                        <s:textarea id="emailMessage" name="webDTO.message" cssStyle="width:606px" rows="4" />
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>webDTO.message</s:param>
                              </s:fielderror>                            
                        </span>                       
                    </td>
                     
                </tr>
            </table>
             <div class="actionsrow">
             <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="#" cssClass="btn" onclick="handleMailAction()"><span class="btn_img"><span class="save">Send Email</span></span></s:a></li>                
                </ul>   
            </del>
        </div> 
 </div>       