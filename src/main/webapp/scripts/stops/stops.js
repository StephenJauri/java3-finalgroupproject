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
    control.remove();
    let beforeIndex = data.insertPosition;
    control.find(".stop-status").text(data.approved ? "Accepted" : "Denied");
    if(beforeIndex !== 0) {
        console.log("#" + (data.approved ? "approved" : "denied") + "-stops > div:nth-child(" + (beforeIndex) + ")");
        $("#" + (data.approved ? "approved" : "denied") + "-stops > div:nth-child(" + (beforeIndex) + ")").after(control)
    } else {
        console.log("#" + (data.approved ? "approved" : "denied") + "-stops > div:nth-child(" + (beforeIndex) + ")");
        control.prependTo("#" + (data.approved ? "approved" : "denied") + "-stops");
    }
});

chatWebSocket.bind("newStopAdded", function(data) {
    $.get("stop?stopId=" + data.stopId, function(newData) {
        $("#pending-stops").append(newData);
    });
});


$(document).ready(function() {
    let denies = $(".deny-stop");
    denies.click(function (event) {
        event.preventDefault();
        const stopId = +$(this).val();
        const json = JSON.stringify({
            "stopId": stopId,
            "approved": false
        });
        chatWebSocket.send("changeStopApproval", json);
    });

    let approves = $(".approve-stop");
    approves.click(function (event) {
        event.preventDefault();
        const stopId = +$(this).val();
        const json = JSON.stringify({
            "stopId": stopId,
            "approved": true
        });
        chatWebSocket.send("changeStopApproval", json);
    });
});