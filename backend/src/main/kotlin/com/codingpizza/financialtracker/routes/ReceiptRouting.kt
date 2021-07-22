package com.codingpizza.financialtracker.routes

import com.codingpizza.financialtracker.ReceiptRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import com.codingpizza.financialtracker.model.Receipt
import io.ktor.request.*
import kotlinx.coroutines.runBlocking

fun Route.receiptRouting() {
    val receiptStorage = mutableListOf<Receipt>()
    route("/receipt") {
        get {
            val receipts = ReceiptRepository.retrieveReceipts()
            if (receipts.isNotEmpty()) {
                call.respond(receipts)
            } else {
                call.respondText("No receipt founds", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val receipt = ReceiptRepository.findById(id = id) ?: return@get call.respondText(
                    "No receipt with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(receipt)
        }
        post {
            val receipt = call.receive<Receipt>()
            ReceiptRepository.storeReceipt(receipt)
            call.respondText("Receipt stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (receiptStorage.removeIf { it.id == id.toLong() }) {
                call.respondText("Receipt removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}