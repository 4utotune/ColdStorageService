var socket

function connect() {
    const host = document.location.host;
    const pathname = document.location.pathname;
    const addr = "ws://" + host + pathname + "statusgui";

    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); // CONNESSIONE

    socket.onopen = function (event) {
        console.log("Connessione al server avvenuta con successo");
    };

    socket.onmessage = function (event) { // RICEZIONE
        let parts = event.data.split("/");
        if (parts.length === 8) {
            curVal.innerHTML = parts[0]
            resVal.innerHTML = parts[1]
            maxVal.innerHTML = parts[2]
            posVal.innerHTML = "(" + parts[3] + "," + parts[4] + ")"
            rejVal.innerHTML = parts[5]
            stateVal.innerHTML = parts[6]
            otherVal.innerHTML = parts[7]
        }
    };

    return socket;
}

const maxVal = document.getElementById("maxVal")
const resVal = document.getElementById("resVal")
const curVal = document.getElementById("curVal")
const posVal = document.getElementById("posVal")
const rejVal = document.getElementById("rejVal")
const stateVal = document.getElementById("stateVal")
const otherVal = document.getElementById("otherVal")

const messageWindow = document.getElementById("messageArea");

function addMessageToWindow(message) {
    messageWindow.innerHTML += "<div class=\"testo\">" + message + "</div>"
}
