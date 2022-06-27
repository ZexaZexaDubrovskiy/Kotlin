package com.example.extendedshoppinglist

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentResultListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.skillbox.extendedshoppinglist.AddFragment

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.Serializable

class MainFragment : Fragment() {

    private fun loadShoppings() {
        val appSharedPreferences = requireContext().getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE)
        val jsonString = appSharedPreferences.getString(KEY_SHOPPINGS, "[]")
        //если не пусто, то загружаем
        if (jsonString != "[]") {
            shoppings.apply {
                clear()
                addAll(getMoshiAdapter().fromJson(jsonString!!)!!)
            }
        }
    }

    private fun getMoshiAdapter(): JsonAdapter<MutableList<Shopping>> {
        val moshi = Moshi.Builder().build()
        val listType = Types.newParameterizedType(MutableList::class.java, Shopping::class.java)
        return moshi.adapter(listType)
    }

    //save
    private fun saveShoppings() {
        val appSharedPreferences = requireContext().getSharedPreferences(KEY_APP_PREFERENCES, MODE_PRIVATE).edit()
        val jsonString = getMoshiAdapter().toJson(shoppings)
        appSharedPreferences.putString(KEY_SHOPPINGS, jsonString)
        appSharedPreferences.apply()
    }

    private val KEY_APP_PREFERENCES = "KEY_APP_PREFERENCES"
    private val KEY_SHOPPINGS = "KEY_SHOPPING"
    override fun onStop() {
        saveShoppings()
        super.onStop()
    }

    private val shoppings: MutableList<Shopping> = mutableListOf(
        Shopping("bread black", "1 box"),
        Shopping("Cheese", "600 g"),
        Shopping("meet var", "500 g"),
        Shopping("Milk with water", "200 g"),
        Shopping("Potato", "7 kg"),
        Shopping("red berry", "3 kg"),
        Shopping("sd", "1 kg"),
        Shopping("Green gorox", "2 box"),
        Shopping("SALO", "700 g"),
        Shopping("old milk", "0,5 l"),
        Shopping("Apple", "2,5 kg"),
        Shopping("PineBanana", "1 kg"),
        Shopping("parents asiat", "200 g"),
        Shopping("parents", "1 gk"),
        Shopping("Spagetti", "2 kg"),
        Shopping("PineAplle", "400 g"),
        Shopping("BlackBerry", "250 g"),
    )





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingAdapter = ShoppingAdapter(requireContext(), R.layout.item_shopping, visibleShoppings)

        loadShoppings() //загрузить данные
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    //для сеоч
    private val onSearchTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            updateShoppings()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            updateShoppings()
            return true
        }

    }

    private fun updateShoppings() {
        val mask = searchView.query.toString()
        visibleShoppings.clear()
        if (mask.isEmpty()) {
            visibleShoppings.addAll(shoppings)
        } else
        {
            visibleShoppings.addAll(shoppings.filter { s -> s.name.contains(mask, true)})
        }
        shoppingAdapter.notifyDataSetChanged()
    }

    private val visibleShoppings: MutableList<Shopping> = mutableListOf()

    private lateinit var searchView : SearchView;
    private lateinit var shoppingAdapter: ShoppingAdapter;
    private lateinit var shoppingListView: ListView;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingListView = requireView().findViewById<ListView>(R.id.shoppingsListView)
        shoppingListView.adapter = shoppingAdapter

        val mainFragmentToolbar =
            requireView().findViewById<androidx.appcompat.widget.Toolbar>(R.id.mainFragmentToolbar)
        mainFragmentToolbar.setOnMenuItemClickListener(onMenuItemClick)

        //search
        searchView = mainFragmentToolbar.menu.findItem(R.id.searchAction).actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(onSearchTextListener)
        updateShoppings()




        val addFloatingActionButton =
            requireView().findViewById<FloatingActionButton>(R.id.addFloatingActionButton)
        addFloatingActionButton.setOnClickListener(onAddFloatingActionButtonClickListener)
        shoppingListView.setOnItemClickListener(onShoppingsItemClickListener)

        parentFragmentManager.setFragmentResultListener(
            AddFragment.REQUEST_KEY_ADD_FRAGMENT,
            viewLifecycleOwner,
            onFragmentResultListener
        )

        childFragmentManager.setFragmentResultListener(
            AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG,
            viewLifecycleOwner,
            onFragmentResultListener
        )



        shoppingListView.setOnItemLongClickListener { _, _, pos, _ ->
            AlertDialogFragment.newInstance(
                AlertDialogFragment.QUESTION_CODE_REMOVE_ITEM,
                "Подтверждение действия",
                "Вы действительно хотите удалить элемент \"${shoppings[pos].name}\"",
                "Да",
                "Все равно да"
            ).show(childFragmentManager, AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG)
            posis = pos;
            true
        }
        updateShoppings()
    }
    private var posis = 0;

    private val onFragmentResultListener = object : FragmentResultListener {
        override fun onFragmentResult(requestKey: String, result: Bundle) {
            when (requestKey) {
               AddFragment.REQUEST_KEY_ADD_FRAGMENT -> {
                       var position = result.getInt(AddFragment.KEY_POSITION) ?: -1
                       val shopping = result.getSerializable(AddFragment.KEY_SHOPPING) as Shopping?
                       if (shopping != null) {
                           if (position < 0) {
                               shoppings.add(shopping)
                           } else {
                               shoppings.set(position, shopping)
                           }
                           shoppings.sortBy { s -> s.name }
                           updateShoppings()
                           position = visibleShoppings.indexOf(shopping)
                           if (position >= 0) {
                               shoppingListView.smoothScrollToPosition(position)
                           }


                           shoppingListView.smoothScrollToPosition(visibleShoppings.indexOf(shopping))
                       }
               }
               AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG -> {
                   val questionCode = result.getInt(AlertDialogFragment.KEY_QUESTION_CODE) ?: 1
                   when (questionCode) {
                       AlertDialogFragment.QUESTION_CODE_CLEAR_ITEMS -> {
                           shoppings.clear()
                           updateShoppings()
                       }
                       AlertDialogFragment.QUESTION_CODE_REMOVE_ITEM -> {
                            shoppings.removeAt(posis)

                           updateShoppings()
                       }


                    }
               }
            }
        }
    }

    private val onShoppingsItemClickListener = object : AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            val shopping = shoppings.get(position)
            editShopping(position, shopping)
        }
    }

    private val onAddFloatingActionButtonClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            editShopping(-1, null)
        }
    }

    private fun editShopping(positon: Int, shopping: Shopping?) {
        (activity as MainActivity).showFragment(AddFragment.newInstance(positon, shopping))
    }

    private val onMenuItemClick =
        object : androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.searchAction -> {

                        return true
                    }
                    R.id.addAction -> {
                        editShopping(-1, null)

                        return true
                    }
                    R.id.clearAction -> {
                        clearShoppings()
                        return true
                    }
                    R.id.infoAction -> {

                        return true
                    }
                    else -> {
                        //Error("Выбрана неверная команда в меню!")
                        return false
                    }
                }
            }
        }

    private fun clearShoppings() {
        AlertDialogFragment.newInstance(
            AlertDialogFragment.QUESTION_CODE_CLEAR_ITEMS,
            "Подтверждение",
            "Вы уверены, что хотите очистить список покупок?",
            "Да",
            "Все равно да"
        ).show(
            childFragmentManager,
            AlertDialogFragment.REQUEST_KEY_ALERT_DIALOG
        )
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle()
            }
    }
}