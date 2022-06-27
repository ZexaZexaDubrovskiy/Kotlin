package edu.skillbox.extendedshoppinglist

import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.extendedshoppinglist.MainFragment
import com.example.extendedshoppinglist.R
import com.example.extendedshoppinglist.Shopping
import com.example.extendedshoppinglist.ShoppingAdapter

class AddFragment : Fragment() {

    companion object {
        // Внешние константы фрагмента
        const val REQUEST_KEY_ADD_FRAGMENT = "REQUEST_KEY_ADD_FRAGMENT"
        const val KEY_POSITION = "KEY_POSITION"
        const val KEY_SHOPPING = "KEY_SHOPPING"

        // Метод создания экземпляра фрагмента
        @JvmStatic
        fun newInstance(position: Int, shopping: Shopping?) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_POSITION, position)
                    putSerializable(KEY_SHOPPING, shopping)
                }
            }
    }

    // *********************************************************************************************
    // Методы фрагмента
    // *********************************************************************************************

    // Метод, вызываемый для конструирования View фрагмента согдасно xml-шаблону
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    // Метод, вызываемый после создания View фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Получение исходных данных фрагмента
        val position = arguments?.getInt(KEY_POSITION) ?: -1
        // При приведении типа не забыть указать Shopping? <- (!), так как при попытке
        // добавления новой покупки в список в фрагмент будет передаваться null-значение!!!
        val shopping = arguments?.getSerializable(KEY_SHOPPING) as Shopping?
        requireView().apply {
            // Формирование заголовка фрагмента
            val addFragmentCaption = findViewById<TextView>(R.id.addFragmentCaption)
            addFragmentCaption.text = if (position < 0) {
                requireContext().getString(R.string.str_new_shopping_caption)
            } else {
                requireContext().getString(R.string.str_edit_shopping_caption)
            }
            // Заполнение визуальных компонентов фрагмента данными о покупке
            shoppingNameEditText = findViewById<EditText>(R.id.shoppingNameEditText)
            shoppingNameEditText.setText(shopping?.name ?: "")
            shoppingQuantityEditText = findViewById<EditText>(R.id.shoppingQuantityEditText)
            shoppingQuantityEditText.setText(shopping?.quantity ?: "")

            //observer
            //shopping.registerDataSetObserver(dataSetObserver)
            // Настройка кнопок фрагмента
            okButton = findViewById<Button>(R.id.okButton)

            okButton.setOnClickListener(onOkCancelButtonsClickListener)
            val cancelButton = findViewById<Button>(R.id.cancelButton)
            cancelButton.setOnClickListener(onOkCancelButtonsClickListener)

         //   shoppingNameEditText.setOnContextClickListener(setOnName)

        }
    }

    private lateinit var okButton: Button
    private lateinit var shoppingNameEditText: EditText
    private lateinit var shoppingQuantityEditText: EditText

    //govo
    private val dataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            if (shoppingNameEditText.text.isNotEmpty() && shoppingQuantityEditText.text.isNotEmpty()) {
                okButton.isEnabled = shoppingNameEditText.text.isNotEmpty()
            }
        }
    }

    //delete
//    private val setOnName = object : View.OnContextClickListener {
//        override fun onContextClick(p0: View?): Boolean {
//            okButton.isEnabled = shoppingQuantityEditText.text.isNotEmpty()
//
//        }
//    }

}


// *********************************************************************************************
// Слушатели событий
// *********************************************************************************************

// Слушатель нажатия на кнопки диалога
private val onOkCancelButtonsClickListener = object : View.OnClickListener {
    override fun onClick(view: View?) {
        if (view?.id == R.id.okButton) {
            val data = Bundle()
            val shoppingNameEditText =
                requireView().findViewById<EditText>(R.id.shoppingNameEditText)
            val shoppingQuantityEditText =
                requireView().findViewById<EditText>(R.id.shoppingQuantityEditText)
            val shopping = Shopping(
                shoppingNameEditText.text.toString(),
                shoppingQuantityEditText.text.toString()
            )
            // data.putInt(MainFragment.KEY_RESULT_TYPE, MainFragment.RESULT_TYPE_OK)
            val position = arguments?.getInt(KEY_POSITION) ?: -1
            data.putInt(KEY_POSITION, position)
            data.putSerializable(KEY_SHOPPING, shopping)
            parentFragmentManager.setFragmentResult(REQUEST_KEY_ADD_FRAGMENT, data)
        }
        parentFragmentManager.popBackStack()
    }
}
}