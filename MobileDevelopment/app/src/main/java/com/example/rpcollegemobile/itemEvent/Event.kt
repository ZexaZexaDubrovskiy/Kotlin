package com.example.rpcollegemobile.itemEvent

import java.io.Serializable
import java.util.*

data class Event(
    val id: Int,
    val activityType: String = "",
    val name: String = "",
    val subdivision: String = "",
    val local: Int = 0,
    val contWorId: Int,
    val contWorName: String = "",
    val planDate: String = "",
    val place: String = "",
    val execDate: String = "",
    val responsibleWorkers: String = "",
    val signed: Int = 0,
    val canceled: Int= 0,
    val planStartTime: String = "",
    val planEndTime: String = "",
    val planDateTimeStr: String = ""
) : Serializable {

//    companion object {
//        fun newInstance(NAME: String, SUBDIVISION: String): Event {
//            val e = Event(1,
//                "",
//                NAME,
//                "",
//                2,
//                1,
//                "",
//                "",
//                "",
//                "",
//                "",
//                1,
//                1,
//                "4",
//                "",
//                "")
//            return e
//        }
//    }
}