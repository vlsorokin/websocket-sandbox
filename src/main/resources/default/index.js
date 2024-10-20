let ws;

function connect() {
  const url = "ws://localhost:8080/sandbox";
  log("CONNECTING: " + url, "green");
  ws = new WebSocket(url);

  ws.onopen = () => {
    log("CONNECTED", "green");
  };

  ws.onmessage = (evt) => {
    log("RESPONSE: " + evt.data, "blue");
    console.log("RESPONSE: ", evt);
  };

  ws.onerror = (evt) => {
    log("ERROR: " + evt.type, "red");
    console.log("ERROR: ", evt);
  };

  ws.onclose = (evt) => {
    log("CLOSE: " + evt.code + " " + evt.reason, "green");
    console.log("CLOSE: ", evt);
  };
}

function randomText(len) {
  const allCharacters =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  var randomText = "";
  for (var i = 0; i < len; i++) {
    var k = Math.floor(Math.random() * allCharacters.length);
    randomText += allCharacters.charAt(k);
  }
  return randomText;
}

function sendText(len) {
  if (!ws) return;
  log("SENDING: " + len + " characters");
  ws.send(randomText(len));
}

function disconnect() {
  if (!ws) return;
  ws.close(4000, "Closed by client");
  ws = null;
}

function $() {
  return document.getElementById(arguments[0]);
}

function log(s, color) {
  $("output").innerHTML += "<p style='color: " + color + ";'>> " + s + " </p>";
}

function clear() {
  $("output").innerHTML = "";
}

$("connect").onclick = connect;
$("disconnect").onclick = disconnect;
$("sendShortText").onclick = () => {
  sendText(10);
};
$("sendLargeText").onclick = () => {
  sendText(10000);
};
$("clear").onclick = clear;
