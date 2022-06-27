package com.example.rpcollegemobile

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rpcollegemobile.databinding.FragmentMainEventBinding
import com.example.rpcollegemobile.itemEvent.Event
import com.example.rpcollegemobile.itemEvent.EventAdapter


class MainEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_event, container, false)
    }

    private lateinit var searchView : SearchView;

    private lateinit var eventAdapter: EventAdapter
    private var viewBinding: FragmentMainEventBinding? = null
    private lateinit var viewModel: EventViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //изменить тулбар сверху
        val window: Window = requireActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(activity?.getResources()!!.getColor(R.color.KhakiWeb))





        bindViewModel()
        eventAdapter = EventAdapter(::onRecyclerViewItemClick)
        viewBinding = FragmentMainEventBinding.bind(view)
        viewBinding?.apply {

            mainFragmentToolbar.title = "Мероприятия"

            //toolbarText
            val mainFragmentToolbar =
                requireView().findViewById<androidx.appcompat.widget.Toolbar>(R.id.mainFragmentToolbar)
           // mainFragmentToolbar.setOnMenuItemClickListener(onMenuItemClick)

            //search
            searchView = mainFragmentToolbar.menu.findItem(R.id.searchAction).actionView as androidx.appcompat.widget.SearchView
            //searchView.setOnQueryTextListener(onSearchTextListener)


            recyclerViewMainEvent.adapter = eventAdapter

            if (resources.configuration.orientation != Configuration.ORIENTATION_PORTRAIT)
            {
                recyclerViewMainEvent.layoutManager = StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL)
            } else
            {
                recyclerViewMainEvent.layoutManager = StaggeredGridLayoutManager(1,
                    StaggeredGridLayoutManager.VERTICAL)
            }

            recyclerViewMainEvent.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                ).apply {
                    setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.shape_item_event)!!)
                }
            )
        }

        viewModel.loadEvent(childFragmentManager)
    }

    private fun onRecyclerViewItemClick(position: Int){
        (activity as MainActivity).showFragment(PagerItemEventFragment.newInstance(viewModel.event.value?.get(position)!!))
    }

    private fun bindViewModel() {
        viewModel = EventViewModel()
        viewModel.event.observe(viewLifecycleOwner, ::updateEvent)
        viewModel.eventIsLoading.observe(viewLifecycleOwner, ::updateLoadingState)
    }
    private fun updateEvent(event: List<Event>){
        eventAdapter.items = event
    }

    private fun updateLoadingState(isLoading: Boolean){

    }


    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainEventFragment()
    }

}