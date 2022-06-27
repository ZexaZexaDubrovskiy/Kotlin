package com.example.rpcollegemobile.itemEvent


import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.rpcollegemobile.databinding.ItemEventBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class EventHolder(
    view: View,
    onItemClick: (position: Int) -> Unit
): RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener { onItemClick(this.layoutPosition) }
    }

    private val viewBinding = ItemEventBinding.bind(view)

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(event: Event){
       // val tittleTextView = itemView.findViewById<TextView>(R.id.TextViewTitleNameEventItemEvent)
        event.name
        viewBinding.apply {
            TextViewActivityTypeEventItemEvent.text = event.activityType
            TextViewNameEventItemEvent.text = event.name

            if (event.planDate != "null") {
                //TextViewDataStartItemEvent.text = SimpleDateFormat("yyyy-MM-dd").parse(event.planDate).toString();
                //не бейте, пожалуйста
                var asd = event.planDate.split('-')
                TextViewDataStartItemEvent.text = asd[2]+"."+asd[1] +"." +asd[0]
            }


            //TextViewDataStartItemEvent.text = l
            //не бейте, пожалуйста
            //var asd = event.planDate.split('-')
            //TextViewDataStartItemEvent.text = asd[2]+"."+asd[1] +"." +asd[0]
        }
    }


    //mechanism destroy

}