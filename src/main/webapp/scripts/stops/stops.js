const planId = new URLSearchParams(window.location.search).get("planId");
const wsMessageUri = "ws://" + document.location.host + document.location.pathname + "/stopslive/" + planId;

// inspired from https://gist.github.com/ismasan/299789
const MultiEventWebsocket = function(url){
    const dispatch = function(eventName, eventData){
        const chain = callbacks[eventName];
        if(typeof chain == 'undefined') return;
        for(let i = 0; i < chain.length; i++){
            chain[i]( eventData );
        }
    };
    const conn = new WebSocket(url);
    const callbacks = {};

    this.bind = function(eventName, callback){
        callbacks[eventName] = callbacks[eventName] || [];
        callbacks[eventName].push(callback);
        return this;
    };

    this.send = function(eventName, eventData){
        const message = JSON.stringify({event: eventName, data: eventData});
        conn.send( message );
        return this;
    };

    conn.onmessage = function(evt){
        if (this.isOpen())
        {
            const json = JSON.parse(evt.data);
            dispatch(json.event, json.data);
        }
    };
    conn.onclose = function(){
        dispatch('close',null);
    }
    conn.onopen = function(){
        dispatch('open',null);
    }


    this.isOpen = function() {
        return conn.readyState === chatWebSocket.OPEN;
    }
};
const chatWebSocket = new MultiEventWebsocket(wsMessageUri);
chatWebSocket.bind("changeStopApproval", function(data){
   alert(JSON.stringify(data));
});

console.log("lets go");

let approves = document.getElementsByClassName("approve-stop");
for (let i = 0; i < approves.length; i++)
{
    approves[i].addEventListener("submit", function(event) {
        event.preventDefault();
        const stopId = approves[i].getAttribute("value");
        const json = JSON.stringify({
            "stopId": stopId,
            "approved": true
        });
        chatWebSocket.send("changeStopApproval", json);
    });
}

let denies = document.getElementsByClassName("deny-stop");
for (let i = 0; i < approves.length; i++)
{
    approves[i].addEventListener("submit", function(event) {
        event.preventDefault();
        const stopId = approves[i].getAttribute("value");
        const json = JSON.stringify({
            "stopId": stopId,
            "approved": false
        });
        chatWebSocket.send("changeStopApproval", json);
    });
}