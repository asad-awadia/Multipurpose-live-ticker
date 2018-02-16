$(function () {
    var eb = new EventBus("/eventbus/");
    eb.onopen = function () {
        eb.registerHandler("chat.to.client", function (err, msg) {
            var oldPrice = document.getElementById("price").textContent;

            if (oldPrice === "Bitcoin") {
                $("#price").text = msg.body
            }

            if (parseInt(oldPrice) > parseInt(msg.body)) {
                $('#price').html("<i class=\"fas fa-angle-down\"></i> " + msg.body)
                    .css("color", "#D80027");
            }
            else {
                $('#price').html("<i class=\"fas fa-angle-up\"></i> " + msg.body)
                    .css("color", "#2bbc8b");
            }
        });
    };
});