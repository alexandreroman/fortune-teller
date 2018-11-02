function updateFortune() {
    var fortuneDiv = document.getElementById("fortune");
    fortuneDiv.innerText = "...";

    var sourceDiv = document.getElementById("fortune-source");
    sourceDiv.innerText = "";

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var json = JSON.parse(this.responseText);
            fortuneDiv.innerText = '“' + json.text + '”';
            if ("host" in json) {
                sourceDiv.innerText = json.host;
            }
        }
    };

    xhttp.open("GET", "fortune", true);
    xhttp.send();
}

var autoReloadTaskId = -1;

function toggleAutoReload(checkbox) {
    var enabled = checkbox.checked;
    if (enabled) {
        autoReloadTaskId = window.setInterval(updateFortune, 5000);
    } else {
        if (autoReloadTaskId != -1) {
            window.clearInterval(autoReloadTaskId);
            autoReloadTaskId = -1;
        }
    }
}

window.onload = function () {
    updateFortune();
};
