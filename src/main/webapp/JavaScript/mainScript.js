$(document).ready(function () {
    $(document).ajaxSend(function (event, jqxhr, settings) {
        jqxhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("user"))
    });
    //var path = 'http://ubuntu4.saluton.dk:20002/Galgeleg/rest';
    //var path = 'http://localhost:8084/mavenproject1/rest';
    var path = 'http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest';

    // On login load useradmin page
    $("#login_form").submit(function () {
        event.preventDefault();
        document.getElementById("loginBtn").disabled = true;
        document.getElementById("loginBtn").style.opacity = 0.5;
        document.getElementById("loginBtn").style.cursor = "progress";

        var person = {
            "username": document.getElementById("usernameField").value,
            "password": document.getElementById("pswField").value
        };

        $.ajax({
            url: path + "/login",
            contentType: "application/json",
            method: 'POST',
            data: JSON.stringify(person),
            dataType: "json",
            success: function (resp) {
                console.log(resp["jwt"]);
                console.log(resp.valueOf(status));
                localStorage.setItem("user", resp["jwt"]); //session Storage						
                location.href = 'index.html';
                document.getElementById("loginBtn").disabled = false;
                document.getElementById("loginBtn").style.opacity = 01;
                document.getElementById("loginBtn").style.cursor = "pointer";

            },
            error: function (resp) {
                //Error handling...
                console.log(resp);
                console.log(resp.status);
                alert(resp.status + "! Wrong credentials");
                document.getElementById("loginBtn").disabled = false;
                document.getElementById("loginBtn").style.opacity = 01;
                document.getElementById("loginBtn").style.cursor = "pointer";
            }
        });
        return false;
    });


});

function setframe(url) {
    document.all.frame.src = url;
}
