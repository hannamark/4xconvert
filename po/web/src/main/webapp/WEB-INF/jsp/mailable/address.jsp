<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<s:set name="isCreate" value="index == null || index == -1" /> 
<s:set name="isNotCreate" value="index != null" /> 
<s:if test="%{isCreate}">
    <title>Add Postal Address</title>
</s:if>
<s:else>
    <title>Edit Postal Address</title>
</s:else>
    <script type="text/javascript">
        var returnVal;
        function close() {
             returnVal = -1;
             window.top.hidePopWin(true);
        }
    </script>
</head>
<body> 
<c:if test="${not empty messages}">
    <po:successMessages/>
    <script>
    close();
    </script>
</c:if>
    <div class="po_wrapper">
        <div class="po_inner">
			<s:if test="%{isCreate}">
	            <h1>Add Postal Address</h1>
			</s:if>
			<s:else>
	            <h1>Edit Postal Address</h1>
			</s:else>        
            <div class="box_white">
                <div class="po_form">
            <s:if test="%{isCreate}">
                <s:set name="formAction"
                    value="'popup/address/add.action'" />
            </s:if> <s:else>
                <s:set name="formAction"
                    value="'popup/address/edit.action'" />
            </s:else>                
     <s:form action="%{formAction}" 
        id="postalAddressForm" 
        onsubmit="return true;">
     <s:actionerror/> 
     <s:hidden key="rootKey"/>
     <s:hidden key="address.id"/>
     <s:hidden key="index"/>
     <po:addressForm address="${address}" addressKeyBase="address" formNameBase="postalAddressForm" required="true"/>
     <input id="enableEnterSubmit" type="submit"/>
     </s:form>
     <div class="btnwrapper" style="margin-bottom:20px;">
	     <po:buttonRow>
	         <po:button href="javascript://noop/" 
	             onclick="$('postalAddressForm').submit();" 
	             style="save" text="Save" 
	             id="submitPostalAddressForm"/>
	     </po:buttonRow>
     </div> 
				</div>   
            </div>
        </div>
    </div>
</body>
</html>
