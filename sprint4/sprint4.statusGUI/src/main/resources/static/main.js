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
        addMessageToWindow("Connessione al server avvenuta con successo");
    };

    socket.onmessage = function (event) { // RICEZIONE
        let [sender, payload] = event.data.split("/");
        if (payload !== undefined) {
            const regex = /^(\w+)\((.*?)\)$/;
            const match = payload.match(regex);
            const type = match[1];
            const data = match[2];

            switch (sender) {
                case "basicrobot":
                    stateVal.innerHTML = payload
                    break;
                case "robotpos":
                    posVal.innerHTML = payload
                    break;
                case "coldstorageservice":
                    if (type==="weight")
                        setPeso(...(parsePeso(data)))
                    if (type==="rejected")
                        rejVal.innerHTML = data
                    break;
                default:
                    addMessageToWindow(event.data)
                    break;
            }
        }
    };
    return socket;
}

const maxVal = document.getElementById("maxVal")
const resVal = document.getElementById("resVal")
const curVal = document.getElementById("curVal")
const posVal = document.getElementById("posVal")
const stateVal = document.getElementById("stateVal")
const rejVal = document.getElementById("rejVal")

const messageWindow = document.getElementById("messageArea");

function parsePeso(payload) {
    const regex = /[+-]?\d+(\.\d+)?/g;
    return payload.match(regex)
}

function setPeso(cur, res, max) {
    curVal.innerHTML = cur.toString()
    resVal.innerHTML = res.toString()
    maxVal.innerHTML = max.toString()
}

function addMessageToWindow(message) {
    messageWindow.innerHTML += "<div class=\"testo\">" + message + "</div>"
}
