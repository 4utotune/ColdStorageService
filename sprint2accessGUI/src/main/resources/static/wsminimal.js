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

const messageWindow = document.getElementById("messageArea");
const weightInput = document.getElementById("inputweight");
const ticketInput = document.getElementById("inputticket");
const storeButton = document.getElementById("store");
const ticketButton = document.getElementById("ticket");

storeButton.onclick = function (event) {
    sendMessage("storerequest-" + weightInput.value);
    weightInput.value = "";
}

ticketButton.onclick = function (event) {
    sendMessage("insertticket-" + ticketInput.value);
    ticketInput.value = "";
}

function sendMessage(message) {
    var jsonMsg = JSON.stringify({'name': message});
    socket.send(message);
}

function addMessageToWindow(message) {
    messageWindow.innerHTML += message + "\n"
}