function submitPressed() {
    var campoPeso = document.getElementById("peso");
    var valorePeso = campoPeso.value;
    var campoTicket = document.getElementById("ticket");
    var valoreTicket = campoTicket.value;
    if (controlla(valorePeso, valoreTicket)) {
        if (valorePeso != "") {
            alert("Peso inserito: " + valorePeso);
        } else {
            alert("Ticket inserito: " + valoreTicket);
        }
    } else {
        alert("Inserire solo un campo!");
    }
}

function controlla(valorePeso, valoreTicket) {
    if (valorePeso == "" && valoreTicket == "") {
        alert("Inserire tutti i campi!");
        return false;
    } if (valorePeso == "" && valoreTicket != "") {
        return true;
    } if (valorePeso != "" && valoreTicket == "") {
        return true;
    } else {
        return false;
    }
}
var value = 0;
function submitPressedPeso() {
    var progress = document.getElementById("progress");
    value += 10;
    if (value > 100) {
        value = 100;
    }
    progress.style.setProperty("--value", value);
    progress.setAttribute("aria-valuenow", value);
}



