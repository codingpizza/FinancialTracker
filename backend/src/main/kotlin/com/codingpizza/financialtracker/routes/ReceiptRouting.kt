package com.codingpizza.financialtracker.routes

import com.codingpizza.financialtracker.ReceiptRepository
import com.codingpizza.financialtracker.model.DeleteResult
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import com.codingpizza.financialtracker.model.Receipt
import io.ktor.request.*

fun Route.receiptRouting() {
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
            val id = call.parameters["id"] ?: return@get call.respondText(
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
            val id = call.parameters["id"]?: return@delete call.respond(HttpStatusCode.BadRequest)
            when (ReceiptRepository.removeById(id)) {
                DeleteResult.Error -> call.respondText("No receipt with id $id", status = HttpStatusCode.NotFound)
                DeleteResult.Success -> call.respondText("Receipt removed correctly", status = HttpStatusCode.Accepted)
            }
        }

    }
}