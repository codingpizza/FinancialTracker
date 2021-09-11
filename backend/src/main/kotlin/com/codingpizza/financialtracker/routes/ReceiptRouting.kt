package com.codingpizza.financialtracker.routes

import com.codingpizza.financialtracker.model.DeleteStatus
import com.codingpizza.financialtracker.model.dto.ReceiptDto
import com.codingpizza.financialtracker.repositories.ReceiptRepository
import com.codingpizza.financialtracker.repositories.ReceiptDtoStoreRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.receiptRouting(
    receiptRepository: ReceiptRepository,
    storeRepository: ReceiptDtoStoreRepository
) {
    route("/receipt") {
        get {
            val receipts = receiptRepository.retrieveReceipts()
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
            val receipt = receiptRepository.findById(id = id) ?: return@get call.respondText(
                    "No receipt with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(receipt)
        }

        patch("{id}") {
            val id = call.parameters["id"] ?: return@patch call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val receipt = call.receive<ReceiptDto>()
            storeRepository.updateReceipt(id.toInt(),receipt)
            call.respondText("Receipt stored correctly", status = HttpStatusCode.OK)

        }

        post {
            val receipt = call.receive<ReceiptDto>()
            storeRepository.storeReceipt(receipt)
            call.respondText("Receipt stored correctly", status = HttpStatusCode.Created)
        }

        delete("{id}") {
            val id = call.parameters["id"]?: return@delete call.respond(HttpStatusCode.BadRequest)
            when (receiptRepository.removeById(id)) {
                DeleteStatus.Error -> call.respondText("No receipt with id $id", status = HttpStatusCode.NotFound)
                DeleteStatus.Success -> call.respondText("Receipt removed correctly", status = HttpStatusCode.Accepted)
            }
        }

    }
}