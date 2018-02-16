package webtests

import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import io.vertx.kotlin.ext.web.handler.sockjs.BridgeOptions
import io.vertx.kotlin.ext.web.handler.sockjs.PermittedOptions
import khttp.get


class Sandbox : AbstractVerticle() {

  override fun start() {

    // Build Vert.x Web router
    val router = Router.router(vertx)

    // Serve the static resources
    router.route().handler(StaticHandler.create())

    // Allow events for the designated addresses in/out of the event bus bridge
    val opts = BridgeOptions(
            outboundPermitteds = listOf(PermittedOptions(
                    address = "chat.to.client")))

    // Create the event bus bridge and add it to the router.
    val ebHandler = SockJSHandler.create(vertx).bridge(opts)
    router.route("/eventbus/*").handler(ebHandler)

    // Start the server
    vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(8080)

    val eb = vertx.eventBus()

    vertx.setPeriodic(3000) { _ ->
      val messageToClient = getMessage()
      eb.publish("chat.to.client", messageToClient)
    }
  }
}

fun main(args: Array<String>) {
  val vertx = Vertx.vertx()
  vertx.deployVerticle(Sandbox()) { sb ->
    if (sb.succeeded()) {
      println("Application started")
    } else {
      println("Could not start application")
      sb.cause().printStackTrace()
    }
  }
}

private fun getMessage(): Double {
  val r = get("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD")
  return r.jsonObject.getDouble("USD")
}