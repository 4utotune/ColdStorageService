var socket = connect();

function connect() {
    const host = document.location.host;
    const pathname = document.location.pathname;
    const addr = "ws://" + host + pathname + "accessgui";

    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); // CONNESSIONE

    socket.onopen = function (event) {
        addMessageToWindow("Connessione al server avvenuta con successo");
    };

    socket.onmessage = function (event) { // RICEZIONE
        let [type, payload] = event.data.split("/");
        if (payload !== undefined)
            switch (type) {
                case "update":
                    setPeso(...payload.split(","))
                    break;
                case "ticket":
                    addMessageToWindow("Ticket: " + payload)
                    break;
                case "notify":
                    if (payload === "chargetaken") {
                        addMessageToWindow("Charge taken!")
                    } else {
                        addMessageToWindow(payload.replace("accepted", "accettato"))
                    }
                    break;
                case "error":
                    addMessageToWindow("Errore! " + payload)
                    break;
                default:
                    addMessageToWindow("" + `${event.data}`)
                    break;
            }
    };
    return socket;
}

const peso = document.getElementById("peso");
const ticket = document.getElementById("ticket");
const messageWindow = document.getElementById("messageArea");
const progress = document.getElementById("progress");
const curSpan = document.getElementById("cur")
const maxSpan = document.getElementById("max")

function setPeso(cur, max) {
    const perc = cur / max * 100
    progress.style.setProperty("--value", Math.ceil(perc).toString());
    progress.setAttribute("aria-valuenow", perc.toString());
    curSpan.innerHTML = cur.toString()
    maxSpan.innerHTML = max.toString()
}

function submitPressedPeso() {
    if (isNaN(peso.value) || peso.value.length === 0) {
        addMessageToWindow("Inserisci un numero valido")
    } else {
        sendMessage("storerequest/" + peso.value);
    }
    peso.value = "";
}

function submitPressedTicket() {
    sendMessage("insertticket/" + ticket.value);
    ticket.value = "";
}

function sendMessage(message) {
    socket.send(message);
}

function addMessageToWindow(message) {
    messageWindow.innerHTML += "<div class=\"testo\">" + message + "</div>"
}