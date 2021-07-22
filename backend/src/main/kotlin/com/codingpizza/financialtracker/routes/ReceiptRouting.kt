package com.codingpizza.financialtracker.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import com.codingpizza.financialtracker.model.Receipt
import io.ktor.request.*

fun Route.receiptRouting() {
    val receiptStorage = mutableListOf<Receipt>()
    route("/receipt") {
        get {
            if (receiptStorage.isNotEmpty()) {
                call.respond(receiptStorage)
            } else {
                call.respondText("No receipt founds",status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val receipt =
                receiptStorage.find { it.id == id } ?: return@get call.respondText(
                    "No receipt with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(receipt)
        }
        post {
            val receipt = call.receive<Receipt>()
            receiptStorage.add(receipt)
            call.respondText("Receipt stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (receiptStorage.removeIf { it.id == id }) {
                call.respondText("Receipt removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}