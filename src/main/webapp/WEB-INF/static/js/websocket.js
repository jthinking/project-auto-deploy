//启动WebSocket JavaScript客户端，连接服务端发送消息

jQuery(function() {
    
    var ws = null;
    if ('WebSocket' in window) {
        ws = new WebSocket(WEB_SOCKET_URL + "?project=" + PROJECT_NAME + "&module=" + MODULE_NAME);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(WEB_SOCKET_URL + "?project=" + PROJECT_NAME + "&module=" + MODULE_NAME);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    ws.onopen = function () {
        console.log('Info: WebSocket connection opened.');
    };
    ws.onmessage = function (event) {
        $("#console").append(event.data + "<br>");
        var console = document.getElementById('console');
        console.scrollTop = console.scrollHeight;
    };
    ws.onclose = function (event) {
        console.log('Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
    };

    //关闭浏览器窗口或刷新页面前触发
    window.onbeforeunload = function() {
        ws.onclose = function () {}; // disable onclose handler first
        if (ws != null) {
            ws.close();
            ws = null;
        }
    };

});