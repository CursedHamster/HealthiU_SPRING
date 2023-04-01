const register_form = $('#register_form');
var error_login = $('error_login')
register_form.submit(function validate() {
    console.log("log log")
    error_login.text("")
    if (document.getElementsByName('login')[0].value === null ||
        document.getElementsByName('login')[0].value === "") {
        error_login.text("Login can't be blank")
        return false;
    } else if (document.getElementsByName('login')[0].value.length < 3 ||
        document.getElementsByName('login')[0].value.length > 20) {
        error_login.text("Login can be of length from 3 to 10")
    }
    if (document.getElementsByName('name')[0].value === null ||
        document.getElementsByName('name')[0].value === "") {
        error_login.text("Name can't be blank")
        return false;
    }
    if (document.getElementsByName('email')[0].value === null ||
        document.getElementsByName('email')[0].value === "") {
        error_login.text("Email can't be blank")
        return false;
    }
    if (document.getElementsByName('password')[0].value === null ||
        document.getElementsByName('password')[0].value === "") {
        error_login.text("Password can't be blank")
        return false;
    }
    if (document.getElementsByName('date_of_birth')[0].value === null ||
        document.getElementsByName('date_of_birth')[0].value === "") {
        error_login.text("Date of birth can't be blank")
        return false;
    }
    return true;
})
;