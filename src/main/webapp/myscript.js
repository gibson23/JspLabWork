/**
 * Password validation
 * Created by admin on 28.05.2016.
 */
function myFunction() {
    var pass1 = document.getElementById("password").value;
    var pass2 = document.getElementById("password2").value;
    var ok = true;
    if (pass1 != pass2) {
        alert("Passwords Do not match");
        document.getElementById("password").style.borderColor = "#E34234";
        document.getElementById("password2").style.borderColor = "#E34234";
        ok = false;
    }
    return ok;
}
/**
 * Email validation
 * @returns {boolean}
 */
function validateEmail() {
    var ok = true;
    var email = document.getElementById("email").value;
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(email)) {
        alert("Invalid email adress");
        document.getElementById("email").style.borderColor = "#E34234";
        ok=false;
    }
    return ok;
}