const planId = new URLSearchParams(window.location.search).get("planId");
const wsMessageUri = "ws://" + document.location.host + document.location.pathname + "live/" + planId;

// inspired from https://gist.github.com/ismasan/299789
const MultiEventWebsocket = function(url){
    let dispatch = function(eventName, eventData){
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
            conn.send(message);
        return this;
    };

    this.isOpen = function() {
        return conn.readyState === chatWebSocket.OPEN;
    }

    conn.onmessage = function(evt){
            const json = JSON.parse(evt.data);
            dispatch(json.event, json.data);
    };
    conn.onclose = function(){
        dispatch('close',null);
    }
    conn.onopen = function(){
        dispatch('open',null);
    }


};
const chatWebSocket = new MultiEventWebsocket(wsMessageUri);
chatWebSocket.bind("changeStopApproval", function(data){
    let control = $("#stop-control-"+data.stopId);
    control.find(".stop-accept").remove();
    control.find(".stop-deny").remove();
    $("#pending-stops").remove("#stop-control-"+data.stopId);
    let beforeIndex = data.insertPosition - 1;
    control.find(".stop-status").text(data.approved ? "Accepted" : "Denied");
    if(beforeIndex !== -1) {
        console.log("#" + (data.approved ? "approved" : "denied") + "-stops > div:nth-child(" + (beforeIndex) + ")");
        $("#" + (data.approved ? "approved" : "denied") + "-stops > div:nth-child(" + (beforeIndex) + ")").after()
    } else {
        $("#" + (data.approved ? "approved" : "denied") + "-stops").prepend(control);
    }
});


let approves = document.getElementsByClassName("approve-stop");
for (let i = 0; i < approves.length; i++)
{
    approves[i].addEventListener("click", function(event) {
        event.preventDefault();
        const stopId = +approves[i].getAttribute("value");
        const json = JSON.stringify({
            "stopId": stopId,
            "approved": true
        });
        chatWebSocket.send("changeStopApproval", json);
    });
}

let denies = document.getElementsByClassName("deny-stop");
for (let i = 0; i < denies.length; i++)
{
    denies[i].addEventListener("click", function(event) {
        event.preventDefault();
        const stopId = +denies[i].getAttribute("value");
        const json = JSON.stringify({
            "stopId": stopId,
            "approved": false
        });
        chatWebSocket.send("changeStopApproval", json);
    });
}