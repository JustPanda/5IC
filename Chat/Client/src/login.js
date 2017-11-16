var WinJS = require('winjs');
var net = require('net');

(function() {
    WinJS.UI.processAll().done(function() {
        var conn = net.createConnection({ port: 9090 }, () => {
            console.log('connected to server!');
            conn.write("login" + '\n');
        })

        var to;
        var name;

        var register = document.getElementById('register');
        var login = document.getElementById("login");

        var Register = function() {
            to = document.getElementById('to').value;
            name = document.getElementById('name').value
            var email = document.getElementById('email').value;
            var password = document.getElementById('password').value;
            var confirm = document.getElementById('confirm').value;

            conn.write(to + "," + name + "," + email + "," + password + "," + confirm + "," + "register" + '\n');
        }

        var Login = function() {
            to = document.getElementById('to').value;
            name = document.getElementById('name').value
            var email = document.getElementById('email').value;
            var password = document.getElementById('password').value;
            var confirm = document.getElementById('confirm').value;

            conn.write(to + "," + name + "," + email + "," + password + "," + confirm + "," + "login" + '\n');
        }

        register.addEventListener("click", Register);
        login.addEventListener("click", Login);

        conn.on('data', (data) => {
            if (data.toString().toLowerCase() == "login success" || "register success") {
                location.href = "./chat.html?" + to + ',' + name;
            }
        });
    })
}())