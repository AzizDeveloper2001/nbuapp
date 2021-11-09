package uz.pdp.appnbu.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import uz.pdp.appnbu.Adapters.AllCurrenciesAdapter
import uz.pdp.appnbu.R
import uz.pdp.appnbu.Utils.MySharedPreference
import uz.pdp.appnbu.ViewModel.MyViewModel
import uz.pdp.appnbu.databinding.FragmentAllCurrenciesBinding
import uz.pdp.appnbu.models.CurrencyModel
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import android.R.menu

import androidx.core.content.ContextCompat.getSystemService

import android.app.SearchManager
import android.content.Context
import androidx.core.content.ContextCompat


class AllCurrenciesFragment(val opencalcpage: Opencalcpage) : Fragment(), SearchView.OnQueryTextListener {


    lateinit var binding:FragmentAllCurrenciesBinding
    lateinit var allCurrenciesAdapter: AllCurrenciesAdapter
    lateinit var list:ArrayList<CurrencyModel>
    lateinit var mySharedPreference: MySharedPreference
    lateinit var myViewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAllCurrenciesBinding.inflate(inflater,container,false)

       setHasOptionsMenu(true)
        mySharedPreference=MySharedPreference.getinstance(requireContext())
        myViewModel=ViewModelProvider(this)[MyViewModel::class.java]
        list=ArrayList()
        allCurrenciesAdapter= AllCurrenciesAdapter(list,object :AllCurrenciesAdapter.onCalculatorClick{
            override fun onclick(currencyModel: CurrencyModel) {
                mySharedPreference.putsharedprefence(currencyModel.code)
                opencalcpage.oncalcpage()
            }

        })
        binding.apply {
           rv.adapter=allCurrenciesAdapter
            myViewModel.getretrofitlist(requireContext()).observe(viewLifecycleOwner, Observer{
                 list.addAll(it)
                allCurrenciesAdapter.notifyDataSetChanged()
            })
        }
        return binding.root
    }


    override fun onResume() {
        list.clear()
        myViewModel.getretrofitlist(requireContext()).observe(viewLifecycleOwner, Observer{
            list.addAll(it)

            allCurrenciesAdapter.updatelist(list)
        })
        super.onResume()
    }

    interface Opencalcpage{
        fun oncalcpage()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        myViewModel.getretrofitlist(requireContext()).observe(viewLifecycleOwner,Observer{
            var text = p0?.lowercase()
            var list=ArrayList<CurrencyModel>()
            for (cmodel in it) {
                if(cmodel.code.toLowerCase().contains(text.toString())){
                    list.add(cmodel)

                }

            }
            allCurrenciesAdapter.updatelist(list)
        })
     return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchmenu,menu)
        val menuitem=menu?.findItem(R.id.search_option)
        var searchview=menuitem.actionView as SearchView


        var  searchAutoComplete =
        searchview.findViewById(androidx.appcompat.R.id.search_src_text) as  SearchView.SearchAutoComplete
        searchAutoComplete.setTextColor(Color.parseColor("#000000"))


        val searchIcon: ImageView =
            searchview.findViewById(androidx.appcompat.R.id.search_button)
        searchIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.search
            )
        )
        val cancelicon: ImageView =
            searchview.findViewById(androidx.appcompat.R.id.search_close_btn)
        cancelicon.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.ic_baseline_clear_24
            )
        )

        searchview.setOnQueryTextListener(this)


        super.onCreateOptionsMenu(menu, inflater)
    }
}