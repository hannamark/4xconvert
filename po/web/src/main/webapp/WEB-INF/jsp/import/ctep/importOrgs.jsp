<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title>CTEP Organization Import</title>
    <c:set var="topic" scope="request" value="importorg"/>
<script type="text/javascript">
function upload() {
    $('progress_div').show();
    $('uploadForm').submit();
}
</script>
</head>
<body>

<po:successMessages/>

<div id="page" style="margin-top:10px;">
    <div class="boxouter_nobottom">
        <h2>CTEP Organization Import</h2>
        <div class="boxouter">
            <div class="box_white">
                <s:actionerror />
                <div id="progress_div" style="display: none; margin: 3px 3px">
                    <img alt="Indicator" align="absmiddle" src="<c:url value="/images/loading.gif"/>" /> Uploading and importing...
                </div>
                <s:form action="import/ctep/org/uploadOrganizations" id="uploadForm" method="POST"
                        enctype="multipart/form-data" onsubmit="upload();">
                    
                    <s:token/>
                    <s:file name="file" label="File" />
                    <s:textfield name="ctepId" label="CTEP ID"/>
                    <input id="enableEnterSubmit" type="submit"/>
                </s:form>
            </div>
        </div>
    </div>
</div>
<div style="clear:left;">
</div>
    <div class="btnwrapper" style="margin-bottom:20px;">
    <po:buttonRow>
       <po:button id="save_button" href="javascript://noop/" onclick="upload();" style="save" text="Upload"/>
    </po:buttonRow>
    </div>
</body>
</html>
