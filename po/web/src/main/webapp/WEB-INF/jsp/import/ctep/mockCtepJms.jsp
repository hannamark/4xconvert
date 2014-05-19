<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html>
<head>
<title>Mock CTEP JMS Message</title>
<c:set var="topic" scope="request" value="" />
<script type="text/javascript">
	function sendJms() {
		$('sendJmsForm').submit();
	}
</script>
</head>
<body>

	<po:successMessages />

	<div id="page" style="margin-top: 10px;">
		<div class="boxouter_nobottom">
			<h2>Mock CTEP JMS Message</h2>
			<div class="boxouter">
				<div class="box_white">
					<s:actionerror />
					<p>
						Use this form to send a 'fake' JMS message from CTEP. The system
						will react as if this message was received from CTEP. So this form
						will help you simulate CTEP messages without actually having to
						get in touch with CTEP to trigger a message. Place XML content of
						the message into the box below. Click <a href="javascript://noop/"
							onclick="$('xmlExample').show();">here</a> for an example.
					</p>
					<div id="xmlExample" style="display: none;">
<pre>&lt;?xml version = '1.0' encoding = 'UTF-8'?&gt;
&lt;ROWSET&gt;
   &lt;ROW&gt;
      &lt;TRANSACTION_TYPE&gt;UPDATE&lt;/TRANSACTION_TYPE&gt;
      &lt;RECORD_TYPE&gt;PERSON&lt;/RECORD_TYPE&gt;
      &lt;RECORD_ID&gt;18231&lt;/RECORD_ID&gt;
      &lt;PERSON_TYPE&gt;ClinicalResearchStaff&lt;/PERSON_TYPE&gt;
      &lt;DUPLICATE_OF/&gt;
   &lt;/ROW&gt;
&lt;/ROWSET&gt;</pre>
					</div>
					<s:form action="import/ctep/jms/sendMockedCtepJms" id="sendJmsForm"
						method="POST" onsubmit="sendJms();">
						<s:textarea name="jmsMsg" label="Message XML" rows="20" cols="60"></s:textarea>
						<input id="enableEnterSubmit" type="submit" />
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<div style="clear: left;"></div>
	<div class="btnwrapper" style="margin-bottom: 20px;">
		<po:buttonRow>
			<po:button id="save_button" href="javascript://noop/"
				onclick="sendJms();" style="save" text="Send JMS" />
		</po:buttonRow>
	</div>
</body>
</html>
