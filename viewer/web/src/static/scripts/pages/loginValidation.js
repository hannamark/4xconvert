function validate() {
    var box = document.forms[0].j_username;
    var re = /^[0-9a-zA-Z\_\.\-]*$/;
    if (!re.exec(box.value)) {
        alert("Invalid Entry:\nOnly Alphanumeric, _ or . are allowed.");
        return false;
    }
    return true;
}

jQuery(function() {
    jQuery("#loginButton").bind("click", function() {
        if (validate()) {
            document.forms[0].submit();
        }
    });
});