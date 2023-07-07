var socket = connect();

function connect() {
    const host = document.location.host;
    const pathname = document.location.pathname;
    const addr = "ws://" + host + pathname + "socket";

    // Assicura che sia aperta un unica connessione
    if (socket !== undefined && socket.readyState !== WebSocket.CLOSED) {
        alert("WARNING: Connessione WebSocket già stabilita");
    }
    socket = new WebSocket(addr); //CONNESSIONE

    socket.onopen = function (event) {
        addMessageToWindow("Connected");
    };
    socket.onmessage = function (event) { //RICEZIONE
        addMessageToWindow("" + `${event.data}`);
    };
    return socket;
}//connect

const peso = document.getElementById("peso");
const ticket = document.getElementById("ticket");
const messageWindow = document.getElementById("messageArea");

function submitPressedPeso(){
    var progress = document.getElementById("progress");
    value += 10;
    if (value > 100) {
        value = 100;
    }
    progress.style.setProperty("--value", value);
    progress.setAttribute("aria-valuenow", value);

    sendMessage("storerequest-" + peso.value);
    peso.value = "";
}

function submitPressedTicket(){
    sendMessage("insertticket-" + ticket.value);
    ticket.value = "";
}

function sendMessage(message) {
    var jsonMsg = JSON.stringify({'name': message});
    socket.send(message);
}

function addMessageToWindow(message) {
    messageWindow.innerHTML += message + "\n"
}