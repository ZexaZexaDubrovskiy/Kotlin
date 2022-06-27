package com.example.extendedshoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView

class ShoppingAdapter(
    private val context: Context,
    private val resource: Int,
    private val objects: MutableList<Shopping>
) :
    BaseAdapter() {

    override fun getCount(): Int {
        return objects.size
    }

    override fun getItem(position: Int): Any {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val shopping = getItem(position) as Shopping
        val holder = if (view != null) {
            view
        } else {
            LayoutInflater.from(context).inflate(resource, parent, false)
        }
        val nameShoppingTextView = holder.findViewById<TextView>(R.id.shoppingNameTextView)
        val quantityShoppingTextView = holder.findViewById<TextView>(R.id.shoppingQuantityTextView)
        nameShoppingTextView.text = shopping.name
        quantityShoppingTextView.text = shopping.quantity
        return holder
    }
}